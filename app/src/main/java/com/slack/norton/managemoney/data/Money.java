package com.slack.norton.managemoney.data;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by norton on 29/09/2017.
 */

public class Money {

    int id = 0;
    int type = 0;
    String name;
    String note;
    int count = 0;
    Date date = new Date();

    public Money() {
    }

    public Money(int id, int type, String name, String note, int count, Date date) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.note = note;
        this.count = count;
        this.date = date;
    }

    public Money(int type, String name, String note, int count) {
        this.type = type;
        this.name = name;
        this.note = note;
        this.count = count;
    }

    public Money(int type, String name, String note, int count, Date date) {
        this.type = type;
        this.name = name;
        this.note = note;
        this.count = count;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String date(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return  sdf.format(date);
    }

    public void setStringDate(String date){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            this.date = sdf.parse(date);
        } catch (Exception e) {
            Log.d("convert date error", date);
        }

    }

    @Override
    public String toString() {
        return  String.format("{ id: %s , name: %s, note: %s, type: %s, date: %s, money: %s }",id, name,note, type, date(), count);
    }
}
