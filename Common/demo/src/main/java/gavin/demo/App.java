package gavin.demo;


import gavin.app.BaseApplication;
import gavin.net.HttpClient;

public class App extends BaseApplication
{
    @Override
    public void onCreate()
    {
        super.onCreate();

        HttpClient.init(this, Constant.SERVER_URL);
    }
}
