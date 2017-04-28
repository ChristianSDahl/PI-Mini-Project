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
 * This class shows the activity to create a new Poll.
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

    public void savePoll(View view) {
        //This thread starts the server in order to save the finished poll.
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    try {
                        //.getText().toString() saves the text in the EditText fields to a string.
                        String finalDescription = description.getText().toString();
                        String finalOption1 = option1.getText().toString();
                        String finalOption2 = option2.getText().toString();
                        //finalPoll is the string that is send to the server. It starts with "newPoll" because that is how we identify the strings coming into the server.
                        String finalPoll = "newPoll" + " 0," + "0," + finalDescription + "," + finalOption1 + "," + finalOption2;

                        // 10.0.2.2 is the IP Address for the local host.
                        InetAddress IP = InetAddress.getByName("10.0.2.2");
                        // We use the port 4445 for our server.
                        Socket clientSocket = new Socket(IP, 4445);
                        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
                        // The finalPoll is sent to the server.
                        outToServer.writeBytes(finalPoll + "\n");
                        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                        messageFromServer = inFromServer.readLine();
                        // messageFromServer confirms that the server accepted the string.
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
            Log.d("finishthread", "thread ends");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // After a String has been sent and saved to the server, the activity goes back to the Poll_Overview.
        Intent i = new Intent(Create_Poll.this, Poll_Overview.class);
        startActivity(i);
    }

    // Method for the back button.
    public void backToOverview(View view) {
        Intent i = new Intent(Create_Poll.this, Poll_Overview.class);
        startActivity(i);
    }

}