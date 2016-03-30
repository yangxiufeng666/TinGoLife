package com.github.tingolife.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.tingolife.R;
import com.github.tingolife.activity.picture.PictureDetailActivity;
import com.github.tingolife.constant.TinGoApi;
import com.github.tingolife.domain.picture.PictureListItem;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

/**
 * Created with com.github.tingolife.adapter
 * User:YangXiuFeng
 * Date:2016/3/24
 * Time:16:53
 */
public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.PictureHolder>{
    List<PictureListItem.TngouEntity> tngouEntityList;
    private DisplayImageOptions options;
    private Activity context;

    public PictureAdapter(List<PictureListItem.TngouEntity> tngouEntityList,Activity context) {
        this.tngouEntityList = tngouEntityList;
        this.context = context;
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.default_pic)
                .cacheInMemory(false)//不存内存,避免OOM
                .cacheOnDisk(true)
                .considerExifParams(true)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    @Override
    public PictureHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.picture_list_item, parent, false);
        return new PictureHolder(view);
    }

    @Override
    public void onBindViewHolder(PictureHolder holder, final int position) {
        ImageLoader.getInstance().displayImage(TinGoApi.PIC_SHOW + tngouEntityList.get(position).getImg(), holder.imageView,options);//+"_800x480"
        holder.title.setText(tngouEntityList.get(position).getTitle());
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, PictureDetailActivity.class);
                intent.putExtra("parentId", (long)tngouEntityList.get(position).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tngouEntityList.size();
    }
    class PictureHolder extends RecyclerView.ViewHolder{
        //            @Bind(R.id.picture)
        ImageView imageView;
        //            @Bind(R.id.picture_title)
        TextView title;
        View root;
        public PictureHolder(View itemView) {
            super(itemView);
//            ButterKnife.bind(this, itemView);
            imageView = (ImageView)itemView.findViewById(R.id.picture);
            title = (TextView)itemView.findViewById(R.id.picture_title);
            root = itemView;
        }
    }
}
