import java.util.*;

public class DataAnalyzer {

    private ArrayList<Map<String, Object>> songs = new ArrayList<>();

    public DataAnalyzer(String fileName) {
        ArrayList<String> lines = FileOperator.getStringList(fileName);
        createSongs(lines);
    }

    private void createSongs(ArrayList<String> songData) {
        if (songData == null || songData.size() <= 1) return;

        for (int i = 1; i < songData.size(); i++) {
            String line = songData.get(i);
            if (line == null || line.isBlank()) continue;

            String[] fields = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
            if (fields.length < 21) continue;

            try {
                Map<String, Object> song = new HashMap<>();
                song.put("track_id", stripQuotes(fields[1]));
                song.put("artists", stripQuotes(fields[2]));
                song.put("album_name", stripQuotes(fields[3]));
                song.put("track_name", stripQuotes(fields[4]));
                song.put("popularity", Integer.parseInt(stripQuotes(fields[5])));
                song.put("duration_ms", Integer.parseInt(stripQuotes(fields[6])));
                song.put("explicit", Boolean.parseBoolean(stripQuotes(fields[7])));
                song.put("danceability", Double.parseDouble(stripQuotes(fields[8])));
                song.put("energy", Double.parseDouble(stripQuotes(fields[9])));
                song.put("key", stripQuotes(fields[10]));
                song.put("loudness", Double.parseDouble(stripQuotes(fields[11])));
                song.put("mode", Integer.parseInt(stripQuotes(fields[12])));
                song.put("speechiness", Double.parseDouble(stripQuotes(fields[13])));
                song.put("acousticness", Double.parseDouble(stripQuotes(fields[14])));
                song.put("instrumentalness", Double.parseDouble(stripQuotes(fields[15])));
                song.put("liveness", Double.parseDouble(stripQuotes(fields[16])));
                song.put("valence", Double.parseDouble(stripQuotes(fields[17])));
                song.put("tempo", Double.parseDouble(stripQuotes(fields[18])));
                song.put("time_signature", Integer.parseInt(stripQuotes(fields[19])));
                song.put("genre", stripQuotes(fields[20]));

                songs.add(song);
            } catch (Exception e) {}
        }
    }

    private static String stripQuotes(String s) {
        if (s == null) return "";
        String t = s.trim();
        if (t.length() >= 2 && t.startsWith("\"") && t.endsWith("\"")) {
            return t.substring(1, t.length() - 1);
        }
        return t;
    }

    // returns song w/ most instrumental (highest instrumentalness value)
    public Map<String, Object> mostInstrumentalSong() {
        if (songs.isEmpty()) return null;
        Map<String, Object> best = songs.get(0);
        for (Map<String, Object> s : songs) {
            if ((double) s.get("instrumentalness") > (double) best.get("instrumentalness")) {
                best = s;
            }
        }
        return best;
    }

    // returns song w/ least instrumental (lowest instrumentalness value)
    public Map<String, Object> leastInstrumentalSong() {
        if (songs.isEmpty()) return null;
        Map<String, Object> least = songs.get(0);
        for (Map<String, Object> s : songs) {
            if ((double) s.get("instrumentalness") < (double) least.get("instrumentalness")) {
                least = s;
            }
        }
        return least;
    }

    // returns avg. instrumentalness across all songs
    public double averageInstrumentalness() {
        if (songs.isEmpty()) return 0;
        double total = 0;
        for (Map<String, Object> s : songs) {
            total += (double) s.get("instrumentalness");
        }
        return total / songs.size();
    }

    // returns avg. instrumentalness for songs in a specific genre
    public double averageInstrumentalnessByGenre(String genre) {
        double total = 0;
        int count = 0;
        for (Map<String, Object> s : songs) {
            if (s.get("genre").toString().equalsIgnoreCase(genre.trim())) {
                total += (double) s.get("instrumentalness");
                count++;
            }
        }
        return count == 0 ? 0 : total / count;
    }

    // Returns top most instrumental songs across all genres
    public ArrayList<Map<String, Object>> topNInstrumentalSongs(int n) {
        songs.sort((a, b) -> Double.compare((double) b.get("instrumentalness"), (double) a.get("instrumentalness")));
        ArrayList<Map<String, Object>> top = new ArrayList<>();
        for (int i = 0; i < Math.min(n, songs.size()); i++) {
            top.add(songs.get(i));
        }
        return top;
    }

    // returns top most instrumental songs within a specific genre
    public ArrayList<Map<String, Object>> topNInstrumentalSongsInGenre(String genre, int n) {
        ArrayList<Map<String, Object>> filtered = new ArrayList<>();
        for (Map<String, Object> s : songs) {
            if (s.get("genre").toString().equalsIgnoreCase(genre.trim())) {
                filtered.add(s);
            }
        }
        filtered.sort((a, b) -> Double.compare((double) b.get("instrumentalness"), (double) a.get("instrumentalness")));
        ArrayList<Map<String, Object>> top = new ArrayList<>();
        for (int i = 0; i < Math.min(n, filtered.size()); i++) {
            top.add(filtered.get(i));
        }
        return top;
    }

    // returns map of each genre to its average instrumentalness
    public Map<String, Double> getAverageInstrumentalnessPerGenre() {
        Map<String, Double> avgMap = new HashMap<>();
        Map<String, Double> total = new HashMap<>();
        Map<String, Integer> count = new HashMap<>();

        for (Map<String, Object> s : songs) {
            String genre = s.get("genre").toString();
            double val = (double) s.get("instrumentalness");
            total.put(genre, total.getOrDefault(genre, 0.0) + val);
            count.put(genre, count.getOrDefault(genre, 0) + 1);
        }

        for (String g : total.keySet()) {
            avgMap.put(g, total.get(g) / count.get(g));
        }
        return avgMap;
    }

    public static void main(String[] args) {
        DataAnalyzer analyzer = new DataAnalyzer("dataset.csv");

        Map<String, Object> most = analyzer.mostInstrumentalSong();
        if (most != null) {
            System.out.println("Most Instrumental: " + most.get("track_name") + " by " + most.get("artists") +
                    " | Instrumentalness: " + most.get("instrumentalness"));
        }

        Map<String, Object> least = analyzer.leastInstrumentalSong();
        if (least != null) {
            System.out.println("Least Instrumental: " + least.get("track_name") + " by " + least.get("artists") +
                    " | Instrumentalness: " + least.get("instrumentalness"));
        }

        System.out.println("Average Instrumentalness: " + analyzer.averageInstrumentalness());

        ArrayList<Map<String, Object>> top5 = analyzer.topNInstrumentalSongs(5);
        System.out.println("\nTop 5 Most Instrumental Songs:");
        for (int i = 0; i < top5.size(); i++) {
            Map<String, Object> s = top5.get(i);
            System.out.println((i + 1) + ") " + s.get("track_name") + " | " +
                    s.get("artists") + " | Instrumentalness: " + s.get("instrumentalness"));
        }

        Map<String, Double> stats = analyzer.getAverageInstrumentalnessPerGenre();
        System.out.println("\nAverage Instrumentalness Per Genre:");
        for (String genre : stats.keySet()) {
            System.out.printf("  %s: %.4f%n", genre, stats.get(genre));
        }
    }
}