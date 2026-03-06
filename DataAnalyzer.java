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

    //counts the numebr of explicit songs in the list
    public int countExplicitSongs(ArrayList<Song> songs) {
        int count = 0;
        for(Song song : songs) {
            if(song.isExplicit()) {
                count++;
            }
        }
        return count;
    }

    //find the average loudness value of songs in the list
    public double averageLoudness(ArrayList<Song> songs) {
        double totalLoudness = 0;
        for(Song song : songs) {
            totalLoudness += song.getLoudness();
        }
        return totalLoudness / songs.size();
    }

    //sorts the songs in the list by a specified attribute
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

    //creates a list of songs with only the track id and energy attributes
    public ArrayList<Song> energyList(ArrayList<Song> songs) {
        ArrayList<Song> energySongs = new ArrayList<>();
        for(Song song : songs) {
            energySongs.add(new Song(song.getTrack_id(), song.getEnergy()));
        }
        return energySongs;
    }

    //finds the lowest energy value in the list of songs
    public double findMinEnergy(ArrayList<Song> songs) {
        double minEnergy = 0.0;
        for(Song song : songs) {
            if(song.getEnergy() < minEnergy) {
                minEnergy = song.getEnergy();
            }
        }
        return minEnergy;
    }

    //finds the highest energy value in the list of songs
    public double findMaxEnergy(ArrayList<Song> songs) {
        double maxEnergy = 0.0;
        for(Song song : songs) {
            if(song.getEnergy() > maxEnergy) {
                maxEnergy = song.getEnergy();
            }
        }
        return maxEnergy;
    }

    //calculates the sum of energy values in the list of songs
    public double findSumEnergy(ArrayList<Song> songs) {
        double sumEnergy = 0.0;
        for(Song song : songs) {
            sumEnergy += song.getEnergy();
        }
        return sumEnergy;
    }

    //calculates the average energy value in the list of songs
    public double findAveEnergy(ArrayList<Song> songs) {
        if (songs.isEmpty()) {
            return 0.0;
        }
        return Math.round(findSumEnergy(songs) / songs.size() * 100.0) / 100.0;
    }

    //gives the track name of the song with the lowest energy value
    public String findMinEnergyTrack(ArrayList<Song> songs) {
        String minEnergyTrack = "";
        double minEnergy = 0.0;
        for(Song song : songs) {
            if(song.getEnergy() < minEnergy) {
                minEnergy = song.getEnergy();
                minEnergyTrack = song.getTrack_name();
            }
        }
        return minEnergyTrack;
    }

    //gives the track name of the song with the highest energy value
    public String findMaxEnergyTrack(ArrayList<Song> songs) {
        String maxEnergyTrack = "";
        double maxEnergy = 0.0;
        for(Song song : songs) {
            if(song.getEnergy() > maxEnergy) {
                maxEnergy = song.getEnergy();
                maxEnergyTrack = song.getTrack_name();
            }
        }
        return maxEnergyTrack;
    }

    //creates a JSON string of the statistics of the energy values in the list of songs
    public String statsToJson(ArrayList<Song> songs) {
        double min = findMinEnergy(songs);
        double max = findMaxEnergy(songs);
        double ave = findAveEnergy(songs);
        double sum = findSumEnergy(songs);
        String minTrack = findMinEnergyTrack(songs);
        String maxTrack = findMaxEnergyTrack(songs);

        return String.format(
                "{\"count\":%d,\"min\":%.1f,\"max\":%.1f,\"avg\":%.1f,\"sum\":%.1f,\"range\":%.1f,\"minTrack\":\"%s\",\"maxTrack\":\"%s\"}",
                songs.size(), min, max, ave, sum, (max - min), minTrack, maxTrack
        );
    }
    
    //creates a JSON string of the list of songs
    public String toJson(ArrayList<Song> songs) {
        StringBuilder json = new StringBuilder("[");
        for(int i = 0; i < songs.size(); i++) {
            json.append(songs.get(i).toJson());
            if(i < songs.size() - 1) {
                json.append(",");
            }
        }
        json.append("]");
        return json.toString();
    }

    public static void main(String[] args) {
        ArrayList<String> songlist = FileOperator.getStringList("dataset.csv");
        DataAnalyzer analyzer = new DataAnalyzer();
        ArrayList<Song> songs = analyzer.createSongsCJ(songlist);
        int explicitCount = analyzer.countExplicitSongs(songs);
        double averageLoudness = analyzer.averageLoudness(songs);
        // ArrayList<Song> sortedSongs = analyzer.sortSongsByLoudness(songs);
        System.out.println("Number of explicit songs: " + explicitCount);
        System.out.println("Average loudness: " + averageLoudness);
        System.out.println("Sorted songs by loudness:");
        // for(Song song : sortedSongs) {
            // System.out.println(song.getTrack_name() + " - Loudness: " + song.getLoudness());
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