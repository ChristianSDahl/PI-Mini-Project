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

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static com.example.christian.pollvotingsoftware.Main_Menu.messageFromServer;
import static com.example.christian.pollvotingsoftware.Main_Menu.messageToServer;

/**
 * Created by Christian on 27-04-2017.
 */

public class Get_Results extends AppCompatActivity {
//    String temp = "75,45,Yes or no?,Yes,No";
//    String[] parts = temp.split(",");
//    float votes1 = Float.parseFloat(parts[0]);
//    float votes2 = Float.parseFloat(parts[1]);
//    String description = parts[2];
//    String option1 = parts[3];
//    String option2 = parts[4];

    public Button backtooverview;


//    float[] allvotes = {votes1,votes2};
//    String[] options = {option1, option2};
//    public PieChart pieChart;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_get_results);
        backtooverview = (Button) findViewById(R.id.backtooverview1);

//        String pollFromServer = reqeustPollFromServer();



//        String specificPollFromServer = reqeustPollFromServer();
        reqeustPollFromServer();
        addDataSet();
    }

    public void reqeustPollFromServer(){

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    try {
                        InetAddress IP = InetAddress.getByName("10.0.2.2");
                        Socket clientSocket = new Socket(IP, 4445);
                        Log.d("Servertesting", "creates socket");
                        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
                        Log.d("Servertesting", "establishes outputstream");
                        outToServer.writeBytes("get results 0"  + "\n");
                        Log.d("SENT", "Message is sent to server");
//
//                        //waits and read input from, maybe not necessary in all cases
                        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                        Log.d("From server", "received shit from server");
                        //modified sentence == results from server
                        messageFromServer = inFromServer.readLine();
                        Log.d("From server", messageFromServer);
                        clientSocket.close();
                        outToServer.close();
                        inFromServer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //Your code goes here
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try {
            thread.join();
            Log.d("finishthread", "thread ends");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Connect to server
        //"getResults 2"
        //INPUT
//        return messageToServer;
    }

    private void addDataSet(){
        Log.d("inputserver",messageFromServer);
        String[] parts = messageFromServer.split(",");
        Log.d("arraysize",String.valueOf(parts.length));
        for(int i = 0; i < parts.length;i++) {
            Log.d("results",parts[i]);
        }
        float votes1 = Float.parseFloat(parts[1]);
        float votes2 = Float.parseFloat(parts[2]);
        String description = parts[3];
        String option1 = parts[4];
        String option2 = parts[5];

        float[] allvotes = {votes1,votes2};
        String[] options = {option1, option2};
        PieChart pieChart;

        pieChart = (PieChart) findViewById(R.id.piechart);
        pieChart.setDescription("");
        pieChart.setRotationEnabled(true);
        pieChart.setHoleRadius(25f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setDrawEntryLabels(true);

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
