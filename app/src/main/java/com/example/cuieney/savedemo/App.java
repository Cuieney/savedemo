package com.example.cuieney.savedemo;

import android.app.Application;

import com.feetsdk.android.FeetSdk;

/**
 * Created by cuieney on 17/2/14.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FeetSdk.getInstance().init(this,"b84373c5-5c78-47d7-8237-42ce8d8f81e2","demo");
        FeetSdk.getInstance().setMobileNetWorkAvailable(this,true);//设置移动网络下可以下载
    }
}
