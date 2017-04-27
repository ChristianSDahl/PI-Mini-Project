package com.example.kirstine.mini_projectuser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;

public class MainMenu extends AppCompatActivity {

    static Socket clientSocket;
    static InetAddress IP;
//    Public int testing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_menu);

        connectToServer();

    }

    //Called when the user touches button "View polls"
    public void viewPolls(View view) {
        //Goes to class called ViewPolls
        Intent i = new Intent(MainMenu.this, ViewPolls.class);
        startActivity(i);
    }

    //Called when the user touches button "Make poll"
    public void makePoll(View view) {
        //Goes to class called MakePoll
        Intent i1 = new Intent(MainMenu.this, MakePoll.class);
        startActivity(i1);
    }

    //Called when the user touches button "View Results"
    public void viewResults(View view) {
        //Goes to class called ViewResults
        Intent i2 = new Intent(MainMenu.this, ViewResults.class);
        startActivity(i2);
    }

    public void connectToServer() {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    try {
                        IP = InetAddress.getByName("10.0.2.2");
//                        Socket clientSocket = new Socket();
                        clientSocket = new Socket(IP, 4445);

//                        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
//                        outToServer.writeBytes(sentence + "\n");
//                        Log.d("SENT", "Message is sent to server");
//
//                        //waits and read input from, maybe not necessary in all cases
//                        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//                        Log.d("From server", "received shit from server");
//                        //modified sentence == results from server
//                        modifiedSentence = inFromServer.readLine();
//                        Log.d("From server", modifiedSentence);
//                        clientSocket.close();
//                        outToServer.close();

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
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}




