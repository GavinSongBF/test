package gavin.demo.activity;

import android.content.Intent;
import android.os.Bundle;

import gavin.activity.SplashBaseActivity;
import gavin.demo.R;

public class SplashActivity extends SplashBaseActivity
{
    private final int SPLASH_DISPLAY_SECONDS = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        showSplash(R.drawable.splash, SPLASH_DISPLAY_SECONDS);
    }

    @Override
    public void splashFinish()
    {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }
}
