package com.github.tingolife.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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

import com.github.tingolife.adapter.PictureAdapter;
import com.github.tingolife.http.callback.StringCallBack;
import com.github.tingolife.widget.SpacesItemDecoration;
import com.github.tingolife.http.OkHttpManager;
import com.github.tingolife.R;
import com.github.tingolife.constant.TinGoApi;
import com.github.tingolife.domain.picture.PictureListItem;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created with com.github.tingolife.fragment
 * User:YangXiuFeng
 * Date:2016/3/19
 * Time:17:13
 */
public class PictureFragment extends Fragment{
    private View rootView;
    @Bind(R.id.picture_swipe)
    protected SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.picture_list)
    protected RecyclerView recyclerView;
    private List<PictureListItem.TngouEntity> tngou;
    private PictureAdapter pictureAdapter;
    private Handler mainHandler;
    private DisplayImageOptions options;
    private int lastVisibleItem;
    private int[] lastVisibleItems;
    private StaggeredGridLayoutManager layoutManager;
    private int pageNum=1;
    private boolean hasMore=true;
    private int id;
    public static Fragment newInstance(int arg){
        PictureFragment fragment = new PictureFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("pictureId", arg);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView != null){
            return rootView;
        }
        id = getArguments().getInt("pictureId");
        rootView = inflater.inflate(R.layout.picture_fragment,container,false);
        ButterKnife.bind(this, rootView);
        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);//
        tngou = new ArrayList<>();
        pictureAdapter = new PictureAdapter(tngou,getActivity());
        recyclerView.setAdapter(pictureAdapter);
        SpacesItemDecoration spacesItemDecoration = new SpacesItemDecoration(16);
        recyclerView.addItemDecoration(spacesItemDecoration);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItems = layoutManager.findLastCompletelyVisibleItemPositions(null);
                lastVisibleItem = Math.max(lastVisibleItems[0], lastVisibleItems[1]);
                Log.e("item", "lastVisibleItem=" + lastVisibleItem);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == pictureAdapter.getItemCount() && hasMore) {
                    swipeRefreshLayout.setRefreshing(true);
                    initData(id, 20, pageNum);
                }
            }
        });
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mainHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);

            }
        });
        // 这句话是为了，第一次进入页面的时候显示加载进度条
        swipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));
        mainHandler = new Handler(Looper.getMainLooper());
        swipeRefreshLayout.setRefreshing(true);
        initData(id, 20, pageNum);
        return rootView;
    }
    private void initData(int id,int rows, final int page){
        OkHttpManager.getOkHttpManager().asyncGet(TinGoApi.PIC_LIST_API + "?id=" + id + "&rows=" + rows + "&page=" + page, new StringCallBack() {
            @Override
            public void onError(Call call, Exception e) {
                Log.e("back", e.getLocalizedMessage());
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onResponse(String response) {
                Log.e("back", response);
                PictureListItem pictureListItem = new Gson().fromJson(response, PictureListItem.class);
                tngou.addAll(pictureListItem.getTngou());
                if (pictureListItem.getTngou().size() > 0) {
                    hasMore = true;
                    pageNum++;
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            pictureAdapter.notifyDataSetChanged();
                        }
                    });
                } else {
                    hasMore = false;
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "没有更多图片了", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
