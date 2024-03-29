package nl.martijndorsman.studiecheck;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import nl.martijndorsman.studiecheck.fragments.VakkenlijstFragmentFour;
import nl.martijndorsman.studiecheck.fragments.VakkenlijstFragmentOne;
import nl.martijndorsman.studiecheck.fragments.VakkenlijstFragmentThree;
import nl.martijndorsman.studiecheck.fragments.VakkenlijstFragmentTwo;

/**
 * Created by Martijn on 25/06/17.
 */

public class VakkenlijstSlideActivity extends FragmentActivity {
    private static final int NUM_PAGES = 4;
    int page = 0;
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide);
        viewPager = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        Bundle b = getIntent().getExtras();
        if (b != null) {
            String pageString = b.getString("page");
            page = Integer.parseInt(pageString);
        }

        viewPager.setCurrentItem(page);
    }

    @Override
    public void onBackPressed() {
            super.onBackPressed();
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new VakkenlijstFragmentOne();
            } else if (position == 1){
                return new VakkenlijstFragmentTwo();
            } else if (position == 2){
                return new VakkenlijstFragmentThree();
            } else {
                return new VakkenlijstFragmentFour();
            }
        }

        @Override
        public CharSequence getPageTitle(int position){
            switch(position){
                case 0:
                    return getString(R.string.jaar1);
                case 1:
                    return getString(R.string.jaar2);
                case 2:
                    return getString(R.string.jaar3en4tl);
                case 3:
                    return getString(R.string.keuzetl);
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}
