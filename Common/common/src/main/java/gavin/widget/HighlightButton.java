package gavin.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Applies a pressed state color filter or disabled state alpha for the button's
 * background drawable.
 * 
 * @author shiki
 */
public class HighlightButton extends Button
{

    public HighlightButton(Context context)
    {
        super(context);
    }

    public HighlightButton(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public HighlightButton(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
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
            super.setBackground(layer);
        }
    }
}
