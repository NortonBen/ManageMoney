package com.slack.norton.managemoney;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.slack.norton.managemoney.Containers.ListMoney;
import com.slack.norton.managemoney.Model.Datas;
import com.slack.norton.managemoney.data.Constant;
import com.slack.norton.managemoney.data.Money;

import java.util.ArrayList;
import java.util.Calendar;

public class DetailMonthActivity extends AppCompatActivity {

    Datas datasModel;
    ListMoney apdater;
    ListView listView;
    ArrayList<Money> list;
    int year;
    int month;

    boolean isEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_month);

        listView = (ListView) findViewById(R.id.detail_month_list_view);


        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH)+1;

        datasModel = new Datas(getApplicationContext());

        list = (ArrayList<Money>) datasModel.getListWithMonth(month,year);
        apdater = new ListMoney(getApplicationContext(), R.layout.item_list, list);
        listView.setAdapter(apdater);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                Money money = list.get(position);
                intent.putExtra("id", money.getId());
                startActivityForResult(intent, Constant.REQUES_DETAIL_MONTH);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Constant.REQUES_DETAIL_MONTH){
            if(resultCode ==  Constant.REQUES_MAIN_RESULT_RElOAD){
                list = (ArrayList<Money>) datasModel.getListWithMonth(month,year);
                apdater.setList(list);
                apdater.notifyDataSetChanged();
                isEdit = true;
                Intent intent = new Intent();
                setResult(Constant.REQUES_MAIN_RESULT_RElOAD, intent);
            }
        }
    }
}
