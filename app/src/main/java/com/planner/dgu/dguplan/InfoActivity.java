package com.planner.dgu.dguplan;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InfoActivity extends BaseActivity implements View.OnClickListener{

    private Button back;

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.back:
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            default: break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        back = (Button)findViewById(R.id.back);
        back.setOnClickListener(this);
    }
}
