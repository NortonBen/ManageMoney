package com.slack.norton.managemoney;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.slack.norton.managemoney.Model.Datas;
import com.slack.norton.managemoney.Model.Types;
import com.slack.norton.managemoney.data.Constant;
import com.slack.norton.managemoney.data.Money;
import com.slack.norton.managemoney.data.Type;

import java.util.ArrayList;
import java.util.Calendar;

import android.widget.DatePicker;


public class AddActivity extends AppCompatActivity {

    Button btn_save;
    Button btn_exit;
    Spinner spinner;
    EditText et_name;
    EditText et_note;
    EditText et_date;
    EditText et_money;

    TextView tv_name_error;
    TextView tv_spinner_error;
    TextView tv_date_error;
    TextView tv_price_error;

    Money money;
    Types typeModel;
    Datas dataModel;
    ArrayList<Type> types;
    ArrayAdapter<String> arrayAdapter;

    boolean isEdit = false;

    private Calendar calendar;
    private int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        init();
        listener();
    }

    private void init() {
        btn_save = (Button) findViewById(R.id.add_btn_save);
        btn_exit = (Button) findViewById(R.id.add_btn_cancel);

        spinner = (Spinner) findViewById(R.id.add_spinner);
        et_name = (EditText) findViewById(R.id.add_edit_name);
        et_date = (EditText) findViewById(R.id.add_date);
        et_note = (EditText) findViewById(R.id.add_edit_note);
        et_money = (EditText) findViewById(R.id.add_edit_price);

        tv_name_error = (TextView) findViewById(R.id.add_edit_name_error);
        tv_spinner_error = (TextView) findViewById(R.id.add_spinner_error);
        tv_date_error = (TextView) findViewById(R.id.add_date_error);
        tv_price_error = (TextView) findViewById(R.id.add_edit_price_error);

        typeModel = new Types(getApplicationContext());
        dataModel = new Datas(getApplicationContext());
        money = new Money();

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month + 1, day);

        types = (ArrayList<Type>) typeModel.getAll();
        String[] arrayData = new String[types.size()];
        for (int i = 0; i < types.size(); i++) {
            arrayData[i] = types.get(i).getName();
        }
        arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_dropdown_item, arrayData);
        spinner.setAdapter(arrayAdapter);

        Intent intent = getIntent();
        if (intent.hasExtra("isEdit")) {
            int id = intent.getIntExtra("id", 0);
            Log.d("Load Data with isEdit", String.valueOf(id));
            money = dataModel.get(id);
            isEdit = true;
            setData();
        }
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
                    year = arg1;
                    month = arg2;
                    day = arg3;
                    showDate(arg1, arg2 + 1, arg3);
                }
            };

    private void listener() {

        et_date.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                showDialog(999);
                return false;
            }
        });

        et_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(999);
            }
        });

        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
                if (validate()) {
                    return;
                }
                Intent intent = new Intent();

                if (!isEdit) {
                    Log.d("insert data", "befor ");
                    if (!dataModel.instert(money)) {
                        Log.e("error", "insert Data error");
                    }
                    Log.d("insert data", "after ");
                    setResult(Constant.REQUES_MAIN_RESULT_RElOAD, intent);
                } else {
                    Log.d("update data", "befor ");
                    if (!dataModel.update(money)) {
                        Log.e("error", "update Data error");
                    }
                    Log.d("update data", "after ");
                    setResult(Constant.REQUES_DETAIL_EDIT, intent);
                }

                finish();
            }
        });

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private boolean validate() {
        boolean isError = false;

        if (money.getName().trim().isEmpty()) {
            tv_name_error.setText("Bạn Chưa nhập Tên!");
            tv_name_error.setVisibility(View.VISIBLE);
            if (!isError) {
                isError = true;
            }
        } else {
            tv_name_error.setVisibility(View.INVISIBLE);
        }

        if (money.getDate() == null) {
            tv_date_error.setText("Bạn chưa chọn ngày!");
            tv_date_error.setVisibility(View.VISIBLE);
            if (!isError) {
                isError = true;
            }
        } else {
            tv_date_error.setVisibility(View.INVISIBLE);
        }

        if (money.getCount() <= 0) {
            tv_price_error.setText("Bạn chưa nhập tiền hoặc nhở hơn không!");
            tv_price_error.setVisibility(View.VISIBLE);
            if (!isError) {
                isError = true;
            }
        } else {
            tv_price_error.setVisibility(View.INVISIBLE);
        }
        return isError;
    }

    private void getData() {
        money.setName(et_name.getText().toString());
        money.setNote(et_note.getText().toString());

        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);
        money.setDate(calendar.getTime());

        int position = spinner.getSelectedItemPosition();
        Type type = types.get(position);
        System.out.println(type);
        money.setType(type.getId());

        try {
            String so = et_money.getText().toString().trim();
            if (so.length() > 0) {
                int soint = Integer.parseInt(so);
                money.setCount(soint);
            } else {
                money.setCount(0);
            }

        } catch (Exception e) {
            money.setCount(0);
            Log.e("conever data", e.getMessage());
        }


    }

    private void setData() {
        et_name.setText(money.getName());
        et_note.setText(money.getNote());
        et_money.setText(String.valueOf(money.getCount()));

        et_date.setText(money.date());
        calendar.setTime(money.getDate());
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month + 1, day);

        int position = 0;
        for (int i = 0; i < types.size(); i++) {
            if (types.get(i).getId() == money.getType()) {
                position = i;
                break;
            }
        }
        spinner.setSelection(position);
    }

    private void showDate(int year, int month, int day) {
        et_date.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }
}
