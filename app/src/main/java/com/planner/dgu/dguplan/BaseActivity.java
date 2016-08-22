package com.planner.dgu.dguplan;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

public class BaseActivity extends Activity {

    public void toast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setContentView(int layoutResID) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.setContentView(layoutResID);
    }

}
