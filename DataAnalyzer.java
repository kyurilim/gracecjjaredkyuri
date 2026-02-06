import java.util.ArrayList;

public class DataAnalyzer{
    
    public ArrayList<Song> createSongs(ArrayList<String> songData) {
        ArrayList<Song> songs = new ArrayList<>();
        for(int i = 1; i < songData.size(); i++) {
            String line = songData.get(i);
            String[] fields = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
            String track_id = fields[1];
            String artists = fields[2];
            String album_name = fields[3];
            String track_name = fields[4];
            int popularity = Integer.parseInt(fields[5]);
            int duration_ms = Integer.parseInt(fields[6]);
            boolean explicit = Boolean.parseBoolean(fields[7]);
            double danceability = Double.parseDouble(fields[8]);
            double energy = Double.parseDouble(fields[9]);
            String key = fields[10];
            double loudness = Double.parseDouble(fields[11]);
            int mode = Integer.parseInt(fields[12]);
            double speechiness = Double.parseDouble(fields[13]);
            double acousticness = Double.parseDouble(fields[14]);
            double instrumentalness = Double.parseDouble(fields[15]);
            double liveness = Double.parseDouble(fields[16]);
            double valence = Double.parseDouble(fields[17]);
            double tempo = Double.parseDouble(fields[18]);
            int time_signature = Integer.parseInt(fields[19]);
            String genre = fields[20];
            Song song = new Song(track_id, artists, album_name, track_name, popularity, duration_ms, explicit, danceability, energy, key, loudness, mode, speechiness, acousticness, instrumentalness, liveness, valence, tempo, time_signature, genre);
            
            songs.add(song);
        }
        return songs;
    }

    public int countExplicitSongs(ArrayList<Song> songs) {
        int count = 0;
        for(Song song : songs) {
            if(song.isExplicit()) {
                count++;
            }
        }
        return count;
    }

    public double averageLoudness(ArrayList<Song> songs) {
        double totalLoudness = 0;
        for(Song song : songs) {
            totalLoudness += song.getLoudness();
        }
        return totalLoudness / songs.size();
    }

    //AI Helped With This
    // public ArrayList<Song> sortSongsByLoudness(ArrayList<Song> songs) {
    //     songs.sort((s1, s2) -> Double.compare(s2.getLoudness(), s1.getLoudness()));
    //     return songs;
    // }

    public static void main(String[] args) {
        ArrayList<String> songlist = FileOperator.getStringList("dataset.csv");
        DataAnalyzer analyzer = new DataAnalyzer();
        ArrayList<Song> songs = analyzer.createSongs(songlist);
        int explicitCount = analyzer.countExplicitSongs(songs);
        double averageLoudness = analyzer.averageLoudness(songs);
        // ArrayList<Song> sortedSongs = analyzer.sortSongsByLoudness(songs);
        System.out.println("Number of explicit songs: " + explicitCount);
        System.out.println("Average loudness: " + averageLoudness);
        // System.out.println("Sorted songs by loudness:");
        // for(Song song : sortedSongs) {
        //     System.out.println(song.getTrack_name() + " - Loudness: " + song.getLoudness());
        // }
    }   
}