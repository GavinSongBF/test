package gavin.app;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

//import org.apache.http.Header;
//import org.apache.http.HttpEntity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import gavin.common.R;
import gavin.model.AppVersion;
import gavin.net.AppVersionResponse;
import gavin.net.HttpClient;
import gavin.util.JSONParser;
import gavin.util.PackageUtils;
import gavin.util.StorageHelper;

/*
 * App版本检测与下载安装，需要下面权限
 * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
 * 
 * 如果解析包发生问题，注意保证升级前后的包名和签名是一致的，一个debug另一个release签名肯定不行
 */

public class AppInstaller
{
    private static final String TAG = AppInstaller.class.getSimpleName();
    private Context context;
    
    @SuppressWarnings("unused")
    private AppInstaller() {}
    
    public AppInstaller(Context context)
    {
        this.context = context;
    }

    /*
     * 检查最新版本
     */
    public void checkVersion(String path)
    {
        String currentVersion = PackageUtils.getVersionName(context);
        String packageName = context.getPackageName();
        RequestParams params = new RequestParams();
        params.put("package_name", packageName);
        params.put("device_type", "Android");
        params.put("version", currentVersion);

        HttpClient.post(path, params, new TextHttpResponseHandler()
        {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable)
            {
                // 什么都不做
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString)
            {
                Log.i("appinstallerresponse",responseString);
                AppVersionResponse response = JSONParser.fromJson(responseString, AppVersionResponse.class);
                if (response.isSuccess())
                {
                    showVersionInfo(response.getData());
                }
            }
        });


    }
    
    /*
     * 显示最新版本信息
     */
    private void showVersionInfo(final AppVersion version)
    {
        //最新版本，什么都不做
        if(version == null || PackageUtils.isNewestVersion(context, version.getVersion()))
        {
            return;
        }
        
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("发现新版本   V" + version.getVersion());
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setCancelable(true);
        builder.setMessage(version.getSummary());
        if(version.getForce_update() == 0)
        {
            builder.setCancelable(false);
            builder.setNeutralButton("稍后再说",new OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    dialog.cancel();
                }
            });
        }
        builder.setPositiveButton("更新版本", new OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.cancel();
                downLoadApp(version.getDownload_url());
            }
        });
        builder.show();
    }
    
    private String fileName;  //下载的文件名，默认使用包名  com.xxx.app.apk
    private File file;        //下载的文件对象，包含了文件全路径
    private ProgressDialog downLoadDialog;         //下载进度条
    private boolean useExternalStorage = false;    //是否使用外部存储，除非外部存储不可用，才使用内部存储
    
    /*
     * 下载服务器上的App
     */
    private void downLoadApp(String downLoadUrl)
    {
    	File dir;
    	
    	//支持外部存储和内部存储，优先使用内部存储
        if(new StorageHelper().isExternalStorageAvailableAndWriteable())
        {
        	//外部存储
        	dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        	useExternalStorage = true;
        }
        else
        {
        	//内部存储
        	dir = context.getFilesDir();
        }
        
        if(!dir.exists())
        {
            dir.mkdirs();
        }
        
        fileName = context.getPackageName() + ".apk";
        
        
        //准备下载文件
        file = new File(dir.getAbsolutePath(), fileName);
        if(file.exists())
        {
            file.delete();
        }
        
        try
        {
            //创建文件，设置权限
            file.createNewFile();
            file.setReadable(true);
            file.setExecutable(true, false);
        
            Log.d(TAG, fileName + " canRead() = " + file.canRead());
            Log.d(TAG, fileName + " canWrite() = " + file.canWrite());
            Log.d(TAG, fileName + " canExecute() = " + file.canExecute());
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return;
        }
        
        //显示进度
        downLoadDialog = new ProgressDialog(context);
        downLoadDialog.setTitle("下载中...");
        downLoadDialog.setIcon(R.mipmap.ic_launcher);
        downLoadDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        downLoadDialog.setCancelable(false);
        downLoadDialog.show();
        

        //开始下载
        RequestParams params = new RequestParams();
        AsyncHttpClient httpClient = new AsyncHttpClient();
        httpClient.get(downLoadUrl, params, new FileAsyncHttpResponseHandler(file)
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, File file)
            {
                downLoadDialog.dismiss();
                installApk();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file)
            {
                downLoadDialog.dismiss();
                Toast.makeText(context, "新版本下载失败: " + throwable.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }

            @SuppressWarnings("resource")
			@Override
            protected byte[] getResponseData(HttpEntity entity) throws IOException
            {
                if (entity != null)
                {
                    InputStream instream = entity.getContent();
                    long contentLength = entity.getContentLength();
                    FileOutputStream buffer;
                    if(useExternalStorage)
                    {
                    	buffer = new FileOutputStream(getTargetFile());
                    }
                    else
                    {
                    	buffer = context.openFileOutput(fileName, Context.MODE_WORLD_READABLE);
                    }
                    
                    if (instream != null)
                    {
                        try
                        {
                            byte[] tmp = new byte[BUFFER_SIZE];
                            int l, count = 0;
                            
                            downLoadDialog.setMax((int) (contentLength));
                            
                            // do not send messages if request has been cancelled
                            while ((l = instream.read(tmp)) != -1 && !Thread.currentThread().isInterrupted()) {
                                count += l;
                                buffer.write(tmp, 0, l);
                                downLoadDialog.setProgress((int) (count));
                                sendProgressMessage(count, (int) contentLength);
                            }
                        } finally {
                            AsyncHttpClient.silentCloseInputStream(instream);
                            buffer.flush();
                            AsyncHttpClient.silentCloseOutputStream(buffer);
                        }
                    }
                }
                return null;
            }
        });
    }
    
    /*
     * 安装Android App
     */
    private void installApk()
    {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
