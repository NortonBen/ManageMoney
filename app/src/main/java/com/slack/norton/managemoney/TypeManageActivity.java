package com.slack.norton.managemoney;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.slack.norton.managemoney.Model.Types;
import com.slack.norton.managemoney.data.Constant;
import com.slack.norton.managemoney.data.Type;

import java.util.ArrayList;
import java.util.List;

public class TypeManageActivity extends AppCompatActivity {

    ListView listView;
    EditText editText;
    Button button;
    Type type;
    Types typeModel;
    ArrayList<String> list;
    ArrayList<Type> all;
    ArrayAdapter<String> adapter;
    boolean isEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_manage);
        typeModel = new Types(getApplicationContext());
        editText = (EditText) findViewById(R.id.type_manage_ed);
        button = (Button) findViewById(R.id.type_manage_bt);
        listView = (ListView) findViewById(R.id.type_manage_lv);
        list = new ArrayList<>();
        type = new Type();
        adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.simple_list_item, list);
        listView.setAdapter(adapter);

        loadData();

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                type = all.get(position);
                showDialog();
                return false;
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editText.getText().toString().trim();
                if (text.length() > 0) {
                    type.setName(editText.getText().toString());
                    typeModel.instert(type);
                    loadData();
                    isEdit = true;
                    editText.setText("");
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(isEdit){
            Intent intent = new Intent();
            setResult(Constant.REQUES_MAIN_RESULT_RElOAD_ALL, intent);
        }

    }

    private void showDialog(){
        new AlertDialog.Builder(this)
            .setTitle("Xóa")
            .setMessage("Bạn Có Muốn Xóa Loại Chi Tiêu '"+type.getName()+"' này?")
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    typeModel.delete(type);
                    loadData();
                    isEdit = true;
                }})
            .setNegativeButton(android.R.string.no, null).show();
    }

    private void loadData() {
        all = (ArrayList<Type>) typeModel.getAll();
        list.clear();
        for (Type t : all) {
            list.add(t.getName());
        }
        adapter.notifyDataSetChanged();
    }


}
