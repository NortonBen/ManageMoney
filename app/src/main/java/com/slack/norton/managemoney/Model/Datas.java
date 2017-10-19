package com.slack.norton.managemoney.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.slack.norton.managemoney.data.Database;
import com.slack.norton.managemoney.data.Money;
import com.slack.norton.managemoney.data.Type;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by norton on 29/09/2017.
 */

public class Datas extends Database implements IModel<Money> {
    public Datas(Context context) {
        super(context);
    }


    @Override
    public boolean instert(Money obj) {
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUM_NAME, obj.getName());
            values.put(COLUM_TYPE, obj.getType());
            values.put(COLUM_MONEY, obj.getCount());
            values.put(COLUM_DATE, obj.date());
            values.put(COLUM_NOTE, obj.getNote());

            Log.d("insert data", obj.toString());
            long insert = writableDatabase.insert(TABLE_DATAS, null, values);
            Log.d("insert data resuft", String.valueOf(insert));
            if (insert > 0) {
                return true;
            }
        } catch (Exception e) {
            Log.e("Database Error Insert", e.getMessage().toString());
        }
        return false;
    }

    @Override
    public boolean update(Money obj) {
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUM_NAME, obj.getName());
            values.put(COLUM_TYPE, obj.getType());
            values.put(COLUM_MONEY, obj.getCount());
            values.put(COLUM_DATE, obj.date());
            values.put(COLUM_NOTE, obj.getNote());

            Log.d("update data", obj.toString());
            int update = writableDatabase.update(TABLE_DATAS, values, COLUM_ID + " = ?", new String[]{String.valueOf(obj.getId())});
            if (update > 0) {
                return true;
            }
        } catch (Exception e) {
            Log.e("Database Error Update", e.getMessage().toString());
        }
        return false;
    }

    @Override
    public boolean delete(Money obj) {
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            Log.d("delete data", obj.toString());
            int delete = writableDatabase.delete(TABLE_DATAS, COLUM_ID+" = ?", new String[]{String.valueOf(obj.getId())});
            if (delete > 0) {
                return true;
            }
        } catch (Exception e) {
            Log.e("Database Error", e.getMessage().toString());
        }
        return false;
    }

    @Override
    public List<Money> getAll() {
        ArrayList<Money> moneys = new ArrayList<>();
        String sql = String.format("Select * from `%s`", TABLE_DATAS);
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            Cursor cursor = writableDatabase.rawQuery(sql, null);
            cursor.moveToFirst();
            do {
                Money money = new Money();
                money.setId(cursor.getInt(COLUM_ID_NO));
                money.setName(cursor.getString(COLUM_NAME_NO));
                money.setNote(cursor.getString(COLUM_NOTE_NO));
                money.setType(cursor.getInt(COLUM_TYPE_NO));
                money.setCount(cursor.getInt(COLUM_MONEY_NO));
                money.setStringDate(cursor.getString(COLUM_DATE_NO));

                Log.d("get Data", money.toString());
                moneys.add(money);
            } while (cursor.moveToNext());
        } catch (Exception e) {
            Log.e("Database Error getAll", e.getMessage().toString());
        }
        return moneys;
    }

    @Override
    public Money get(int id) {
        String sql = String.format("Select * from `%s` where  %s = %s", TABLE_DATAS, COLUM_ID, String.valueOf(id));
        Money money = new Money();
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            Cursor cursor = writableDatabase.rawQuery(sql, null);
            cursor.moveToFirst();
            if (cursor.getCount() > 0) {
                money.setId(cursor.getInt(COLUM_ID_NO));
                money.setName(cursor.getString(COLUM_NAME_NO));
                money.setNote(cursor.getString(COLUM_NOTE_NO));
                money.setType(cursor.getInt(COLUM_TYPE_NO));
                money.setCount(cursor.getInt(COLUM_MONEY_NO));
                money.setStringDate(cursor.getString(COLUM_DATE_NO));
                Log.d("get Data", money.toString());
            }
        } catch (Exception e) {
            Log.e("Database Error Get", e.getMessage().toString());
        }
        return money;
    }

    public List<Money> getListWithDay(Date date) {
        ArrayList<Money> moneys = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String sql = String.format("Select * from `%s` where  %s = '%s'", TABLE_DATAS, COLUM_DATE, sdf.format(date));
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            Cursor cursor = writableDatabase.rawQuery(sql, null);
            cursor.moveToFirst();
            do {
                Money money = new Money();
                money.setId(cursor.getInt(COLUM_ID_NO));
                money.setName(cursor.getString(COLUM_NAME_NO));
                money.setNote(cursor.getString(COLUM_NOTE_NO));
                money.setType(cursor.getInt(COLUM_TYPE_NO));
                money.setCount(cursor.getInt(COLUM_MONEY_NO));

                money.setStringDate(cursor.getString(COLUM_DATE_NO));

                Log.d("get Data", money.toString());
                moneys.add(money);
            } while (cursor.moveToNext());
        } catch (Exception e) {
            Log.e("Database Error", e.getMessage().toString());
        }
        return moneys;
    }

    public List<Money> getListWithMonth(int month, int year) {
        ArrayList<Money> moneys = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month -1);
        Date startDate = c.getTime();
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date lastDate = c.getTime();

        String sql = String.format("Select * from `%s` where  `"+COLUM_DATE+"` BETWEEN  date('%s') AND  date('%s')", TABLE_DATAS, sdf.format(startDate), sdf.format(lastDate));

        Log.d("sql data Month", sql);
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            Cursor cursor = writableDatabase.rawQuery(sql, new String[]{});
            cursor.moveToFirst();
            Log.d("count data", String.valueOf(cursor.getCount()));
            if(cursor.getCount() > 0){
                do {
                    Money money = new Money();
                    money.setId(cursor.getInt(COLUM_ID_NO));
                    money.setName(cursor.getString(COLUM_NAME_NO));
                    money.setNote(cursor.getString(COLUM_NOTE_NO));
                    money.setType(cursor.getInt(COLUM_TYPE_NO));
                    money.setCount(cursor.getInt(COLUM_MONEY_NO));
                    money.setStringDate(cursor.getString(COLUM_DATE_NO));

                    moneys.add(money);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("Database Error M", e.getMessage().toString());
        }
        return moneys;
    }
}
