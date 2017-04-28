package com.example.christian.pollvotingsoftware;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;

import static java.net.InetAddress.getByName;

/**
 * Created by Nickl on 27-04-2017.
 */

public class ServerThread implements Runnable {
    private Socket clientSocket = null;

    public ServerThread() {
    }

    @Override
    public void run() {
        try {
            InetAddress IP = getByName("10.0.2.2");
            clientSocket = new Socket(IP, 4445);
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            while (true) {
                if (Main_Menu.messageToServer.equals("loadPolls")) {
                    //sends to server with outToServer outputstream - sentence replaced with whatevs
                    outToServer.writeBytes(Main_Menu.messageToServer + '\n');
                    Main_Menu.messageToServer = "";
                    //waits and read input from, maybe not necessary in all cases
                    BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("Servertesting", "creates socket");
        Log.d("Servertesting", "establishes outputstream");
        while (true) {

        }

    }

    public static void main(String args[]) {
        //INPUTCODEHERE
    }
}
