package com.dannextech.apps.daktari_online.Activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.dannextech.apps.daktari_online.R;
import com.dannextech.apps.daktari_online.adapter.PharmacyAdapter;
import com.dannextech.apps.daktari_online.model.PharmacyModel;
import com.dannextech.apps.daktari_online.utils.Utilities;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PharmacyList extends AppCompatActivity {

    private RecyclerView rvPharmList;

    private Location myLoc;

    private ArrayList<PharmacyModel> pharmList = new ArrayList<>();

    public DatabaseReference mReference;

    private double latitude, longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy_list);

        myLoc = new Location("");

        rvPharmList = findViewById(R.id.rvPharmList);

        mReference = FirebaseDatabase.getInstance().getReference().child("Pharmacy");

        mReference.keepSynced(true);

        final Utilities utils = new Utilities();

        FusedLocationProviderClient mFusedLocationClient = LocationServices.getFusedLocationProviderClient(PharmacyList.this);

        if (ActivityCompat.checkSelfPermission(PharmacyList.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(PharmacyList.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Toast.makeText(getApplicationContext(), "You need to allow this app to access location", Toast.LENGTH_SHORT).show();

            askForPermission();
            return;
        }
        utils.showProgressDialog(PharmacyList.this,"Loading Data","Please Wait");
        mFusedLocationClient.getLastLocation().addOnSuccessListener(PharmacyList.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                // Got last known location. In some rare situations this can be null.
                if (location != null) {
                    myLoc.setLongitude(location.getLongitude());
                    myLoc.setLatitude(location.getLatitude());
                }
            }
        });

        Log.e("Dannex", "onBindViewHolder: "+longitude+" lksdjflsj "+latitude);

        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    PharmacyModel ambModel = snapshot.getValue(PharmacyModel.class);
                    pharmList.add(ambModel);
                    Log.e("Dannex", "onDataChange: "+ambModel.getName());
                }

                rvPharmList.hasFixedSize();
                rvPharmList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                rvPharmList.setAdapter(new PharmacyAdapter(pharmList,myLoc));
                utils.hideProgressDialog();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void askForPermission() {
        ActivityCompat.requestPermissions(PharmacyList.this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                1);
    }
}
