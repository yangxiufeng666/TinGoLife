package com.github.tingolife.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.tingolife.R;
import com.github.tingolife.activity.picture.PictureDetailActivity;
import com.github.tingolife.constant.TinGoApi;
import com.github.tingolife.domain.picture.PictureListItem;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created with com.github.tingolife.adapter
 * User:YangXiuFeng
 * Date:2016/3/24
 * Time:16:53
 */
public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.PictureHolder> {
    List<PictureListItem.TngouEntity> tngouEntityList;
    private DisplayImageOptions options;
    private Activity context;

    public PictureAdapter(List<PictureListItem.TngouEntity> tngouEntityList, Activity context) {
        this.tngouEntityList = tngouEntityList;
        this.context = context;
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.default_pic)
                .cacheInMemory(false)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .build();
        setHasStableIds(true);
    }

    @Override
    public PictureHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.picture_list_item, parent, false);
        return new PictureHolder(view);
    }

    @Override
    public void onBindViewHolder(PictureHolder holder, final int position) {
        PictureListItem.TngouEntity tngouEntity = tngouEntityList.get(position);
        ImageLoader.getInstance().displayImage(TinGoApi.PIC_SHOW + tngouEntity.getImg(), holder.picture, options);//+"_800x480"
        holder.pictureTitle.setText(tngouEntity.getTitle());
        holder.gallerySize.setText(tngouEntity.getSize()+"张");
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, PictureDetailActivity.class);
                intent.putExtra("parentId", (long) tngouEntityList.get(position).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tngouEntityList.size();
    }

    class PictureHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.picture)
        ImageView picture;
        @Bind(R.id.picture_title)
        TextView pictureTitle;
        @Bind(R.id.root)
        LinearLayout root;
        @Bind(R.id.gallery_size)
        TextView gallerySize;
        public PictureHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
