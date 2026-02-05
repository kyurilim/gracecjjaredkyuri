import java.io.*;
import java.util.*;
/*
 * Reads data from a file
 */
public class FileOperator {
    private static File myFile;
    private static Scanner fileReader;
    /*
    * Creates the File and Scanner to read the specified filename
    */
    public static void createFile(String filename){
        myFile = new File(filename);
        try {
            fileReader = new Scanner(myFile);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File not found. Please enter a valid file.");
        }

    }
    /*
    * Returns an ArrayList of Strings from a file
    */
    public static ArrayList<String> getStringList(String filename) {
        createFile(filename);
        ArrayList<String> linesList = new ArrayList<>();
      
        while (fileReader.hasNextLine()) {
            linesList.add(fileReader.nextLine());
        }
        return linesList;
    }
     /*
   * Returns an ArrayList of doubles from a file
   */
    public static ArrayList<Double> getDoubleList(String filename) {
        createFile(filename);
        ArrayList<Double> linesList = new ArrayList<>();
      
        while (fileReader.hasNextDouble()) {
            linesList.add(fileReader.nextDouble());
        }
        return linesList;
    }
     /*
   * Returns an ArrayList of ints from a file
   */
    public static ArrayList<Integer> getIntList(String filename) {
        createFile(filename);
        ArrayList<Integer> linesList = new ArrayList<>();
        
        while (fileReader.hasNextInt()) {
            linesList.add(fileReader.nextInt());
        }
        return linesList;
    }

    /*
    * Returns an ArrayList of words from a file
    */
    public static ArrayList<String> getWords(String text) {
        // ArrayList<String> wordsList = new ArrayList<>();
        // for(String line : lines){
        //     String[] words = line.split(" ");
        //     for(String word : words){
        //         wordsList.add(word);
        //     }
        // }
        // return wordsList;
      ArrayList<String> tempList = new ArrayList<>();
      String[] words = text.split(" ");
        for(String word : words){
            tempList.add(word);
        }
        return tempList;
    }


    
}