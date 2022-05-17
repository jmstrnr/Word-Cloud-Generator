package ie.gmit.dip;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
* IgnoredWords used create list of words to be ignored, and check that a word is not in that list
*/
public class IgnoredWords {
	// Constant and not intended to be changed or mutated by code
	private static final String FILE_PATH = "./ignorewords.txt";

	private static ArrayList<String> ignoredWords = new ArrayList<String>();

	/**
	* Checks if the passed-in word is in the list of words to be ignored
	*
	* @param word The word to be checked
	* @return boolean Indicates whether the word is to be ignored or not
    */
	// Running time: O(n)/linear - uses ArrayList.contains() which is O(n)
	public static boolean checkIfIgnored(String word) {
		if (ignoredWords.contains(word)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	* Adds the words in the ignorewords.txt file to an arraylist
    */
	// Running time: O(n)/linear - time taken grows linearly as the input file has
	// more words to be added to arraylist
	public static void createIgnoredWordsArray() throws IOException {
		BufferedReader reader; 
		
		// Read text from the ignorewords.txt file
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(FILE_PATH)));
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException("File not found. Error: " + e);
		}

		try {
			String currentWord;
			// Read each line until the end of the stream/file has been reached
			while ((currentWord = reader.readLine()) != null) {
				// Add the normalized word to be ignored to the arraylist
				ignoredWords.add(currentWord.toLowerCase());
			}
		} catch (IOException e) {
			throw new IOException("Error reading from file. Error: " + e);
		}
		reader.close();
	}
}
