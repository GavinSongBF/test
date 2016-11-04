package gavin.app;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import gavin.common.R;
import gavin.util.FileUtils;
import gavin.util.JSONParser;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class BaseFragment extends Fragment
{
    protected Context context;
    private boolean isActive;
    private Toast mToast;

    protected ImageLoader imageLoader = ImageLoader.getInstance();

    protected ProgressDialog loadingProgress;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null)
        {
            getFragmentManager().beginTransaction().remove(this).commit();
        }
    }

    public boolean onBackPressed()
    {
        return false;
    }

    public void onButtonPressed(Uri uri)
    {

    }

    public BaseFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        context = getActivity();
        isActive = true;
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        isActive = false;
    }

    /**
     * 读取文本数据
     *
     * @param fileName
     *            文件名
     * @return String, 读取到的文本内容，失败返回null
     */
    public String readFile(String fileName)
    {
        return FileUtils.readFile(getActivity(), fileName);
    }

    /**
     * 存储文本数据，一般用作网络数据的硬盘缓存
     *
     * @param fileName
     *            文件名，要在系统内保持唯一
     * @param content
     *            文本内容
     * @return boolean 存储成功的标志
     */
    public boolean writeFile(String fileName, String content)
    {
        return FileUtils.writeFile(getActivity(), fileName, content);
    }

    /**
     * 读取数据对象数组
     * 已废弃，请使用：readListFromJsonFile
     * @param fileName
     *            文件名
     * @param classOfT
     *            对象类别，例如Order.class
     * @return List, 读取到的数据对象数组，失败返回null
     */
    @Deprecated
    public <T extends Parcelable> List<T> readParcelableList(String fileName, Class<T> classOfT)
    {
        return FileUtils.readParcelableList(getActivity(), fileName, classOfT);
    }

    /**
     * 读取数据对象
     * 已废弃，请使用：readObjectFromJsonFile
     * @param fileName
     *            文件名
     * @param classOfT
     *            对象类别，例如Order.class
     * @return List, 读取到的数据对象数组，失败返回null
     */
    @Deprecated
    public <T extends Parcelable> T readParcelable(String fileName, Class<T> classOfT)
    {
        return FileUtils.readParcelable(getActivity(), fileName, classOfT);
    }

    /**
     * 存储文本数据，一般用作网络数据的硬盘缓存
     * 已废弃，请使用：writeListToJsonFile
     * @param fileName
     *            文件名，要在系统内保持唯一
     * @param list
     *            数组对象，对象需要实现Parcelable接口
     * @return boolean 存储成功的标志
     */
    @Deprecated
    public <T extends Parcelable> boolean writeParcelableList(String fileName, List<T> list)
    {
        return FileUtils.writeParcelableList(getActivity(), fileName, list);
    }

    /**
     * 存储文本数据，一般用作网络数据的硬盘缓存
     * 已废弃，请使用：writeObjectToJsonFile
     * @param fileName
     *            文件名，要在系统内保持唯一
     * @param parcelObject
     *            实现了Parcelable接口的对象
     * @return boolean 存储成功的标志
     */
    @Deprecated
    public <T extends Parcelable> boolean writeParcelable(String fileName, T parcelObject)
    {
        return FileUtils.writeParcelable(getActivity(), fileName, parcelObject);
    }

    /**
     * 读取数据对象数组
     *
     * @param fileName
     *            文件名
     * @param classOfT
     *            对象类别，例如Order.class
     * @return List<T>, 读取到的数据对象数组，失败返回null
     */
    public <T> List<T> readListFromJsonFile(String fileName, Class<T> classOfT)
    {
        List<T> list = null;
        String content = readFile(fileName);
        if (content != null && content.trim().length() > 0)
        {
            list = JSONParser.listFromJson(content, classOfT);
        }
        return list;
    }

    /**
     * 读取数据对象
     *
     * @param fileName
     *            文件名
     * @param classOfT
     *            对象类别，例如Order.class
     * @return T, 读取到的POJO对象，失败返回null
     */
    public <T> T readObjectFromJsonFile(String fileName, Class<T> classOfT)
    {
        T object = null;
        String content = readFile(fileName);
        if (content != null && content.trim().length() > 0)
        {
            object = new Gson().fromJson(content, classOfT);
        }

        return object;
    }

    /**
     * 存储文本数据，一般用作网络数据的硬盘缓存
     *
     * @param fileName
     *            文件名，要在系统内保持唯一
     * @param list
     *            数组对象，包含任意的POJO对象
     * @return boolean 存储成功的标志
     */
    public boolean writeListToJsonFile(String fileName, List list)
    {
        return writeFile(fileName, new Gson().toJson(list));
    }

    /**
     * 存储文本数据，一般用作网络数据的硬盘缓存
     *
     * @param fileName
     *            文件名，要在系统内保持唯一
     * @param object
     *            任意POJO对象
     * @return boolean 存储成功的标志
     */
    public boolean writeObjectToJsonFile(String fileName, Object object)
    {
        return writeFile(fileName, new Gson().toJson(object));
    }

    /**
     * 显示Toast消息
     *
     * @param message
     */
    public void showToast(String message)
    {
        this.showToast(message, Gravity.CENTER);
    }

    /**
     * 显示Toast消息
     *
     * @param messageResourceID
     */
    public void showToast(int messageResourceID)
    {
        showToast(getString(messageResourceID), Gravity.CENTER);
    }

    public void showToast(String message, int gravity)
    {
        if (isActive == false)
        {
            return;
        }

        hiddenKeyboard();

        if (mToast == null)
        {
            mToast = Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT);
        }
        mToast.setGravity(gravity, 0, 0);
        mToast.setText(message);
        mToast.show();
    }

    public void hiddenKeyboard()
    {
        Activity activity = getActivity();
        if(isActive && activity != null)
        {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView()
                                                .getApplicationWindowToken(),
                                        InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public void showKeyboard(View view)
    {
        Activity activity = getActivity();
        if(isActive && activity != null)
        {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.RESULT_UNCHANGED_SHOWN);
        }
    }

    /**
     * 显示加载中对话框
     * @return
     */
    public void showLoading()
    {
        showLoading(getString(R.string.totem_loading));
    }

    /**
     * 显示加载中对话框
     *
     * @param messageID
     *            默认为“加载中...”
     * @return
     */
    public void showLoading(int messageID)
    {
        showLoading(null, getString(messageID));
    }

    /**
     * 显示加载中对话框
     *
     * @param message
     *            默认为“加载中...”
     * @return
     */
    public void showLoading(String message)
    {
        showLoading(null, message);
    }

    /**
     * 显示加载中对话框
     *
     * @param titleID
     * @param messageID
     *            默认为“加载中...”
     * @return
     */
    public void showLoading(int titleID, int messageID)
    {
        showLoading(getString(titleID), getString(messageID));
    }

    /**
     * 显示加载中对话框
     *
     * @param title
     * @param message
     *            默认为“加载中...”
     * @return
     */
    public void showLoading(String title, String message)
    {
        if(isActive == false)
        {
            return;
        }

        if (message == null)
        {
            message = getString(R.string.totem_loading);
        }

        if (loadingProgress == null)
        {
            loadingProgress = new ProgressDialog(getActivity());
            loadingProgress.setTitle(title);
            loadingProgress.setMessage(message);
            loadingProgress.show();
            loadingProgress.setCancelable(true);// 设置进度条是否可以按退回键取消

            // 设置点击进度对话框外的区域对话框不消失
            loadingProgress.setCanceledOnTouchOutside(false);
        }
    }

    /**
     * 隐藏加载框
     */
    public void hiddenLoadingView()
    {
        if (loadingProgress != null)
        {
            loadingProgress.dismiss();
            loadingProgress = null;
        }
    }
}