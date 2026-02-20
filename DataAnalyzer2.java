// DataAnalyzer.java
import java.util.ArrayList;

public class DataAnalyzer2 {
    public ArrayList<Country> getCountryInternet() {
        ArrayList<Country> list = new ArrayList<>();
        list.add(new Country("USA",     92.5));
        list.add(new Country("Brazil",  74.3));
        list.add(new Country("India",   43.0));
        list.add(new Country("Germany", 89.7));
        list.add(new Country("Nigeria", 36.1));
        list.add(new Country("Japan",   93.2));
        return list;
    }
       // ── Different team members write these methods ──────────────────

    public double findMin(ArrayList<Country> countries) {
        return 0.0;
    }
    public double findMax(ArrayList<Country> countries) {
        return 0.0;
    }    
    public double findSum(ArrayList<Country> countries) {
        return 0.0;
    }
    public double findAve(ArrayList<Country> countries) {
        return 0.0;
    }
     public String findMinCountry(ArrayList<Country> countries) {
        return "MinC";
    }
     public String findMaxCountry(ArrayList<Country> countries) {
        return "MaxC";
    }
    // ────────────────────────────────────────────────────────────────

    public String statsToJson(ArrayList<Country> countries) {
        double min        = findMin(countries);
        double max        = findMax(countries);
        double avg        = findAve(countries);
        double sum        = findSum(countries);
        String minCountry = findMinCountry(countries);
        String maxCountry = findMaxCountry(countries);

        return String.format(
                "{\"count\":%d,\"min\":%.1f,\"max\":%.1f,\"avg\":%.1f,\"sum\":%.1f,\"range\":%.1f,\"minCountry\":\"%s\",\"maxCountry\":\"%s\"}",
                countries.size(), min, max, avg, sum, (max - min), minCountry, maxCountry
        );
    }
    
}
