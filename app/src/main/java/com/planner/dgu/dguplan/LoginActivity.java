package com.planner.dgu.dguplan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import util.SFCallback;
import util.userDTO;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private Button _login;
    private EditText _id, _pw;

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.bt_signin:
                signIn();
                break;
            default: break;
        }
    }

    public void signIn(){
        _id.setError(null);
        _pw.setError(null);
        if(_id.getText().length() != 10){
            _id.setError("유효한 정보를 입력하세요");
            return;
        }
        if(_pw.getText().length() < 5){
            _pw.setError("유효한 패스워드를 입력하세요");
            return;
        }
        final ProgressDialog pdial = new ProgressDialog(this, R.style.AppTheme_Dark_Dialog);
        pdial.setMessage("계정 정보를 불러오는 중...");
        pdial.setCancelable(false);
        pdial.show();
        _login.setEnabled(false);
        IntroActivity.session = new userDTO(new SFCallback() {
            public void callback() {
                pdial.dismiss();
                _login.setEnabled(true);
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        }, new SFCallback() {
            public void callback() {
                pdial.dismiss();
                _login.setEnabled(true);
                toast("인터넷에 연결할 수 없습니다.");
            }
        }, new SFCallback() {
            public void callback() {
                pdial.dismiss();
                _login.setEnabled(true);
                toast("로그인을 실패하였습니다.");
            }
        }, _id.getText().toString(), _pw.getText().toString());
        IntroActivity.session.execute();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        _login = (Button)findViewById(R.id.bt_signin);
        _id = (EditText)findViewById(R.id.idp);
        _pw = (EditText)findViewById(R.id.pwp);
        _login.setOnClickListener(this);
    }

}