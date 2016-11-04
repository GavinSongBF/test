package gavin.activity;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;

import gavin.util.ScreenUtils;

public abstract class SplashBaseActivity extends Activity
{
    public abstract void splashFinish();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    /**
     * 使用编程方式调整Splash图片
     * 优点: 独立性更强，文件少
     * 缺点: 难掌握
     */
    protected void showSplash(int resourceId, int delaySeconds)
    {
        setContentView(getContentView(resourceId));
        
        //定时进入
        new Handler().postDelayed(new Runnable()
        {
            public void run()
            {
                splashFinish();

                finish();
            }
        }, delaySeconds * 1000);
    }

    private Drawable getLayerDrawable(int resourceId)
    {
        Drawable drawable = getResources().getDrawable(resourceId);
        Drawable[] drawableArray = {drawable};
        LayerDrawable layerDrawable = new LayerDrawable(drawableArray);

        //状态栏高度设置
        int statusBarHeight = ScreenUtils.getStatusBarHeight(this);
        layerDrawable.setLayerInset(0, 0, -statusBarHeight, 0, 0);
        return layerDrawable;
    }

    private View getContentView(int resourceId)
    {
        //程序完成布局
        RelativeLayout layout = new RelativeLayout(this);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        layout.setLayoutParams(params);

        //设置背景
        Drawable drawable = getLayerDrawable(resourceId);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
        {
            layout.setBackground(drawable);
        }
        else
        {
            layout.setBackgroundDrawable(drawable);
        }

        return layout;
    }
}
