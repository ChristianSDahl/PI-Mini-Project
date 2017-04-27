package com.example.kirstine.mini_projectuser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static com.example.kirstine.mini_projectuser.MainMenu.clientSocket;

/**
 * Created by Kirstine on 27-04-2017.
 */


//Array of options, which will run through an ArrayAdapter, that will show in the ListView
//Array of options --> ArrayAdapter --> ListView

//List view: {views: da_items.xml}

public class ViewPolls extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_viewpolls);


        init();
    }

    //Called when the user touches button "back"
    public void back3(View view) {
        //Goes back to the Menu
        Intent i = new Intent(ViewPolls.this, MainMenu.class);
        startActivity(i);
    }

    public void init() {
        pollsFromServer();

        TableLayout ll = (TableLayout) findViewById(R.id.displayLinear);
        for (int i = 0; i < 20; i++) {
            if (i % 2 == 0) {
                TableRow desc = new TableRow(this);
                TableRow.LayoutParams pl = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                desc.setLayoutParams(pl);
                TextView description = new TextView(this);
                description.setText("NEW TESTING");
                desc.addView(description);
                ll.addView(desc, i);
            } else {
                TableRow row = new TableRow(this);
                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                row.setLayoutParams(lp);
                Button button = new Button(this);
                Button button2 = new Button(this);
                row.addView(button);
                row.addView(button2);
                ll.addView(row, i);
            }
        }
    }

    public String[] pollsFromServer(){
        try{
            String sentence = "loadPolls";
            String modifiedSentence;
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            outToServer.writeBytes(sentence + "\n");
            Log.d("SENT", "Message is sent to server");

            //waits and read input from server
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            Log.d("From server", "received shit from server");
            //modified sentence == results from server
            modifiedSentence = inFromServer.readLine();
            Log.d("From server", modifiedSentence);
            clientSocket.close();
            outToServer.close();
        }catch(IOException e){
            e.printStackTrace();
        }

        return null;
    }
}
