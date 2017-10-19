package com.slack.norton.managemoney.Containers;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.slack.norton.managemoney.R;
import com.slack.norton.managemoney.data.Money;
import com.slack.norton.managemoney.util.Convert;

import java.util.ArrayList;

/**
 * Created by norton on 29/09/2017.
 */

public class ListMoney extends ArrayAdapter<Money> {
    Context context;
    int resource;
    ArrayList<Money> list;
    public ListMoney(@NonNull Context context, @LayoutRes int resource, ArrayList<Money> list) {
        super(context, resource, list);
        this.list = list;
        this.resource = resource;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, null);
        TextView nameView  = (TextView) convertView.findViewById(R.id.item_list_name);
        TextView dateView  = (TextView) convertView.findViewById(R.id.item_list_date);
        TextView moneyView = (TextView) convertView.findViewById(R.id.item_list_money);
        Money money = list.get(position);
        nameView.setText(money.getName());
        dateView.setText(money.date());
        moneyView.setText(Convert.money( money.getCount()));
        return convertView;
    }

    public void setList(ArrayList<Money> list) {
        this.list = list;
    }
}
