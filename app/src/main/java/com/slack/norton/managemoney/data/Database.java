package com.slack.norton.managemoney.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by norton on 29/09/2017.
 */

public class Database extends SQLiteOpenHelper {

    public static int version = 1;
    public static String DATABASE_NAME = "manage_money_app";
    public static String TABLE_DATAS = "datas";
    public static String TABLE_CONFIG = "configs";
    public static String TABLE_TYPES = "types";
    public static String COLUM_NAME = "name";
    public static int COLUM_NAME_NO = 1;
    public static String COLUM_ID = "id";
    public static int COLUM_ID_NO = 0;
    public static String COLUM_VALUE = "value";
    public static int COLUM_VALUE_NO = 1;
    public static String COLUM_MONEY = "money";
    public static int COLUM_MONEY_NO = 5;
    public static String COLUM_DATE = "date";
    public static int COLUM_DATE_NO = 3;
    public static String COLUM_TYPE = "type_id";
    public static int COLUM_TYPE_NO = 2;
    public static String COLUM_NOTE = "note";
    public static int COLUM_NOTE_NO = 4;


    public Context context;
    private String Create_TABLE_DATAS = "CREATE TABLE `datas` ( `id` INTEGER PRIMARY KEY   AUTOINCREMENT , `name` VARCHAR(100) , `type_id` INT  , `date` DATE , `note` TEXT NULL , `money` INT )";
    private String Create_TABLE_TYPES = "CREATE TABLE `types` ( `id` INTEGER PRIMARY KEY   AUTOINCREMENT, `name` VARCHAR(100))";
    private String Create_TABLE_CONFIG = "CREATE TABLE `configs` ( `id` INTEGER PRIMARY KEY   AUTOINCREMENT, `name` VARCHAR(100)  , `value` TEXT)";


    public Database(Context context) {
        super(context, DATABASE_NAME, null , version);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(Create_TABLE_DATAS);
            db.execSQL(Create_TABLE_TYPES);
            db.execSQL(Create_TABLE_CONFIG);
            db.execSQL("INSERT INTO `types` (`name`) VALUES ('Mua Sắm'), ('Du Lich'), ('Học Tâp'), ('Ăn Uống'), ('Bạn Bè'), ('Sinh Hoạt')");

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            db.execSQL("INSERT INTO `configs` (`name`,`value`) VALUES ('created_at','"+sdf.format(new Date()) +"')");
        }catch (Exception e){
            Log.e("Create Database: ", e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATAS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATAS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONFIG);
            onCreate(db);
        }
    }

}
