import java.util.ArrayList;

public class DataAnalyzer{
    
    //creates a list of songs from a file
    public ArrayList<Song> createSongsCJ(ArrayList<String> songData) {
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
            double instrumentalness = Double.parseDouble(fields[15]);
            String key = fields[10];
            double loudness = Double.parseDouble(fields[11]);
            int mode = Integer.parseInt(fields[12]);
            double speechiness = Double.parseDouble(fields[13]);
            double acousticness = Double.parseDouble(fields[14]);
            double energy = Double.parseDouble(fields[9]);
            double liveness = Double.parseDouble(fields[16]);
            double valence = Double.parseDouble(fields[17]);
            double tempo = Double.parseDouble(fields[18]);
            int time_signature = Integer.parseInt(fields[19]);
            String genre = fields[20];
            Song song = new Song(track_id, artists, album_name, track_name, popularity, duration_ms, explicit, danceability, energy, key, loudness, mode, speechiness, acousticness,instrumentalness,  liveness, valence, tempo, time_signature, genre);
            
            songs.add(song);
        }
        return songs;
    }

    //finds the index of a target number in a list of doubles using binary search
    public int binarySearch(ArrayList<Integer> songs, double targetNumber) {
        int minIndex = 1;
        int maxIndex = songs.size();

        while (minIndex <= maxIndex){
            double midIndex = Math.floor(minIndex + (maxIndex - minIndex) / 2);
            int midNumber = songs.get((int)midIndex);
            if (midNumber == targetNumber) {
                return (int)midIndex;
            } else if (midNumber < targetNumber) {
                minIndex = (int)midIndex + 1;
            } else {
                maxIndex = (int)midIndex - 1;
            }
        }
        return -1;
    }

    //iterates through a list of songs to find the index of a target track name
    public int linearSearch(ArrayList<Song> songs, String targetTrackName){
        int index = 0;
        while(index < songs.size()){
            if(songs.get(index).getTrack_name().equals(targetTrackName)){
                return index;
            }
            index++;
        }
        return -1;
    }

    //reverses list order
    public ArrayList<Song> reverseList(ArrayList<Song> songs) {
        ArrayList<Song> reversed = new ArrayList<>();
        for(int i = songs.size() - 1; i >= 0; i--) {
            reversed.add(songs.get(i));
        }
        return reversed;
    }

    //LOWEST
    public double findMinInstrumentalness(ArrayList<Song> songs) {
        double minInstrumentalness = 0.0;
        for(Song song : songs) {
            if(song.getInstrumentalness() < minInstrumentalness) {
                minInstrumentalness = song.getInstrumentalness();
            }
        }
        return minInstrumentalness;
    }

    //HIGHEST
    public double findMaxInstrumentalness(ArrayList<Song> songs) {
        double maxInstrumentalness = 0.0;
        for(Song song : songs) {
            if(song.getInstrumentalness() > maxInstrumentalness) {
                maxInstrumentalness = song.getInstrumentalness();
            }
        }
        return maxInstrumentalness;
    }

    //SUM
    public double findSumInstrumentalness(ArrayList<Song> songs) {
        double sumInstrumentalness = 0.0;
        for(Song song : songs) {
            sumInstrumentalness += song.getInstrumentalness();
        }
        return sumInstrumentalness;
    }

    //AVG
    public double findAveInstrumentalness(ArrayList<Song> songs) {
        if (songs.isEmpty()) {
            return 0.0;
        }
        return Math.round(findSumInstrumentalness(songs) / songs.size() * 100.0) / 100.0;
    }

    //MAX
    public String findMaxInstrumentalnessTrack(ArrayList<Song> songs) {
        String maxInstrumentalnessTrack = "";
        double maxInstrumentalness = 0.0;
        for(Song song : songs) {
            if(song.getInstrumentalness() > maxInstrumentalness) {
                maxInstrumentalness = song.getInstrumentalness();
                maxInstrumentalnessTrack = song.getTrack_name();
            }
        }
        return maxInstrumentalnessTrack;
    }
    public String toJSon(ArrayList<Song> songs){
        String json = "[";
          for(Song song : songs) {  
            json += song.toJson() + ",";
          }
            json = json.substring(0, json.length() - 1); // Remove trailing comma
            json += "]";
            return json;
    }

    //MIN
    public String findMinInstrumentalnessTrack(ArrayList<Song> songs) {
        String minInstrumentalnessTrack = "";
        double minInstrumentalness = 0.0;
        for(Song song : songs) {
            if(song.getInstrumentalness() < minInstrumentalness) {
                minInstrumentalness = song.getInstrumentalness();
                minInstrumentalnessTrack = song.getTrack_name();
            }
        }
        return minInstrumentalnessTrack;
    }

    public static void main(String[] args) {
        ArrayList<String> songlist = FileOperator.getStringList("dataset.csv");
        DataAnalyzer analyzer = new DataAnalyzer();
        ArrayList<Song> songs = analyzer.createSongsCJ(songlist);

        //SUM
        double sum = analyzer.findSumInstrumentalness(songs);
        System.out.println("Sum of Instrumentalness: " + sum);

        //AVG
        double avg = analyzer.findAveInstrumentalness(songs);
        System.out.println("Average Instrumentalness: " + avg);

        //MIN
        double min = analyzer.findMinInstrumentalness(songs);
        System.out.println("Minimum Instrumentalness: " + min);

        //MAX
        double max = analyzer.findMaxInstrumentalness(songs);
        System.out.println("Maximum Instrumentalness: " + max);

        //HIGHEST
        String maxTrack = analyzer.findMaxInstrumentalnessTrack(songs);
        System.out.println("Track with Highest Instrumentalness: " + maxTrack);

        //LOWEST
        String minTrack = analyzer.findMinInstrumentalnessTrack(songs);
        System.out.println("Track with Lowest Instrumentalness: " + minTrack);
}
}