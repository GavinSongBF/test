package gavin.demo.activity;

import android.os.Bundle;
import android.os.StrictMode;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import gavin.app.BaseActivity;
import gavin.demo.R;

public class RoundCornerViewActivity extends BaseActivity
{
    private ImageView avatarView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        StrictMode.enableDefaults();

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_round_corner_view);

        avatarView = (ImageView) findViewById(R.id.imageView2);
        avatarView.setImageResource(0);
        
        
        //必须先初始化ImageLoader，参考totem.app.TTApplication.initImageLoader()
        //这里totem.app.TTApplication和AndroidManifest.xml配置中自动完成初始化工作，不需要业务人员关注

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                            .displayer(new RoundedBitmapDisplayer(30))
                            .cacheInMemory(true)
                            .cacheOnDisc(true)
                            .build();

        ImageLoader.getInstance().displayImage("http://img2.cache.netease.com/cnews/2015/2/9/201502090944224f351_550.jpg", avatarView, options);
    }
}
