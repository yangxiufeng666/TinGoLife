package com.github.tingolife.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.tingolife.R;
import com.github.tingolife.adapter.PictureAdapter;
import com.github.tingolife.constant.TinGoApi;
import com.github.tingolife.domain.picture.PictureListItem;
import com.github.tingolife.http.OkHttpManager;
import com.github.tingolife.http.callback.StringCallBack;
import com.github.tingolife.utils.MD5Util;
import com.github.tingolife.widget.SpacesItemDecoration;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created with com.github.tingolife.fragment
 * User:YangXiuFeng
 * Date:2016/4/11
 * Time:9:13
 */
public class LatestPictureFragment extends Fragment {
    @Bind(R.id.picture_list)
    RecyclerView recyclerView;
    @Bind(R.id.picture_swipe)
    SwipeRefreshLayout swipeRefreshLayout;
    private View rootView;
    private List<PictureListItem.TngouEntity> tngou;
    private PictureAdapter pictureAdapter;
    private StaggeredGridLayoutManager layoutManager;
    String tagUrl;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView != null) {
            return rootView;
        }
        rootView = inflater.inflate(R.layout.picture_fragment, container, false);
        ButterKnife.bind(this, rootView);
        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        tngou = new ArrayList<>();
        pictureAdapter = new PictureAdapter(tngou,getActivity());
        recyclerView.setAdapter(pictureAdapter);
        SpacesItemDecoration spacesItemDecoration = new SpacesItemDecoration(16);
        recyclerView.addItemDecoration(spacesItemDecoration);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                tngou.clear();
                initData();
            }
        });
        initData();
        return rootView;
    }
    private void initData(){
        tagUrl = TinGoApi.PIC_LATEST;
        OkHttpManager.getOkHttpManager().asyncGet(tagUrl, new StringCallBack() {
            @Override
            public void onError(Call call, Exception e) {
                Log.e("back", e.getLocalizedMessage());
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getActivity(), "请检查您的网络！！！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                PictureListItem pictureListItem = new Gson().fromJson(response, PictureListItem.class);
                tngou.addAll(pictureListItem.getTngou());
                if (pictureListItem.getTngou().size() > 0) {
                    pictureAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getActivity(), "没有更多图片了", Toast.LENGTH_SHORT).show();
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        OkHttpManager.getOkHttpManager().cancelTag(MD5Util.getMD5String(tagUrl));
    }
}
