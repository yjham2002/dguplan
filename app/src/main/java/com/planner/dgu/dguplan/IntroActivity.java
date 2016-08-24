package com.planner.dgu.dguplan;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import util.SFCallback;
import util.userDTO;

public class IntroActivity extends BaseActivity {

    public static userDTO session;
    private Handler h;
    private int delayTime = 1000;
    private ImageView iv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

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
                session = new userDTO(new SFCallback() {
                    public void callback() {
                        h.postDelayed(intro, delayTime);
                    }
                });
                session.execute();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

    }

    Runnable intro = new Runnable() {
        public void run() {
            Intent i = new Intent(IntroActivity.this, MainActivity.class);
            startActivity(i);
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
