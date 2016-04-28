package com.github.tingolife.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.tingolife.R;
import com.github.tingolife.utils.PreferenceUtils;
import com.github.tingolife.utils.Util;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.android.tpush.service.XGPushService;

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
        String dd = fnum.format(Util.getDirSize(ImageLoader.getInstance().getDiskCache().getDirectory()));
        cacheSize.setText(dd + "M");
        boolean isOpenPush = PreferenceUtils.getPrefBoolean(getApplicationContext(),"isOpenPush",true);
        switchPush.setChecked(isOpenPush);
        switchPush.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked){
                    // 反注册，调用本接口后，APP将停止接收通知和消息
                    XGPushManager.unregisterPush(getApplicationContext());
                }else {
                    Context context = getApplicationContext();
                    XGPushManager.registerPush(context);
                    // 2.36（不包括）之前的版本需要调用以下2行代码
                    Intent service = new Intent(context, XGPushService.class);
                    context.startService(service);
                }
                PreferenceUtils.setPrefBoolean(getApplicationContext(),"isOpenPush",isChecked);
            }
        });
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
