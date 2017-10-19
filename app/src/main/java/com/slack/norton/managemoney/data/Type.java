package com.slack.norton.managemoney.data;

/**
 * Created by norton on 01/10/2017.
 */

public class Type {
    public Type(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Type() {
    }

    int id;
    String name;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return  String.format("{ id: %s , name: %s }",id, name);
    }
}
