package com.slack.norton.managemoney.util;

import android.util.Log;

import com.github.mikephil.charting.data.PieEntry;
import com.slack.norton.managemoney.Model.Config;
import com.slack.norton.managemoney.Model.Datas;
import com.slack.norton.managemoney.Model.Types;
import com.slack.norton.managemoney.data.Money;
import com.slack.norton.managemoney.data.Type;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by norton on 08/10/2017.
 */

public class Statistics {

    ArrayList<Statistics.Data> thongke = new ArrayList<>();
    ArrayList<Money> moneys;
    Types typeModel;
    Datas dataModel;
    Config configModel;
    ArrayList<PieEntry> entries;
    int price;
    int year;
    int month;
    int total;

    public Statistics() {
    }

    public Statistics(Types typeModel, Datas dataModel, Config configModel, ArrayList<PieEntry> entries) {
        this.typeModel = typeModel;
        this.dataModel = dataModel;
        this.configModel = configModel;
        this.entries = entries;

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH)+1;

        loadType();
    }

    public Statistics(Types typeModel, Datas dataModel, ArrayList<PieEntry> entries) {
        this.typeModel = typeModel;
        this.dataModel = dataModel;
        this.entries = entries;

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH)+1;

        loadType();
    }

    public int getTotal() {
        total =  configModel.getInt("total_money");
        return total;
    }


    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getPrice() {
        return price;
    }

    public void loadType() {
        ArrayList<Type> types = ( ArrayList<Type>) typeModel.getAll();
        thongke.clear();
        for (Type type: types){
            thongke.add(new Statistics.Data(type));
        }
    }

    public void getData(){

        ArrayList<Money> moneys = (ArrayList<Money>) dataModel.getListWithMonth(month,year);
        price = 0;
        entries.clear();
        for (Statistics.Data t: thongke){
            t.setMoney(0);
            for (Money money: moneys){
                if(money.getType() == t.getType().getId()){
                    t.addMoney(money.getCount());
                    price += money.getCount();
                }
            }
        }

        for (Statistics.Data t: thongke){
            if(t.getMoney() > 0){
                PieEntry pieEntry = new PieEntry((float) t.getMoney(), t.getType().getName());
                entries.add(pieEntry);
            }
            Log.d("add PieEntry", t.toString());
        }
    }

    public class Data{
        Type type;
        int money;

        public Data(Type type) {
            this.type = type;
        }

        public Type getType() {
            return type;
        }

        public void setType(Type type) {
            this.type = type;
        }

        public int getMoney() {
            return money;
        }

        public void addMoney(int money) {
            this.money += money;
        }

        public void setMoney(int money) {
            this.money = money;
        }

        @Override
        public String toString() {
            return  String.format("{ money: %s, type: %s }", money, type.toString());
        }
    }

}
