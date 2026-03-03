import java.util.*;
import java.io.*;

public class Popularity {

    private ArrayList<Map<String, Object>> songs = new ArrayList<>();

    public Popularity(String fileName) {
        ArrayList<String> lines = FileOperator.getStringList(fileName);
        loadSongs(lines);
    }

    private void loadSongs(ArrayList<String> songData) {
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
            } catch (Exception e) {
                // skip malformed rows
            }
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

    public Map<String, Object> mostPopularSong() {
        if (songs.isEmpty()) return null;
        Map<String, Object> best = songs.get(0);
        for (Map<String, Object> s : songs) {
            if ((int) s.get("popularity") > (int) best.get("popularity")) {
                best = s;
            }
        }
        return best;
    }

    public Map<String, Object> mostPopularSongInGenre(String genre) {
        Map<String, Object> best = null;
        for (Map<String, Object> s : songs) {
            if (s.get("genre").toString().equalsIgnoreCase(genre.trim())) {
                if (best == null || (int) s.get("popularity") > (int) best.get("popularity")) {
                    best = s;
                }
            }
        }
        return best;
    }

    public double averagePopularityByGenre(String genre) {
        int total = 0, count = 0;
        for (Map<String, Object> s : songs) {
            if (s.get("genre").toString().equalsIgnoreCase(genre.trim())) {
                total += (int) s.get("popularity");
                count++;
            }
        }
        return count == 0 ? 0 : (double) total / count;
    }

    public ArrayList<Map<String, Object>> topNSongs(int n) {
        songs.sort((a, b) -> Integer.compare((int) b.get("popularity"), (int) a.get("popularity")));
        ArrayList<Map<String, Object>> top = new ArrayList<>();
        for (int i = 0; i < Math.min(n, songs.size()); i++) {
            top.add(songs.get(i));
        }
        return top;
    }

    public ArrayList<Map<String, Object>> topNSongsInGenre(String genre, int n) {
        ArrayList<Map<String, Object>> filtered = new ArrayList<>();
        for (Map<String, Object> s : songs) {
            if (s.get("genre").toString().equalsIgnoreCase(genre.trim())) {
                filtered.add(s);
            }
        }
        filtered.sort((a, b) -> Integer.compare((int) b.get("popularity"), (int) a.get("popularity")));
        ArrayList<Map<String, Object>> top = new ArrayList<>();
        for (int i = 0; i < Math.min(n, filtered.size()); i++) {
            top.add(filtered.get(i));
        }
        return top;
    }

    public Map<String, Double> getAveragePopularityPerGenre() {
        Map<String, Double> avgMap = new HashMap<>();
        Map<String, Integer> total = new HashMap<>();
        Map<String, Integer> count = new HashMap<>();

        for (Map<String, Object> s : songs) {
            String genre = s.get("genre").toString();
            int pop = (int) s.get("popularity");
            total.put(genre, total.getOrDefault(genre, 0) + pop);
            count.put(genre, count.getOrDefault(genre, 0) + 1);
        }

        for (String g : total.keySet()) {
            avgMap.put(g, (double) total.get(g) / count.get(g));
        }
        return avgMap;
    }

    public ArrayList<Map<String, Object>> getAllSongs() {
        return songs;
    }
}