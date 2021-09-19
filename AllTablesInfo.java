package com.example.myproject.TablesInfo;

import android.provider.BaseColumns;

public class AllTablesInfo {
    private AllTablesInfo() {}

    /* Inner class that defines the table contents */
    public static class LogIn implements BaseColumns {
        public static final String TABLE_NAME = "LogIn";
        public static final String COLUMN_USER_NAME = "User";
        public static final String COLUMN_PASSWORD = "Pass";
    }
}
