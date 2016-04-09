package com.github.tingolife.activity.picture;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.tingolife.http.OkHttpManager;
import com.github.tingolife.R;
import com.github.tingolife.constant.TinGoApi;
import com.github.tingolife.domain.picture.PictureDetail;
import com.github.tingolife.http.callback.StringCallBack;
import com.github.tingolife.utils.MD5Util;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import okhttp3.Call;
import uk.co.senab.photoview.PhotoView;

/**
 * Created with com.github.tingolife.activity.picture
 * User:YangXiuFeng
 * Date:2016/3/22
 * Time:11:15
 */
public class PictureDetailActivity extends AppCompatActivity{
    @Bind(R.id.picture_detail_page)
    protected ViewPager picDetailPager;
    @Bind(R.id.album_title)
    protected TextView titleTxtView;
    @Bind(R.id.count)
    protected TextView countTxtView;
    String tagUrl ;
    private List<PictureDetail.ListEntity> pictureListEntity;
    private static DisplayImageOptions options;
    private PictureAdapter adapter;
    private int pictureCount;
    private ArrayList<Fragment> fragmentList = new ArrayList<>();
    private  Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    adapter.notifyDataSetChanged();
                    Bundle bundle = msg.getData();
                    titleTxtView.setText(bundle.getString("title"));
                    pictureCount=bundle.getInt("count");
                    countTxtView.setText("1/"+pictureCount);
                    break;
                case 1:
                    break;
            }
            super.handleMessage(msg);
        }

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picture_detail_layout);
        ButterKnife.bind(this);
        pictureListEntity = new ArrayList<>();
        long id = getIntent().getLongExtra("parentId",0);
        adapter = new PictureAdapter(getSupportFragmentManager(),fragmentList);
        picDetailPager.setAdapter(adapter);
        initData(id);
        picDetailPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                countTxtView.setText((position + 1) + "/" + pictureCount);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void initData(long id){
        tagUrl = TinGoApi.PIC_DETAIL + "?id=" + id;
        OkHttpManager.getOkHttpManager().asyncGet(tagUrl, new StringCallBack() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(PictureDetailActivity.this, "请检查您的网络！！！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                PictureDetail pictureDetail = new Gson().fromJson(response, PictureDetail.class);
                pictureListEntity.addAll(pictureDetail.getList());
                ArrayList<Fragment> pictureFragments = new ArrayList<Fragment>();
                for (PictureDetail.ListEntity entity : pictureListEntity) {
                    pictureFragments.add(OnePicture.newInstance(entity.getSrc()));
                }
                adapter.addFragment(pictureFragments);
                Message message = handler.obtainMessage();
                message.what = 0;
                Bundle bundle = new Bundle();
                bundle.putString("title", pictureDetail.getTitle());
                bundle.putInt("count", pictureDetail.getSize());
                message.setData(bundle);
                handler.sendMessage(message);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpManager.getOkHttpManager().cancelTag(MD5Util.getMD5String(tagUrl));
    }

    public static class OnePicture extends Fragment{
        View rootView;
        @Bind(R.id.photoView)
        PhotoView photoView;
        Bitmap loadedBitmap;
        @Bind(R.id.save)
        TextView save;
        @Bind(R.id.loading)
        MaterialProgressBar materialProgressBar;
        String suffix;
        public static Fragment newInstance(String arg){
            OnePicture fragment = new OnePicture();
            Bundle bundle = new Bundle();
            bundle.putString("src", arg);
            fragment.setArguments(bundle);
            return fragment;
        }
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            if (null != rootView){
                return rootView;
            }
            rootView = inflater.inflate(R.layout.picture_detail_pager_item,container,false);
            ButterKnife.bind(this, rootView);
            String src = getArguments().getString("src");
            int last = src.lastIndexOf(".");
            suffix = src.substring(last+1);
            Log.e("ee","suffix="+suffix);
            ImageLoader.getInstance().displayImage(TinGoApi.PIC_SHOW + src, photoView, options, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    materialProgressBar.setVisibility(View.VISIBLE);
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    materialProgressBar.setVisibility(View.VISIBLE);
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    materialProgressBar.setVisibility(View.GONE);
                    loadedBitmap = loadedImage;
                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {
                }
            });
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    savePicture();
                }
            });
            return rootView;
        }
        public void savePicture(){
            if (null != loadedBitmap){
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                if("jpg".equals(suffix)||"jpeg".equals(suffix)){
                    loadedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                }else{
                    loadedBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                }
                byte[] byteArray = stream.toByteArray();
                File dir=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/tinGoLife" );
                if(!dir.isFile()){
                    dir.mkdir();
                }
                String fileName = UUID.randomUUID() +"."+suffix;
                File file=new File(dir, fileName);
                try {
                    FileOutputStream fos=new FileOutputStream(file);
                    fos.write(byteArray, 0 , byteArray.length);
                    fos.flush();
                    fos.close();
                    Toast.makeText(getActivity(),"保存成功:/tinGoLife/"+fileName,Toast.LENGTH_SHORT).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                Toast.makeText(getActivity(),"图片正在加载....",Toast.LENGTH_SHORT).show();
            }
        }
    }

    class PictureAdapter extends FragmentStatePagerAdapter{
        private List<Fragment> pictureFragments;
        public PictureAdapter(FragmentManager fm,ArrayList<Fragment> list) {
            super(fm);
            options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.defalut_load_img)
                    .cacheInMemory(false)//不存内存,避免OOM
                    .cacheOnDisk(true)
                    .considerExifParams(true)
                    .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .build();
            pictureFragments = new ArrayList<Fragment>();
            pictureFragments.addAll(list);
        }
        public void addFragment(ArrayList<Fragment> list){
            pictureFragments.addAll(list);
        }
        @Override
        public Fragment getItem(int position) {
            return pictureFragments.get(position);
        }

        @Override
        public int getCount() {
            return pictureFragments.size();
        }
    }
}
