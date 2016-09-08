package com.example.tiger.weather.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.example.tiger.weather.R;
import com.mrocker.advertising.AdsManager;
import com.mrocker.advertising.OnAdEventListener;
import com.orhanobut.logger.Logger;

/**
 * Created by tiger on 16/9/2.
 */
public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash_ad);
        LinearLayout splash_open_view = (LinearLayout) findViewById(R.id.splash_view);
        AdsManager.showOpeningScreenAds(SplashActivity.this, splash_open_view, true, true, "SxRf76Rur0kq", "7nnbJWmEiMGx", "showId", "target", new OnAdEventListener() {
            @Override
            public void onShowSuccess() {
                Logger.d("openingAd showSucessful");
            }

            @Override
            public void onShowFailed() {
                Logger.d("openingAd", "showFailed");
                intentToMain();
            }

            @Override
            public void onAdClick(String s) {
                Logger.d("openingAd", "onClick link: " + s);
                AdsManager.closeOpeningScreenAds(SplashActivity.this);
                intentToMain();
            }

            @Override
            public void onAdClosed() {
                Logger.d("openingAd closed");
                intentToMain();
            }
        });
    }

    private void intentToMain() {
        startActivity(new Intent(SplashActivity.this, ChooseAreaActivity.class));
        finish();
    }
}
