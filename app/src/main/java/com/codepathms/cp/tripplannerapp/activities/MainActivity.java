package com.codepathms.cp.tripplannerapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.codepathms.cp.tripplannerapp.R;
import com.codepathms.cp.tripplannerapp.fragments.CreatedItineraryListFragment;
import com.codepathms.cp.tripplannerapp.fragments.ItineraryListFragment;
import com.codepathms.cp.tripplannerapp.fragments.SavedItineraryListFragment;

public class MainActivity extends AppCompatActivity {
    private ItineraryListFragment itineraryListFragment;
    private SavedItineraryListFragment savedItineraryListFragment;
    private CreatedItineraryListFragment createdItineraryListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set up viewpager for tabs
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new ItinerariesPagerAdapter(getSupportFragmentManager()));
        PagerSlidingTabStrip pagerSlidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        pagerSlidingTabStrip.setViewPager(viewPager);

    }

    //return order of the fragments in the viewpager
    public class ItinerariesPagerAdapter extends FragmentPagerAdapter {
        final int PAGE_COUNT = 3;
        final String[] tabTitles = {"Explore", "Saved", "My Plans"};
        public ItinerariesPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if ( position==0 ) {
                return new ItineraryListFragment();
            } else if (position == 1) {
                return new SavedItineraryListFragment();
                //TODO: RETURN SAVED LIST
            } else if (position == 2) {
               return new CreatedItineraryListFragment();
                //TODO: RETURN VISITED LIST
            } else {
                return null;
            }
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment createdFragment = (Fragment) super.instantiateItem(container, position);
            // save the appropriate reference depending on position
            switch (position) {
                case 0:
                    itineraryListFragment = (ItineraryListFragment) createdFragment;
                    break;
                case 1:
                    savedItineraryListFragment = (SavedItineraryListFragment) createdFragment;
                    break;
                case 2:
                    createdItineraryListFragment = (CreatedItineraryListFragment) createdFragment;
                    break;
            }
            return createdFragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
//            return super.getPageTitle(position);
            return tabTitles[position];
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {

            String newItineraryId = (String) data.getStringExtra("NEW_ITINERARY_ID");
            if (itineraryListFragment != null) {
                itineraryListFragment.newItineraryCreated(newItineraryId);
            }

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // perform query here
                if (itineraryListFragment != null) {
                    itineraryListFragment.getItineraries(query);
                }

                // workaround to avoid issues with some emulators and keyboard devices firing twice if a keyboard enter is used
                // see https://code.google.com/p/android/issues/detail?id=24599
                searchView.clearFocus();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}
