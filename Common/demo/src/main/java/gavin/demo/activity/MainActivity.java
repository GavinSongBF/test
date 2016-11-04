package gavin.demo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import gavin.app.BaseActivity;
import gavin.demo.R;


public class MainActivity extends BaseActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void simpleList(View v)
    {
        startActivity(new Intent(this, SimpleListActivity.class));
    }

    public void roundCornerView(View v)
    {
        startActivity(new Intent(this, RoundCornerViewActivity.class));
    }

    public void highlightView(View v)
    {
        startActivity(new Intent(this, HighlightViewActivity.class));
    }

    public void customWidget(View v)
    {
        startActivity(new Intent(this, CustomWidgetActivity.class));
    }

    public void pinnedSectionList(View v)
    {
        startActivity(new Intent(context, PinnedSectionListActivity.class));
    }

    public void pullToRefreshListView(View v)
    {
        startActivity(new Intent(context, PullToRefreshListViewActivity.class));
    }

    public void choosePhoto(View v)
    {
        startActivity(new Intent(context, PhotoActivity.class));
    }

    public void httpRequest(View v)
    {
        startActivity(new Intent(context, HttpRequestActivity.class));
    }

    public void cacheData(View v)
    {
        startActivity(new Intent(context, CacheDataActivity.class));
    }
}
