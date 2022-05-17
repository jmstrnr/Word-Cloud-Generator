package ie.gmit.dip;

import java.net.URL;
import java.util.Scanner;

/**
* Runner used to run application and get input from user
*/
public class Runner {
	private static boolean isRunning = true;  
	private static int wordCount = 100; 

	/**
	* Getter for isRunning
	*
	* @return isRunning The programs running state
	*/
	// Running time: O(1)/constant - takes no input
	public static boolean getIsRunning() {
		return isRunning;
	}

	/**
	* Setter for isRunning
	*
	* @param state The programs running state
	*/
	// Running time: O(1)/constant - does not vary depending on size of input
	public static void setIsRunning(boolean state) {
		isRunning = state;
	}

	/**
	* Getter for wordCount
	*
	* @return wordCount The number of words to be displayed in word-cloud
	*/
	// Running time: O(1)/constant - takes no input
	public static int getWordCount() {
		return wordCount;
	}

	/**
	* Setter for wordCount
	*
	* @param count The number of words to be displayed in word-cloud
	*/
	// Running time: O(1)/constant - does not vary depending on size of input
	public static void setWordCount(int count) {
		wordCount = count;
	}

	/**
	* Creates two threads to output the frequency table text file and word-cloud
	*
	* @param outputFile The name used for the output file
	* @param frequencyTable The frequency table the files are generated from
	*/
	// Running time: O(n)/linear - since the methods invoked in the threads are both
	// linear time complexity
	private static void generateOutput(String outputFile, FrequencyTable frequencyTable) {
		// Separate threads are used to generate each output file to improve performance

		// Thread to generate the frequency table text file containing all results
		Thread textFileThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					frequencyTable.output(outputFile);
				} catch (Exception e) {
					System.out.println("Error generating output frequency table. Error: " + e);
				}
			}
		});
		textFileThread.start();

		// Thread to generate the word-cloud image file
		Thread wordCloudThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					WordCloud.outputWordCloud(outputFile, wordCount, frequencyTable);
				} catch (Exception e) {
					System.out.println("Error generating word cloud image. Error: " + e);
				}
			}
		});
		wordCloudThread.start();
	}

	
	/**
	* Runs the program and prompts user for input
	*/
	// Running time: varies depending on user input choice.
	// If user enters 1 or 2 running time is O(n log n) since the operation to sort the frequency 
	// table has the highest time complexity.
	// If the user enters 3 or 4 running time is constant since there is no input.
	public static void main(String[] args) throws Exception {

		do { // Loop that keeps menu running while isRunning is true
			System.out.println("***************************************************");
			System.out.println("*                                                 *");
			System.out.println("*           Word Cloud Generator                  *");
			System.out.println("*                                                 *");
			System.out.println("***************************************************");

			System.out.println("(1) Enter filename"); 
			System.out.println("(2) Enter URL"); 
			System.out.println("(3) Set maximum displayed word count. Current: " + getWordCount()); 
			System.out.println("(4) Quit"); 
			System.out.println("\nPlease choose an option by entering [1-4]>");

			IgnoredWords.createIgnoredWordsArray();  // Create list of words to be ignored
			FrequencyTable frequencyTable = new FrequencyTable();  

			Scanner scanner = new Scanner(System.in);
			int userChoice;

			try {
				userChoice = scanner.nextInt();
				scanner.nextLine();
			} catch (Exception e) {
				scanner.close();
				throw new Exception("Invalid input. Please enter a number between 1 and 4. Error: " + e);
			}

			switch (userChoice) {

			case 1 -> {
				System.out.println("You chose 1");

				// Prompt user for input file name
				System.out.println("Please enter the filename you wish to generate a word-cloud from.");
				String inputFile = scanner.nextLine().trim();
				System.out.println("You chose: " + inputFile);

				// Prompt user for output file name
				System.out.println(
						"Please enter the name to give the output file (do not include a period or the file extension).");

				String outputFile = scanner.nextLine().trim();

				if (outputFile.contains(".")) {
					scanner.close();
					throw new Exception("Invalid input. The output file name cannot include a period/full stop.");
				}

				System.out.println("You chose: " + outputFile);

				frequencyTable.clear(); // Clear results from previous runs

				Parser.parseFile(inputFile, frequencyTable); // Parse the file

				generateOutput(outputFile, frequencyTable); // Generate the output files
			}

			case 2 -> {
				System.out.println("You chose 2");

				// Prompt user for input URL
				System.out.println("Please enter the URL you wish to generate a word-cloud from.");
				String inputURLString = scanner.nextLine().trim();
				System.out.println("You chose: " + inputURLString);

				// Prompt user for output file name
				System.out.println(
						"Please enter the name to give the output file (do not include a period or the file extension).");

				String outputFile = scanner.nextLine().trim();

				if (outputFile.contains(".")) {
					scanner.close();
					throw new Exception("Invalid input. The output file name cannot include a period/full stop.");
				}

				System.out.println("You chose: " + outputFile);

				frequencyTable.clear(); // Clear results from previous runs

				URL url = new URL(inputURLString); // Convert URL string to a URL object
				Parser.parseURL(url, frequencyTable); // Parse the URL

				generateOutput(outputFile, frequencyTable); // Generate the output files
			}

			case 3 -> {
				System.out.println("You chose 3");
				System.out.println(
						"The current maximum number of words displayed in the output word-cloud is: " + getWordCount());

				System.out.println(
						"Please enter the maximum number of words to be displayed (must be between 10 and 100).");

				int inputWordCount;

				try {
					inputWordCount = scanner.nextInt();
					scanner.nextLine();

					if (inputWordCount < 10 || inputWordCount > 100) {
						scanner.close();
						throw new Exception(); // Go to catch block to give user feedback
					}
				} catch (Exception e) {
					throw new Exception("Invalid input. Please enter a number between 10 and 100. Error: " + e);
				}

				setWordCount(inputWordCount); 

				System.out.println("The word count has been set to:  " + getWordCount());
			}

			case 4 -> {
				System.out.println("You chose 4");
				System.out.println("Shutting down the application and closing the menu...");

				scanner.close(); // Close to avoid potential resource leaks
				setIsRunning(false); // Set isRunning to false to close the menu to prevent user input
			}

			default -> System.out.println("Invalid selection. Please enter a number between 1 and 4.");
			}

		} while (isRunning); // Keep the terminal open to allow user input while isRunning is true
	}
}