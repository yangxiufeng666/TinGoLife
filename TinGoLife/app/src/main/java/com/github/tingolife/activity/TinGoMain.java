package com.github.tingolife.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.AlertDialogWrapper;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.github.tingolife.R;
import com.github.tingolife.fragment.LatestPictureFragment;
import com.github.tingolife.fragment.PictureParentFragment;
import com.github.tingolife.utils.ImageloaderUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.OnekeyShareTheme;

public class TinGoMain extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.nav_view)
    NavigationView navigationView;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @Bind(R.id.action_share)
    FloatingActionButton actionShare;
    @Bind(R.id.action_settings)
    FloatingActionButton actionSettings;
    @Bind(R.id.multiple_actions)
    FloatingActionsMenu multipleActions;
    private PictureParentFragment pictureFragment;
    private LatestPictureFragment latestPictureFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tin_go_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        actionShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showShare(TinGoMain.this, null, true);
                multipleActions.collapse();
            }
        });
        actionSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                multipleActions.collapse();
                Intent intent = new Intent(TinGoMain.this, SettingActivity.class);
                startActivity(intent);
            }
        });
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_latest_gallery);
        if (latestPictureFragment == null) {
            latestPictureFragment = new LatestPictureFragment();
        }
        setFragment(latestPictureFragment);
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_fragment, fragment);
        if (!(fragment instanceof PictureParentFragment)) {
            transaction.addToBackStack(null);
        }
        //提交修改
        transaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            new AlertDialogWrapper.Builder(this)
                    .setTitle("Hi")
                    .setMessage("客官，真的要走吗？")
                    .setPositiveButton("嗯", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            ImageLoader.getInstance().destroy();
                            finish();
                        }
                    }).setNegativeButton("再看看", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).show();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_gallery:
                if (pictureFragment == null) {
                    pictureFragment = new PictureParentFragment();
                }
                if (!pictureFragment.isVisible()) {
                    setFragment(pictureFragment);
                }
                break;
            case R.id.nav_latest_gallery:
                if (latestPictureFragment == null) {
                    latestPictureFragment = new LatestPictureFragment();
                }
                if (!latestPictureFragment.isVisible()) {
                    setFragment(latestPictureFragment);
                }
                break;
            case R.id.nav_setting:
                Intent intent = new Intent(this, SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_share:
                showShare(TinGoMain.this, null, false);
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * 演示调用ShareSDK执行分享
     *
     * @param context
     * @param platformToShare 指定直接分享平台名称（一旦设置了平台名称，则九宫格将不会显示）
     * @param showContentEdit 是否显示编辑页
     */
    public static void showShare(Context context, String platformToShare, boolean showContentEdit) {
        OnekeyShare oks = new OnekeyShare();
        oks.setSilent(!showContentEdit);
        if (platformToShare != null) {
            oks.setPlatform(platformToShare);
        }
        //ShareSDK快捷分享提供两个界面第一个是九宫格 CLASSIC  第二个是SKYBLUE
        oks.setTheme(OnekeyShareTheme.CLASSIC);
        // 令编辑页面显示为Dialog模式
        oks.setDialogMode();
        // 在自动授权时可以禁用SSO方式
        oks.disableSSOWhenAuthorize();
        oks.setTitle("美图社区");
        oks.setTitleUrl("https://github.com/babylikebird/TinGoLife");
        oks.setText("喜欢看赏心悦目的图片吗？那就来美悦看看吧");
        oks.setImageUrl("http://a.hiphotos.baidu.com/zhidao/pic/item/728da9773912b31bb82f07408418367adab4e11c.jpg");
        oks.setUrl("http://www.mob.com"); //微信不绕过审核分享链接
        oks.setSite("美悦");  //QZone分享完之后返回应用时提示框上显示的名称
        oks.setSiteUrl("http://mob.com");//QZone分享参数
        oks.setVenueName("美悦");
        oks.setVenueDescription("喜欢看赏心悦目的图片吗？那就来美悦看看吧");
        // 启动分享
        oks.show(context);
    }

}
