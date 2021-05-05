package com.example.myadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.myadmin.getData.CallAPI;
import com.example.myadmin.getData.GetData_Product;
import com.example.myadmin.model.SanPham;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PieChartActivity extends AppCompatActivity implements OnChartValueSelectedListener {

    private PieChart mChart;
    List<SanPham> sanphamList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);

        mChart = (com.github.mikephil.charting.charts.PieChart) findViewById(R.id.piechart);
        mChart.setRotationEnabled(true);
        mChart.setDescription(new Description());
        mChart.setHoleRadius(35f);
        mChart.setTransparentCircleAlpha(0);
        mChart.setCenterText("PieChart");
        mChart.setCenterTextSize(10);
        mChart.setDrawEntryLabels(true);
        requestSanPham();

    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Toast.makeText(this, "Số lượng: "
                + e.getY()
               , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected() {

    }

    private void addDataSet(PieChart pieChart) {
        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();
        float[] yData = { 25, 40, 70 };
        String[] xData = { "January", "February", "January" };

        for (int i = 0; i < sanphamList.size();i++){
            yEntrys.add(new PieEntry(sanphamList.get(i).getQuantity(),sanphamList.get(i).getName()));
        }
        for (int i = 0; i < sanphamList.size();i++){
            xEntrys.add(sanphamList.get(i).getName());
        }

        PieDataSet pieDataSet=new PieDataSet(yEntrys,"");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);
        String[] colorsTxt = getApplicationContext().getResources().getStringArray(R.array.colors);
        ArrayList<Integer> colors= new ArrayList<Integer>();
        for (int i = 0; i < sanphamList.size(); i++) {
            int newColor = Color.parseColor(colorsTxt[i]);
            colors.add(newColor);
        }

//        colors.add(Color.GRAY);
//        colors.add(Color.BLUE);
//        colors.add(Color.RED);
//        colors.add(Color.MAGENTA);
//        colors.add(Color.)
//        colors.add(Color.GREEN);
//        colors.add(Color.YELLOW);

        pieDataSet.setColors(colors);
        pieDataSet.setSliceSpace(2f);
        pieChart.setDrawSliceText(false);
        pieDataSet.setValueTextColor(Color.WHITE);
        //pieDataSet.setValueTextSize(10f);
        //pieDataSet.setSliceSpace(5f);
        Legend legend=pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        //legend.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);
        legend.setYEntrySpace(20f);
        //legend.setYOffset(1);
        legend.setWordWrapEnabled(true);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);

        PieData pieData=new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }
    private void requestSanPham() {
        GetData_Product service =  CallAPI.getRetrofitInstance().create(GetData_Product.class);
        Call<ArrayList<SanPham>> call = service.getStatic();
        call.enqueue(new Callback<ArrayList<SanPham>>() {
            @Override
            public void onResponse(Call<ArrayList<SanPham>> call, Response<ArrayList<SanPham>> response) {
                Log.d("arrmovie", response.toString());
                //show san pham
                sanphamList = response.body();
                addDataSet(mChart);
                mChart.setOnChartValueSelectedListener(PieChartActivity.this);
            }
            @Override
            public void onFailure(Call<ArrayList<SanPham>> call, Throwable t) {
                Log.d("arrmovie", t.toString());
            }

        });
    }
}