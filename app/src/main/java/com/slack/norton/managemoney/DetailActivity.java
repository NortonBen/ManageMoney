package com.slack.norton.managemoney;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.slack.norton.managemoney.Model.Datas;
import com.slack.norton.managemoney.Model.Types;
import com.slack.norton.managemoney.data.Constant;
import com.slack.norton.managemoney.data.Money;
import com.slack.norton.managemoney.data.Type;
import com.slack.norton.managemoney.util.Convert;

public class DetailActivity extends AppCompatActivity {

    Datas dataModel;
    Types typeModel;
    Money money;

    Button btn_edit;
    Button btn_del;
    Button btn_back;

    TextView tv_name;
    TextView tv_date;
    TextView tv_type;
    TextView tv_note;
    TextView tv_money;

    LinearLayout linearLayout_note;

    boolean reload = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();

        dataModel = new Datas(getApplicationContext());
        typeModel = new Types(getApplicationContext());


        btn_edit = (Button) findViewById(R.id.detail_btn_edit);
        btn_del = (Button) findViewById(R.id.detail_btn_del);
        btn_back = (Button) findViewById(R.id.detail_btn_back);

        tv_name = (TextView) findViewById(R.id.detail_name_view);
        tv_date = (TextView) findViewById(R.id.detail_date_view);
        tv_money = (TextView) findViewById(R.id.detail_price_view);
        tv_note = (TextView) findViewById(R.id.detail_note_view);
        tv_type = (TextView) findViewById(R.id.detail_type_view);

        linearLayout_note = (LinearLayout) findViewById(R.id.detail_layout_note);

        if(intent.hasExtra("notEdit")){
            btn_edit.setEnabled(false);
            btn_del.setEnabled(false);
            btn_edit.setVisibility(View.INVISIBLE);
            btn_del.setVisibility(View.INVISIBLE);
        }

        loadData(intent.getIntExtra("id",0));

        btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataModel.delete(money);
                reload = true;
                resultItent();
                finish();
            }
        });


        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddActivity.class);
                intent.putExtra("isEdit", true);
                intent.putExtra("id", money.getId());
                startActivityForResult(intent, Constant.REQUES_DETAIL);
            }
        });


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultItent();
                finish();
            }
        });
    }


    private void resultItent(){
        if(reload){
            Intent intent = new Intent();
            setResult(Constant.REQUES_MAIN_RESULT_RElOAD,intent);
        }

    }

    private void loadData(int id){

        Log.d("load detail id", String.valueOf(id));

        money = dataModel.get(id);
        Type type = typeModel.get(money.getType());

        tv_name.setText(money.getName());
        tv_date.setText(money.date());
        if(!money.getNote().trim().isEmpty()){
            linearLayout_note.setVisibility(View.VISIBLE);
            tv_note.setText(money.getNote());
        }else{
            linearLayout_note.setVisibility(View.INVISIBLE);
        }
        tv_type.setText(type.getName());
        tv_money.setText(Convert.addPoint(money.getCount()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == Constant.REQUES_DETAIL){
            if(resultCode == Constant.REQUES_DETAIL_EDIT){
                loadData(money.getId());
                reload = true;
                Intent intent = new Intent();
                setResult(Constant.REQUES_MAIN_RESULT_RElOAD, intent);
            }
        }

    }
}
