package ie.gmit.dip;

import java.util.Map;

/**
* AbstractFrequencyTable used to define basic functionality that derived classes must implement
*/
public abstract class AbstractFrequencyTable {
	private Map<String, Integer> table; 
	private boolean isSorted; 

	/**
	* Implementation should return the table containing the key-value pairs
	*
	* @return Map<String, Integer> The Map containing the words (key) and their frequency (value)
    */
	abstract Map<String, Integer> getTable();

	/**
	* Implementation should return isSorted value
	*
	* @return boolean The boolean showing whether the map is sorted or not
    */
	abstract boolean getIsSorted();

	/**
	* Implementation should set isSorted value
	*
	* @param state The state of the frequency table in terms of whether its sorted or not
    */
	abstract void setIsSorted(boolean state);

	/**
	* Implementation should return the frequency of the passed-in word
	*
	* @param word The word whose frequency is to be returned
	* @return int The frequency of the passed-in word
    */
	abstract int getFrequency(String word);

	/**
	* Implementation should increment the frequency of the passed-in word,
	* or set it to 1 if it is not already present in the frequency table
	*
	* @param word The word whose frequency is to be incremented
    */
	abstract void incrementFrequency(String word);

	/**
	* Implementation should clear the table of its key-value mappings
    */
	abstract void clear();

	/**
	* Implementation should sort the table in descending order
    */
	abstract void sort() throws Exception;
}
