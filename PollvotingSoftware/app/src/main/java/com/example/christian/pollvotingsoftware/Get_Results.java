package com.example.christian.pollvotingsoftware;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Christian on 27-04-2017.
 */

public class Get_Results extends AppCompatActivity {
    String temp = "75,45,Yes or no?,Yes,No";
    String[] parts = temp.split(",");
    float votes1 = Float.parseFloat(parts[0]);
    float votes2 = Float.parseFloat(parts[1]);
    String description = parts[2];
    String option1 = parts[3];
    String option2 = parts[4];

    public Button backtooverview;


    float[] allvotes = {votes1,votes2};
    String[] options = {option1, option2};
    public PieChart pieChart;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_get_results);
        backtooverview = (Button) findViewById(R.id.backtooverview1);

        pieChart = (PieChart) findViewById(R.id.piechart);
        pieChart.setDescription("");
        pieChart.setRotationEnabled(true);
        pieChart.setHoleRadius(25f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setDrawEntryLabels(true);

        addDataSet();
    }


    private void addDataSet(){
        List<PieEntry> pieEntries = new ArrayList<>();
        for (int i=0; i<allvotes.length; i++){
            pieEntries.add(new PieEntry(allvotes[i],options[i]));
        }

        PieDataSet pieDataSet = new PieDataSet(pieEntries, description);
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(15);

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.LTGRAY);
        colors.add(Color.DKGRAY);

        pieDataSet.setColors(colors);

        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);
        legend.setTextSize(15f);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.animateY(750);
        pieChart.invalidate();

    }
    public void backToOverview1(View view){
        Intent i = new Intent(Get_Results.this, Poll_Overview.class);
        startActivity(i);
    }
}
