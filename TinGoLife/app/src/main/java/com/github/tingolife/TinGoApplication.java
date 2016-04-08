package com.github.tingolife;

import android.app.Application;
import android.graphics.Bitmap;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * Created with com.github.tingolife
 * User:YangXiuFeng
 * Date:2016/3/21
 * Time:16:39
 */
public class TinGoApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        initImageloader();
        CrashReport.initCrashReport(getApplicationContext());
    }
    private void initImageloader(){
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(TinGoApplication.this);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
    }
}
