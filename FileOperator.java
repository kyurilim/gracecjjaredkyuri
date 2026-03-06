import java.io.*;
import java.util.*;


public class FileOperator {
    private static File myFile;
    private static Scanner fileReader;

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

    public static ArrayList<String> getStringList(String filename) {
        createFile(filename);
        ArrayList<String> linesList = new ArrayList<>();
      
        while (fileReader.hasNextLine()) {
            linesList.add(fileReader.nextLine());
        }
        return linesList;
    }

    public static ArrayList<String> getStringList(String filename, int numLines) {
        createFile(filename);
        ArrayList<String> linesList = new ArrayList<>();
      
        for (int i = 0; i < numLines && fileReader.hasNextLine(); i++) {
            linesList.add(fileReader.nextLine());
        }
        return linesList;
    }

    public static ArrayList<Double> getDoubleList(String filename) {
        createFile(filename);
        ArrayList<Double> linesList = new ArrayList<>();
      
        while (fileReader.hasNextDouble()) {
            linesList.add(fileReader.nextDouble());
        }
        return linesList;
    }

    public static ArrayList<Integer> getIntList(String filename) {
        createFile(filename);
        ArrayList<Integer> linesList = new ArrayList<>();
        
        while (fileReader.hasNextInt()) {
            linesList.add(fileReader.nextInt());
        }
        return linesList;
    }

    public static ArrayList<String> getWords(String text) {
      ArrayList<String> tempList = new ArrayList<>();
      String[] words = text.split(" ");
        for(String word : words){
            tempList.add(word);
        }
        return tempList;
    }   
}