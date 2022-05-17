package ie.gmit.dip;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

/**
* WordCloud used to create a word-cloud image file with the words from the frequency table
*/
public class WordCloud {
	private static Random random = new Random();

	// Used to randomize the font-style and color of the output text
	private static Font font1 = new Font(Font.SANS_SERIF, Font.BOLD, 15);
	private static Font font2 = new Font(Font.SERIF, Font.ITALIC, 15);
	private static Font font3 = new Font(Font.MONOSPACED, Font.PLAIN, 15);
	private static Font[] fonts = { font1, font2, font3 };
	private static Font currentFont = font1;

	private static Color[] colors = { Color.black, Color.blue, Color.cyan, Color.darkGray, Color.green, Color.magenta,
			Color.orange, Color.pink, Color.red };

	/**
	* Selects a random index in the passed-in array, and returns the element at that index
	*
	* @param arr The array the random index will be selected from
	* @return T The element at the randomly selected index 
    */
	// Running time: O(1)/constant - uses Random.nextInt() which is constant time
	private static <T> T selectRandomIndex(T... arr) {
		// Return the element from a random index between 0 and the last index
		return arr[random.nextInt(arr.length)];
	}

	/**
	* Generates and outputs a word-cloud image file from the words of the frequency table
	*
	* @param fileName The name of the output image file
	* @param wordCount The number of words to be displayed in the word-cloud
	* @param frequencyTable The frequency table used to generate the word-cloud
    */
	// Running time: O(n)/linear - has two loops, but the second is not nested inside 
	// the first (which would be quadratic). 
	// This results in O(n + n) = O(2n) which simplifies to O(n)
	public static void outputWordCloud(String fileName, int wordCount, FrequencyTable frequencyTable) throws Exception {
		int height = 1200;
		int width = 1800;

		// Used to store x and y position of each word on the graphics
		int x = 100;
		int y = 100;

		// Used to gradually decrease the font-size for words with lower frequency
		int fontSize = 60;

		// Used to hold the words to be output, since the freqency table 
		// is a LinkedHashMap that does not have numbered indexes 
		ArrayList<String> words = new ArrayList<String>(); 

		// Push all the keys of the sorted frequency table onto the words array
		for (String key : frequencyTable.getTable().keySet()) {
			words.add(key);
		}

		// Create the image file and background canvas
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics graphics = image.getGraphics();

		for (int i = 0; i < wordCount; i++) {
			// If not already at minimum font size of 15 pixels, reduce font size every 2 iterations
			if (fontSize > 15 && (i % 2 == 0)) {
				fontSize--;
			}

			// Set random font and color
			currentFont = selectRandomIndex(fonts); 
			currentFont = currentFont.deriveFont((float) fontSize); // Apply the correct size to the random font
			graphics.setFont(currentFont);
			
			graphics.setColor(selectRandomIndex(colors));

			// Get the current word and calculate how much space to allocate it
			String currentWord = words.get(i);
			int wordSpace = (currentWord.length() * fontSize) + 30;

			graphics.drawString(currentWord, x, y);

			// Change the value of x or y so the next word doesnt overlap
			if (x <= (width - wordSpace - 250)) {
				x += wordSpace;
			} else { // If about to go out of bounds horizontally, reset x and move down vertically
				x = 100;
				y += fontSize + 30;
			}
		}

		graphics.dispose(); // Dispose of graphics object to free up memory
		ImageIO.write(image, "png", new File(fileName + ".png")); // Output image file
	}
}
