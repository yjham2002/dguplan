package com.planner.dgu.dguplan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import util.userDTO;

public class MainActivity extends FragmentActivity implements View.OnClickListener{

    private SharedPreferences prefs;
    private SharedPreferences.Editor prefEditor;
    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;
    private Button menu2, menu3, menus;
    private TextView tv1, tv2;
    private DrawerArrowDrawable drawerArrowDrawable;
    private float offset;
    private boolean flipped;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences("DGUPLAN", MODE_PRIVATE);
        prefEditor = prefs.edit();

        prefEditor.putBoolean("autoLogin", true);
        prefEditor.putString("userId", userDTO.userId);
        prefEditor.putString("userPw", userDTO.userPw);
        prefEditor.commit();

        menu2 = (Button)findViewById(R.id.menu2);
        menu3 = (Button)findViewById(R.id.menu3);
        menus = (Button)findViewById(R.id.menus);

        menu2.setOnClickListener(this);
        menu3.setOnClickListener(this);
        menus.setOnClickListener(this);

        tv2 = (TextView)findViewById(R.id.sName); // Get textView instance from view
        tv2.setText(userDTO.userName);
        tv1 = (TextView)findViewById(R.id.sID); // Get textView instance from view
        tv1.setText(userDTO.userId);

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        final ImageView imageView = (ImageView) findViewById(R.id.drawer_indicator);
        final Resources resources = getResources();
        drawerArrowDrawable = new DrawerArrowDrawable(resources);
        drawerArrowDrawable.setStrokeColor(resources.getColor(R.color.white));
        imageView.setImageDrawable(drawerArrowDrawable);

        mSectionsPagerAdapter = new MainActivity.SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        drawer.setDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                offset = slideOffset;

                // Sometimes slideOffset ends up so close to but not quite 1 or 0.
                if (slideOffset >= .995) {
                    flipped = true;
                    drawerArrowDrawable.setFlip(flipped);
                } else if (slideOffset <= .005) {
                    flipped = false;
                    drawerArrowDrawable.setFlip(flipped);
                }

                drawerArrowDrawable.setParameter(offset);
            }
        });
        // gravity compat added
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerVisible(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });

    }

    public void onClick(View v){
        switch(v.getId()){
            case R.id.menus:
                break;
            case R.id.menu2:
                break;
            case R.id.menu3:
                prefEditor.putBoolean("autoLogin", false);
                prefEditor.commit();
                Intent i = new Intent(this, LoginActivity.class);
                startActivity(i);
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            default: break;
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment temp;
            switch(position){
                case 0: temp = new fragment_main(); break;
                case 1: temp = new fragment_sub_01(); break;
                default: temp = null; break;
            }
            return temp;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String title = null;
            switch (position) {
                case 0:
                    title = userDTO.conn;
                    break;
                case 1:
                    title = "과제 목록";
                    break;
                default: break;

            }
			/*
			SpannableStringBuilder sb = new SpannableStringBuilder("   "); // space added before text for convenience
			try {
				myDrawable.setBounds(1, 1, myDrawable.getIntrinsicWidth(), myDrawable.getIntrinsicHeight());
				ImageSpan span = new ImageSpan(myDrawable, DynamicDrawableSpan.ALIGN_BASELINE);
				sb.setSpan(span, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			} catch (Exception e) {
			}*/
            return title;
        }
    }

    public boolean mFlag;

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            if(msg.what == 0){
                mFlag=false;
            }
        }
    };

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)){
            if(!mFlag) {
                Toast.makeText(getApplicationContext(), "뒤로 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
                mFlag = true;
                mHandler.sendEmptyMessageDelayed(0, 2000);
                return false;
            } else {
                finish();
                System.exit(0);
            }
        }
        return super.onKeyDown(keyCode, event);
    }


}
