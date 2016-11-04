package gavin.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Scroller;

public class HeaderScrollView extends StickyScrollView {

	private int len = 30;
	private Scroller scroller;
	private boolean scrollerType = false;
	private View view;
	int primaryY;
	float startY;
	int primaryH;
	int bottom;
	int srcBottom;
	boolean flag = true;

	public HeaderScrollView(Context context) {
		super(context);
		init(context);
	}

	public HeaderScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public HeaderScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context) {
		scroller = new Scroller(context);

	}

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		
		float currentY = ev.getY();
		switch (ev.getAction()&MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			startY = currentY;
			bottom = view.getBottom();
			if (flag) {
				srcBottom = bottom;
				flag = false;
			}
			break;
		case MotionEvent.ACTION_MOVE:

			if (view.isShown() && view.getTop() >= 0) {

				int y = (int) (srcBottom + (currentY - startY) / 5.0f);
				if (y < view.getBottom() + len && y >= srcBottom) {
					view.setLayoutParams(new RelativeLayout.LayoutParams(view.getWidth(), y));
				}
				scrollerType = false;
			}
			break;
		case MotionEvent.ACTION_UP:
			
			scrollerType = true;

			scroller.startScroll((view.getLeft() + view.getRight()) / 2, view.getBottom(), 0 - view.getLeft() * 2,
					srcBottom - view.getBottom(), 160);
			invalidate();
			break;

		}
		
		return super.dispatchTouchEvent(ev);
	}
	@Override
	public void computeScroll() {

		if (scroller.computeScrollOffset()) {
			int y = scroller.getCurrY();
			int x = scroller.getCurrX();
			if (!scroller.isFinished() && scrollerType && y >= srcBottom) {
				view.setLayoutParams(new RelativeLayout.LayoutParams(view.getWidth(), y));
			}
			invalidate();
		}
		super.computeScroll();
	}
	
}
