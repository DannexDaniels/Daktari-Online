package com.dannextech.apps.daktari_online.utils;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class Utilities {

    private ProgressDialog progressDialog;

    public Utilities() {
    }

    public void showProgressDialog(Context context, String title, String message) {
        progressDialog = ProgressDialog.show(context,title,message,true);
    }

    public void hideProgressDialog() {
        progressDialog.dismiss();
    }

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = null;
        if (connectivityManager != null) {
            activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        }
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void askForLocationPermission(Activity activity) {
        ActivityCompat.requestPermissions(activity,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                1);
    }

    /*public showDatePicker(final Context context) {
        Log.e("DANNEX", "showDatePicker: I have been clicked");
        int mYear,mMonth,mDay;
        Calendar cal = Calendar.getInstance();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                makeDate(dayOfMonth,(month+1),year);
            }
        },mYear,mMonth,mDay);
        datePickerDialog.getDatePicker().setMaxDate(cal.getTimeInMillis());
        //datePickerDialog.getDatePicker().setMaxDate(mDay + 7);
        datePickerDialog.show();
    }

    private String makeDate(int dayOfMonth, int month, int year) {
        return dayOfMonth + "/" + month + "/" +year;
    }*/

    public void alertMessage(Context context,String title, String message){
        // 1. Instantiate an AlertDialog.Builder with its constructor
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);

        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage(message)
                .setTitle(title);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                dialog.dismiss();
            }
        });

        // 3. Get the AlertDialog from create()
        AlertDialog dialog = builder.create();

        dialog.show();
    }

}
