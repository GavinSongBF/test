package gavin.app;

import android.annotation.SuppressLint;
import android.content.ComponentCallbacks2;
import android.content.res.Configuration;

import com.nostra13.universalimageloader.core.ImageLoader;

@SuppressLint("NewApi")
public class MemoryCallbacks implements ComponentCallbacks2
{
    protected BaseApplication application;
    
    @Override
    public void onConfigurationChanged(final Configuration newConfig)
    {
    }

    @Override
    public void onLowMemory()
    {
        ImageLoader.getInstance().clearMemoryCache();
    }

    @Override
    public void onTrimMemory(final int level)
    {
        if (level == ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN)
        {
            application.wasInBackground = true;
            application.applicationDidEnterBackground();
        }
        
        // you might as well implement some memory cleanup here and be a nice Android dev.
    }
}
