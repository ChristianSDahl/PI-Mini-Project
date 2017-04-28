package dk.aau.pi.med2.nolse16.vote.system;

import java.net.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.*;

public class VotingServerThread extends Thread {
	private Socket socket = null;
	private String tempInCheck;

	public VotingServerThread(Socket socket) {
		super("MultiVotingServerThread");
		this.socket = socket;
	}

	public void run() {
		while (true) {
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String inputLine;
				if ((inputLine = in.readLine()) != null) {
					System.out.println(inputLine);

					// Load Polls
					if (inputLine.equals("loadPolls")) {
						String tempString = loadPolls();

						// ByteArrayOutputStream outToClient = new
						// ByteArrayOutputStream(socket.getOutputStream());
						DataOutputStream outToClient = new DataOutputStream(socket.getOutputStream());
						// PrintWriter out = new
						// PrintWriter(socket.getOutputStream(), true);
						outToClient.writeBytes(tempString + "\n");
						System.out.println(outToClient);
						// out.writeBytes("sending from server");
						System.out.println("sent from server now");
						outToClient.close();
						in.close();
						break;
					}

					// Create Polls
					else if (inputLine.substring(0, 7).equals("newPoll")) {
						String pollData = inputLine.substring(8);
						savePolls(pollData);
						DataOutputStream outToClient = new DataOutputStream(socket.getOutputStream());
						// PrintWriter out = new
						// PrintWriter(socket.getOutputStream(), true);
						outToClient.writeBytes("Poll saved" + "\n");
						System.out.println(outToClient);
						// out.writeBytes("sending from server");
						System.out.println("sent from server now");
						outToClient.close();
						in.close();
						break;
					}

					// Get results
					else if ((tempInCheck = inputLine.substring(0, 11)).equals("get results")) {
						String[] tempArray = inputLine.split(",");
						int tempElement = Integer.parseInt(tempArray[0].substring(12));
						String tempString = loadSpecificPoll(tempElement);
						System.out.println("This is the pole line we want to use: " + tempString);
						System.out.println("This is what is sent to the client:" + tempString);
						DataOutputStream outToClient = new DataOutputStream(socket.getOutputStream());
						outToClient.writeBytes(tempString + "\n");
						System.out.println("sent from server now");
						outToClient.close();
						in.close();
						break;
					}
					else if (inputLine.substring(0, 8).equals("votePoll")) {
						String newVotes = inputLine.substring(9);
						String[] allOldPolls = PollClass.loadStrings("./polls.txt");
						System.out.println(allOldPolls.length);
						String[] tempArray = inputLine.split(",");
						int tempElement = Integer.parseInt(tempArray[0].substring(9));
						System.out.println(tempElement);
						allOldPolls[tempElement] = newVotes;
						savePolls(allOldPolls);
//						outToClient.close();
						in.close();
						break;
					}

				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public String loadPolls() {
		String[] tempStringArray = PollClass.loadStrings("./polls.txt");
		String tempString = "";

		for (int i = 1; i < tempStringArray.length; i++) {
			if (i == tempStringArray.length - 1) {
				tempString += tempStringArray[i] + "\n";
			} else {
				tempString += tempStringArray[i] + ";";
			}
		}
		return tempString;
	}

	public String loadSpecificPoll(int specifiedPoll) {
		String[] tempStringArray = PollClass.loadStrings("./polls.txt");
		String tempString = "";

		for (int i = 0; i < tempStringArray.length; i++) {
			if (i == specifiedPoll) {
				tempString = tempStringArray[i + 1];
				System.out.println("found poll " + specifiedPoll);
			}
		}
		return tempString;
	}

	public void savePolls(String string) {
		// need to add line number infront
		try (FileWriter fw = new FileWriter("polls.txt", true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter out = new PrintWriter(bw)) {
			int linecount = PollClass.countLines("polls.txt") + 1;
			out.println("");
			out.print(String.valueOf(linecount) + "," + string);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void savePolls(String[] polls) {
		// StringBuilder builder = new StringBuilder();
		// for (String string : polls) {
		// builder.append(string);
		// }

		// builder.toString();
		// return builder.toString();
		String string = null;
		try (FileWriter fw = new FileWriter("polls.txt", false);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter out = new PrintWriter(bw)) {
			for (int i = 0; i < polls.length; i++) {
				string = polls[i];
				if (i == polls.length - 1) {
					out.print(string);
				} else {
					out.println(string);
				}
			}
			// out.println(string);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}