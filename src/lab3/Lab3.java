/**
 * @author Adil Qumar
 * 
 * This program basically reads a csv file from SpotifyCharts and then creates
 * an output file containing all the artists and how many times they appear on
 * the list. The program scans in the input file and then create a 2d array with rows and 
 * columns. There are multiple try/catch methods used to catch possible exceptions
 * and throw/block them while displaying the appropriate message if necessary.
 * After creating the 2d array, a LinkedList is created with the elements from the
 * arrays being placed into the list using java.util.LinkedList & List classes. 
 * After the LinkedList is filled with data, it is arranged in alphabetical/natural
 * order because the "Music Producer" wants the artists name in that specific order.
 * To print the needed information to an output file, a PrintWriter is used to
 * write to a text file. The 2d array information that is written in the file is generated
 * by using a for loop to call the artists array until the array has reached the end.
 * The output file thus contains each artists name that appears on the csv file and 
 * how many times they appear on the list while making sure that an artist who 
 * appears multiple times is not given a separate line of text. The LinkedList is
 * then printed to an output file in which the artists names appear in alphabetical
 * order.
 */

package lab3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Arrays;
import java.util.List;
import java.util.LinkedList;
import java.util.Comparator;

public class Lab3 {
    
    public static void main(String args[] ) throws Exception {
        SongInfo w = new SongInfo();
        w.readFile();
        w.reportArtists();
    }
}
// This class represents array structure of data in file in terms of rows and columns
class SongInfo{
   int cols;
   int rows;
   String[][] data;
    //The next set of code is setting a blueprint for the arrays
    public SongInfo(){
        this.cols = 5;
        this.rows = 200;
        this.data = new String[rows][cols];
    }
    // The next set of code is reading a preselected file into the data array
    // When reading in the text, the code specifically check for quotes and removes them
    // While splitting lines, the code makes sure that commas within () and [] are not counted
    public void readFile(){
        File text = new File("SpotifyCharts.csv");
        Scanner sc;
        try {
            sc = new Scanner(text);
            int row =0, col=0;
            String ignore1 = sc.nextLine();
            String ignore2 = sc.nextLine();
            while(sc.hasNextLine()){
                String line = sc.nextLine();
                // The next two lines basically help the program interpret the text file.
                // The two lines say to ignore commas in between quoations and then ignore
                // the quotations when reading in the text into the array.
                String newLine = line.replace("\"", "");
                String[] strArr = newLine.split(",(?![^()]*\\))(?=(((?!\\]).)*\\[)|[^\\[\\]]*$)");
                data[row][col] = strArr[col];col++;
                data[row][col] = strArr[col];col++; 
                data[row][col] = strArr[col];col++;
                data[row][col] = strArr[col];col++;
                data[row][col] = strArr[col];col++;
                col=0;row++; 
            }
        }
        // The next set of codes catch an exception and block it and
        // displays the appropriate message.
        catch (FileNotFoundException e) {
            System.out.println("The text file not found");
        }
        catch (NumberFormatException e) {
            System.out.println("The text file has wrong data");
        }
    }
    // The next set of code creates report of which artists appear on the csv file
    // and how many times they appear.
    public void reportArtists(){
        PrintWriter writer1 = null;
        PrintWriter writer2 = null;
        try {
            writer1 = new PrintWriter(new FileWriter("Artists.txt"));
        }
        catch (FileNotFoundException e) {
            // The next line of code catches an exception and blocks it.
            System.out.println("File not found");
        }
        catch (IOException e) {
            // The next line of code catches an exception and blocks it.
            System.out.println("File is not accesible");
        }
        String[] artistsArr = new String[rows];
        int[] count = new int[rows];
        int index = 0;
        int atWhichIndex=-1;
        for(int i=0;i<rows;i++){
            atWhichIndex = getArtistsExistsIndex(artistsArr,data[i][2]);
            if(atWhichIndex<0){
                artistsArr[index] = data[i][2];
                count[index]=count[index]+1;
                index++;
            }
            else{
                count[atWhichIndex]=count[atWhichIndex]+1;
            }
        }
        // The next set of code contains a for-loop which basically goes through
        // the artistArr[] and writes them to an output file. The if/else statments
        // are used just for formatting the output file so that it has a clean look
        // to it and is easily readable.
        writer1.write("Artists\t\t\t\t# of times on list\n");
        writer1.write("__________________________________________________\n");
        for(int i=0;i<index;i++){
            int length = artistsArr[i].length();
            if(length < 7){
                writer1.write(artistsArr[i]+" \t\t\t\t"+count[i]+" times\n");
            }
            else if(length > 6 && length < 15){
                writer1.write(artistsArr[i]+" \t\t\t"+count[i]+" times\n");
            }
            else if(length > 14 && length < 19){
                writer1.write(artistsArr[i]+" \t\t"+count[i]+" times\n");
            }
            else if(length > 18 && length < 23){
                writer1.write(artistsArr[i]+" \t\t"+count[i]+" times\n");
            }
            else{
                writer1.write(artistsArr[i]+" \t"+count[i]+" times\n");
            }
        }
        writer1.flush();
        writer1.close();  
        // The next set of code takes the array artistsArr and places the elements
        // into a LinkedList. After filling the list, a code is called to arrange
        // the artists in alphabetical order.
        try {
            writer2 = new PrintWriter(new FileWriter("AlphabeticalArtists.txt"));
        }
        catch (FileNotFoundException e) {
            // The next line of code catches an exception and blocks it.
            System.out.println("File not found");
        }
        catch (IOException e) {
            // The next line of code catches an exception and blocks it.
            System.out.println("File is not accesible");
        }
        List<String> artistNames = Arrays.asList(artistsArr);
        LinkedList<String> linkedListArtists = new LinkedList<String>(artistNames);
        // The next line of code removes any empty values from the Linked List.
        while(linkedListArtists.remove(null)){
        }
        linkedListArtists.sort(Comparator.naturalOrder());
        writer2.println("Top Streaming Artists");
        writer2.println("__________________________________________________");
        for(String artists : linkedListArtists){
            writer2.println(artists);
        }
        writer2.flush();
        writer2.close();
    }
    // The next few lines of code gets position of artists in the array
    public int getArtistsExistsIndex(String[] arr, String artist){
        int result=-1;
        for(int i=0;i<rows;i++){
            if(artist.equals(arr[i])){ 
                return i;
            }
        }
        return result;
    }
}

class Artist{
    private String name;
    Artist next;
}

class TopStreamingArtists {
    Artist first;
    public void TopStreamingArtists(){
      first = null;
    }
    public boolean isEmpty(){
     return (first == null);
    }
}