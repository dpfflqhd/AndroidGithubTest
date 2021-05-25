package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


import me.relex.circleindicator.CircleIndicator;

public class MainSwipeActivity extends AppCompatActivity {

    FragmentPagerAdapter adapterViewPager;
    Fragment Home;
    SecondFragment secondFragment;
    public static String email = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_swipe);


       /* Bundle arguments = new Bundle();
        arguments.putString("id", email);
        Log.d("bundle, 받아온 데이터 : ", email);

        secondFragment.setArguments(arguments);*/

            /*getSupportFragmentManager().beginTransaction()
                    .replace(R.id.vpPager, secondFragment)
                    .commit();*/



        Intent read_intent = getIntent();

        email = read_intent.getStringExtra("id");

        Log.d("result액티비티, 받아온 데이터 : ", email);




        ViewPager vpPager = (ViewPager) findViewById(R.id.vpPager);

        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());



        vpPager.setAdapter(adapterViewPager);

        vpPager.setCurrentItem(1);



        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);

        indicator.setViewPager(vpPager);






//        Home = new SecondFragment();
//        getSupportFragmentManager().beginTransaction().replace(R.id.vpPager, Home).commit();

    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 3;








        public MyPagerAdapter(FragmentManager fragmentManager) {

            super(fragmentManager);


        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {




            Log.d("getItem email", email);
            switch (position) {
                case 0:
                    return FirstFragment.newInstance(0, email);
                case 1:
                    return SecondFragment.newInstance(1, email);
                case 2:
                    return ThirdFragment.newInstance(2, "Page # 3");
                default:
                    return null;
            }


        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }

    }



}