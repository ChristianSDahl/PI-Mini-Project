package com.example.christian.pollvotingsoftware;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;

import static java.net.InetAddress.getByName;

public class Main_Menu extends AppCompatActivity {
    public Button connect;
    public static String messageToServer;
    public static String messageFromServer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__menu);

        connect = (Button) findViewById(R.id.connect);

        messageToServer = "loadPolls";
    }

    public void connectToServer(View view) {
//        ServerThread serverThread = new ServerThread();
//        Thread serverConnection = new Thread(serverThread);
//        serverConnection.start();
//        try {
//            InetAddress IP = InetAddress.getByName("10.0.2.2");
//            Socket clientSocket = new Socket(IP, 4445);
//            Log.d("Servertesting", "creates socket");
//        }catch (IOException e) {
//            e.printStackTrace();
//        }

//        serverThread.

//
//        Thread thread = new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//                try {
//                    try {
//                        InetAddress IP = InetAddress.getByName("10.0.2.2");
//                        Socket clientSocket = new Socket(IP, 4445);
//                        Log.d("Servertesting", "creates socket");
//                        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
//                        Log.d("Servertesting", "establishes outputstream");
//                        outToServer.writeBytes(messageToServer + "\n");
//                        Log.d("SENT", "Message is sent to server");
////
////                        //waits and read input from, maybe not necessary in all cases
//                        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//                        Log.d("From server", "received shit from server");
//                        //modified sentence == results from server
//                        messageFromServer = inFromServer.readLine();
//                        Log.d("From server", messageFromServer);
//                        clientSocket.close();
//                        outToServer.close();
//                        inFromServer.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    //Your code goes here
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        thread.start();
//        try {
//            thread.join();
//            Log.d("finishthread", "thread ends");
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }


        //Goes to class called ViewPolls
        Intent i = new Intent(Main_Menu.this, Poll_Overview.class);
        startActivity(i);
    }
}