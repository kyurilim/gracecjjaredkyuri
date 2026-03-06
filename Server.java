import com.sun.net.httpserver.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Map;

public class Server {

    public static void serveFile(HttpExchange res, String filePath, String contentType) throws IOException {
        byte[] bytes = Files.readAllBytes(Path.of(filePath));
        res.getResponseHeaders().set("Content-Type", contentType);
        res.sendResponseHeaders(200, bytes.length);
        res.getResponseBody().write(bytes);
        res.getResponseBody().close();
    }

    public static String songsToJson(ArrayList<Map<String, Object>> songs) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < songs.size(); i++) {
            Map<String, Object> s = songs.get(i);
            sb.append("{");
            sb.append("\"track_name\":\"").append(s.get("track_name")).append("\",");
            sb.append("\"artists\":\"").append(s.get("artists")).append("\",");
            sb.append("\"genre\":\"").append(s.get("genre")).append("\",");
            sb.append("\"popularity\":").append(s.get("popularity"));
            sb.append("}");
            if (i < songs.size() - 1) sb.append(",");
        }
        sb.append("]");
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        System.out.println("Running at http://localhost:8080");

        
        server.createContext("/", res -> serveFile(res, "index.html", "text/html"));
        server.createContext("/style.css", res -> serveFile(res, "style.css", "text/css"));
        server.createContext("/code.js", res -> serveFile(res, "code.js", "application/javascript"));

        
        server.createContext("/data", exchange -> {
            exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().set("Content-Type", "application/json");

            Popularity popularity = new Popularity("dataset.csv");
            String json = songsToJson(popularity.topNSongs(1000));

            byte[] response = json.getBytes();
            exchange.sendResponseHeaders(200, response.length);
            exchange.getResponseBody().write(response);
            exchange.getResponseBody().close();
        });

        
        server.createContext("/stats", exchange -> {
            exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().set("Content-Type", "application/json");

            Popularity popularity = new Popularity("dataset.csv");
            Map<String, Double> genreStats = popularity.getAveragePopularityPerGenre();

            StringBuilder sb = new StringBuilder();
            sb.append("{");
            int idx = 0;
            for (String genre : genreStats.keySet()) {
                sb.append("\"").append(genre).append("\":").append(genreStats.get(genre));
                if (idx < genreStats.size() - 1) sb.append(",");
                idx++;
            }
            sb.append("}");

            byte[] response = sb.toString().getBytes();
            exchange.sendResponseHeaders(200, response.length);
            exchange.getResponseBody().write(response);
            exchange.getResponseBody().close();
        });

        server.start();
    }
}