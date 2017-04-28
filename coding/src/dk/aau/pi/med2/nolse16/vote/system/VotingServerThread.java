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
					// Vote Polls
					else if (inputLine.substring(0, 8).equals("votePoll")) {
						String[] tempArrayforInput = inputLine.split(",");
						System.out.println("Problem number: " + tempArrayforInput[0].substring(8,9));
						String oldPoll = inputLine.substring(9);
						System.out.println("oldpoll: " + oldPoll);
						String[] oldPollArray = oldPoll.split(",");
						String[] changedPollArray = oldPollArray;
						if (Integer.parseInt(tempArrayforInput[0].substring(8,9)) == 1) {
							System.out.println("change before: " + changedPollArray[1]);
							changedPollArray[1] = String.valueOf(((Integer.parseInt(changedPollArray[1])+1)));
							System.out.println("change after: " + changedPollArray[1]);
						}else{
							changedPollArray[2] = String.valueOf(((Integer.parseInt(changedPollArray[2])+1)));
						}
						String newVote = "";
						
//						for (int i = 0;i<changedPollArray.length;i++){
//						newVote += changedPollArray[i];
//						}
						newVote = String.join(",", changedPollArray);
						System.out.println("newvote: " + newVote);
						String[] allOldPolls = PollClass.loadStrings("./polls.txt");
						
						
						allOldPolls[Integer.parseInt(changedPollArray[0])] = newVote;
						savePolls(allOldPolls);

						System.out.println("votepolls succesful");
						in.close();
						break;
						
//						System.out.println("inputline: " + inputLine);
//						String newVotes = inputLine.substring(11);
//						int decider = Integer.parseInt(inputLine.substring(9, 10));
//						String pollChange = "";
//						if (decider == 1) {
//							String[] changedVote1 = newVotes.split(",");
//							pollChange = changedVote1[1].replace(changedVote1[1], (changedVote1[1]) + 1);
//						} else if (decider == 2) {
//							String[] changedVote2 = newVotes.split(",");
//							pollChange = changedVote2[2].replace(changedVote2[2], (changedVote2[2]) + 1);
//						}
//						System.out.println("New votes: " + String.valueOf(newVotes));
//						System.out.println("pollchange: " + pollChange);
//						String[] allOldPolls = PollClass.loadStrings("./polls.txt");
//						System.out.println(allOldPolls.length);
//						String[] tempArray = pollChange.split(",");
//						int tempElement = Integer.parseInt(tempArray[0]);
//						System.out.println(tempElement);
//						allOldPolls[tempElement] = newVotes;
//						savePolls(allOldPolls);
//						// outToClient.close();
//						System.out.println("votepolls succesful");
//						in.close();
//						break;
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