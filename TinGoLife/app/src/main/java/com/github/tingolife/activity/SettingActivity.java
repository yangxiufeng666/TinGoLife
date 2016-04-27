package com.github.tingolife.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.tingolife.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.text.DecimalFormat;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created with com.github.tingolife.activity
 * User:YangXiuFeng
 * Date:2016/4/9
 * Time:15:11
 */
public class SettingActivity extends BaseActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.cacheSize)
    TextView cacheSize;
    @Bind(R.id.clear)
    RelativeLayout clearCache;
    @Bind(R.id.switch_push)
    SwitchCompat switchPush;
    @Bind(R.id.current_version)
    TextView currentVersion;
    @Bind(R.id.check_update)
    RelativeLayout checkUpdate;
    @Bind(R.id.about)
    RelativeLayout about;
    @Bind(R.id.push_toggle_layout)
    RelativeLayout pushToggleLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_layout);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("设置");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextColor(0xffffffff);
        currentVersion.setText("v" + getVersion());
        clearCache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageLoader.getInstance().clearDiskCache();
                cacheSize.setText("0.00M");
            }
        });
        DecimalFormat fnum = new DecimalFormat("##0.00");
        String dd = fnum.format(getDirSize(ImageLoader.getInstance().getDiskCache().getDirectory()));
        cacheSize.setText(dd + "M");
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public float getDirSize(File file) {
        //判断文件是否存在
        if (file.exists()) {
            //如果是目录则递归计算其内容的总大小
            if (file.isDirectory()) {
                File[] children = file.listFiles();
                float size = 0;
                for (File f : children)
                    size += getDirSize(f);
                return size;
            } else {//如果是文件则直接返回其大小,以“兆”为单位
                float size = (float) file.length() / 1024 / 1024;
                return size;
            }
        } else {
            return 0.0f;
        }
    }

    private String getVersion() {
        PackageManager manager = this.getPackageManager();
        try {
            PackageInfo packageInfo = manager.getPackageInfo(this.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }
    @OnClick(R.id.push_toggle_layout)
    protected void pushToggleLayoutClick(){
        switchPush.toggle();
    }
}
