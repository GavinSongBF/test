package gavin.app;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
//import com.umeng.analytics.MobclickAgent;

import gavin.common.R;

public class BaseFragmentActivity extends FragmentActivity
{
    private static final String TAG = BaseActivity.class.getName();

    private Toast mToast;

    public boolean isDestroyed = false;

    protected ImageLoader imageLoader = ImageLoader.getInstance();

    protected Context context;

    protected ProgressDialog loadingProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        context = this;
        isDestroyed = false;
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        BaseApplication application = (BaseApplication) this.getApplication();
        if (application.wasInBackground)
        {
            application.applicationWillEnterForeground();
        }

        // Android 4.0以下的方案
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH)
        {
            application.stopActivityTransitionTimer();
        }
        //umeng统计时常开始
//        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause()
    {
        super.onPause();

        // Android 4.0以下的方案
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH)
        {
            ((BaseApplication) this.getApplication())
                    .startActivityTransitionTimer();
        }

        //umeng统计时常结束
//        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy()
    {
        isDestroyed = true;
        if (mToast != null)
        {
            mToast.cancel();
        }
        super.onDestroy();
    }

    protected void goBack(View v)
    {
        finish();
    }

    public <T extends View> T findView(int resId)
    {
        View v = findViewById(resId);
        if(v == null) return null;
        return (T) v;
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
        if (isDestroyed)
        {
            return;
        }

        hiddenKeyboard();

        if (mToast == null)
        {
            mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        }
        mToast.setGravity(gravity, 0, 0);
        mToast.setText(message);
        mToast.show();
    }

    public void hiddenKeyboard()
    {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindow().getDecorView()
                                            .getApplicationWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public void showKeyboard(View view)
    {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.RESULT_UNCHANGED_SHOWN);
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
        if (message == null)
        {
            message = getString(R.string.totem_loading);
        }

        if (loadingProgress == null)
        {
            loadingProgress = new ProgressDialog(this);
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