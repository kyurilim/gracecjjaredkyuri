import java.util.ArrayList;

public class DataAnalyzer{
    public static void main(String[] args) {
        ArrayList<String> artistlist = FileOperator.getStringList("artists.txt");
        for (String artist : artistlist) {
            System.out.println(artist);
        }
    }   
}