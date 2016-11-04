package gavin.demo.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import java.util.Date;

import cz.msebera.android.httpclient.Header;
import gavin.app.BaseActivity;
import gavin.demo.Constant;
import gavin.demo.R;
import gavin.net.HttpClient;
import gavin.net.PagingResponse;
import gavin.widget.PullToRefreshListView;

public class PullToRefreshListViewActivity extends BaseActivity implements AdapterView.OnItemClickListener
{
    int pageIndex = 1;
    Date refreshDate;
    PullToRefreshListView listView;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_to_refresh);

        listView  = findView(R.id.list_view);
        listView.setPullToRefreshListener(dataLoader);
        listView.setOnItemClickListener(this);
        listView.setCanRefresh(true);
        listView.setCanLoadMore(true);
    }

    PullToRefreshListView.PullToRefreshListener dataLoader = new PullToRefreshListView.PullToRefreshListener()
    {
        @Override
        public void onRefresh()
        {
            loadData(pageIndex);
        }

        @Override
        public void onLoadMore()
        {
            loadData(pageIndex+1);//加载下一页
        }
    };

    private void loadData(final int newPageIndex)
    {
        RequestParams params = new RequestParams();
        params.put("mobile", "13810215352");
        params.put("password", "123456");

        HttpClient.post(Constant.PATH_DEMO, params, new TextHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString)
            {
                //解析数据
                PagingResponse response = new PagingResponse();
                if(response.isSuccess(context))
                {
                    //刷新数据，这里数据仅供示例，以服务器获取的为准
                    response.setHasMore(true);
                    if(newPageIndex == 1)
                    {
                        //第1页，直接刷新所有数据
                        adapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, mStrings);
                        listView.setAdapter(adapter);

                        //更新刷新时间
                        refreshDate = new Date();
                    }
                    else
                    {
                        //更多页，把数据附加上去
                        adapter.addAll("测试1", "测试2", "测试3", "测试4", "测试5", "测试6", "测试7", "测试8", "测试9", "测试10");
                        adapter.notifyDataSetChanged();
                    }

                    //更新页码
                    pageIndex = newPageIndex;

                    //设置是否能继续加载更多
                    listView.setCanLoadMore(response.isHasMore());
                }

                //界面复原
                if(newPageIndex == 1)
                {
                    listView.refreshComplete(refreshDate);
                }
                else
                {
                    listView.loadMoreComplete();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable)
            {
                showToast("网络错误或者服务器错误");
                if(newPageIndex == 1)
                {
                    listView.refreshComplete(null);
                }
                else
                {
                    listView.loadMoreComplete();
                }
            }
        });
    }

    protected void onResume()
    {
        super.onResume();
        autoRefresh();
    }

    private void autoRefresh()
    {
        //自动加载
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                listView.triggerRefresh(false);
            }
        }, 200);
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
    {
        System.out.println("Click Item: " + position);
    }

    private String[] mStrings = {
            "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam",
            "Abondance", "Ackawi", "Acorn", "Adelost", "Affidelice au Chablis",
            "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre",
            "Allgauer Emmentaler"};
}
