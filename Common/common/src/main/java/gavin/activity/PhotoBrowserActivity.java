package gavin.activity;


import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

import java.util.ArrayList;
import java.util.List;

import gavin.app.BaseActivity;
import gavin.widget.HackyViewPager;
import uk.co.senab.photoview.PhotoView;

/**
 * Lock/Unlock button is added to the ActionBar.
 * Use it to temporarily disable ViewPager navigation in order to correctly interact with ImageView by gestures.
 * Lock/Unlock state of ViewPager is saved and restored on configuration changes.
 * <p/>
 * Julia Zudikova
 */

public class PhotoBrowserActivity extends BaseActivity
{
    private static final String ISLOCKED_ARG = "isLocked";

    private ViewPager mViewPager;
    private SamplePagerAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        mViewPager = new HackyViewPager(this);
        mViewPager.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        setContentView(mViewPager);


        mAdapter = new SamplePagerAdapter();
        ArrayList<String> photos = getIntent().getStringArrayListExtra("photos");
        mAdapter.drawables = photos;
        mViewPager.setAdapter(mAdapter);

        if (savedInstanceState != null)
        {
            boolean isLocked = savedInstanceState.getBoolean(ISLOCKED_ARG, false);
            ((HackyViewPager) mViewPager).setLocked(isLocked);
        }
    }

    class SamplePagerAdapter extends PagerAdapter
    {
        private List<String> drawables;

        @Override
        public int getCount()
        {
            return drawables.size();
        }

        @Override
        public View instantiateItem(ViewGroup container, int position)
        {
            PhotoView photoView = new PhotoView(container.getContext());

            Log.d("PhotoBrowser", "position=" + position + ", url =" + drawables.get(position));
            imageLoader.displayImage(drawables.get(position), photoView);

            // Now just add PhotoView to ViewPager and return it
            container.addView(photoView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }


    private void toggleViewPagerScrolling() {
        if (isViewPagerActive()) {
            ((HackyViewPager) mViewPager).toggleLock();
        }
    }

    private void toggleLockBtnTitle() {
        boolean isLocked = false;
        if (isViewPagerActive()) {
            isLocked = ((HackyViewPager) mViewPager).isLocked();
        }
    }

    private boolean isViewPagerActive() {
        return (mViewPager != null && mViewPager instanceof HackyViewPager);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (isViewPagerActive()) {
            outState.putBoolean(ISLOCKED_ARG, ((HackyViewPager) mViewPager).isLocked());
        }
        super.onSaveInstanceState(outState);
    }

}
