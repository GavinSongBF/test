package gavin.demo.activity;

import android.os.Bundle;
import android.view.View;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import java.io.File;
import java.io.FileNotFoundException;

import cz.msebera.android.httpclient.Header;
import gavin.app.BaseActivity;
import gavin.demo.Constant;
import gavin.demo.R;
import gavin.net.BaseRequest;
import gavin.net.BaseResponse;
import gavin.net.HttpClient;
import gavin.net.LoginResponse;
import gavin.net.UploadRequest;
import gavin.util.JSONParser;


public class HttpRequestActivity extends BaseActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_request);
    }

    public void loadData(View v)
    {
        //显示加载框
        showLoading();

        //准备参数
        RequestParams params = new RequestParams();
        params.put("mobile", "13811522352");
        params.put("password", "123456");

        HttpClient.post(Constant.PATH_LOGIN, params, new TextHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString)
            {
                //隐藏加载框
                hiddenLoadingView();

                //JSON String解析成Java对象
                BaseResponse response = JSONParser.fromJson(responseString, BaseResponse.class);

                //实现业务逻辑
                if (response.isSuccess(context))
                {
                    //请求成功，处理业务
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable)
            {
                hiddenLoadingView();
                showToast("网络错误或者服务器错误");
            }
        });
    }


    public void loadDataWithRequestObject(View v)
    {
        //显示加载框
        showLoading();

        //准备参数
        BaseRequest request = new BaseRequest();

        HttpClient.post(Constant.PATH_USER_DETAIL, request, new TextHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString)
            {
                //隐藏加载框
                hiddenLoadingView();

                //JSON String解析成Java对象
                LoginResponse response = JSONParser.fromJson(responseString, LoginResponse.class);

                //实现业务逻辑
                if (response.isSuccess(context))
                {
                    //请求成功，处理业务
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable)
            {
                hiddenLoadingView();
                showToast("网络错误或者服务器错误");
            }
        });
    }

    public void postData(View v)
    {
        //准备参数
        RequestParams params = new RequestParams();
        File imageFile = new File("/path/filename.jpg"); //image.getPath()
        try
        {
            params.put("image", imageFile);
        }
        catch(FileNotFoundException e)
        {
        }

        //准备参数方式2
        UploadRequest request = new UploadRequest();
        request.setFile(imageFile);

        HttpClient.post(Constant.PATH_IMAGE_UPLOAD, params, new TextHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString)
            {
                hiddenLoadingView();

                //JSON String解析成Java对象
                BaseResponse response = JSONParser.fromJson(responseString, BaseResponse.class);
                if (response.isSuccess(context))
                {
                    //请求成功，处理业务
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable)
            {
                hiddenLoadingView();
                showToast("网络错误或者服务器错误");
            }
        });
    }

}
