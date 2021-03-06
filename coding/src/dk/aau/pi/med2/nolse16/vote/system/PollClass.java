package dk.aau.pi.med2.nolse16.vote.system;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;

// Class made to make it easier for ourselves to understand the handling of the polls
public class PollClass {
	// methods are static in order for them to be called without an
	// instanciation of the PollClass
	public static int countLines(String filename) throws IOException {
		InputStream is = new BufferedInputStream(new FileInputStream(filename));
		try {
			byte[] c = new byte[1024];
			int count = 0;
			int readChars = 0;
			boolean empty = true;
			while ((readChars = is.read(c)) != -1) {
				empty = false;
				for (int i = 0; i < readChars; ++i) {
					if (c[i] == '\n') {
						++count;
					}
				}
			}
			return (count == 0 && !empty) ? 1 : count;
		} finally {
			is.close();
		}
	}

	public static String[] loadStrings() {
		BufferedReader reader = null;
		int count = 0;
		try {
			count = countLines("./polls.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
		String[] tempStringArray = null;
		if (count != 0) {
			tempStringArray = new String[count + 1];
		}

		try {
			reader = new BufferedReader(new FileReader("./polls.txt"));

			int linecount = 0;
			String line;
			while ((line = reader.readLine()) != null) {
				tempStringArray[linecount] = line;
				linecount++;
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return tempStringArray;
	}
}
