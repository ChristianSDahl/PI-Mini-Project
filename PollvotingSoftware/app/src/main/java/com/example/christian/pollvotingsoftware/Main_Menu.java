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

/**
 * This is the class for the launcher activity. It has a connect button making sure the connection is opened before the rest of the app is available.
 */
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

    // Moves to the Poll_Overview which then connects to the server when opened.
    public void connectToServer(View view) {
        Intent i = new Intent(Main_Menu.this, Poll_Overview.class);
        startActivity(i);
    }
}