package com.dannextech.apps.daktari_online.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.crashlytics.android.Crashlytics;
import com.dannextech.apps.daktari_online.R;
import com.dannextech.apps.daktari_online.database.Queries;
import io.fabric.sdk.android.Fabric;

public class SplashScreen extends AppCompatActivity {

    LinearLayout btnGetStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_splash_screen);

        btnGetStarted = findViewById(R.id.btnGetStarted);

        Queries query = new Queries(SplashScreen.this);
        Animation myFadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fadein);
        findViewById(R.id.ivLogoSplashScreen).startAnimation(myFadeInAnimation);

        if (query.getFirstTimer().equals("new")||query.getFirstTimer().equals("true")){
            btnGetStarted.startAnimation(myFadeInAnimation);
        }else {
            btnGetStarted.setVisibility(View.GONE);
            Thread th = new Thread(){
                public void run(){
                    try{
                        sleep(5000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }finally {
                        startActivity(new Intent(SplashScreen.this, PatientHome.class));
                    }
                }
            };
            th.start();
        }

        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SplashScreen.this, ChooseAccountType.class));
            }
        });
    }
}
