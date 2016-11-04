package gavin.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import gavin.common.R;
import gavin.util.DensityUtils;
import gavin.widget.PageIndicator;

public abstract class GuideBaseActivity extends FragmentActivity
{
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;

    static int resources[] = {R.mipmap.ic_launcher, R.mipmap.ic_launcher};

    public abstract int[] getGuideResourcesIds();
    public abstract void guideFinishByUser();

    protected PageIndicator pageIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.totem_guide);

        resources = getGuideResourcesIds();

        pageIndicator = (PageIndicator) findViewById(R.id.page_indicator);
        pageIndicator.setPadding(0);
        pageIndicator.setIndicatorColor(0xFFFFFF);
        pageIndicator.setIndicatorCount(resources.length);
        pageIndicator.setIndicatorSize(DensityUtils.dip2px(10));
        pageIndicator.setIndicatorSpacing(DensityUtils.dip2px(8));
        pageIndicator.moveIndicator(0);

        mPager = (ViewPager) findViewById(R.id.view_pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener()
        {
            int oldState;
            int oldPosition;

            @Override
            public void onPageSelected(int position)
            {
                pageIndicator.moveIndicator(position);
                oldPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state)
            {
                if(oldState == 1 && state == 0 && oldPosition == resources.length - 1)
                {
                    guideFinishByUser();
                }

                oldState = state;
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {
            }
        });
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter
    {
        public ScreenSlidePagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
            return ScreenSlidePageFragment.create(position);
        }

        @Override
        public int getCount()
        {
            return resources.length;
        }
    }

    public static class ScreenSlidePageFragment extends Fragment
    {
        public static final String ARG_PAGE = "page";
        private int mPageNumber;

        public static ScreenSlidePageFragment create(int pageNumber)
        {
            ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_PAGE, pageNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public ScreenSlidePageFragment()
        {
        }

        @Override
        public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            mPageNumber = getArguments().getInt(ARG_PAGE);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.totem_guide_item, container, false);
            if(mPageNumber >= 0 && mPageNumber < resources.length)
            {
                ((ImageView) rootView.findViewById(R.id.image)).setImageResource(resources[mPageNumber]);
            }
            return rootView;
        }

        public int getPageNumber()
        {
            return mPageNumber;
        }
    }
}
