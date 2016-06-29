package com.mh.scrollablelayout;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.mh.scrollablelayout.lib.ScrollableLayout;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_invest);
        initView();
    }

    private void initView() {
        PtrClassicFrameLayout pfl_root= (PtrClassicFrameLayout) findViewById(R.id.pfl_root);
        ScrollableLayout sl_root= (ScrollableLayout) findViewById(R.id.sl_root);
        ViewPager vp_scroll= (ViewPager) findViewById(R.id.vp_scroll);

        findViewById(R.id.tv_grandtotal);//累计总收益
        findViewById(R.id.tv_grandtotal_value);
        findViewById(R.id.tv_uncollected);//本月待收
        findViewById(R.id.tv_collected);//本月回款


        findViewById(R.id.tv_page1);
        findViewById(R.id.tv_page2);
        findViewById(R.id.line);

        findViewById(R.id.vp_scroll);
        findViewById(R.id.vp_scroll);
        findViewById(R.id.vp_scroll);
        findViewById(R.id.vp_scroll);
        findViewById(R.id.vp_scroll);
        findViewById(R.id.vp_scroll);

    }
}
