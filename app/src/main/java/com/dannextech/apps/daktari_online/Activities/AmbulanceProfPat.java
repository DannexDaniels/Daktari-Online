package com.dannextech.apps.daktari_online.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.dannextech.apps.daktari_online.R;

public class AmbulanceProfPat extends AppCompatActivity {

    private TextView tvOrg, tvCounty, tvTown, tvPhone, tvDriver, tvPlateNo, tvCategory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambulance_prof_pat);

        tvOrg = findViewById(R.id.tvAmbOrganization);
        tvCategory = findViewById(R.id.tvAmbCategory);
        tvCounty = findViewById(R.id.tvAmbCounty);
        tvTown = findViewById(R.id.tvAmbTown);
        tvPhone = findViewById(R.id.tvAmbPhone);
        tvDriver = findViewById(R.id.tvAmbDriver);
        tvPlateNo = findViewById(R.id.tvAmbPlateNo);


    }
}
