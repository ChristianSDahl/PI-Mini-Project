package com.example.christian.pollvotingsoftware;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;

import static com.example.christian.pollvotingsoftware.Main_Menu.messageFromServer;
import static com.example.christian.pollvotingsoftware.Main_Menu.messageToServer;

/**
 * Created by Christian on 27-04-2017.
 */

public class Create_Poll extends AppCompatActivity {

    public Button backtooverview;
    public Button savepoll;
    public EditText description;
    public EditText option1;
    public EditText option2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_create_poll);
        backtooverview = (Button) findViewById(R.id.backtooverview);
        savepoll = (Button) findViewById(R.id.savepoll);

        description = (EditText) findViewById(R.id.editDesc);
        option1 = (EditText) findViewById(R.id.editButton1);
        option2 = (EditText) findViewById(R.id.editButton2);
    }

    public void savePoll(View view){



        //start server
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    try {
                        String finalDescription = description.getText().toString();
                        String finalOption1 = option1.getText().toString();
                        String finalOption2 = option2.getText().toString();
                        String finalPoll = "newPoll" + " 0," + "0," + finalDescription + "," + finalOption1 + "," + finalOption2;

                        InetAddress IP = InetAddress.getByName("10.0.2.2");
                        Socket clientSocket = new Socket(IP, 4445);
                        Log.d("Servertesting", "creates socket");
                        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
                        Log.d("Servertesting", "establishes outputstream");
                        outToServer.writeBytes(finalPoll + "\n");
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

//        Log.d("done", finalPoll);
    }




public void backToOverview(View view){
    Intent i = new Intent(Create_Poll.this, Poll_Overview.class);
    startActivity(i);
}

}