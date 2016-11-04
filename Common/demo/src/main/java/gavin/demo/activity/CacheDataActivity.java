package gavin.demo.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import gavin.app.BaseActivity;
import gavin.content.Preferences;
import gavin.demo.R;
import gavin.demo.model.Product;


public class CacheDataActivity extends BaseActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cache_data);
    }

    public void saveFile(View v)
    {
        String content = readFile("data_file_name.txt");
        Log.d("Common", "content = " + content);
    }

    public void loadFile(View v)
    {
        boolean success = writeFile("data_file_name.txt", "这里是要存储的数据");
        Log.d("Common", "writeFile return = " + success);
    }

    public void savePreferences(View v)
    {
        Preferences.getPreferences(this).putString("mobile", "13811522352");
    }

    public void loadPreferences(View v)
    {
        String mobile = Preferences.getPreferences(this).getString("mobile", "13811522352");
        Log.d("Common", "mobile = " + mobile);
    }

    public void saveObjectToJsonFile(View v)
    {
        Product p = new Product();
        p.setName("Android");
        p.setImage("http://www.android.com");
        boolean success = writeObjectToJsonFile("product.json", p);
        Log.d("Common", "writeObjectToJsonFile return = " + success);
    }

    public void loadObjectFromJsonFile(View v)
    {
        Product product = readObjectFromJsonFile("product.json", Product.class);
        Log.d("Common", "product = " + product);
    }

    public void saveListToJsonFile(View v)
    {
        List<Product> list = new ArrayList<>();
        Product p = new Product();
        p.setName("Android");
        p.setImage("http://www.android.com");
        list.add(p);

        Product p2 = new Product();
        p2.setName("iOS");
        p2.setImage("http://www.apple.com");
        list.add(p2);

        boolean success = writeListToJsonFile("product_list.json", list);

        Log.d("Common", "writeParcelableList return = " + success);
    }

    public void loadListFromJsonFile(View v)
    {
        List<Product> list = readListFromJsonFile("product_list.json", Product.class);
        Log.d("Common", "list = " + list);
    }
}
