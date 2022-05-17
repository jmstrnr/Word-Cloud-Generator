package ie.gmit.dip;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
* Parser used to parse input into words and use those words to fill out frequency table
*/
public class Parser {

	/**
	* Parses the input file, separates it into words, and adds those words to the frequency table
	*
	* @param file The filepath of the input file
	* @param frequencyTable The frequency table to add words to
    */
	// Running time: O(n²)/quadratic - running time grows linearly with each
	// character in the input text file due to the while loop, and nested within that while loop
	// is another operation that grows linearly  with each word in the ignored words list that 
	// must be checked against. This results in O(n * n) = O(n²)
	public static void parseFile(String file, FrequencyTable frequencyTable) throws Exception {

		BufferedReader reader; 

		// Read text from input file
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException("File not found. Error: " + e);
		}

		try {
			StringBuffer buffer = new StringBuffer(); // Used to store each character until a complete word is formed
			char character;
			int characterInt; // Used to store integer returned from current character by read method
			String word;

			// Read a single character until the end of the stream/file has been reached
			while ((characterInt = reader.read()) != -1) {

				character = (char) characterInt; 

				if (character >= 'A' && character <= 'Z' || character >= 'a' && character <= 'z' || character == '\'') {

					// If the character is an apostrophe, ignore it and continue to the next character
					if (character == '\'') {
						continue;
					}

					buffer.append(character); // Add each character to the buffer to form a complete word
				} else { // When reaching another character, like space, newline, comma, period, etc...

					// Create a word string from the characters in the buffer + normalize it by making 
					// all characters lowercase to remove duplicates from the frequency table
					word = buffer.toString().toLowerCase();

					buffer.setLength(0); // Clear the buffer of previous data, which will be garbage collected

					// Check if the word is to be ignored, and if not, increase its frequency
					if (word.length() > 0 && IgnoredWords.checkIfIgnored(word) == false) {
						frequencyTable.incrementFrequency(word);
					}
				}

			}
			frequencyTable.sort(); // Sort the table for outputting files
		} catch (IOException e) {
			reader.close();
			throw new IOException("Error reading from file. Error: " + e);
		}
		reader.close();
	}

	/**
	* Parses the input URL stream, separates it into words, and adds those words to the frequency table
	*
	* @param url The url of the webpage to be parsed
	* @param frequencyTable The frequency table to add words to
    */
	// Running time: O(n²)/quadratic - running time grows linearly with each
	// character in the input URL stream due to the while loop, and nested within that while loop
	// is another operation that grows linearly  with each word in the ignored words list that 
	// must be checked against. This results in O(n * n) = O(n²)
	public static void parseURL(URL url, FrequencyTable frequencyTable) throws Exception {
		BufferedReader reader;

		// Read text from input URL
		try {
			reader = new BufferedReader(new InputStreamReader(url.openStream()));
		} catch (Exception e) {
			throw new Exception("Error streaming from URL. Error: " + e);
		}

		try {
			StringBuffer buffer = new StringBuffer(); // Used to store each character until a complete word is formed
			char character;
			int characterInt; // Used to store integer returned from current character by read method
			String word; 
			// Used to keep track of whether the current character is outside a HTML tag and should not be ignored
			boolean outsideTag = true; 

			// Read a single character until the end of the stream/file has been reached
			while ((characterInt = reader.read()) != -1) {

				character = (char) characterInt; 

				// If the character is the opening bracket of a HTML tag, use outsideTag
				// variable to ensure no characters are added until the tag is exited
				if (character == '<') {
					outsideTag = false;
					continue;
				}

				// If the character is the closing bracket of a HTML tag, change outsideTag
				// variable so that the next character can get added
				if (character == '>') {
					outsideTag = true;
					continue;
				}

				if (character >= 'A' && character <= 'Z' || character >= 'a' && character <= 'z' || character == '\'') {

					// If the character is an apostrophe, ignore it and continue to the next character
					if (character == '\'') {
						continue;
					}

					// If character is outside a HTML tag, add the character
					if (outsideTag) {
						buffer.append(character); // Add each character to the buffer to form a complete word
					}
				} else { 
					// When not in a HTML tag and reaching another character, like space, newline, comma, period, etc...

					// Create a word string from the characters in the buffer + normalize it by making 
					// all characters lowercase to remove duplicates from the frequency table
					word = buffer.toString().toLowerCase();

					buffer.setLength(0); // Clear the buffer of previous data, which will be garbage collected

					// Check if the word is to be ignored, and if not, increase its frequency
					if (word.length() > 0 && IgnoredWords.checkIfIgnored(word) == false) {
						frequencyTable.incrementFrequency(word);
					}
				}
			}
			frequencyTable.sort(); // Sort the table for outputting files
		} catch (IOException e) {
			reader.close();
			throw new IOException("Error reading from URL. Error: " + e);
		}
		reader.close();
	}

}