package gavin.widget;


import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class HighlightLinearLayout extends LinearLayout
{
    public HighlightLinearLayout(final Context context, final AttributeSet attrs, final int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public HighlightLinearLayout(final Context context, final AttributeSet attrs)
    {
        super(context, attrs);
    }

    public HighlightLinearLayout(final Context context)
    {
        super(context);
    }
    
    @Override
    public void setBackgroundDrawable(Drawable d)
    {
        if(d == null)
        {
            super.setBackgroundDrawable(d);
        }
        else
        {
        	HighlightDrawable layer = new HighlightDrawable(d);
			layer.pressedFilter = new LightingColorFilter(
					Color.rgb(238, 238, 238), 1);
			super.setBackgroundDrawable(layer);
        }
    }


    @TargetApi(16)
    public void setBackground(Drawable d)
    {
        if(d == null)
        {
            super.setBackground(d);
        }
        else
        {
            HighlightDrawable layer = new HighlightDrawable(d);
            layer.pressedFilter = new LightingColorFilter(
                    Color.rgb(238, 238, 238), 1);
            super.setBackground(layer);
        }
    }
}
