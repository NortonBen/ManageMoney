package com.slack.norton.managemoney.data;

import java.util.Date;

/**
 * Created by norton on 29/09/2017.
 */

public class Money {

    int id = 0;
    int type = 0;
    String name;
    String note;
    float count = 0;
    Date date = new Date();

    public Money(int id, int type, String name, String note, float count, Date date) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.note = note;
        this.count = count;
        this.date = date;
    }

    public Money(int type, String name, String note, float count) {
        this.type = type;
        this.name = name;
        this.note = note;
        this.count = count;
    }

    public Money(int type, String name, String note, float count, Date date) {
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

    public float getCount() {
        return count;
    }

    public void setCount(float count) {
        this.count = count;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
