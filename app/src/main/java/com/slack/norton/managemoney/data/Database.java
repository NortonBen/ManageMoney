package com.slack.norton.managemoney.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by norton on 29/09/2017.
 */

public class Database extends SQLiteOpenHelper {

    public static int version = 1;
    public static String DATABASE_NAME = "manage_money";
    public static String TABLE_DATAS = "datas";
    public static String TABLE_CONFIG = "config";
    public static String TABLE_TYPES = "types";
    public static String COLUM_NAME = "name";
    public static String COLUM_ID = "id";
    public static String COLUM_VALUE= "value";
    public static String COLUM_MONEY = "money";
    public static String COLUM_DATE = "date";
    public static String COLUM_TYPE = "type_id";
    public static String COLUM_NOTE = "note";


    public Context context;
    private String Create_TABLE_DATAS = "create table "+TABLE_DATAS+"(" +
            COLUM_ID+" int primary key," +
            COLUM_NAME+" nvarchar(100) not null," +
            COLUM_TYPE+" int not null," +
            COLUM_NOTE+" text," +
            COLUM_DATE+" date not null," +
            COLUM_MONEY+" float not null" +
            ")";
    private String Create_TABLE_TYPES = "";
    private String Create_TABLE_CONFIG = "";


    public Database(Context context) {
        super(context, DATABASE_NAME, null , version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
