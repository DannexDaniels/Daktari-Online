package com.dannextech.apps.daktari_online.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.dannextech.apps.daktari_online.R;
import com.dannextech.apps.daktari_online.adapter.DiagnosisAdapter;
import com.dannextech.apps.daktari_online.model.DiagnosisModel;
import com.dannextech.apps.daktari_online.utils.Utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DiagnosisResults extends AppCompatActivity {

    private String url = "";

    private ArrayList<DiagnosisModel> diagResults;

    private RecyclerView rvDiagResults;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosis_results);

        rvDiagResults = findViewById(R.id.rvDiagResults);

        diagResults = new ArrayList<>();

        String symptoms = getIntent().getStringExtra("symptoms");
        String gender = "male";
        String yob = "1990";
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6ImRhbm5leGRhbmllbHNAZ21haWwuY29tIiwicm9sZSI6IlVzZXIiLCJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9zaWQiOiIzNzc5IiwiaHR0cDovL3NjaGVtYXMubWljcm9zb2Z0LmNvbS93cy8yMDA4LzA2L2lkZW50aXR5L2NsYWltcy92ZXJzaW9uIjoiMjAwIiwiaHR0cDovL2V4YW1wbGUub3JnL2NsYWltcy9saW1pdCI6Ijk5OTk5OTk5OSIsImh0dHA6Ly9leGFtcGxlLm9yZy9jbGFpbXMvbWVtYmVyc2hpcCI6IlByZW1pdW0iLCJodHRwOi8vZXhhbXBsZS5vcmcvY2xhaW1zL2xhbmd1YWdlIjoiZW4tZ2IiLCJodHRwOi8vc2NoZW1hcy5taWNyb3NvZnQuY29tL3dzLzIwMDgvMDYvaWRlbnRpdHkvY2xhaW1zL2V4cGlyYXRpb24iOiIyMDk5LTEyLTMxIiwiaHR0cDovL2V4YW1wbGUub3JnL2NsYWltcy9tZW1iZXJzaGlwc3RhcnQiOiIyMDE4LTA5LTAzIiwiaXNzIjoiaHR0cHM6Ly9zYW5kYm94LWF1dGhzZXJ2aWNlLnByaWFpZC5jaCIsImF1ZCI6Imh0dHBzOi8vaGVhbHRoc2VydmljZS5wcmlhaWQuY2giLCJleHAiOjE1NTM0NDY4OTUsIm5iZiI6MTU1MzQzOTY5NX0._hElfbYyA0ghCf494oQdCBxbFrl4HeyAA395Imn0X8E";
        url = "https://sandbox-healthservice.priaid.ch/diagnosis?symptoms=["+ symptoms +"]&gender="+ gender +"&year_of_birth="+ yob +"&token="+ token +"&format=json&language=en-gb";

        Log.e("DannexDaniels", "onCreate: "+url);
        getData();

        findViewById(R.id.ivBackDiagResults).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DiagnosisResults.this, Symptoms.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(DiagnosisResults.this, Symptoms.class));
    }

    private void getData() {
        final ProgressDialog progressDialog = new ProgressDialog(DiagnosisResults.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        final Utilities utils = new Utilities();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.e("Dannex", "onResponse: almost there "+response );
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        JSONObject jj = (JSONObject) jsonObject.get("Issue");

                        DiagnosisModel results = new DiagnosisModel();
                        results.setId(jj.getString("ID"));
                        results.setName(jj.getString("Name"));
                        results.setAccuracy(jj.getString("Accuracy"));

                        Log.e("Dannex", "onResponse: "+jj );
                        diagResults.add(results);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                        Log.e("Dannex", "onResponse: error occured");
                    }
                }
                progressDialog.dismiss();
                if (diagResults.isEmpty()){
                    utils.alertMessage(DiagnosisResults.this,"Results","We can't tell the problem from your symptoms. You need to see a doctor. Do you wish to be referred to a doctor?");
                }else{
                    //put the recycler view here
                    rvDiagResults.hasFixedSize();
                    rvDiagResults.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    DiagnosisAdapter diagnosisAdapter = new DiagnosisAdapter(diagResults);
                    rvDiagResults.setAdapter(diagnosisAdapter);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
                progressDialog.dismiss();

                utils.alertMessage(DiagnosisResults.this,"Error",error.toString());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

}
