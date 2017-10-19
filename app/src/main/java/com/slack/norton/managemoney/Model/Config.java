package com.slack.norton.managemoney.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.slack.norton.managemoney.data.Database;
import com.slack.norton.managemoney.data.Type;

/**
 * Created by norton on 29/09/2017.
 */

public class Config extends Database {
    public Config(Context context) {
        super(context);
    }

    public String getValue(String name){
        String sql = String.format("Select * from `%s` where %s = '%s'",TABLE_CONFIG, COLUM_NAME, name.trim().toLowerCase() );
        String value = "";
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            Cursor cursor = writableDatabase.rawQuery(sql, new String[]{});
            cursor.moveToFirst();
            if(cursor.getCount() >0){
                Log.d("get config count", String.valueOf(cursor.getCount()) );
                value = cursor.getString(cursor.getColumnIndex(COLUM_VALUE));
            }
            Log.d("get config", value);
        }catch (Exception e){
            Log.e("Database Error",e.getMessage().toString());
        }
        return value;
    }

    public int getInt(String name){
        int value =  0;
        try{
            value = Integer.parseInt(this.getValue(name));
        }catch (Exception e){
            Log.e("parent INT", e.getMessage());
        }
        return value;
    }

    public float getFloat(String name){
        float value =  0;
        try{
            value = Float.parseFloat(this.getValue(name));
        }catch (Exception e){
            Log.e("parent Float", e.getMessage());
        }
        return value;
    }

    public boolean setValue(String name, String value){
        name = name.trim().toLowerCase();
        ContentValues values = new ContentValues();
        try {
            values.put(COLUM_VALUE, value);
            SQLiteDatabase writableDatabase = getWritableDatabase();
            if(getValue(name).trim().isEmpty()){
                values.put(COLUM_NAME, name);
                long insert = writableDatabase.insert(TABLE_CONFIG, null, values);
                Log.d("set config insert", String.valueOf(insert));
                if(insert > 0){
                    return  true;
                }
            }else{
                Log.d("before config update", name);
                long update = writableDatabase.update(TABLE_CONFIG, values, COLUM_NAME+" = ?", new String[]{ name } );
                Log.d("set config update", String.valueOf(update));
                if(update > 0){
                    return  true;
                }
            }

        }catch (Exception e){
            Log.e("Database Error",e.getMessage().toString());
        }
        return false;
    }

}
