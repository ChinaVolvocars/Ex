package com.mh.scrollablelayout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mh.scrollablelayout.lib.ScrollableLayout;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

public class Main2Activity extends AppCompatActivity implements PtrHandler, View.OnClickListener, ViewPager.OnPageChangeListener {

    private PtrClassicFrameLayout pfl_root;
    private ScrollableLayout sl_root;
    private ViewPager vp_scroll;

    private List<BaseFragment> fragments = new ArrayList<>();
    private RelativeLayout ly_page1;
    private RelativeLayout ly_page2;
    private View line;

    private int line_width;
    private TextView tv_page1;
    private TextView tv_page2;

    private float titleMaxScrollHeight;
    private float hearderMaxHeight;
    private float avatarTop;
    private float maxScrollHeight;
    private TextView tv_title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_invest);
        initView();
    }

    private void initView() {
        pfl_root = (PtrClassicFrameLayout) findViewById(R.id.pfl_root);
        sl_root = (ScrollableLayout) findViewById(R.id.sl_root);
        vp_scroll = (ViewPager) findViewById(R.id.vp_scroll);
        line = findViewById(R.id.line);

        findViewById(R.id.tv_grandtotal);//累计总收益
        findViewById(R.id.tv_grandtotal_value);
        findViewById(R.id.tv_uncollected);//本月待收
        findViewById(R.id.tv_collected);//本月回款

        tv_title = (TextView) findViewById(R.id.tv_top_title);

        ly_page1 = (RelativeLayout) findViewById(R.id.ly_page1);
        ly_page2 = (RelativeLayout) findViewById(R.id.ly_page2);

        tv_page1 = (TextView) findViewById(R.id.tv_page1);
        tv_page2 = (TextView) findViewById(R.id.tv_page2);


//        findViewById(R.id.vp_scroll);
//        findViewById(R.id.vp_scroll);
//        findViewById(R.id.vp_scroll);

        sl_root.setOnScrollListener(new ScrollableLayout.OnScrollListener() {
            @Override
            public void onScroll(int currentY, int maxY) {
                currentY = -currentY;
                if (titleMaxScrollHeight == 0) {
                    titleMaxScrollHeight = ((View) tv_title.getParent()).getBottom() - tv_title.getTop();
                    maxScrollHeight = hearderMaxHeight + titleMaxScrollHeight;
                }
                if (hearderMaxHeight == 0) {
                    //   hearderMaxHeight = tv_name.getTop();
                    maxScrollHeight = hearderMaxHeight + titleMaxScrollHeight;
                }
                if (avatarTop == 0) {
                    // avatarTop = iv_avatar.getTop();
                }

                int alpha = 0;
                int baseAlpha = 60;
                if (0 > avatarTop + currentY) {
                    alpha = Math.min(255, (int) (Math.abs(avatarTop + currentY) * (255 - baseAlpha) / (hearderMaxHeight - avatarTop) + baseAlpha));
                    //  iv_spit.setVisibility(View.VISIBLE);
                } else {
                    // iv_spit.setVisibility(View.GONE);
                }

                // iv_spit.getBackground().setAlpha(alpha);

                // tv_title.setTranslationY(Math.max(0, maxScrollHeight + translationY));

            }
        });

        pfl_root.setEnabledNextPtrAtOnce(true);
        pfl_root.setLastUpdateTimeRelateObject(this);
        pfl_root.setPtrHandler(this);
        pfl_root.setKeepHeaderWhenRefresh(true);

        CommAdapter commAdapter = new CommAdapter(getSupportFragmentManager());
        fragments.add(RecyclerViewSimpleFragment.newInstance());
        fragments.add(RecyclerViewSimpleFragment2.newInstance());

        vp_scroll.addOnPageChangeListener(this);
        line_width = getWindowManager().getDefaultDisplay().getWidth() / fragments.size();
        line.getLayoutParams().width = line_width;
        line.requestLayout();

        vp_scroll.setAdapter(commAdapter);
//        vp_scroll.addOnPageChangeListener(this);
        sl_root.getHelper().setCurrentScrollableContainer(fragments.get(0));

        ly_page1.setOnClickListener(this);
        ly_page2.setOnClickListener(this);

    }

    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        if (vp_scroll.getCurrentItem() == 0 && sl_root.isCanPullToRefresh()) {
            return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
        }
        if (vp_scroll.getCurrentItem() == 1 && sl_root.isCanPullToRefresh()) {
            return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
        }
        return false;
    }

    @Override
    public void onRefreshBegin(PtrFrameLayout frame) {
        if (fragments.size() > vp_scroll.getCurrentItem()) {
            fragments.get(vp_scroll.getCurrentItem()).pullToRefresh();
        }
    }

    public void refreshComplete() {
        if (pfl_root != null) {
            pfl_root.refreshComplete();
        }
    }



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
        sl_root.getHelper().setCurrentScrollableContainer(fragments.get(position));

        changeState(position);
    }


    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public class CommAdapter extends FragmentPagerAdapter {
        public CommAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return getCount() > position ? fragments.get(position) : null;
        }

        @Override
        public int getCount() {
            return fragments == null ? 0 : fragments.size();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ly_page1:
                vp_scroll.setCurrentItem(0);
                break;
            case R.id.ly_page2:
                vp_scroll.setCurrentItem(1);
                break;
        }
    }
    /**
     * 根据传入的值改变状态
     *
     * @param position
     */
    private void changeState(int position) {
        if (position == 0) {
            tv_page1.setTextColor(getResources().getColor(R.color.text_invest_color1));
            tv_page2.setTextColor(getResources().getColor(R.color.text_invest_color2));

//            tv_page1.setTextSize(getResources().getDimension(R.dimen.text_invest_size1));
//            tv_page2.setTextSize(getResources().getDimension(R.dimen.text_invest_size2));
        } else {
            tv_page1.setTextColor(getResources().getColor(R.color.text_invest_color2));
            tv_page2.setTextColor(getResources().getColor(R.color.text_invest_color1));

//            tv_page1.setTextSize(getResources().getDimension(R.dimen.text_invest_size2));
//            tv_page2.setTextScaleX(getResources().getDimension(R.dimen.text_invest_size1));
        }

    }
}
