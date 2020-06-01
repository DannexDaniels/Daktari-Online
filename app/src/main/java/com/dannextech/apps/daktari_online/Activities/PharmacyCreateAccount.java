package com.dannextech.apps.daktari_online.Activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.dannextech.apps.daktari_online.R;
import com.dannextech.apps.daktari_online.database.Queries;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class PharmacyCreateAccount extends AppCompatActivity {

    private EditText etPharmacyName, etSurnameSup, etFirstNameSup, etLastNameSup, etLocDesc, etPhone, etEmail, etLicenseSup, etLicencePharmacy,etPassword, etConfPass;
    private Spinner spCounty, spTowns;
    private FloatingActionButton fabNext, fabBack;
    private ViewFlipper vfCreateAccountPharmacy;
    private ProgressDialog progressDialog;

    private double latitude, longitude;

    private boolean locSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy_create_account);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fabBack = findViewById(R.id.fabBackPharmacy);
        fabNext = findViewById(R.id.fabNextPharmacy);

        spCounty = findViewById(R.id.spCountyPharmacy);
        spTowns = findViewById(R.id.spTownPharmacy);

        etPharmacyName = findViewById(R.id.etNamePharmacy);
        etLicencePharmacy = findViewById(R.id.etLicenceNumberPharmacy);
        etPhone = findViewById(R.id.etphonePharmacy);
        etEmail = findViewById(R.id.etEmailPharmacy);
        etSurnameSup = findViewById(R.id.etSurnameNameSuperintendent);
        etFirstNameSup = findViewById(R.id.etFirstNameSuperintendent);
        etLastNameSup = findViewById(R.id.etLastNameSuperintendent);
        etLicenseSup = findViewById(R.id.etLicenceNumberSuperintendent);
        etLocDesc = findViewById(R.id.etLocationDescription);

        vfCreateAccountPharmacy = findViewById(R.id.vfCreateAccountPharmacy);

        locSet = false;

        FusedLocationProviderClient mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Toast.makeText(this, "You need to allow this app to access location", Toast.LENGTH_SHORT).show();

            askForPermission();
            return;
        }
        mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                // Got last known location. In some rare situations this can be null.
                if (location != null) {
                    longitude = location.getLongitude();
                    latitude = location.getLatitude();
                }
            }
        });

        findViewById(R.id.ivBackPharmacy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PharmacyCreateAccount.this, ChooseAccountType.class));
            }
        });

        findViewById(R.id.btnFinishRegisterPharmacy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkAvailable()) {
                    showCreatingAccountDialog();
                    uploadPharmacyDetails(etPharmacyName.getText().toString().trim(),etLicencePharmacy.getText().toString(),etPhone.getText().toString().trim(),etEmail.getText().toString().trim(),
                            etSurnameSup.getText().toString().trim(), etFirstNameSup.getText().toString().trim(),etLastNameSup.getText().toString().trim(),etLicenseSup.getText().toString().trim(),
                            spCounty.getSelectedItem().toString().trim(),spTowns.getSelectedItem().toString().trim(),etLocDesc.getText().toString().trim(),etPassword.getText().toString().trim());
                }else {
                    Snackbar.make(v,"You need to be online to finish",Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.btnLocationYes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locSet = true;
                Snackbar.make(v, "Location has been set",Snackbar.LENGTH_SHORT).show();
                etLocDesc.setVisibility(View.VISIBLE);
            }
        });
        findViewById(R.id.btnLocationNo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (locSet){
                    Snackbar.make(v,"Location has been un set",Snackbar.LENGTH_SHORT).show();
                    locSet = false;
                }
                etLocDesc.setVisibility(View.VISIBLE);

            }
        });

        fabNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextView();
            }
        });

        fabBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousView();
            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Log.e("DannexDaniels", "onRequestPermissionsResult: Pemission granted" );
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(PharmacyCreateAccount.this, "Permission denied to access location", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void askForPermission() {
        ActivityCompat.requestPermissions(PharmacyCreateAccount.this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                1);
    }


    private void uploadPharmacyDetails(String pharmName, String pharmNo, String phone, String email,
                                       String surnameSup, String fnameSup, String lnameSup, String supNo,
                                       String county, String town, String locdesc,String password) {
        //creating a reference to the folder users where the details will be saved
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference().child("Pharmacy/" + etLicencePharmacy.getText().toString().trim());


        //creating references to where specific details will be saved
        DatabaseReference pharmNameRef = databaseReference.child("name");
        DatabaseReference pharmNoRef = databaseReference.child("license");
        DatabaseReference passRef = databaseReference.child("password");

        DatabaseReference surnameRef = databaseReference.child("supsurname");
        DatabaseReference fnameRef = databaseReference.child("supfname");
        DatabaseReference lnameRef = databaseReference.child("suplname");
        DatabaseReference supNoRef = databaseReference.child("suplicense");

        DatabaseReference countyRef = databaseReference.child("county");
        DatabaseReference townRef = databaseReference.child("town");
        DatabaseReference descRef = databaseReference.child("description");
        DatabaseReference longRef = databaseReference.child("longitude");
        DatabaseReference lattRef = databaseReference.child("lattitude");

        DatabaseReference phoneRef = databaseReference.child("phone");
        DatabaseReference emailRef = databaseReference.child("email");

        //Setting the values
        pharmNameRef.setValue(pharmName);
        pharmNoRef.setValue(pharmNo);
        passRef.setValue(password);

        phoneRef.setValue(phone);
        emailRef.setValue(email);

        surnameRef.setValue(surnameSup);
        fnameRef.setValue(fnameSup);
        lnameRef.setValue(lnameSup);
        supNoRef.setValue(supNo);

        countyRef.setValue(county);
        townRef.setValue(town);
        if (locSet){
            lattRef.setValue(""+latitude);
            longRef.setValue(""+longitude);
        }else{
            lattRef.setValue("0.0");
            longRef.setValue("0.0");
        }
        descRef.setValue(locdesc, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                hideCreatingAccountDialog();
                Toast.makeText(PharmacyCreateAccount.this,"Account created Successfully",Toast.LENGTH_SHORT).show();
                //Add User Locally
                Queries query = new Queries(PharmacyCreateAccount.this);
                query.addUser(etLicencePharmacy.getText().toString().trim(),etPhone.getText().toString().trim(),etPassword.getText().toString().trim(), "pharmacy");
                query.addFirstTimer(etPhone.getText().toString().trim(),"false");

                startActivity(new Intent(PharmacyCreateAccount.this, ChooseAccountType.class));
            }
        });




    }

    private void showCreatingAccountDialog() {
        progressDialog = ProgressDialog.show(PharmacyCreateAccount.this,"Creating Account","Please Wait",true);
    }

    private void hideCreatingAccountDialog() {
        progressDialog.dismiss();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = null;
        if (connectivityManager != null)
            activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    public void nextView(){
        vfCreateAccountPharmacy.setInAnimation(PharmacyCreateAccount.this,R.anim.slide_in_right);
        vfCreateAccountPharmacy.setOutAnimation(PharmacyCreateAccount.this,R.anim.slide_out_left);
        vfCreateAccountPharmacy.showNext();

        int childView = vfCreateAccountPharmacy.getDisplayedChild();

        if (childView == 0){
            fabBack.hide();
            fabNext.show();
        }else if (childView == 1){
            fabBack.show();
            fabNext.show();
        }else if (childView == 2){
            fabBack.show();
            fabNext.show();
        }else if(childView == 3){
            fabBack.show();
            fabNext.hide();
        }
    }

    public void previousView(){

        vfCreateAccountPharmacy.setInAnimation(PharmacyCreateAccount.this,android.R.anim.slide_in_left);
        vfCreateAccountPharmacy.setOutAnimation(PharmacyCreateAccount.this,android.R.anim.slide_out_right);
        vfCreateAccountPharmacy.showPrevious();

        int childView = vfCreateAccountPharmacy.getDisplayedChild();

        if (childView == 0){
            fabBack.hide();
            fabNext.show();
        }else if (childView == 1){
            fabBack.show();
            fabNext.show();
        }else if (childView == 2){
            fabBack.show();
            fabNext.show();
        }else if(childView == 3){
            fabBack.show();
            fabNext.hide();
        }
    }
}
