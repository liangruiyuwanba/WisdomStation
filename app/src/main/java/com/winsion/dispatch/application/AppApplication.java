package com.winsion.dispatch.application;

import android.app.Application;
import android.content.Context;

import com.winsion.dispatch.BuildConfig;
import com.winsion.dispatch.MyObjectBox;
import com.winsion.dispatch.R;
import com.winsion.dispatch.data.NetDataSource;
import com.winsion.dispatch.utils.CrashUtils;
import com.winsion.dispatch.utils.DirAndFileUtils;
import com.winsion.dispatch.utils.LogUtils;

import java.io.IOException;

import io.objectbox.BoxStore;

/**
 * Created by 10295 on 2017/12/5 0005
 */

public class AppApplication extends Application {
    private static Context mApplicationContext;
    private static BoxStore mBoxStore;
    // 测试模式开关
    public static boolean TEST_MODE = false;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplicationContext = getApplicationContext();
        // 全局捕获异常并保存异常信息
        CrashUtils.getInstance().init(this);
        // 初始化数据库
        mBoxStore = MyObjectBox.builder().androidContext(this).build();
        // 初始化网络库
        NetDataSource.init(this);
        // 初始化LOG
        String logDir;
        try {
            logDir = DirAndFileUtils.getLogDir().toString();
        } catch (IOException e) {
            logDir = getCacheDir().toString();
        }
        String logTag = getString(R.string.app_name);
        LogUtils.init(BuildConfig.DEBUG, true, logDir, LogUtils.FILTER_V, logTag);
    }

    public static Context getContext() {
        return mApplicationContext;
    }

    public static BoxStore getBoxStore() {
        return mBoxStore;
    }
}