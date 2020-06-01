package com.dannextech.apps.daktari_online.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Helper extends SQLiteOpenHelper {

    private Helper mInstance = null;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "DaktariOnline";

    private static final String SQL_CREATE_USER = "CREATE TABLE " +
            Contract.Authentication.TABLE_NAME + "(" +
            Contract.Authentication.COL_PHONE + " TEXT PRIMARY KEY, " +
            Contract.Authentication.COL_IDNO + " TEXT, " +
            Contract.Authentication.COL_ACCOUNT_TYPE + " TEXT, " +
            Contract.Authentication.COL_STATUS + " TEXT, " +
            Contract.Authentication.COL_PASSWORD + " TEXT)";

    private static final String SQL_CREATE_FIRST_TIMER = "CREATE TABLE " +
            Contract.FirstTimer.TABLE_NAME + "(" +
            Contract.FirstTimer.COL_PHONE + " TEXT PRIMARY KEY, " +
            Contract.FirstTimer.COL_CONDITION + " TEXT)";

    private static String SQL_DELETE_USER = "DROP TABLE IF EXISTS " + Contract.Authentication.TABLE_NAME;
    private static String SQL_DELETE_FIRST_TIMER = "DROP TABLE IF EXISTS " + Contract.Authentication.TABLE_NAME;


    public Helper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_USER);
        db.execSQL(SQL_CREATE_FIRST_TIMER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_USER);
        db.execSQL(SQL_DELETE_FIRST_TIMER);
        onCreate(db);
    }

    @Override
    protected void finalize() throws Throwable {
        this.close();
        super.finalize();
    }

    public Helper getInstance(Context context){
        if (mInstance==null){
            mInstance = new Helper(context.getApplicationContext());
        }
        return mInstance;
    }
}
