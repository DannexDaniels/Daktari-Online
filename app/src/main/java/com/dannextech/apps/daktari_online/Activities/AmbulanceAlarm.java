package com.dannextech.apps.daktari_online.Activities;

import android.content.Context;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.dannextech.apps.daktari_online.R;

public class AmbulanceAlarm extends AppCompatActivity {

    private ImageView btnAlarm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambulance_alarm);

        final Vibrator vibrator = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(60000); //

        btnAlarm = findViewById(R.id.btnAmbAlarm);
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);

        btnAlarm.startAnimation(myAnim);

        btnAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAlarm.clearAnimation();
                vibrator.cancel();
            }
        });
    }
}
