package com.nikhil.otw.webview;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nikhil.otw.webview.utils.SharedPrefUtils;
import com.nikhil.otw.webview.utils.TextValidationUtils;
import com.nikhil.otw.webview.utils.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.fl_content)
    FrameLayout flContent;
    @BindView(R.id.rl_start)
    RelativeLayout rlStart;
    private Handler defaultHandler;
    private final int SPLASH_TIMEOUT_MS = 2000;
    private View mobileScreen;
    private EditText etMobile;
    private TextView tvSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);


        init();
    }

    private void init() {


        defaultHandler = new Handler();
        showLoginOptions();



    }

    private void showLoginOptions() {
        defaultHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                check if user has signed up
                if (!SharedPrefUtils.getString(SplashActivity.this, "mobile").equals("")) {
//                    check if user has logged in

                    startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                    finish();

                } else {

                    ivLogo.startAnimation(AnimationUtils.loadAnimation(SplashActivity.this, R.anim.a_move_up));
                    showMobileScreen();

                }
            }
        }, SPLASH_TIMEOUT_MS);
    }

    private void showMobileScreen() {


        mobileScreen = getLayoutInflater().inflate(R.layout.l_mobile, null);
        etMobile = ButterKnife.findById(mobileScreen, R.id.et_mobile);
        tvSignup = ButterKnife.findById(mobileScreen, R.id.tv_signup_btn);

        flContent.addView(mobileScreen);
        mobileScreen.startAnimation(AnimationUtils.loadAnimation(SplashActivity.this, R.anim.a_fade_in));

        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!TextValidationUtils.isEmpty(etMobile)) {
                    SharedPrefUtils.put(SplashActivity.this, "mobile", etMobile.getText().toString());
                    startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                    finish();
                } else {
                    UIUtils.buildAlertDialog(SplashActivity.this,"","Please enter mobile number","Ok",false).show();
                }

            }
        });


    }
}
