package gavin.demo.util;

import android.util.Log;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.io.InputStream;

import cz.msebera.android.httpclient.Header;
import gavin.app.BaseActivity;
import gavin.demo.Constant;
import gavin.net.HttpClient;

public class ImageUploader
{
    public static void uploadImage(final BaseActivity activity, String fileName, final int viewId)
    {
        RequestParams params = new RequestParams();
        try
        {
            InputStream inputStream = activity.openFileInput(fileName);
            params.put("image", inputStream, fileName, "image/jpeg");
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return;
        }

        HttpClient.post(Constant.PATH_IMAGE_UPLOAD, params, new TextHttpResponseHandler()
        {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable)
            {
                Log.d("demo", "responseString=" + responseString + "  throwable=" + throwable);
                activity.showToast("上传失败");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString)
            {
                Log.d("demo", "responseString=" + responseString);
            }
        });
    }
}

