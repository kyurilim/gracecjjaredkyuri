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

    public ArrayList<Song> sortSongs(ArrayList<Song> songs, String attribute) {
        songs.sort((s1, s2) -> {
            switch (attribute.toLowerCase()) {
                case "loudness":
                    return Double.compare(s2.getLoudness(), s1.getLoudness());
                case "danceability":
                    return Double.compare(s2.getDanceability(), s1.getDanceability());
                case "energy":
                    return Double.compare(s2.getEnergy(), s1.getEnergy());
                case "tempo":
                    return Double.compare(s2.getTempo(), s1.getTempo());
                default:
                    return 0;
            }
        });
        return songs;
    }

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

    public ArrayList<Song> reverseList(ArrayList<Song> songs) {
        ArrayList<Song> reversed = new ArrayList<>();
        for(int i = songs.size() - 1; i >= 0; i--) {
            reversed.add(songs.get(i));
        }
        return reversed;
    }
    
    public ArrayList<Song> loudnessList(ArrayList<Song> songs) {
        ArrayList<Song> loudnessSongs = new ArrayList<>();
        for(Song song : songs) {
            loudnessSongs.add(new Song(song.getTrack_id(), song.getLoudness()));
        }
        return loudnessSongs;
    }

    public double findMinLoudness(ArrayList<Song> songs) {
        double minLoudness = 0.0;
        for(Song song : songs) {
            if(song.getLoudness() < minLoudness) {
                minLoudness = song.getLoudness();
            }
        }
        return minLoudness;
    }

    public double findMaxLoudness(ArrayList<Song> songs) {
        double maxLoudness = 0.0;
        for(Song song : songs) {
            if(song.getLoudness() > maxLoudness) {
                maxLoudness = song.getLoudness();
            }
        }
        return maxLoudness;
    }

    public double findSumLoudness(ArrayList<Song> songs) {
        double sumLoudness = 0.0;
        for(Song song : songs) {
            sumLoudness += song.getLoudness();
        }
        return sumLoudness;
    }

    public double findAveLoudness(ArrayList<Song> songs) {
        return findSumLoudness(songs) / songs.size();
    }

    public String statsToJson(ArrayList<Song> songs) {
        double minLoudness = findMinLoudness(songs);
        double maxLoudness = findMaxLoudness(songs);
        double aveLoudness = findAveLoudness(songs);
        double sumLoudness = findSumLoudness(songs);

        return String.format(
                "{\"count\":%d,\"min\":%.1f,\"max\":%.1f,\"avg\":%.1f,\"sum\":%.1f,\"range\":%.1f}",
                songs.size(), minLoudness, maxLoudness, aveLoudness, sumLoudness, (maxLoudness - minLoudness)
        );
    }
    

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
        ArrayList<Integer> numberList = FileOperator.getIntList("numbers.txt");
        long startTime = System.nanoTime();
        int index = analyzer.binarySearch(numberList, 5);
        long endTime = System.nanoTime(); 
        System.out.println("Index of '5': " + index);
        System.out.println("Binary search took " + (endTime - startTime) + " nanoseconds");
        startTime = System.nanoTime();
        index = analyzer.linearSearch(songs, "FLY HIGH!!");
        endTime = System.nanoTime();
        System.out.println("Index of 'FLY HIGH!!': " + index);
        System.out.println("Linear search took " + (endTime - startTime) + " nanoseconds");
        ArrayList<Song> songsSortedByLoudness = analyzer.sortSongs(songs, "loudness");
        ArrayList<Song> reversedSongs = analyzer.reverseList(songsSortedByLoudness);
        System.out.println("Top 5 quietest songs:");
        for(int i = 0; i < 5; i++) {
            System.out.println(reversedSongs.get(i).getTrack_name() + " - Loudness: " + reversedSongs.get(i).getLoudness());
        }
    }   
}