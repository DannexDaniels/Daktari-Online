package com.dannextech.apps.daktari_online.Activities;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dannextech.apps.daktari_online.R;
import com.dannextech.apps.daktari_online.model.AccessToken;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class PatientHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_home);

        new myAuthentication().execute("");
        findViewById(R.id.lvSymptomsPatient).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PatientHome.this, Symptoms.class));
            }
        });
        findViewById(R.id.lvAmbulancePatient).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PatientHome.this, AmbulanceList.class));
            }
        });
        findViewById(R.id.lvChemistPatient).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PatientHome.this, PharmacyList.class));
            }
        });
        findViewById(R.id.lvProfilePatient).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PatientHome.this,PatientProfile.class));
            }
        });
    }


    private class myAuthentication extends AsyncTask<String,Void,String>{

        private static final String TAG = "DannexDaniels";

        @Override
        protected String doInBackground(String... strings) {
            myAuth();
            return "success";
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (isNetworkAvailable()){
                Log.e(TAG, "onPreExecute: Network Available");
            }else {
                Log.e(TAG, "onPreExecute: No Network Access" );
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e(TAG, "onPostExecute: finished" );
        }

        private boolean isNetworkAvailable() {
            ConnectivityManager connectivityManager
                    = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = null;
            if (connectivityManager != null) {
                activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            }
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }

        private void myAuth() {

            String authServiceUrl = "https://sandbox-authservice.priaid.ch/login";
            String userName = "dannexdaniels@gmail.com";
            String password = "t3B8ScMf9j7RWq64A";
            String language = "en";
            String healthServiceUrl = "https://healthservice.priaid.ch";

            String computedHashString = "";

            SecretKeySpec keySpec = new SecretKeySpec(
                    password.getBytes(),
                    "HmacMD5");

            //key = new SecretKeySpec((secret_key).getBytes(),"UTF-8");
            Mac mac;
            try {
                mac = Mac.getInstance("HmacMD5");
                mac.init(keySpec);
                byte[] result = mac.doFinal(authServiceUrl.getBytes());

                //converting the result to base64 as a string
                computedHashString = Base64.encodeToString(result,Base64.DEFAULT);
                Log.e(TAG, "myAuth: hash string "+computedHashString);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            }

            URL url;
            try {
                url = new URL("https://sandbox-authservice.priaid.ch/login");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Authorization","Bearer "+userName+":"+computedHashString);
                conn.setRequestMethod("POST");
                conn.connect();

                Log.e(TAG, "myAuth: response "+conn.getResponseMessage() );
            } catch (MalformedURLException e) {
                Log.e(TAG, "myAuth: MalformedURLExecption "+e );
            } catch (ProtocolException e) {
                Log.e(TAG, "myAuth: ProtocalExecption "+e );
            } catch (IOException e) {
                Log.e(TAG, "myAuth: IOExecption "+e );
            }


            /*try {
                LoadToken(userName, password, authServiceUrl);
            } catch (Exception e) {
                e.printStackTrace();
            }*/
        }

        private void LoadToken(String username, String password, String url) throws Exception {

            SecretKeySpec keySpec = new SecretKeySpec(
                    password.getBytes(),
                    "HmacMD5");

            String computedHashString = "";
            try {
                Mac mac = Mac.getInstance("HmacMD5");
                mac.init(keySpec);
                byte[] result = mac.doFinal(url.getBytes());

                //converting the result to base64 as a string
                computedHashString = Base64.encodeToString(result,Base64.DEFAULT);

            } catch (NoSuchAlgorithmException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                throw new Exception("Can not create token (NoSuchAlgorithmException)");
            } catch (InvalidKeyException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                throw new Exception("Can not create token (InvalidKeyException)");
            }

            URL myurl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) myurl.openConnection();
            conn.setRequestProperty("Authorization","Bearer "+username+":"+computedHashString);
            conn.setRequestMethod("POST");
            conn.connect();

            ObjectMapper objectMapper = new ObjectMapper();
            if(conn.getResponseCode() != conn.HTTP_OK){
                RetrieveException(conn, objectMapper);
            }
            AccessToken accessToken = objectMapper.readValue(conn.getResponseMessage(), AccessToken.class);

            Toast.makeText(PatientHome.this,"result is "+conn.getResponseMessage(),Toast.LENGTH_LONG).show();
        }


        private void RetrieveException(HttpURLConnection conn, ObjectMapper objectMapper) throws Exception {

            String errorMessage = objectMapper.readValue(conn.getContent().toString(), String.class);
            System.out.println("Resposne with status code: " + conn.getResponseCode() + ", error message: " + errorMessage);
            throw new Exception(errorMessage);
        }
    }
}
