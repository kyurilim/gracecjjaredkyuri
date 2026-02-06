public class Song{
    private int popularity, duration_ms, duration_minutes, duration_seconds, mode, time_signature;
    private String track_id, artists, album_name, track_name, key, genre;
    private boolean explicit;
    private double danceability, energy, loudness, speechiness, acousticness, instrumentalness, liveness, valence, tempo;

    public Song(String track_id, String artists, String album_name, String track_name, int popularity, int duration_ms, boolean explicit, double danceability, double energy, String key, double loudness, int mode, double speechiness, double acousticness, double instrumentalness, double liveness, double valence, double tempo, int time_signature, String genre) {
        this.track_id = track_id;
        this.artists = artists;
        this.album_name = album_name;
        this.track_name = track_name;
        this.popularity = popularity;
        this.duration_ms = duration_ms;
        this.explicit = explicit;
        this.danceability = danceability;
        this.energy = energy;
        this.key = key;
        this.loudness = loudness;
        this.mode = mode;
        this.speechiness = speechiness;
        this.acousticness = acousticness;
        this.instrumentalness = instrumentalness;
        this.liveness = liveness;
        this.valence = valence;
        this.tempo = tempo;
        this.time_signature = time_signature;
        this.genre = genre;
    }

    public String getTrack_id() {
        return track_id;
    }
    public String getArtists() {
        return artists;
    }
    public String getAlbum_name() {
        return album_name;
    }
    public String getTrack_name() {
        return track_name;
    }
    public int getPopularity() {
        return popularity;
    }
    public int getDuration_ms() {
        return duration_ms;
    }
    public boolean isExplicit() {
        return explicit;
    }
    public double getDanceability() {
        return danceability;
    }
    public double getEnergy() {
        return energy;
    }
    public String getKey() {
        return key;
    }
    public double getLoudness() {
        return loudness;
    }
    public int getMode() {
        return mode;
    }
    public double getSpeechiness() {
        return speechiness;
    }
    public double getAcousticness() {
        return acousticness;
    }
    public double getInstrumentalness() {
        return instrumentalness;
    }
    public double getLiveness() {
        return liveness;
    }
    public double getValence() {
        return valence;
    }
    public double getTempo() {
        return tempo;
    }
    public int getTime_signature() {
        return time_signature;
    }
    public String getGenre() {
        return genre;
    }

    public String toString() {
        return "Track ID: " + track_id + "\nArtists: " + artists + "\nAlbum Name: " + album_name + "\nTrack Name: " + track_name + "\nPopularity: " + popularity + "\nDuration (ms): " + duration_ms + "\nExplicit: " + explicit + "\nDanceability: " + danceability + "\nEnergy: " + energy + "\nKey: " + key + "\nLoudness: " + loudness + "\nMode: " + mode + "\nSpeechiness: " + speechiness + "\nAcousticness: " + acousticness + "\nInstrumentalness: " + instrumentalness + "\nLiveness: " + liveness + "\nValence: " + valence + "\nTempo: " + tempo + "\nTime Signature: " + time_signature + "\nGenre: " + genre;
    }
}