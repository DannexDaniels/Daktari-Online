package com.dannextech.apps.daktari_online.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.chip.ChipGroup;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.dannextech.apps.daktari_online.R;
import com.dannextech.apps.daktari_online.adapter.SymptomsAdapter;
import com.dannextech.apps.daktari_online.model.SymptomModel;
import com.dannextech.apps.daktari_online.utils.Utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Symptoms extends Activity {

    private ArrayList<SymptomModel> symptoms;

    private RecyclerView rvSymptoms;
    private ChipGroup cgSymptoms;

    SymptomsAdapter symptomsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptoms);

        rvSymptoms = findViewById(R.id.rvSymptoms);
        cgSymptoms = findViewById(R.id.cgSelectedSymptoms);

        symptoms = new ArrayList<>();

        findViewById(R.id.ivBackSymptoms).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Symptoms.this,PatientHome.class));
            }
        });

        getData();


        findViewById(R.id.ivDiagnosisResults).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedSymptoms = symptomsAdapter.getSym().replace("ss,","");

                startActivity(new Intent(Symptoms.this,DiagnosisResults.class).putExtra("symptoms",selectedSymptoms));
            }
        });
    }

    private void getData() {
        final ProgressDialog progressDialog = new ProgressDialog(Symptoms.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        final Utilities utils = new Utilities();

        String url = "https://sandbox-healthservice.priaid.ch/symptoms?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6ImRhbm5leGRhbmllbHNAZ21haWwuY29tIiwicm9sZSI6IlVzZXIiLCJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9zaWQiOiIzNzc5IiwiaHR0cDovL3NjaGVtYXMubWljcm9zb2Z0LmNvbS93cy8yMDA4LzA2L2lkZW50aXR5L2NsYWltcy92ZXJzaW9uIjoiMjAwIiwiaHR0cDovL2V4YW1wbGUub3JnL2NsYWltcy9saW1pdCI6Ijk5OTk5OTk5OSIsImh0dHA6Ly9leGFtcGxlLm9yZy9jbGFpbXMvbWVtYmVyc2hpcCI6IlByZW1pdW0iLCJodHRwOi8vZXhhbXBsZS5vcmcvY2xhaW1zL2xhbmd1YWdlIjoiZW4tZ2IiLCJodHRwOi8vc2NoZW1hcy5taWNyb3NvZnQuY29tL3dzLzIwMDgvMDYvaWRlbnRpdHkvY2xhaW1zL2V4cGlyYXRpb24iOiIyMDk5LTEyLTMxIiwiaHR0cDovL2V4YW1wbGUub3JnL2NsYWltcy9tZW1iZXJzaGlwc3RhcnQiOiIyMDE4LTA5LTAzIiwiaXNzIjoiaHR0cHM6Ly9zYW5kYm94LWF1dGhzZXJ2aWNlLnByaWFpZC5jaCIsImF1ZCI6Imh0dHBzOi8vaGVhbHRoc2VydmljZS5wcmlhaWQuY2giLCJleHAiOjE1NTM0NDY4OTUsIm5iZiI6MTU1MzQzOTY5NX0._hElfbYyA0ghCf494oQdCBxbFrl4HeyAA395Imn0X8E&format=json&language=en-gb";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.e("Dannex", "onResponse: almost there "+response );
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        SymptomModel symptom = new SymptomModel();
                        symptom.setId(jsonObject.getString("ID"));
                        symptom.setName(jsonObject.getString("Name"));
                        Log.e("Dannex", "onResponse: "+jsonObject );
                        symptoms.add(symptom);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                        Log.e("Dannex", "onResponse: error occured");
                    }
                }
                progressDialog.dismiss();
                //put the recycler view here
                Log.e("Dannex", "onResponse: "+symptoms.size() );

                if (symptoms.isEmpty()){
                    utils.alertMessage(Symptoms.this,"Results","We can't get symptoms list at the moment. Please try again later");
                }else {
                    rvSymptoms.hasFixedSize();
                    rvSymptoms.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
                    symptomsAdapter = new SymptomsAdapter(symptoms,cgSymptoms);
                    rvSymptoms.setAdapter(symptomsAdapter);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
                progressDialog.dismiss();

                utils.alertMessage(Symptoms.this,"Error",error.toString());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }




}
