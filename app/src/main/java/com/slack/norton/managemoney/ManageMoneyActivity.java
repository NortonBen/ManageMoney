package com.slack.norton.managemoney;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.slack.norton.managemoney.Model.Config;
import com.slack.norton.managemoney.data.Constant;

public class ManageMoneyActivity extends AppCompatActivity {

    Button button_cancel;
    Button button_save;
    EditText editText;
    Config configModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_money);
        button_cancel = (Button) findViewById(R.id.manage_money_cancel);
        button_save = (Button) findViewById(R.id.manage_money_save);
        editText = (EditText) findViewById(R.id.manage_money_number);
        configModel = new Config(getApplicationContext());

        int number =  configModel.getInt("total_money");
        editText.setText(String.valueOf(number));



        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = editText.getText().toString();
                if(number.length() < 1){
                    number = "0";
                }
                configModel.setValue("total_money", number);

                Intent intent = new Intent();
                setResult(Constant.REQUES_MAIN_RESULT_RElOAD_MONEY, intent);
                finish();
            }
        });

        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
