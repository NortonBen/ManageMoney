package com.slack.norton.managemoney;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.slack.norton.managemoney.Containers.ApdapterListMonth;
import com.slack.norton.managemoney.Model.Config;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ListMonthSearchActivity extends AppCompatActivity {

    ListView listView;
    Config configModel;
    Date date = new Date();
    ApdapterListMonth apdapter;
    ArrayList<ListMonthSearchActivity.DataList> dataLists = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_month_search);

        listView = (ListView) findViewById(R.id.search_month_list_view);

        configModel= new Config(getApplicationContext());

        String dateStr = configModel.getValue("created_at");
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            this.date = sdf.parse(dateStr);
        } catch (Exception e) {
            Log.d("convert date error", dateStr);
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) +1;

        calendar.setTime(new Date());

        int yearNow = calendar.get(Calendar.YEAR);
        int montNow = calendar.get(Calendar.MONTH) +1;
        while (true){
            dataLists.add(new DataList(year,month,String.format("Tháng %s Năm %s ",month, year)));
            Log.d("get list", String.format("Tháng %s Năm %s ",month, year));
            month++;
            if(month > 12){
                month = 1;
                year++;
            }
            if(year == yearNow && month > montNow){
                Log.d("break while", "exit get list");
                break;
            }
        }

        apdapter = new ApdapterListMonth(getApplicationContext(),R.layout.simple_list_item_1,dataLists);
        listView.setAdapter(apdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DataList item = dataLists.get(position);
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                intent.putExtra("month", item.getMonth());
                intent.putExtra("year", item.getYear());
                startActivity(intent);
            }
        });
    }

    public class DataList{
        int year;
        int month;
        String text;

        public DataList() {

        }

        public DataList(int year, int month, String text) {
            this.year = year;
            this.month = month;
            this.text = text;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}
