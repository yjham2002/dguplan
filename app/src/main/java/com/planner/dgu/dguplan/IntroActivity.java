package com.planner.dgu.dguplan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import util.SFCallback;
import util.userDTO;

public class IntroActivity extends BaseActivity {

    private SharedPreferences prefs;
    private SharedPreferences.Editor prefEditor;
    public static userDTO session;
    private Handler h;
    private int delayTime = 1200;
    private ImageView iv;
    private boolean isSuccess = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        isSuccess = true;
        prefs = getSharedPreferences("DGUPLAN", MODE_PRIVATE);
        prefEditor = prefs.edit();

        iv = (ImageView)findViewById(R.id.imageView);
        iv.setDrawingCacheEnabled(true);

        AnimationSet animset = new AnimationSet(false);
        Animation anim2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alpha_anim);
        animset.addAnimation(anim2);
        iv.startAnimation(animset);
        h = new Handler();

        animset.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if(prefs.getBoolean("autoLogin", false)){
                    IntroActivity.session = new userDTO(new SFCallback() {
                        public void callback() {
                            isSuccess = true;
                            h.postDelayed(intro, delayTime);
                        }
                    }, new SFCallback() {
                        public void callback() {
                            isSuccess = false;
                            toast("인터넷에 연결할 수 없습니다.");
                            h.postDelayed(intro, delayTime);
                        }
                    }, new SFCallback() {
                        public void callback() {
                            isSuccess = false;
                            toast("로그인을 실패하였습니다.");
                            h.postDelayed(intro, delayTime);
                        }
                    }, prefs.getString("userId", "#"), prefs.getString("userPw", "#"));
                    IntroActivity.session.execute();
                }else {
                    isSuccess = false;
                    h.postDelayed(intro, delayTime);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

    }

    Runnable intro = new Runnable() {
        public void run() {
            Intent i = new Intent(IntroActivity.this, LoginActivity.class);
            Intent ii = new Intent(IntroActivity.this, MainActivity.class);
            if(!isSuccess) startActivity(i);
            else startActivity(ii);
            finish();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        h.removeCallbacks(intro);
    }
}
