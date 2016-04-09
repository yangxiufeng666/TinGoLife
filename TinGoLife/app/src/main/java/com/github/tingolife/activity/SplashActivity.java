package com.github.tingolife.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.github.tingolife.R;

/**
 * Created with com.github.tingolife.activity
 * User:YangXiuFeng
 * Date:2016/3/30
 * Time:16:34
 */
public class SplashActivity extends AppCompatActivity{
    private Handler handler;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent(SplashActivity.this,TinGoMain.class);
            SplashActivity.this.startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            finish();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);
        handler = new Handler();
        handler.postDelayed(runnable,1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}
