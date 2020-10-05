# Lab3
This program basically reads a csv file from SpotifyCharts and then creates an output file containing all the artists and how many times they appear on the list. Also, it then uses the 2d array and places the elemetns into a LinkedList and displays those results as well. The program scans in the input file and then create a 2d array with rows and columns. There are multiple try/catch methods used to catch possible exceptions and throw/block them while displaying the appropriate message if necessary. For the LinkedList, after the list is filled, the program uses the natural order code to arrange the artists name in alphabetical order. To print the needed information to an output file, a PrintWriter is used to write to text files. The information for the 2d array that is written in the file is generated by using a for loop to call the artists array until the array has reached the end. The output file thus contains each artists name that appears on the csv file and how many times they appear on the list while making sure that an artist who appears multiple times is not given a separate line of text. The output file for the LinkeList is basically created by using a writer to write the List with one artist per line.

**You need to run the Lab3.java file which is located in the src folder. Make sure when you clone repository that the SpotifyCharts csv file is located in the Lab 3 folder so that the code can read it without throwing a Nullpointer error.
