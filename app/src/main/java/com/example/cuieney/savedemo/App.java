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
        FeetSdk.getInstance().init(this,"99b36eda-3c91-4715-84ee-480c90ffe82f","demo");
        FeetSdk.getInstance().setMobileNetWorkAvailable(this,true);//设置移动网络下可以下载
    }
}
