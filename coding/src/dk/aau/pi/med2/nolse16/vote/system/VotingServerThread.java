package dk.aau.pi.med2.nolse16.vote.system;

import java.net.*;
import java.io.*;

// class for the serverthread. Extends the Thread Class.
public class VotingServerThread extends Thread {
	private Socket socket = null;

	// constructor for the thread
	public VotingServerThread(Socket socket) {
		super("MultiVotingServerThread");
		this.socket = socket;
	}

	// since this class is extending the Thread class, the method run has to be
	// implemented.
	public void run() {
		// after the server-thread is started the following blocks of code will
		// be
		// continuously run on the thread until any breaks occur of course.
		while (true) {
			// inside a try/catch block to easily handle IOExceptions thrown
			try {
				// instanciating a bufferedreader to more easily handle the
				// input received from the client through the socket.
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String inputLine;
				if ((inputLine = in.readLine()) != null) {

					// Load Polls - checks the input
					if (inputLine.equals("loadPolls")) {
						String tempString = loadPolls();
						// instanciates a outputstream to send bytes of data to
						// the client through socket
						DataOutputStream outToClient = new DataOutputStream(socket.getOutputStream());
						// write bytes converts string to bytes
						outToClient.writeBytes(tempString + "\n");
						System.out.println("sent from server now");

						// makes sure to close all streams to not leak any data
						outToClient.close();
						in.close();

						// jumps outside of while-loop
						break;
					}

					// Create Polls - checks the input
					else if (inputLine.substring(0, 7).equals("newPoll")) {
						String pollData = inputLine.substring(8);

						// runs method defined further below. A void function
						// which takes a string and adds the string to a file
						// placed on the server system.
						savePolls(pollData);
						DataOutputStream outToClient = new DataOutputStream(socket.getOutputStream());
						outToClient.writeBytes("Poll saved" + "\n");
						System.out.println("sent from server now");

						outToClient.close();
						in.close();
						break;
					}

					// Get results - checks the input
					else if ((inputLine.substring(0, 11)).equals("get results")) {
						// String.split is a easy way to convert a string file
						// into an Array. In this case by using the separator
						// ",".
						String[] tempArray = inputLine.split(",");
						int tempElement = Integer.parseInt(tempArray[0].substring(12));
						// runs method loadSpecificPoll with returns a string of
						// data. Takes an int as an input-variable to declare
						// which
						// poll is wanted from the data.
						String tempString = loadSpecificPoll(tempElement);
						DataOutputStream outToClient = new DataOutputStream(socket.getOutputStream());
						outToClient.writeBytes(tempString + "\n");
						System.out.println("sent from server now");

						outToClient.close();
						in.close();
						break;
					}
					// Vote Polls - checks the input
					else if (inputLine.substring(0, 8).equals("votePoll")) {
						String[] tempArrayforInput = inputLine.split(",");
						String oldPoll = inputLine.substring(9);
						String[] oldPollArray = oldPoll.split(",");
						String[] changedPollArray = oldPollArray;
						// if/else statement with depends on a char which can
						// either be a 1 or 2 indicating which voteNumber the
						// client has clicked on.
						if (Integer.parseInt(tempArrayforInput[0].substring(8, 9)) == 1) {
							changedPollArray[1] = String.valueOf(((Integer.parseInt(changedPollArray[1]) + 1)));
						} else {
							changedPollArray[2] = String.valueOf(((Integer.parseInt(changedPollArray[2]) + 1)));
						}
						String newVote = "";
						// String.join is the reverse of .split. It returns a
						// string object with a separator and String[] as input
						newVote = String.join(",", changedPollArray);
						// runs method loadStrings which is defiend in the
						// static PollClass. Handles and returns a String[] from
						// a file defined within the method.
						String[] allOldPolls = PollClass.loadStrings();
						allOldPolls[Integer.parseInt(changedPollArray[0])] = newVote;
						// runs the method savePolls with a String[] as a
						// variable. This method takes the String[] inputted and
						// writes it into a txt.file.
						savePolls(allOldPolls);
						System.out.println("votepolls succesful");

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
		// runs method defined in the PollClass.
		String[] tempStringArray = PollClass.loadStrings();
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
		String[] tempStringArray = PollClass.loadStrings();
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
		try (FileWriter fw = new FileWriter("polls.txt", true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter out = new PrintWriter(bw)) {
			int linecount = PollClass.countLines("polls.txt") + 1;
			out.println("");
			out.print(String.valueOf(linecount) + "," + string);
			
			bw.close();
			out.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void savePolls(String[] polls) {
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
			bw.close();
			out.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}