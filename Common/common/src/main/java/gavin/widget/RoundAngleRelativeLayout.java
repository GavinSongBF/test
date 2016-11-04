package gavin.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;

import java.lang.ref.SoftReference;

import gavin.common.R;
import gavin.util.DensityUtils;

/**
 * 圆角relativelayout
 * 
 * @author baiwenlong
 * 
 */
public class RoundAngleRelativeLayout extends HighlightRelativeLayout {

	private Paint paint;
	private Paint paint2;

	private int width;
	private int height;

	private int angleSize;

	private SoftReference<Bitmap> sr;

	public RoundAngleRelativeLayout(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs);
	}

	public RoundAngleRelativeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public RoundAngleRelativeLayout(Context context) {
		super(context);
		init(context, null);
	}

	private void init(Context context, AttributeSet attrs) {
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(Color.WHITE);
		paint.setAntiAlias(true);
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));

		paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint2.setXfermode(null);

		angleSize = DensityUtils.dip2px(5);
		if (attrs != null) {
			TypedArray a = getContext().obtainStyledAttributes(attrs,
					R.styleable.round_angle);

			angleSize = a.getDimensionPixelSize(
					R.styleable.round_angle_round_angle_size, angleSize);

			a.recycle();
		}

	}

	@Override
	public void draw(Canvas canvas) {

		Bitmap bmpCache = null;
		if (sr != null) {
			bmpCache = sr.get();
		}

		if (bmpCache == null || bmpCache.isRecycled()
				|| bmpCache.getWidth() != width
				|| bmpCache.getHeight() != height) {
			if (bmpCache != null) {
				bmpCache.recycle();
			}
			bmpCache = Bitmap.createBitmap(width, height,
					Bitmap.Config.ARGB_8888);
			sr = new SoftReference<Bitmap>(bmpCache);
		} else {
			bmpCache.eraseColor(0);
		}

		Canvas canvas2 = new Canvas(bmpCache);
		super.draw(canvas2);
		drawLiftUp(canvas2);
		drawRightUp(canvas2);
		drawLiftDown(canvas2);
		drawRightDown(canvas2);
		canvas.drawBitmap(bmpCache, 0, 0, paint2);

	}

	private void drawLiftUp(Canvas canvas) {
		Path path = new Path();
		path.moveTo(0, angleSize);
		path.lineTo(0, 0);
		path.lineTo(angleSize, 0);
		path.arcTo(new RectF(0, 0, angleSize * 2, angleSize * 2), -90, -90);
		path.close();
		canvas.drawPath(path, paint);
	}

	private void drawLiftDown(Canvas canvas) {
		Path path = new Path();
		path.moveTo(0, height - angleSize);
		path.lineTo(0, height);
		path.lineTo(angleSize, height);
		path.arcTo(new RectF(0, height - angleSize * 2, 0 + angleSize * 2,
				getHeight()), 90, 90);
		path.close();
		canvas.drawPath(path, paint);
	}

	private void drawRightDown(Canvas canvas) {
		Path path = new Path();
		path.moveTo(width - angleSize, height);
		path.lineTo(width, height);
		path.lineTo(width, height - angleSize);
		path.arcTo(new RectF(width - angleSize * 2, height - angleSize * 2,
				width, height), 0, 90);
		path.close();
		canvas.drawPath(path, paint);
	}

	private void drawRightUp(Canvas canvas) {
		Path path = new Path();
		path.moveTo(width, angleSize);
		path.lineTo(width, 0);
		path.lineTo(width - angleSize, 0);
		path.arcTo(
				new RectF(width - angleSize * 2, 0, width, 0 + angleSize * 2),
				-90, 90);
		path.close();
		canvas.drawPath(path, paint);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		width = w;
		height = h;
	}

	public int getAngleSize() {
		return angleSize;
	}

	public void setAngleSize(int angleSize) {
		this.angleSize = angleSize;
	}

}
