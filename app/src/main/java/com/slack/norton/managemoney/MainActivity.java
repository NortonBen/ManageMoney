package com.slack.norton.managemoney;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.MenuItem;
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

public class MainActivity extends Activity implements NavigationView.OnNavigationItemSelectedListener {

    ListView listView;
    ListMoney apdater;
    TextView tv_chi;
    TextView tv_con;
    ArrayList<Money> list;

    Datas datasModel;
    Types typeModel;
    Config configModel;

    FloatingActionButton btn_add;
    FloatingActionButton btn_menu;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ArrayList<PieEntry> entries;

    ScrollView sv;

    Button button_detail;
    Button button_search;

    Statistics statistics;

    private PieChart mChart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void getConnectView(){
        drawerLayout  =  (DrawerLayout) findViewById(R.id.drawer_layout);
        listView = (ListView) findViewById(R.id.main_lv_list);
        btn_add = (FloatingActionButton) findViewById(R.id.main_btn_add);
        btn_menu = (FloatingActionButton) findViewById(R.id.main_btn_menu);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        tv_chi= (TextView) findViewById(R.id.main_lable_prince);
        tv_con = (TextView) findViewById(R.id.main_lable_total);

        mChart = (PieChart) findViewById(R.id.pieChart1);
        button_detail = (Button) findViewById(R.id.main_btn_detail);
        button_search = (Button) findViewById(R.id.main_btn_search);
    }



    private void init(){
        getConnectView();


        entries = new ArrayList<>();
        datasModel = new Datas(getApplicationContext());
        typeModel = new Types(getApplicationContext());
        configModel = new Config(getApplicationContext());

        statistics = new Statistics(typeModel,datasModel,configModel,entries);

        navigationView.setNavigationItemSelectedListener(this);

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
        int money_c = statistics.getTotal() - statistics.getPrice();
        String str = "";
        if(money_c < 0){
            str = Convert.money(Math.abs(money_c));
            str = "- "+str;
        }else{
            str = Convert.money(money_c);
        }
        tv_con.setText(str);
        paintChart();
    }



    private void listener(){
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddActivity.class);
                startActivityForResult(intent, Constant.REQUES_MAIN);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                Money money = list.get(position);
                intent.putExtra("id", money.getId());
                startActivityForResult(intent, Constant.REQUES_MAIN);
            }
        });
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        button_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DetailMonthActivity.class);
                startActivityForResult(intent, Constant.REQUES_MAIN);
            }
        });

        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ListMonthSearchActivity.class);
                startActivity(intent);
            }
        });

    }

    private void loadList(){
        Date date = new Date();
        //ArrayList<Money> listWithDay = (ArrayList<Money>) datasModel.getAll();
        Log.d("loadList ","get list money today");
        this.list = (ArrayList<Money>) datasModel.getListWithDay(date);
        apdater.setList(list);
        apdater.notifyDataSetChanged();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_add) {
            Intent intent = new Intent(getApplicationContext(), AddActivity.class);
            startActivityForResult(intent, Constant.REQUES_MAIN);
        }
        if (id == R.id.nav_manage_type){
            Intent intent = new Intent(getApplicationContext(), TypeManageActivity.class);
            startActivityForResult(intent, Constant.REQUES_MAIN);
        }
        if(id == R.id.nav_manage_money_total){
            Intent intent = new Intent(getApplicationContext(), ManageMoneyActivity.class);
            startActivityForResult(intent, Constant.REQUES_MAIN);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Constant.REQUES_MAIN){
            if(resultCode ==  Constant.REQUES_MAIN_RESULT_RElOAD){
                Log.d("reload","reload in onActivityResult ");
                loadList();
                loadStatistics();
            }

            if(resultCode == Constant.REQUES_MAIN_RESULT_RElOAD_ALL){
                Log.d("reload all","reload in onActivityResult ");
                statistics.loadType();
                loadList();
                loadStatistics();
            }

            if(resultCode == Constant.REQUES_MAIN_RESULT_RElOAD_MONEY){
                Log.d("reload money","reload in onActivityResult ");
                loadStatistics();
            }
        }
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
        SpannableString s = new SpannableString("Chi tiêu \n tháng "+String.valueOf(calendar.get(Calendar.MONTH)+1));
        s.setSpan(new RelativeSizeSpan(2f), 0, 8, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 8, s.length(), 0);
        return s;
    }
}
