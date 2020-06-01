package com.dannextech.apps.daktari_online.Activities;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.dannextech.apps.daktari_online.R;
import com.dannextech.apps.daktari_online.utils.Utilities;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PatientProfile extends AppCompatActivity {

    private TextView tvdob,tvemail, tvname, tvgender,tvidno,tvinsno, tvinsurance, tvkingender, tvkinmail, tvkinname, tvkinphone, tvkinrelation, tvphone;

    Utilities utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_profile);

        utils = new Utilities();
        utils.showProgressDialog(PatientProfile.this,"Loading","please wait...");

        tvname = findViewById(R.id.tvNameProfile);
        tvidno = findViewById(R.id.tvIDProfile);
        tvgender = findViewById(R.id.tvGenderProfile);
        tvdob = findViewById(R.id.tvDOBProfile);

        tvphone = findViewById(R.id.tvPhoneProfile);
        tvemail = findViewById(R.id.tvEmailProfile);

        tvinsno = findViewById(R.id.tvInsuranceNoProfile);
        tvinsurance = findViewById(R.id.tvInsuranceNameProfile);

        tvkinname = findViewById(R.id.tvKinNameProfile);
        tvkingender = findViewById(R.id.tvKinGenderProfile);
        tvkinphone = findViewById(R.id.tvKinPhoneProfile);
        tvkinmail = findViewById(R.id.tvKinEmailProfile);
        tvkinrelation = findViewById(R.id.tvKinRelationProfile);

        SharedPreferences preferences = getSharedPreferences("user",MODE_PRIVATE);

        retrieveUserDetails(preferences.getString("reference", "null"));

    }

    private void retrieveUserDetails(String uid) {

        //creating a reference to the folder users where the details will be saved
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference().child("Patients/SjARgrkgShPMADhJXphIyinKfIX2");
        DatabaseReference dobRef = databaseReference.getRef().child("dob");
        DatabaseReference emailRef = databaseReference.getRef().child("email");
        DatabaseReference fnameRef = databaseReference.getRef().child("fname");
        DatabaseReference genderRef = databaseReference.getRef().child("gender");
        DatabaseReference idRef = databaseReference.getRef().child("idno");
        DatabaseReference insnoRef = databaseReference.getRef().child("insno");
        DatabaseReference insuranceRef = databaseReference.getRef().child("insurance");
        DatabaseReference kingenderRef = databaseReference.getRef().child("kingender");
        DatabaseReference kinmailRef = databaseReference.getRef().child("kinmail");
        DatabaseReference kinnameRef = databaseReference.getRef().child("kinname");
        DatabaseReference kinphoneRef = databaseReference.getRef().child("kinphone");
        DatabaseReference kinrelationRef = databaseReference.getRef().child("kinrelation");
        DatabaseReference lnameRef = databaseReference.getRef().child("lname");
        DatabaseReference phoneRef = databaseReference.getRef().child("phone");
        DatabaseReference surnameRef = databaseReference.getRef().child("surname");

        final String[] surname = new String[1];
        final String[] fname = new String[1];
        final String[] lname = new String[1];

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("Profile Log", "onDataChange: details are "+dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        surnameRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                surname[0] = dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        fnameRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                fname[0] = dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        lnameRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lname[0] = dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        idRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                setIdNumber(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        genderRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                setGender(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        dobRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                setDOB(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        phoneRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                setPhone(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        emailRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                setEmail(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        insnoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                setInsuranceNumber(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        insuranceRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                setInsurance(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        kinnameRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                setKinName(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        kingenderRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                setKinGender(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        kinphoneRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                setKinPhone(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        kinmailRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                setKinMail(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        kinrelationRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                setKinRelation(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        setNameFull(surname[0], fname[0], lname[0]);


    }

    private void setNameFull(String s, String f, String l){
        tvname.setText(s + " " + f + " " + l);
        utils.hideProgressDialog();
    }

    private void setKinRelation(String s) {
        tvkinrelation.setText(s);
    }

    private void setKinMail(String s) {
        tvkinmail.setText(s);
    }

    private void setKinPhone(String s) {
        tvkinphone.setText(s);
    }

    private void setEmail(String s) {
        tvemail.setText(s);
    }

    private void setIdNumber(String s) {
        tvidno.setText(s);
    }

    private void setPhone(String s) {
        tvphone.setText(s);
    }

    private void setDOB(String s) {
        tvdob.setText(s);
    }

    private void setGender(String s) {
        tvgender.setText(s);
    }

    private void setKinGender(String s) {
        tvkingender.setText(s);
    }

    private void setKinName(String s) {
        tvkinname.setText(s);
    }

    private void setInsurance(String s) {
        tvinsurance.setText(s);
    }

    private void setInsuranceNumber(String s) {
        tvinsno.setText(s);
    }
}
