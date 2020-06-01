package com.dannextech.apps.daktari_online.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.dannextech.apps.daktari_online.R;
import com.dannextech.apps.daktari_online.database.Queries;
import com.dannextech.apps.daktari_online.utils.Utilities;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class AmbulanceCreateAccount extends AppCompatActivity {

    private EditText etOrgName, etPlateNo, etDriver, etPhoneMain, etPhoneOther, etEmailMain, etEmailOther,etLocDesc,etPass, etConfPass;
    private Spinner spCounty, spTowns,spCatAmb;
    private FloatingActionButton fabNext, fabBack;
    private ViewFlipper vfCreateAccountAmb;

    private double latitude, longitude;

    private boolean locSet;

    private Utilities utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambulance_create_account);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fabBack = findViewById(R.id.fabBackAmb);
        fabNext = findViewById(R.id.fabNextAmb);

        etOrgName = findViewById(R.id.etNameOrg);
        etPlateNo = findViewById(R.id.etPlateNumber);
        etDriver = findViewById(R.id.etNameDriver);
        etPhoneMain = findViewById(R.id.etphoneMainAmb);
        etPhoneOther = findViewById(R.id.etphoneOtherAmb);
        etEmailMain = findViewById(R.id.etEmailMainAmb);
        etEmailOther = findViewById(R.id.etEmailOtherAmb);
        etLocDesc = findViewById(R.id.etLocationDescriptionAmb);
        etPass = findViewById(R.id.etPasswordAmb);
        etConfPass = findViewById(R.id.etConfPassAmb);

        spCatAmb = findViewById(R.id.spAmbCat);
        spCounty = findViewById(R.id.spCountyAmb);
        spTowns = findViewById(R.id.spTownAmb);

        vfCreateAccountAmb = findViewById(R.id.vfCreateAccountAmbulance);

        locSet = false;

        utils = new Utilities();

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

            utils.askForLocationPermission(AmbulanceCreateAccount.this);
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

        findViewById(R.id.ivBackAmb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AmbulanceCreateAccount.this, ChooseAccountType.class));
            }
        });

        findViewById(R.id.btnFinishRegisterAmb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (utils.isNetworkAvailable(getApplicationContext())) {
                    utils.showProgressDialog(AmbulanceCreateAccount.this,"Creating Account","Loading");
                    uploadAmbulanceDetails(etOrgName.getText().toString().trim(),spCatAmb.getSelectedItem().toString().trim(),etPlateNo.getText().toString().trim(),etDriver.getText().toString().trim(),
                            etPhoneMain.getText().toString().trim(),etPhoneOther.getText().toString().trim(),etEmailMain.getText().toString().trim(),etEmailOther.getText().toString().trim(),
                            spCounty.getSelectedItem().toString().trim(),spTowns.getSelectedItem().toString().trim(),etLocDesc.getText().toString().trim(), etPass.getText().toString().trim());
                }else {
                    Snackbar.make(v,"You need to be online to finish",Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.btnLocationYesAmb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locSet = true;
                Snackbar.make(v, "Location has been set",Snackbar.LENGTH_SHORT).show();
                etLocDesc.setVisibility(View.VISIBLE);
            }
        });
        findViewById(R.id.btnLocationNoAmb).setOnClickListener(new View.OnClickListener() {
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

    public void nextView(){
        vfCreateAccountAmb.setInAnimation(AmbulanceCreateAccount.this,R.anim.slide_in_right);
        vfCreateAccountAmb.setOutAnimation(AmbulanceCreateAccount.this,R.anim.slide_out_left);
        vfCreateAccountAmb.showNext();

        int childView = vfCreateAccountAmb.getDisplayedChild();

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

        vfCreateAccountAmb.setInAnimation(AmbulanceCreateAccount.this,android.R.anim.slide_in_left);
        vfCreateAccountAmb.setOutAnimation(AmbulanceCreateAccount.this,android.R.anim.slide_out_right);
        vfCreateAccountAmb.showPrevious();

        int childView = vfCreateAccountAmb.getDisplayedChild();

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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Toast.makeText(AmbulanceCreateAccount.this, "Permission granted to access location", Toast.LENGTH_SHORT).show();
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(AmbulanceCreateAccount.this, "Permission denied to access location", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void uploadAmbulanceDetails(String orgName, String orgCat, String plateNo, String driver,
                                       String phoneMain, String phoneOther, String emailMain, String emailOthers,
                                       String county, String town, String locdesc,String password) {
        //creating a reference to the folder users where the details will be saved
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference().child("Ambulance/" + etPlateNo.getText().toString().trim());

        //creating references to where specific details will be saved
        DatabaseReference orgRef = databaseReference.child("organization");
        DatabaseReference catRef = databaseReference.child("category");
        DatabaseReference vehicleRef = databaseReference.child("vehicleNo");
        DatabaseReference driverRef = databaseReference.child("driver");
        DatabaseReference passRef = databaseReference.child("password");

        DatabaseReference phoneMainRef = databaseReference.child("phoneMain");
        DatabaseReference emailMainRef = databaseReference.child("emailMain");
        DatabaseReference phoneOtherRef = databaseReference.child("phoneOther");
        DatabaseReference emailOtherRef = databaseReference.child("emailOther");

        DatabaseReference countyRef = databaseReference.child("county");
        DatabaseReference townRef = databaseReference.child("town");
        DatabaseReference descRef = databaseReference.child("description");
        DatabaseReference longRef = databaseReference.child("longitude");
        DatabaseReference lattRef = databaseReference.child("lattitude");

        //Setting the values
        orgRef.setValue(orgName);
        catRef.setValue(orgCat);
        driverRef.setValue(driver);
        vehicleRef.setValue(plateNo);
        passRef.setValue(password);

        phoneMainRef.setValue(phoneMain);
        phoneOtherRef.setValue(phoneOther);
        emailMainRef.setValue(emailMain);
        emailOtherRef.setValue(emailOthers);

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
                utils.hideProgressDialog();
                Toast.makeText(AmbulanceCreateAccount.this,"Account created Successfully",Toast.LENGTH_SHORT).show();
                //Add User Locally
                Queries query = new Queries(AmbulanceCreateAccount.this);
                query.addUser(etPlateNo.getText().toString().trim(),etPhoneMain.getText().toString().trim(),etPass.getText().toString().trim(), "ambulance");
                query.addFirstTimer(etPhoneMain.getText().toString().trim(),"false");

                startActivity(new Intent(AmbulanceCreateAccount.this, Login.class));
            }
        });
    }


}
