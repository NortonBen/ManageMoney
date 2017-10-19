package com.slack.norton.managemoney.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.slack.norton.managemoney.data.Database;
import com.slack.norton.managemoney.data.Type;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by norton on 29/09/2017.
 */

public class Types extends Database implements IModel<Type> {
    public Types(Context context) {
        super(context);
    }

    @Override
    public boolean instert(Type obj) {
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUM_NAME, obj.getName());
            long insert = writableDatabase.insert(TABLE_TYPES, null, values);
            if (insert > 0) {
                return true;
            }
        } catch (Exception e) {
            Log.e("Database Error", e.getMessage().toString());
        }
        return false;
    }

    @Override
    public boolean update(Type obj) {
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUM_NAME, obj.getName());
            int update = writableDatabase.update(TABLE_TYPES, values, "id = ?", new String[]{String.valueOf(obj.getId())});
            if (update > 0) {
                return true;
            }
        } catch (Exception e) {
            Log.e("Database Error", e.getMessage().toString());
        }
        return false;
    }

    @Override
    public boolean delete(Type obj) {
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            int delete = writableDatabase.delete(TABLE_TYPES, "id = ?", new String[]{String.valueOf(obj.getId())});
            if (delete > 0) {
                return true;
            }
        } catch (Exception e) {
            Log.e("Database Error", e.getMessage().toString());
        }
        return false;
    }

    @Override
    public List<Type> getAll() {
        ArrayList<Type> types = new ArrayList<>();
        String sql = "select id,name from `" + TABLE_TYPES + "`";
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            Cursor cursor = writableDatabase.rawQuery(sql, null);
            cursor.moveToFirst();
            do {
                Type type = new Type();
                type.setId(cursor.getInt(COLUM_ID_NO));
                type.setName(cursor.getString(COLUM_NAME_NO));
                Log.d("get type", type.toString());
                types.add(type);
            } while (cursor.moveToNext());
        } catch (Exception e) {
            Log.e("Database Error", e.getMessage().toString());
        }
        return types;
    }

    @Override
    public Type get(int id) {
        String sql = String.format("Select * from `%s` where %s = %s", TABLE_TYPES, COLUM_ID, String.valueOf(id));
        Type type = new Type();
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            Cursor cursor = writableDatabase.rawQuery(sql, new String[]{});
            cursor.moveToFirst();
            if (cursor.getCount() > 0) {
                type.setId(cursor.getInt(COLUM_ID_NO));
                type.setName(cursor.getString(COLUM_NAME_NO));

                Log.d("get type", type.toString());
            }
        } catch (Exception e) {
            Log.e("Database Error", e.getMessage().toString());
        }
        return type;
    }
}
