# Word Cloud Generator

This application can allow the user to enter a text file or URL, and will generate a frequency table containing every word in the input image file or url, and its frequency. 
It will then output a word-cloud image file containing the most frequent words, with less frequent words represented using a smaller font-size.
It will also output a text file containing all the words and their frequency in descending order, from most frequent to least frequent.

Upon starting the application, the user will be prompted to choose between 4 options:
1) - To enter the path for a text file. The text file will be parsed, and the resulting frequency table will be used to generate an output word-cloud image file and frequency table text file.
2) - To enter a URL. The above process will occur, but while parsing the URL conditional logic is used to avoid adding words inside of a HTML tag.
3) - To set the maximum number of words to be displayed in the output word-cloud image file. 
4) - To close the terminal and prevent further user input.

For testing the application I used a variety of inputs, including:
* A text file of The Lord of the Rings trilogy (lotr.txt) to test the file parsing functionality
* The Wikipedia page on Ireland (https://en.wikipedia.org/wiki/Ireland) to test the URL parsing functionality
The output files generated from the above inputs are contained in the "output" folder.