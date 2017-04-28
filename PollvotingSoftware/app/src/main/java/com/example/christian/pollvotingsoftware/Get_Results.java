package com.example.christian.pollvotingsoftware;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static com.example.christian.pollvotingsoftware.Main_Menu.messageFromServer;

/**
 * This is the class for the activity that displays the results of a given poll.
 */

public class Get_Results extends AppCompatActivity {
    public Button backtooverview;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_get_results);
        backtooverview = (Button) findViewById(R.id.backtooverview1);

        requestPollFromServer();
        addDataSet();
    }

    public void requestPollFromServer(){
        // To get results from a different poll switch this int to it's integer.
        final int pollToGetResultsFrom = 0;

        //This thread starts the server in order to save the finished poll.
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    try {
                        // 10.0.2.2 is the IP Address for the local host.
                        InetAddress IP = InetAddress.getByName("10.0.2.2");
                        // We use the port 4445 for our server.
                        Socket clientSocket = new Socket(IP, 4445);
                        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
                        //The request is sent to the server.
                        outToServer.writeBytes("get results " + pollToGetResultsFrom + "\n");
                        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                        //The relevant string data is received as messageFromServer.
                        messageFromServer = inFromServer.readLine();
                        Log.d("From server", messageFromServer);
                        clientSocket.close();
                        outToServer.close();
                        inFromServer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void addDataSet(){
        //The string poll data is split up into a string array with the different elements.
        String[] parts = messageFromServer.split(",");
        for(int i = 0; i < parts.length;i++) {
            Log.d("results",parts[i]);
        }

        //The variables needed for the data representation pie chart is prepared.
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

        //The data for the pie chart is counted
        List<PieEntry> pieEntries = new ArrayList<>();
        for (int i=0; i<allvotes.length; i++){
            pieEntries.add(new PieEntry(allvotes[i],options[i]));
        }

        //Pie Chart design
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

    // Method for the back button.
    public void backToOverview1(View view){
        Intent i = new Intent(Get_Results.this, Poll_Overview.class);
        startActivity(i);
    }
}
