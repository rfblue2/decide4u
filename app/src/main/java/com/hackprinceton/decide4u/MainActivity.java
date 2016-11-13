package com.hackprinceton.decide4u;

import android.app.ActionBar;
import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    TabPagerAdapter myAdapter;
    ViewPager myViewPager;
    TabLayout myTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myViewPager = (ViewPager) findViewById(R.id.pager);
        myAdapter = new TabPagerAdapter(getSupportFragmentManager());
        myViewPager.setAdapter(myAdapter);

        myTabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        myTabLayout.setupWithViewPager(myViewPager);
    }

public static class TabPagerAdapter extends FragmentPagerAdapter {
    private static int NUM_ITEMS = 2;
    private String tabTitles[] = new String[] {"Decision Feed", "My Dashboard"};

    public TabPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return FeedFragment.newInstance(0, "Decision Feed");
            case 1:
                return DashFragment.newInstance(1, "My Dashboard");
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}

}
