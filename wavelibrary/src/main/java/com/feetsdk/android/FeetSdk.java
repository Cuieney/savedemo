package com.feetsdk.android;

import android.content.Context;

import com.feetsdk.android.feetsdk.annotation.PlayMusicType;
import com.feetsdk.android.feetsdk.download.DownloadControler;
import com.feetsdk.android.feetsdk.http.HttpControler;
import com.feetsdk.android.feetsdk.music.MusicHelper;
import com.feetsdk.android.feetsdk.player.PlayerControler;
import com.feetsdk.android.feetsdk.ui.FwController;

import java.lang.ref.WeakReference;

/**
 * Created by cuieney on 16/11/9.
 *
 */
public class FeetSdk {

    private FeetConfig config;


    private static class FeetSdkHolder {
        private static final FeetSdk INSTANCE = new FeetSdk();
    }

    public synchronized static FeetSdk getInstance() {
        return FeetSdkHolder.INSTANCE;
    }

    private FeetSdk() {
    }

//    public void init(Context context){
//        this.context = context;
//        config.initDevInfo(context);
//        init(context,config.getAppkey(), config.getAppChannel());
//    }

    public void init(Context context,String appKey, String appChannel){
        config = new FeetConfig(context);
        if (config.checkSelf(appKey,appChannel)) {
//            return;
        }
        config.initDevInfo(context);
        config.setAppKey(appKey);
        config.setAppChannel(appChannel);
        config.initAppInfo(context);
        config.initDevInfo(context);
        config.debug("使用开始");
    }

    public void setMobileNetWorkAvailable(Context context,boolean isOpen){
        config.initDevInfo(context);
        config.setCellularData(isOpen);
    }

    public void setMusicLibrarySize(Context context,int size){
        config.initDevInfo(context);
        config.setDefaultMusciSize(size);
    }

    private static boolean getMobileNetWorkAvailable(){
        FeetConfig feetConfig = new FeetConfig();
        return feetConfig.getCellularData();
    }

    private static HttpControler getHttpControler(Context context) {
        return new HttpControler(context);
    }

    private static DownloadControler getDownloadControler(Context context) {
        return new DownloadControler(context);
    }

    private static PlayerControler getPlayerControler(Context context,@PlayMusicType int type){
        return new PlayerControler(context,type);
    }

    private static MusicHelper getMusicHelper(Context context){
        return new MusicHelper(context);
    }

    public static FwController getFeetUiController( ){
        return  FwController.getInstance();
    }

}
