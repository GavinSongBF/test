package gavin.demo.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import gavin.app.BaseActivity;
import gavin.demo.R;
import gavin.demo.model.Product;
import gavin.widget.SimpleBaseAdapter;

/**
 * Created by Ma on 2015/2/10.
 */
public class SimpleListActivity extends BaseActivity
{
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_list);

        initView();

        showData();
    }

    private void initView()
    {
        listView = findView(R.id.listView);
    }

    private void showData()
    {
        List<Product> list = new ArrayList<Product>();

        Product p = new Product();
        p.setName("鼠标");
        p.setImage("http://img10.360buyimg.com/n7/g13/M04/02/0A/rBEhU1HvTPEIAAAAAADoyHtILa0AABWLQC3z_0AAOjg806.jpg");
        list.add(p);

        p = new Product();
        p.setName("键盘");
        p.setImage("http://img13.360buyimg.com/n7/jfs/t601/353/534992334/65732/fc45df91/546f1d0fNd8dcd534.jpg");
        list.add(p);

        SimpleListAdapter adapter = new SimpleListAdapter(this, list);
        listView.setAdapter(adapter);
    }

    public class SimpleListAdapter extends SimpleBaseAdapter<Product>
    {
        public SimpleListAdapter(Context context, List<Product> data)
        {
            super(context, data, R.layout.item_simple_list);
        }

        public void reuseItemView(int position, View convertView, ViewHolder holder)
        {
            // 1. 获取item的数据
            Product product = (Product) getItem(position);

            // 2. 获得显示用的视图
            ImageView imageView = holder.getView(R.id.image_view);
            TextView textView = holder.getView(R.id.text_view);

            // 3. 设置数据，这里也可以改显示样式，比如隐藏某个控件
            imageLoader.displayImage(product.getImage(), imageView);
            textView.setText(product.getName());
        }
    }
}
