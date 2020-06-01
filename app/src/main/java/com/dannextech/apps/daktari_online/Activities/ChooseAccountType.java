package com.dannextech.apps.daktari_online.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.dannextech.apps.daktari_online.R;


public class ChooseAccountType extends AppCompatActivity implements View.OnClickListener {

    LinearLayout btnPatient, btnDoctor, btnAmbulance, btnPharmacy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_account_type);

        btnAmbulance = findViewById(R.id.btnAmbulance);
        btnPatient = findViewById(R.id.btnPatient);
        btnDoctor = findViewById(R.id.btnDoctor);
        btnPharmacy = findViewById(R.id.btnPharmacy);

        btnPatient.setOnClickListener(this);
        btnAmbulance.setOnClickListener(this);
        btnDoctor.setOnClickListener(this);
        btnPharmacy.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnPatient:
                startActivity(new Intent(ChooseAccountType.this,PatientCreateAccount.class));
                break;
            case R.id.btnDoctor:
                break;
            case R.id.btnPharmacy:
                startActivity(new Intent(ChooseAccountType.this, PharmacyCreateAccount.class));
                break;
            case R.id.btnAmbulance:
                startActivity(new Intent(ChooseAccountType.this, AmbulanceCreateAccount.class));
                break;
                default:
                    Snackbar.make(v,"Wrong Selection",Snackbar.LENGTH_SHORT).show();
        }
    }
}
