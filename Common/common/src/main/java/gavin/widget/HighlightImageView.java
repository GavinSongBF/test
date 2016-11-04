package gavin.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class HighlightImageView extends ImageView {

	@Override
	public void setImageDrawable(Drawable d) {
		// Replace the original background drawable (e.g. image) with a
		// LayerDrawable that
		// contains the original drawable.
		this.setBackgroundResource(0);
		HighlightDrawable layer = new HighlightDrawable(d);
		super.setImageDrawable(layer);
	}

	public HighlightImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public HighlightImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public HighlightImageView(Context context) {
		super(context);
	}
}
