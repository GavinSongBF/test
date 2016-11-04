package gavin.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

public class ScreenUtils
{
    public static int getStatusBarHeight(Context context)
    {
        int result = 0;
        Resources resources = context.getResources();
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0)
        {
            result = resources.getDimensionPixelSize(resourceId);
        }
        return result;
    }

    //吴祥的华为手机上状态栏高度不对
    public static int getStatusBarHeightUseConstant(Context context)
    {
        return (int) Math.ceil(25 * context.getResources().getDisplayMetrics().density);
    }

    public static int getNavigationBarHeight(Context context)
    {
        int result = 0;
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0)
        {
            result = resources.getDimensionPixelSize(resourceId);
        }
        return result;
    }
    
    public static Point getScreenSize(Context context)
    {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size;
    }
}
