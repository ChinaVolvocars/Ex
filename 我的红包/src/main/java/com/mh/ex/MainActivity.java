package com.mh.ex;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity {
    private ArrayList<Fragment> fragments;

    private ViewPager viewPager;

    private TextView tab_game;

    private TextView tab_app;

    private int line_width;

    private View line;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tab_game = (TextView) findViewById(R.id.tab_game);
        tab_app = (TextView) findViewById(R.id.tab_app);
        line = findViewById(R.id.line);

        fragments = new ArrayList<Fragment>();
        fragments.add(new APPFragment());
        fragments.add(new GameFragment());
        line_width = getWindowManager().getDefaultDisplay().getWidth() / fragments.size();

        line.getLayoutParams().width = line_width;
        line.requestLayout();

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new FragmentStatePagerAdapter(
                getSupportFragmentManager()) {

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public Fragment getItem(int positon) {
                return fragments.get(positon);
            }
        });

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                float tagerX = position * line_width + positionOffset / fragments.size();
                Animation animation = new TranslateAnimation(tagerX, line_width * position, 0, 0);
                animation.setDuration(100);
                animation.setFillAfter(true);
                line.startAnimation(animation);


            }

            @Override
            public void onPageSelected(int position) {
                changeState(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        tab_game.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1);

            }
        });

        tab_app.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0);
            }
        });
    }

    /* 根据传入的值来改变状态 */
    private void changeState(int position) {
        if (position == 0) {
            tab_app.setTextColor(getResources().getColor(R.color.green));
            tab_game.setTextColor(getResources().getColor(R.color.gray_white));
        } else {
            tab_game.setTextColor(getResources().getColor(R.color.green));
            tab_app.setTextColor(getResources().getColor(R.color.gray_white));
        }
    }


}
