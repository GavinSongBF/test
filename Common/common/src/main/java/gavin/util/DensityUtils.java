package gavin.util;

import gavin.app.BaseApplication;

public class DensityUtils
{
    /**
     * dip转换像素px
     */
    public static int dip2px(float dpValue)
    {
        try
        {
            final float scale = BaseApplication.getInstance().getResources().getDisplayMetrics().density;
            return (int) (dpValue * scale + 0.5f);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return (int) dpValue;
    }

    /**
     * 像素px转换为dip
     */
    public static int px2dip(float pxValue)
    {
        try
        {
            final float scale = BaseApplication.getInstance().getResources().getDisplayMetrics().density;
            return (int) (pxValue / scale + 0.5f);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return (int) pxValue;
    }
}
