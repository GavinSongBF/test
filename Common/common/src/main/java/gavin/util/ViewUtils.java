package gavin.util;

import android.os.Build;
import android.view.View;

import java.util.concurrent.atomic.AtomicInteger;


public class ViewUtils
{
    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    /**
     * 生成一个随机的viewId，一般用法: view.setId(ViewUtils.generateViewId());
     * This value will not collide with ID values generated at build time by aapt for R.id.
     *
     * @return a generated ID value
     */
    public static int generateViewId()
    {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1)
        {
            for (;;) {
                final int result = sNextGeneratedId.get();
                // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
                int newValue = result + 1;
                if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
                if (sNextGeneratedId.compareAndSet(result, newValue)) {
                    return result;
                }
            }
        }
        else
        {
            return View.generateViewId();
        }
    }
}
