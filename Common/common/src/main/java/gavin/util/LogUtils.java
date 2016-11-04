package gavin.util;

import android.util.Log;

public class LogUtils
{
    public static boolean showLog = true;

    public static final String TAG = "TOTEM";
    
    public static void v(String logText)
    {
        if (showLog)
        {
            Log.v(TAG, String.valueOf(logText));
        }
    }

    public static void d(String logText)
    {
        if (showLog)
        {
            Log.d(TAG, String.valueOf(logText));
        }
    }

    public static void i(String logText)
    {
        if (showLog)
        {
            Log.i(TAG, String.valueOf(logText));
        }
    }
    
    public static void w(String logText)
    {
        if (showLog)
        {
            Log.w(TAG, String.valueOf(logText));
        }
    }

    public static void e(String logText)
    {
        if (showLog)
        {
            Log.e(TAG, String.valueOf(logText));
        }
    }
    
    public static void v(String tag, String logText)
    {
        if (showLog)
        {
            Log.v(tag, logText);
        }
    }

    public static void d(String tag, String logText)
    {
        if (showLog)
        {
            Log.d(tag, logText);
        }
    }

    public static void i(String tag, String logText)
    {
        if (showLog)
        {
            Log.i(tag, logText);
        }
    }

    public static void w(String tag, String logText)
    {
        if (showLog)
        {
            Log.w(tag, logText);
        }
    }

    public static void e(String tag, String logText)
    {
        if (showLog)
        {
            Log.e(tag, logText);
        }
    }

    public static void d(Class<?> c, String logText)
    {
        if (showLog)
        {
            Log.d(TAG, "[" + c.getSimpleName() + "]" + logText);
        }
    }

    public static void d(Object o, String logText)
    {
        if (showLog)
        {
            Log.d(TAG, "[" + o.getClass().getSimpleName() + "]" + logText);
        }
    }
}
