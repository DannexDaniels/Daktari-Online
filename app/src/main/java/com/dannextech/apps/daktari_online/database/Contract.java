package com.dannextech.apps.daktari_online.database;

import android.provider.BaseColumns;

public class Contract {
    public Contract() {
    }

    public static class Authentication implements BaseColumns {
        public static final String TABLE_NAME = "authentication";
        public static final String COL_IDNO = "idNo";
        public static final String COL_PHONE = "phone";
        public static final String COL_PASSWORD = "password";
        public static final String COL_ACCOUNT_TYPE = "type";
        public static final String COL_STATUS = "status";
    }

    public static class FirstTimer implements BaseColumns{
        public static final String TABLE_NAME = "first_timer";
        public static final String COL_PHONE = "phone";
        public static final String COL_CONDITION = "condition";
    }
}
