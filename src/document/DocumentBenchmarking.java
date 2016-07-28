package document;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * A class for timing the EfficientDocument and BasicDocument classes
 * 
 * @author Amit
 *
 */
public class DocumentBenchmarking {

	public static void main(String[] args) {

		// Each test should be run more than once to get bigger numbers and less
		// noise.
		int trials = 200;

		// The text to test on
		String textfile = "data/warAndPeace.txt";

		// The amount of characters to increment each step
		int increment = 30000;

		// The number of steps to run.
		int numSteps = 20;

		// THe number of characters to start with.
		int start = 50000;

		System.out.println("NumberOfChars\tBasicTime\tEfficientTime");
		for (int numToCheck = start; numToCheck < numSteps * increment + start; numToCheck += increment) {
			// numToCheck holds the number of characters that should be read
			// from the file to create both a BasicDocument and an
			// EfficientDocument.

			/**
			 * Each time through this loop the code should: <br>
			 * 1. Print out numToCheck followed by a tab (\t) (NOT a newline) <br>
			 * 2. Read numToCheck characters from the file into a String Hint: use the
			 * helper method below. <br>
			 * 3. Time a loop that runs trials times (trials is the variable above) that: <br>
			 * a. Creates a BasicDocument<br>
			 * b. Calls fleshScore on this document<br>
			 * 4. Print out the time it took to complete the loop in step 3 (on the same line as the
			 * first print statement) followed by a tab (\t)<br>
			 * 5. Time a loop that runs trials times (trials is the variable above) that: <br>
			 * a. Creates an EfficientDocument <br>
			 * b. Calls fleshScore on this document <br>
			 * 6. Print out the time it took to complete the loop in step 5 (on the
			 * same line as the first print statement) followed by a newline(\n)
			 */
			System.out.print(numToCheck + "\t");
			String stringToUse = getStringFromFile(textfile, numToCheck);
			long begin = System.nanoTime();
			for (int i = 0; i < trials; i++) {
				Document basic = new BasicDocument(stringToUse);
				basic.getFleschScore();
			}
			long end = System.nanoTime();
			System.out.print(((double) (end - begin) / 1000000000) + "\t");

			begin = System.nanoTime();
			for (int i = 0; i < trials; i++) {
				Document efficient = new EfficientDocument(stringToUse);
				efficient.getFleschScore();
			}
			end = System.nanoTime();
			System.out.println(((double) (end - begin) / 1000000000));
		}

	}

	/**
	 * Get a specified number of characters from a text file
	 * 
	 * @param filename
	 *            The file to read from
	 * @param numChars
	 *            The number of characters to read
	 * @return The text string from the file with the appropriate number of
	 *         characters
	 */
	public static String getStringFromFile(String filename, int numChars) {

		StringBuffer s = new StringBuffer();
		try {
			FileInputStream inputFile = new FileInputStream(filename);
			InputStreamReader inputStream = new InputStreamReader(inputFile);
			BufferedReader bis = new BufferedReader(inputStream);
			int val;
			int count = 0;
			while ((val = bis.read()) != -1 && count < numChars) {
				s.append((char) val);
				count++;
			}
			if (count < numChars) {
				System.out.println("Warning: End of file reached at " + count
						+ " characters.");
			}
			bis.close();
		} catch (Exception e) {
			System.out.println(e);
			System.exit(0);
		}

		return s.toString();
	}
}
