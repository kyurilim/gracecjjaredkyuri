import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * FileOperator - Utility class for reading and processing data files.
 * Provides helper methods to load data from CSV and text files.
 */
public class FileOperator {
    
    /**
     * Reads a CSV file and returns lines as a list of strings.
     * Each line represents a row of data.
     * 
     * @param filename The path to the CSV file to read
     * @return A list of strings, each representing one line from the file
     */
    public static List<String> readCSVFile(String filename) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return lines;
    }
    
    /**
     * Parses a CSV line into individual data fields separated by commas.
     * Handles basic CSV format parsing.
     * 
     * @param csvLine A single line of CSV data
     * @return An array of strings representing each field in the CSV line
     */
    public static String[] parseCSVLine(String csvLine) {
        return csvLine.split(",");
    }
    
    /**
     * Reads all lines from a text file and returns them as a list.
     * 
     * @param filename The path to the text file
     * @return A list of strings, each representing one line
     */
    public static List<String> readTextFile(String filename) {
        return readCSVFile(filename);
    }
}
