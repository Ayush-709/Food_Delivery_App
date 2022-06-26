package com.practice.foodingo.Support_Files;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class MyAdapter extends FragmentPagerAdapter {

    private final ArrayList<Fragment> list_frag = new ArrayList<>();
    private final ArrayList<String> list_string = new ArrayList<>();
    public MyAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return list_frag.get(position);
    }

    @Override
    public int getCount() {
        return list_frag.size();
    }

    public void addFrag(Fragment fragment, String str){
        list_frag.add(fragment);
        list_string.add(str);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        return list_string.get(position);
    }
}
