package dk.aau.pi.med2.nolse16.vote.system;

import java.net.*;
import java.io.*;

public class VotingServer {
	// main method for server
	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = null;
		// Boolean in place to enable/disable the listening for new clients
		boolean listening = true;

		// try/catch block to catch any IOExceptions thrown by ServerSockets
		// constructor
		try {
			serverSocket = new ServerSocket(4445);
		} catch (IOException e) {
			// error print to indicate port not available
			System.err.println("Could not listen on port defined:");
			System.exit(-1);
		}
		// while searching for new clients
		while (listening) {
			// waiting for a client to create a connection with server. When
			// connection has been established a new thread is created and
			// started.
			new VotingServerThread(serverSocket.accept()).start();
			System.out.println("Client accepted");
		}
		// if server was to stop listening the socket would stop
		serverSocket.close();
	}
}
