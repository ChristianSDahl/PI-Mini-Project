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


//    public ServerThread(InetAddress IP, Socket clientSocket, DataOutputStream outToServer) {
//        setIP(IP);
//        setClientSocket(clientSocket);
//        setOutToServer(outToServer);
//    }

    public ServerThread() {

    }


    @Override
    public void run() {
        try {
            InetAddress IP = getByName("10.0.2.2");
//            setIP(IP);
            clientSocket = new Socket(IP, 4445);
//            setClientSocket(clientSocket);
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
//            setOutToServer(outToServer);
            while(true){
                if (Main_Menu.messageToServer.equals("loadPolls")) {
                    //sends to server with outToServer outputstream - sentence replaced with whatevs
                    outToServer.writeBytes(Main_Menu.messageToServer + '\n');
                    Main_Menu.messageToServer="";
                    //waits and read input from, maybe not necessary in all cases
                    BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    //modified sentence == results from server
//                    modifiedSentence = inFromServer.readLine();
//                    System.out.println(modifiedSentence);
//                    System.out.println("reaches here");
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }

//            clientSocket = new Socket(getIP(), 4445);
        Log.d("Servertesting", "creates socket");
//            outToServer = new DataOutputStream(getClientSocket().getOutputStream());
        Log.d("Servertesting", "establishes outputstream");
        while (true) {

        }

    }

    public static void main(String args[]) {
        //INPUTCODEHERE
    }
//
//    public InetAddress getIP() {
//        return IP;
//    }
//
//    public void setIP(InetAddress IP) {
//        this.IP = IP;
//    }
//
//    public Socket getClientSocket() {
//        return clientSocket;
//    }
//
//    public void setClientSocket(Socket clientSocket) {
//        this.clientSocket = clientSocket;
//    }
//
//    public DataOutputStream getOutToServer() {
//        return outToServer;
//    }
//
//    public void setOutToServer(DataOutputStream outToServer) {
//        this.outToServer = outToServer;
//    }
}
