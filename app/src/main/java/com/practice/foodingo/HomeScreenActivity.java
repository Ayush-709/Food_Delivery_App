package com.practice.foodingo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.practice.foodingo.Support_Files.MyAdapter;

public class HomeScreenActivity extends AppCompatActivity {
    TabLayout tl;
    ViewPager vp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        tl = findViewById(R.id.tabHomeScreen);
        vp = findViewById(R.id.vpHomeScreen);

        tl.setupWithViewPager(vp);

        MyAdapter adapter = new MyAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.addFrag(new Customer(),"CUSTOMER");
        adapter.addFrag(new Admin(),"ADMIN");
        vp.setAdapter(adapter);

    }
}