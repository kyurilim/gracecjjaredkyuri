public class Song{
    private int popularity, duration_ms, duration_minutes, duration_seconds, mode, time_signature;
    private String track_id, artists, album_name, track_name, key, genre;
    private boolean explicit;
    private double danceability, energy, loudness, speechiness, acousticness, instrumentalness, liveness, valence, tempo;

    public Song() {
        this.track_id = "";
        this.artists = "";
        this.album_name = "";
        this.track_name = "";
        this.popularity = 0;
        this.duration_ms = 0;
        this.explicit = false;
        this.danceability = 0.0;
        this.energy = 0.0;
        this.key = "";
        this.loudness = 0.0;
        this.mode = 0;
        this.speechiness = 0.0;
        this.acousticness = 0.0;
        this.instrumentalness = 0.0;
        this.liveness = 0.0;
        this.valence = 0.0;
        this.tempo = 0.0;
        this.time_signature = 0;
        this.genre = "";
    }

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

    public void setTrack_id(String track_id) {
        this.track_id = track_id;
    }
    public void setArtists(String artists) {
        this.artists = artists;
    }
    public void setAlbum_name(String album_name) {
        this.album_name = album_name;
    }
    public void setTrack_name(String track_name) {
        this.track_name = track_name;
    }
    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }
    public void setDuration_ms(int duration_ms) {
        this.duration_ms = duration_ms;
    }
    public void setExplicit(boolean explicit) {
        this.explicit = explicit;
    }
    public void setDanceability(double danceability) {
        this.danceability = danceability;
    }
    public void setEnergy(double energy) {
        this.energy = energy;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public void setLoudness(double loudness) {
        this.loudness = loudness;
    }
    public void setMode(int mode) {
        this.mode = mode;
    }
    public void setSpeechiness(double speechiness) {
        this.speechiness = speechiness;
    }
    public void setAcousticness(double acousticness) {
        this.acousticness = acousticness;
    }
    public void setInstrumentalness(double instrumentalness) {
        this.instrumentalness = instrumentalness;
    }
    public void setLiveness(double liveness) {
        this.liveness = liveness;
    }
    public void setValence(double valence) {
        this.valence = valence;
    }
    public void setTempo(double tempo) {
        this.tempo = tempo;
    }
    public void setTime_signature(int time_signature) {
        this.time_signature = time_signature;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    

    public String toString() {
        return "Track ID: " + track_id + "\nArtists: " + artists + "\nAlbum Name: " + album_name + "\nTrack Name: " + track_name + "\nPopularity: " + popularity + "\nDuration (ms): " + duration_ms + "\nExplicit: " + explicit + "\nDanceability: " + danceability + "\nEnergy: " + energy + "\nKey: " + key + "\nLoudness: " + loudness + "\nMode: " + mode + "\nSpeechiness: " + speechiness + "\nAcousticness: " + acousticness + "\nInstrumentalness: " + instrumentalness + "\nLiveness: " + liveness + "\nValence: " + valence + "\nTempo: " + tempo + "\nTime Signature: " + time_signature + "\nGenre: " + genre;
    }

    public String toJson(){
        return "{\"track_id\":\"" + track_id + "\",\"artists\":\"" + artists + "\",\"album_name\":\"" + album_name + "\",\"track_name\":\"" + track_name + "\",\"popularity\":" + popularity + ",\"duration_ms\":" + duration_ms + ",\"explicit\":" + explicit + ",\"danceability\":" + danceability + ",\"energy\":" + energy + ",\"key\":\"" + key + "\",\"loudness\":" + loudness + ",\"mode\":" + mode + ",\"speechiness\":" + speechiness + ",\"acousticness\":" + acousticness + ",\"instrumentalness\":" + instrumentalness + ",\"liveness\":" + liveness + ",\"valence\":" + valence + ",\"tempo\":" + tempo + ",\"time_signature\":" + time_signature + ",\"genre\":\"" + genre +"\"}";
    }
}