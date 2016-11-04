package gavin.net;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

public class HttpClient
{
    private static String BASE_URL = "http://www.163.com";

    private static AsyncHttpClient client;

    private static PersistentCookieStore cookieStore;

    private static final String TAG = HttpClient.class.getSimpleName();

    public static void init(Context context, String serverBaseUrl)
    {
        BASE_URL = serverBaseUrl;

        client = new AsyncHttpClient();
        client.getHttpClient();
        client.setTimeout(20 * 1000);
        client.setUserAgent("Android");


        cookieStore = new PersistentCookieStore(context);
        client.setCookieStore(cookieStore);
    }

    public static PersistentCookieStore getCookieStore()
    {
        return cookieStore;
    }

    public static void get(String path, RequestParams params, AsyncHttpResponseHandler responseHandler)
    {
//        addBaseUserInfo(params);
        client.get(getAbsoluteUrl(path), params, responseHandler);
    }

    public static void post(String path, RequestParams params, AsyncHttpResponseHandler responseHandler)
    {
//        addBaseUserInfo(params);
        client.post(getAbsoluteUrl(path), params, responseHandler);
    }

    public static void post(String path, BaseRequest request, AsyncHttpResponseHandler responseHandler)
    {
        RequestParams params = request.getRequestParams();
//        addBaseUserInfo(params);
        String realUrl = getAbsoluteUrl(path);
        client.post(realUrl, params, responseHandler);
    }

    public static void get(String path, BaseRequest request, AsyncHttpResponseHandler responseHandler)
    {
        RequestParams params = request.getRequestParams();
//        addBaseUserInfo(params);
        String realUrl = getAbsoluteUrl(path);
        client.get(realUrl, params, responseHandler);
    }

    public static String getAbsoluteUrl(String relativeUrl)
    {
        return BASE_URL + relativeUrl;
    }

//    public static void addBaseUserInfo(RequestParams params)
//    {
//        BaseApplication application = BaseApplication.getInstance();
//        BaseUser user = application.getUser(BaseUser.class);
//        if (user != null && user.isLogin())
//        {
//            HashMap<String, Object> map = user.getBaseUserInfo();
//            if(map != null)
//            {
//                for (String key : map.keySet())
//                {
//                    params.put(key, map.get(key).toString());
//                }
//            }
//        }
//    }
}