package com.slack.norton.managemoney;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.slack.norton.managemoney.Containers.ListMoney;
import com.slack.norton.managemoney.Model.Config;
import com.slack.norton.managemoney.Model.Datas;
import com.slack.norton.managemoney.Model.Types;
import com.slack.norton.managemoney.data.Constant;
import com.slack.norton.managemoney.data.Money;
import com.slack.norton.managemoney.util.Convert;
import com.slack.norton.managemoney.util.Statistics;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class SearchActivity extends AppCompatActivity {

    TextView tv_title;

    ListView listView;
    ListMoney apdater;
    TextView tv_chi;
    ArrayList<Money> list;

    Button button_back;

    Datas datasModel;
    Types typeModel;
    Config configModel;
    int year;
    int month;


    DrawerLayout drawerLayout;
    ArrayList<PieEntry> entries;

    ScrollView sv;


    Statistics statistics;

    private PieChart mChart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        init();
    }

    private void getConnectView(){
        drawerLayout  =  (DrawerLayout) findViewById(R.id.drawer_layout);
        listView = (ListView) findViewById(R.id.main_lv_list);
        tv_chi= (TextView) findViewById(R.id.main_lable_prince);

        mChart = (PieChart) findViewById(R.id.pieChart1);
        tv_title = (TextView) findViewById(R.id.search_tilte);
        button_back = (Button) findViewById(R.id.search_back);
    }



    private void init(){
        getConnectView();


        entries = new ArrayList<>();
        datasModel = new Datas(getApplicationContext());
        typeModel = new Types(getApplicationContext());
        configModel = new Config(getApplicationContext());

        statistics = new Statistics(typeModel,datasModel,configModel,entries);

        Intent intent = getIntent();
        year = intent.getIntExtra("year",2017);
        month = intent.getIntExtra("month",1);
        statistics.setMonth(month);
        statistics.setYear(year);

        tv_title.setText(String.format("Chi tiêu trong tháng : %s - %s ",String.valueOf(month), String.valueOf(year)));

        list = new ArrayList<>();
        apdater = new ListMoney(getApplicationContext(), R.layout.item_list, list);
        listView.setAdapter(apdater);

        listener();

        loadStatistics();

        loadList();

        sv = (ScrollView)findViewById(R.id.man_scroll_view);
        sv.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                sv.fullScroll(View.FOCUS_UP);
            }
        });
    }

    private void loadStatistics(){
        statistics.getData();
        tv_chi.setText(Convert.money(statistics.getPrice()));

        paintChart();
    }



    private void listener(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
            Money money = list.get(position);
            intent.putExtra("id", money.getId());
            intent.putExtra("notEdit", true);
            startActivityForResult(intent, Constant.REQUES_MAIN);
            }
        });

        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void loadList(){
        Date date = new Date();
        Log.d("loadList ","get list money getListWithMonth");
        this.list = (ArrayList<Money>) datasModel.getListWithMonth(month,year);
        apdater.setList(list);
        apdater.notifyDataSetChanged();
    }


    private void paintChart(){
        mChart.getDescription().setEnabled(false);

        mChart.setCenterText(generateCenterText());
        mChart.setCenterTextSize(10f);

        // radius of the center hole in percent of maximum radius
        mChart.setHoleRadius(45f);
        mChart.setTransparentCircleRadius(50f);

        Legend l = mChart.getLegend();
        l.setEnabled(false);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setDrawInside(false);

        mChart.setData(generatePieData());

    }

    protected PieData generatePieData() {

        Log.d("generatePieData ", "ok");
        PieDataSet ds1 = new PieDataSet(entries,null);
        ds1.setColors(ColorTemplate.MATERIAL_COLORS);
        ds1.setSliceSpace(2f);
        ds1.setValueTextColor(Color.BLACK);
        ds1.setValueTextSize(12f);

        PieData d = new PieData(ds1);
        return d;
    }

    private SpannableString generateCenterText() {
        Calendar calendar = Calendar.getInstance();
        SpannableString s = new SpannableString( String.format("Chi tiêu \n %s - %s ",String.valueOf(month), String.valueOf(year)));
        s.setSpan(new RelativeSizeSpan(2f), 0, 8, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 8, s.length(), 0);
        return s;
    }
}
