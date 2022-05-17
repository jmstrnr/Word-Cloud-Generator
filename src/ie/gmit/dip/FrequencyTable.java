package ie.gmit.dip;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
* FrequencyTable used to store words and their frequency as key-value pairs, 
* and perform operations on those key-value pairs
*/
public class FrequencyTable extends AbstractFrequencyTable {
	// Comparator used to compare values in a map for sorting
	private static Comparator mapComparator = Map.Entry.comparingByValue();

	private Map<String, Integer> table;
	private boolean isSorted; // Keeps track of whether frequency table is sorted or not

	/**
	* Constructor to initialize a new FrequencyTable instance
    */
	public FrequencyTable() {
		// A LinkedHashMap is used because it retains insertion order which is useful when sorting.
		this.table = new LinkedHashMap<>();;
		this.isSorted = false;
	}

	/**
	* Getter for the table containing key-value pairs
	*
	* @return Map<String, Integer> The Map containing the words (key) and their frequency (value)
    */
	// Running time: O(1)/constant - takes no input
	@Override
	public Map<String, Integer> getTable() {
		return table;
	}

	/**
	* Getter for isSorted
	*
	* @return boolean The boolean showing whether the map is sorted or not
    */
	// Running time: O(1)/constant - takes no input
	@Override
	public boolean getIsSorted() {
		return isSorted;
	}

	/**
	* Setter for isSorted
	*
	* @param state The state of the frequency table in terms of whether its sorted or not
    */
	// Running time: O(1)/constant - does not vary depending on size of input
	@Override
	public void setIsSorted(boolean state) {
		isSorted = state;
	}

	/**
	* Returns the frequency of the passed-in word
	*
	* @param word The word whose frequency is to be returned
	* @return int The frequency of the passed-in word
    */
	// Running time: O(1)/constant - uses LinkedHashMap.containsKey() which is constant time
	@Override
	public int getFrequency(String word) {
		if (table.containsKey(word)) { // If the frequency table contains the word, return its frequency
			return table.get(word);
		} else { // Otherwise return 0, since the word isn't found
			return 0;
		}
	}

	/**
	* Increments the frequency of the passed-in word,
	* or sets it to 1 if it is not already present in the frequency table
	*
	* @param word The word whose frequency is to be incremented
    */
	// Running time: O(1)/constant - uses LinkedHashMap.containsKey() and 
	// LinkedHashMap.put() which are both constant time
	@Override
	public void incrementFrequency(String word) {
		if (table.containsKey(word)) { // If the frequency table contains the word, increment its frequency by 1
			table.put(word, getFrequency(word) + 1);
		} else { // Otherwise if the word isn't found, add it to the table with a frequency of 1
			table.put(word, 1);
		}
	}

	/**
	* Clears the table of its key-value mappings
    */
	// Running time: O(n)/linear - takes longer depending on how many key-value pairs are to be removed
	@Override
	public void clear() {
		table.clear();
		setIsSorted(false); 
	}

	/**
	* Sort key-value pairs from highest to lowest
    */
	// Running time: O(n log n) - uses Collections.sort() which is is O(n*log(n))
	@Override
	public void sort() throws Exception {
		// Check that there is data to be sorted
		if (table.isEmpty()) {
			// If there isn't, throw exception and give user feedback
			throw new Exception("The frequency table is empty and cannot be sorted.");
		}

		// If already sorted, give user feedback and exit method
		if (getIsSorted()) {
			System.out.println("The frequency table is already sorted");
			return;
		}

		// Copy data to a list to use Collections sorting methods
		List<Map.Entry<String, Integer>> list = new ArrayList<>(table.entrySet());

		// Sort the values using the mapComparator. 
		// Default order is ascending, so reverseOrder used to get descending order.
		list.sort(Collections.reverseOrder(mapComparator));

		clear(); // Clear the table of existing values so the values can be added in sorted order

		// Add each key and value in the sorted list to the frequency table, from highest to lowest
		for (Map.Entry<String, Integer> entry : list) {
			table.put(entry.getKey(), entry.getValue());
		}

		setIsSorted(true);
	}

	/**
	* Outputs a text file containing all the key-value pairs of the frequency table
	*
	* @param fileName The name of the file to be output
    */
	// Running time: O(n)/linear - time taken grows linearly as the frequency table
	// gets more key-value pairs
	public void output(String fileName) throws Exception {
		// Check that there is data to be output
		if (table.isEmpty()) {
			// If there isn't, throw exception and give user feedback
			throw new Exception("The frequency table is empty and cannot be output.");
		}

		// PrintWriter used to output text to the newly created file
		PrintWriter output = new PrintWriter(new File(fileName + "output.txt"));

		// Add each word and its frequency to the output text file, with arrows between
		// the key and value for easier visualization
		for (Map.Entry<String, Integer> entry : table.entrySet()) {
			output.println(entry.getKey() + "\t=>\t" + entry.getValue());
		}
		output.close(); 
	}
}