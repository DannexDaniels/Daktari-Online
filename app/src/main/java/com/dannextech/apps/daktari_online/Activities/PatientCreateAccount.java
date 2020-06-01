package com.dannextech.apps.daktari_online.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.chaos.view.PinView;
import com.dannextech.apps.daktari_online.R;
import com.dannextech.apps.daktari_online.database.Queries;
import com.dannextech.apps.daktari_online.utils.Utilities;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class PatientCreateAccount extends AppCompatActivity {

    private ViewFlipper vfCreateAccount;
    private EditText etSurname, etFirstName, etLastName, etIdNumber, etDOB, etInsuranceNo, etKinName, etKinPhone, etKinEmail, etPhone, etEmail, etPassword, etConfPassword;
    private Spinner spGender, spInsurace, spKinGender,spKinRelationship;
    private FloatingActionButton fabNext,fabPrev;
    private PinView pvVerifyCode;

    private FirebaseAuth mAuth;

    private String verificationId;
    private int phone;

    Utilities utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_create_account);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();

        utils = new Utilities();

        fabNext = findViewById(R.id.fabNextPatient);
        fabPrev = findViewById(R.id.fabBackPatient);

        vfCreateAccount = findViewById(R.id.vfCreateAccountPatient);

        etSurname = findViewById(R.id.etSurnamePatient);
        etFirstName = findViewById(R.id.etFirstNamePatient);
        etLastName = findViewById(R.id.etLastNamePatient);
        etIdNumber = findViewById(R.id.etNationalIdPatient);
        etDOB = findViewById(R.id.etDob);
        etInsuranceNo = findViewById(R.id.etInsuranceNumberPatient);
        etKinName = findViewById(R.id.etKinNamePatient);
        etKinPhone = findViewById(R.id.etKinPhonePatient);
        etKinEmail = findViewById(R.id.etKinEmailPatient);
        etPhone = findViewById(R.id.etPhonePatient);
        etEmail = findViewById(R.id.etEmailPatient);
        etPassword = findViewById(R.id.etPasswordPatient);
        etConfPassword = findViewById(R.id.etConfPassPatient);

        spGender = findViewById(R.id.spGenderPatient);
        spInsurace = findViewById(R.id.spInsurance);
        spKinGender = findViewById(R.id.spGenderKinPatient);
        spKinRelationship = findViewById(R.id.spRelationKinPatient);

        pvVerifyCode = findViewById(R.id.pvVerifyCode);

        Button btnFinish = findViewById(R.id.btnFinishRegisterPatient);
        Button btnVerifyCode = findViewById(R.id.btnVerifyCode);

        findViewById(R.id.ivBackPatient).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PatientCreateAccount.this, ChooseAccountType.class));
            }
        });

        //On Click listeners
        fabNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextView();
            }
        });

        fabPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousView();
            }
        });

        etDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone = Integer.parseInt(etPhone.getText().toString().trim());
                if (checkPassword(etPassword.getText().toString().trim(),etConfPassword.getText().toString().trim())){
                    if (utils.isNetworkAvailable(getApplicationContext())){
                        nextView();
                        utils.showProgressDialog(PatientCreateAccount.this,"Sending Verification Code","Please Wait");
                        sendVerificationCode("+254"+phone);
                    }else {
                        utils.alertMessage(PatientCreateAccount.this,"Alert","You need Internet Connection to continue");
                    }
                }

            }
        });

        btnVerifyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    verifyCode(Objects.requireNonNull(pvVerifyCode.getText()).toString().trim());
                }else {
                    verifyCode(pvVerifyCode.getText().toString().trim());
                }
            }
        });
    }

    private void showDatePicker() {
        Log.e("DANNEX", "showDatePicker: I have been clicked");
        int mYear,mMonth,mDay;
        Calendar cal = Calendar.getInstance();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Toast.makeText(getApplicationContext(),year+"/"+(month+1)+"/"+dayOfMonth,Toast.LENGTH_SHORT).show();
                setText(dayOfMonth,(month+1),year);
            }
        },mYear,mMonth,mDay);
        datePickerDialog.getDatePicker().setMaxDate(cal.getTimeInMillis());
        //datePickerDialog.getDatePicker().setMaxDate(mDay + 7);
        datePickerDialog.show();
    }

    private void setText(int dayOfMonth, int month, int year) {
        etDOB.setText(dayOfMonth + "/" + month + "/" +year);
    }

    public void nextView(){
        vfCreateAccount.setInAnimation(PatientCreateAccount.this,R.anim.slide_in_right);
        vfCreateAccount.setOutAnimation(PatientCreateAccount.this,R.anim.slide_out_left);
        vfCreateAccount.showNext();

        int childView = vfCreateAccount.getDisplayedChild();

        if (childView == 0){
            fabPrev.hide();
            fabNext.show();
        }else if (childView == 1){
            fabPrev.show();
            fabNext.show();
        }else if (childView == 2){
            fabPrev.show();
            fabNext.show();
        }else if (childView == 3){
            fabPrev.show();
            fabNext.show();
        }else if(childView == 4){
            fabPrev.show();
            fabNext.hide();
        }else if(childView == 5){
            fabPrev.show();
            fabNext.hide();
        }
    }

    public void previousView(){

        vfCreateAccount.setInAnimation(PatientCreateAccount.this,android.R.anim.slide_in_left);
        vfCreateAccount.setOutAnimation(PatientCreateAccount.this,android.R.anim.slide_out_right);
        vfCreateAccount.showPrevious();

        int childView = vfCreateAccount.getDisplayedChild();

        if (childView == 0){
            fabPrev.hide();
            fabNext.show();
        }else if (childView == 1){
            fabPrev.show();
            fabNext.show();
        }else if (childView == 2){
            fabPrev.show();
            fabNext.show();
        }else if (childView == 3){
            fabPrev.show();
            fabNext.show();
        }else if(childView == 4){
            fabPrev.show();
            fabNext.hide();
        }else if(childView == 5){
            fabPrev.show();
            fabNext.hide();
        }
    }



    private boolean checkPassword(String pass, String passconf) {
        if (!pass.equals(passconf)){
            etConfPassword.setError("Do not match with Password");
            Toast.makeText(getApplicationContext(),"Password do not match the Confirm Password",Toast.LENGTH_SHORT).show();
            return false;
        }else if (pass.length()<6){
            etPassword.setError("Password should be more than 6 characters");
            Toast.makeText(getApplicationContext(),"Password should be more than 6 characters",Toast.LENGTH_SHORT).show();
            return false;
        }else {
            Log.e("DANNEX", "checkPassword: password is authentic");
            return true;
        }
    }

    private void sendVerificationCode(String phone){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phone,60, TimeUnit.SECONDS,PatientCreateAccount.this,mCallBacks);
        Log.e("DannexDaniels", "sendVerificationCode: sending code");
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String codeResent = phoneAuthCredential.getSmsCode();
            if (codeResent != null){
                Log.e("DannexDaniels", "onVerificationCompleted: code sent" );
                pvVerifyCode.setText(codeResent);
                utils.hideProgressDialog();
                verifyCode(codeResent);

            }

            Log.e("DannexDaniels", "onVerificationCompleted: code sent I have been called" );

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            utils.hideProgressDialog();
            if (e instanceof FirebaseAuthInvalidCredentialsException) {
                // Invalid request
                Toast.makeText(getApplicationContext(),"Invalid Phone Number: "+e.toString(),Toast.LENGTH_SHORT).show();
                Log.e("DannexDaniels", "Invalid Phone Number: "+e.toString() );
            } else if (e instanceof FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
                Toast.makeText(getApplicationContext(),"many sms sent",Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
            utils.hideProgressDialog();
        }
    };

    private void verifyCode(String code){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId,code);
        signInWithPhoneAuthCredential(credential);
    }

    private void uploadUserDetails(FirebaseUser user,
                                   String surname, String fname, String lname, String gender,String idNo, String dob,
                                   String insNo, String ins,
                                   String kinName, String kinEmail, String kinPhone, String kinGender, String kinRelation,
                                   String phone, String email) {
        //creating a reference to the folder users where the details will be saved
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference().child("Patients/" + idNo);


        //creating references to where specific details will be saved
        DatabaseReference surnameRef = databaseReference.child("surname");
        DatabaseReference fnameRef = databaseReference.child("fname");
        DatabaseReference lnameRef = databaseReference.child("lname");
        DatabaseReference genderRef = databaseReference.child("gender");
        DatabaseReference idNoRef = databaseReference.child("idno");
        DatabaseReference dobRef = databaseReference.child("dob");

        DatabaseReference insNoRef = databaseReference.child("insno");
        DatabaseReference insRef = databaseReference.child("insurance");

        DatabaseReference kinNameRef = databaseReference.child("kinname");
        DatabaseReference kinEmailRef = databaseReference.child("kinmail");
        DatabaseReference kinGenderRef = databaseReference.child("kingender");
        DatabaseReference kinPhoneRef = databaseReference.child("kinphone");
        DatabaseReference kinRelationRef = databaseReference.child("kinrelation");

        DatabaseReference phoneRef = databaseReference.child("phone");
        DatabaseReference emailRef = databaseReference.child("email");

        //Setting the values
        surnameRef.setValue(surname);
        fnameRef.setValue(fname);
        lnameRef.setValue(lname);
        genderRef.setValue(gender);
        dobRef.setValue(dob);
        idNoRef.setValue(idNo);

        insRef.setValue(ins);
        insNoRef.setValue(insNo);

        kinNameRef.setValue(kinName);
        kinGenderRef.setValue(kinGender);
        kinPhoneRef.setValue(kinPhone);
        kinEmailRef.setValue(kinEmail);
        kinRelationRef.setValue(kinRelation);

        phoneRef.setValue(phone);
        emailRef.setValue(email, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                utils.hideProgressDialog();
                Toast.makeText(getApplicationContext(), "Account Created successfully", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential phoneAuthCredential) {
        mAuth.signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("DANNEX", "signInWithCredential:success");
                            utils.showProgressDialog(PatientCreateAccount.this,"Creating account","Please Wait...");
                            uploadUserDetails(task.getResult().getUser(),
                                    etSurname.getText().toString(),etFirstName.getText().toString().trim(),etLastName.getText().toString().trim(), etIdNumber.getText().toString().trim(), etDOB.getText().toString().trim(),spGender.getSelectedItem().toString().trim(),
                                    etInsuranceNo.getText().toString().trim(),spInsurace.getSelectedItem().toString().trim(),
                                    etKinName.getText().toString().trim(),etKinEmail.getText().toString().trim(),etKinPhone.getText().toString().trim(),spKinGender.getSelectedItem().toString().trim(),spKinRelationship.getSelectedItem().toString().trim(),
                                    etPhone.getText().toString().trim(),etEmail.getText().toString().trim());
                            Toast.makeText(getApplicationContext(),"Sign In Successfull",Toast.LENGTH_SHORT).show();

                            //Add User Locally
                            Queries query = new Queries(PatientCreateAccount.this);
                            query.addUser(etIdNumber.getText().toString().trim(),etPhone.getText().toString().trim(),etPassword.getText().toString().trim(), "patient");
                            query.addFirstTimer(etPhone.getText().toString().trim(),"false");

                            startActivity(new Intent(PatientCreateAccount.this,Login.class));

                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w("DANNEX", "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(getApplicationContext(),"Invalid Code entered",Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getApplicationContext(),"Error: "+task.getException(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }



}
