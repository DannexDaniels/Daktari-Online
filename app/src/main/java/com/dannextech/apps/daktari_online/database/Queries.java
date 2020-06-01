package com.dannextech.apps.daktari_online.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dannextech.apps.daktari_online.model.UserModel;

import static android.content.Context.MODE_PRIVATE;

public class Queries {

    private SQLiteDatabase db;
    private Helper dbHelper;
    SharedPreferences.Editor editor;

    public Queries(Context context) {
        dbHelper = new Helper(context);
        dbHelper.getInstance(context);
        editor = context.getSharedPreferences("user",MODE_PRIVATE).edit();
    }

    public void addUser(String id, String phone, String password,String type){
        db = dbHelper.getWritableDatabase();
        ContentValues val = new ContentValues();
        val.put(Contract.Authentication.COL_IDNO,id);
        val.put(Contract.Authentication.COL_PHONE,phone);
        val.put(Contract.Authentication.COL_PASSWORD,password);
        val.put(Contract.Authentication.COL_ACCOUNT_TYPE,type);
        val.put(Contract.Authentication.COL_STATUS,"out");

        db.insert(Contract.Authentication.TABLE_NAME,null,val);
        db.close();
    }

    public void addFirstTimer(String phone, String condition){
        db = dbHelper.getWritableDatabase();
        ContentValues val = new ContentValues();
        val.put(Contract.FirstTimer.COL_PHONE,phone);
        val.put(Contract.FirstTimer.COL_CONDITION,condition);

        db.insert(Contract.FirstTimer.TABLE_NAME,null,val);
        db.close();
    }

    public String getFirstTimer(){
        db = dbHelper.getReadableDatabase();

        String columns[] = {Contract.FirstTimer.COL_PHONE,Contract.FirstTimer.COL_CONDITION};
        Cursor cursor = db.query(Contract.FirstTimer.TABLE_NAME,columns,null,null,null,null,null);

        String result = null;

        while (cursor.moveToNext()){
            result = cursor.getString(cursor.getColumnIndexOrThrow(Contract.FirstTimer.COL_CONDITION));
        }

        if (result == null){
            return "new";
        }else {
            return result;
        }
    }

    public UserModel [] getPassword(String phone){
        db = dbHelper.getReadableDatabase();

        String columns[] = {Contract.Authentication.COL_IDNO, Contract.Authentication.COL_PHONE, Contract.Authentication.COL_PASSWORD, Contract.Authentication.COL_ACCOUNT_TYPE, Contract.Authentication.COL_STATUS};
        String selection = Contract.Authentication.COL_PHONE + "=?";
        String selectionArgs[] = {phone};

        Cursor cursor = db.query(Contract.Authentication.TABLE_NAME,columns,selection,selectionArgs,null,null,null);

        UserModel user [] = new UserModel[cursor.getCount()];
        String password = null;
        while (cursor.moveToNext()){
            user[0] = new UserModel();
            user[0].setPassword(cursor.getString(cursor.getColumnIndexOrThrow(Contract.Authentication.COL_PASSWORD)));
            user[0].setIdno(cursor.getString(cursor.getColumnIndexOrThrow(Contract.Authentication.COL_IDNO)));
            user[0].setPhone(cursor.getString(cursor.getColumnIndexOrThrow(Contract.Authentication.COL_PHONE)));
            user[0].setStatus(cursor.getString(cursor.getColumnIndexOrThrow(Contract.Authentication.COL_STATUS)));
            user[0].setType(cursor.getString(cursor.getColumnIndexOrThrow(Contract.Authentication.COL_ACCOUNT_TYPE)));
            password = cursor.getString(cursor.getColumnIndexOrThrow(Contract.Authentication.COL_PASSWORD));
        }
        if (password == null){
            return null;
        }else {
            return user;
        }

    }


}
