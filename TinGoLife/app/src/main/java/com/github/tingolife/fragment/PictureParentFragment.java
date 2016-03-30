package com.github.tingolife.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.tingolife.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created with com.github.tingolife.fragment
 * User:YangXiuFeng
 * Date:2016/3/22
 * Time:9:38
 */
public class PictureParentFragment extends Fragment{
    private View rootView;
    @Bind(R.id.picture_tab)
    protected TabLayout tabLayout;
    @Bind(R.id.picture_viewpage)
    protected ViewPager viewPager;
    private List<Fragment> fragmentList;
    List<String> listTitle;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView != null){
            return rootView;
        }
        rootView = inflater.inflate(R.layout.pic_parent_layout,container,false);
        ButterKnife.bind(this, rootView);
        initTab();
        return rootView;
    }
    private void initTab(){
        listTitle = new ArrayList<>();
        fragmentList = new ArrayList<>();
        listTitle.add("性感美女");
        listTitle.add("韩日美女");
        listTitle.add("丝袜美腿");
        listTitle.add("美女照片");
        listTitle.add("美女写真");
        listTitle.add("清纯美女");
        listTitle.add("性感车模");
        for (int i=0;i< listTitle.size();i++) {
            tabLayout.addTab(tabLayout.newTab().setText(listTitle.get(i)));
            fragmentList.add(PictureFragment.newInstance(i+1));
        }
        initViewPager();
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }
    private void initViewPager(){
        viewPager.setAdapter(new ViewPagerAdapter(getActivity().getSupportFragmentManager()));
    }
    private class ViewPagerAdapter extends FragmentStatePagerAdapter{
        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return listTitle.get(position);
        }
    }
}
