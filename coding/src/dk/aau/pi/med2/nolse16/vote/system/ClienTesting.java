package dk.aau.pi.med2.nolse16.vote.system;

import java.io.*;
import java.net.*;

public class ClienTesting {
	public static void main(String argv[]) throws Exception {
		while (true) {
			String sentence;
			String modifiedSentence;
			BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
			Socket clientSocket = new Socket("localhost", 4445);
			DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
			
			String tempInCheck;
			

			sentence = inFromUser.readLine();
			
			
//			LoadPolls
			if (sentence.equals("loadPolls")) {
				//sends to server with outToServer outputstream - sentence replaced with whatevs
				outToServer.writeBytes(sentence + '\n');
				//waits and read input from, maybe not necessary in all cases
				BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				//modified sentence == results from server
				modifiedSentence = inFromServer.readLine();
				System.out.println(modifiedSentence);
				System.out.println("reaches here");
			}
//			Create Poll
			else if (sentence.substring(0, 7).equals("newPoll")) {
				//sends to server with outToServer outputstream - sentence replaced with whatevs
				outToServer.writeBytes(sentence + '\n');
				//waits and read input from, maybe not necessary in all cases
				BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				//modified sentence == results from server
				modifiedSentence = inFromServer.readLine();
				System.out.println(modifiedSentence);	
				System.out.println("reaches here");
			}
//			Get Results
			else if ((tempInCheck = sentence.substring(0,11)).equals("get results")) {
				//sends to server with outToServer outputstream - sentence replaced with whatevs
				System.out.println("get results + some shit is sent to server");
				outToServer.writeBytes(sentence + '\n');
				//waits and read input from, maybe not necessary in all cases
				BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				//modified sentence == results from server
				modifiedSentence = inFromServer.readLine();
				System.out.println(modifiedSentence);	

//				System.out.println("reaches here");
			}
			else if ((tempInCheck = sentence.substring(0,8)).equals("votePoll")) {
				//sends to server with outToServer outputstream - sentence replaced with whatevs
				System.out.println("get results + some shit is sent to server");
				outToServer.writeBytes(sentence + '\n');
				//waits and read input from, maybe not necessary in all cases
//				BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				//modified sentence == results from server
//				modifiedSentence = inFromServer.readLine();
//				System.out.println(modifiedSentence);	
			}
//			outToServer.writeBytes(sentence + '\n');
			// modifiedSentence = inFromServer.readLine();
			// System.out.println("FROM SERVER: " + modifiedSentence);
			clientSocket.close();
		}
	}
}
	