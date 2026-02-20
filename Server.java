import com.sun.net.httpserver.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.file.*;
import java.util.ArrayList;

public class Server {

    // Helper method that accepts HttpExchange
    public static void serveFile(HttpExchange res, String filePath, String contentType) throws IOException {
        byte[] bytes = Files.readAllBytes(Path.of(filePath));
        res.getResponseHeaders().set("Content-Type", contentType);
        res.sendResponseHeaders(200, bytes.length);
        res.getResponseBody().write(bytes);
        res.getResponseBody().close();
    }
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        System.out.println("Running at http://localhost:8080");


        // Then your handlers become clean one-liners:
        server.createContext("/", new HttpHandler() {
            public void handle(HttpExchange res) throws IOException {
                serveFile(res, "src/index.html", "text/html");
            }
        });

        server.createContext("/style.css", new HttpHandler() {
            public void handle(HttpExchange res) throws IOException {
                serveFile(res, "src/style.css", "text/css");
            }
        });

        server.createContext("/code.js", new HttpHandler() {
            public void handle(HttpExchange res) throws IOException {
                serveFile(res, "src/code.js", "application/javascript");
            }
        });


        // Serve ArrayList as JSON
        server.createContext("/data", exchange -> {
            // Add CORS header so browser fetch() works
            exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().set("Content-Type", "application/json");

            // Manually convert ArrayList to JSON array string (no library!)
            DataAnalyzer analyzer = new DataAnalyzer();
            ArrayList<Song> songs = analyzer.createSongs(FileOperator.getStringList("dataset.csv"));
            String json = analyzer.statsToJson(songs);

            byte[] response = json.getBytes();
            exchange.sendResponseHeaders(200, response.length);
            exchange.getResponseBody().write(response);
            exchange.getResponseBody().close();


        });
        
        server.createContext("/stats", exchange -> {
            exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            ArrayList<Song> songs = new DataAnalyzer().createSongs(FileOperator.getStringList("dataset.csv"));
            String json = new DataAnalyzer().statsToJson(songs);
            byte[] response = json.getBytes();
            exchange.sendResponseHeaders(200, response.length);
            exchange.getResponseBody().write(response);
            exchange.getResponseBody().close();
        });


        server.start();

    }
}
