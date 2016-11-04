package gavin.app;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.Timer;
import java.util.TimerTask;

import gavin.model.BaseUser;
import gavin.util.FileUtils;

@SuppressLint("NewApi")
public class BaseApplication extends Application
{
    private static final String TAG = BaseApplication.class.getName();

    public boolean wasInBackground;

    public void applicationWillEnterForeground()
    {
        Log.d(TAG, "applicationWillEnterForeground()");
    }

    public void applicationDidEnterBackground()
    {
        Log.d(TAG, "applicationDidEnterBackground()");
    }

    private static BaseApplication instance;

    // 检测Application处于前台，还是处于后台
    // 下面代码适用于Android4.0及以上版本
    private MemoryCallbacks mMemoryCallbacks;

    @Override
    public void onCreate()
    {
        super.onCreate();

        instance = this;

        wasInBackground = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)
        {
            mMemoryCallbacks = new MemoryCallbacks();
            mMemoryCallbacks.application = BaseApplication.this;
            registerComponentCallbacks(mMemoryCallbacks);
            //unregisterComponentCallbacks(mMemoryCallbacks);
        }

        initImageLoader(getApplicationContext());
    }

    // 检测Application处于前台，还是处于后台
    // 下面代码适用于Android4.0以下版本，不包含4.0
    // Ref: http://stackoverflow.com/questions/4414171/how-to-detect-when-an-android-app-goes-to-the-background-and
    // -come-back-to-the-fo
    private Timer mActivityTransitionTimer;
    private TimerTask mActivityTransitionTimerTask;
    private final long MAX_ACTIVITY_TRANSITION_TIME_MS = 2000;

    protected void startActivityTransitionTimer()
    {
        this.mActivityTransitionTimer = new Timer();
        this.mActivityTransitionTimerTask = new TimerTask()
        {
            public void run()
            {
                BaseApplication.this.wasInBackground = true;
            }
        };

        this.mActivityTransitionTimer.schedule(mActivityTransitionTimerTask, MAX_ACTIVITY_TRANSITION_TIME_MS);
    }

    protected void stopActivityTransitionTimer()
    {
        if (this.mActivityTransitionTimerTask != null)
        {
            this.mActivityTransitionTimerTask.cancel();
        }

        if (this.mActivityTransitionTimer != null)
        {
            this.mActivityTransitionTimer.cancel();
        }

        this.wasInBackground = false;
    }

    public static <T extends BaseApplication> T getInstance()
    {
        return (T)instance;
    }

    private BaseUser user;
    public <T extends BaseUser> T getUser(Class<T> classOfT)
    {
        if (user == null || !classOfT.isInstance(user))
        {
            String content = FileUtils.readFile(getApplicationContext(), "app_user.json");
            user = new Gson().fromJson(content, classOfT);
            user = FileUtils.readObjectFromJsonFile(getApplicationContext(), "app_user.json", classOfT);
        }

        return (T)user;
    }

    public <T extends BaseUser> void setUser(T user)
    {
        this.user = user;
        FileUtils.writeObjectToJsonFile(getApplicationContext(), "app_user.json", user);
    }

    public static void initImageLoader(Context context)
    {
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
        .cacheInMemory(true)
        .cacheOnDisk(true)
        .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .defaultDisplayImageOptions(defaultOptions)
                .build();

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }
}
