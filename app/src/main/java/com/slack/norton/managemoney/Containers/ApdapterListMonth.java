package com.slack.norton.managemoney.Containers;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.slack.norton.managemoney.ListMonthSearchActivity;
import com.slack.norton.managemoney.R;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by norton on 08/10/2017.
 */

public class ApdapterListMonth extends ArrayAdapter<ListMonthSearchActivity.DataList> {

    ArrayList<ListMonthSearchActivity.DataList> list;
    int resource;
    Context context;

    public ApdapterListMonth(@NonNull Context context, @LayoutRes int resource,ArrayList<ListMonthSearchActivity.DataList> list) {
        super(context, resource);
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
        TextView textview = (TextView)convertView.findViewById(R.id.text_view);
        ListMonthSearchActivity.DataList item = list.get(position);
        textview.setText(item.getText());
        return convertView;
    }
}
