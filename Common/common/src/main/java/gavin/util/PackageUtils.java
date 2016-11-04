package gavin.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

/**
 * Created by Ma on 2015/2/17.
 */
public class PackageUtils
{
   /**
     * 获得版本编号
     *
     * @return
     */
    public static int getVersionCode(Context context)
    {
        int versionCode = 0;
        try
        {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            versionCode = info.versionCode;

        }
        catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 获得版本名称
     *
     * @return
     */
    public static String getVersionName(Context context)
    {
        String versionName = "UNKNOWN";
        try
        {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            versionName = info.versionName;

        }
        catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * 获得版本名称
     *
     * @return
     */
    public static String getMetaData(Context context, String key)
    {
        String value = null;
        try
        {
            ApplicationInfo app = context.getPackageManager().getApplicationInfo(context.getPackageName(),PackageManager.GET_META_DATA);
            Bundle bundle = app.metaData;
            value = bundle.getString(key);
        }
        catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        return value;
    }



    /**
     * 比较版本大小
     *
     * @param context
     * @param newestVersion
     * @return
     */
    public static boolean isNewestVersion(Context context, String newestVersion)
    {
        if (newestVersion != null)
        {
            String currentVersion = getVersionName(context);

            LogUtils.d("currentVersion = " + currentVersion);
            LogUtils.d("newestVersion = " + newestVersion);

            try
            {
                String[] current = currentVersion.split("\\.");
                String[] newest = newestVersion.split("\\.");

                //两个版本号的最小长度
                int length = current.length < newest.length ? current.length : newest.length;

                //在两个版本号最小长度内做比较
                for (int i = 0; i < length; i++)
                {
                    int currentInt = Integer.valueOf(current[i]);
                    int newestInt = Integer.valueOf(newest[i]);

                    if (currentInt == newestInt)
                    {
                        continue;
                    }
                    else if(currentInt > newestInt)
                    {
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                }

                if (newest.length > current.length)
                {
                    return false;
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        return true;
    }
}
