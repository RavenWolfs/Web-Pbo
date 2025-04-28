import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/", new FormHandler());
        server.createContext("/test", new ApiTest());
        server.createContext("/submit", new OrderHandler());
        server.createContext("/racikancoffe1.css", new CssHandler());
        server.setExecutor(null);
        System.out.println("Server running on http://localhost:8080");
        server.start();
    }
    
    static class CssHandler implements HttpHandler {
        public void handle(HttpExchange exchange) throws IOException {
            File file = new File("racikancoffe1.css"); // pastikan file ada di direktori yang sama dengan Main.java
            if (file.exists()) {
                byte[] response = new byte[(int) file.length()];
                new FileInputStream(file).read(response);
                exchange.getResponseHeaders().set("Content-Type", "text/css");
                exchange.sendResponseHeaders(200, response.length);
                OutputStream os = exchange.getResponseBody();
                os.write(response);
                os.close();
            } else {
                String notFound = "CSS file not found";
                exchange.sendResponseHeaders(404, notFound.length());
                OutputStream os = exchange.getResponseBody();
                os.write(notFound.getBytes());
                os.close();
            }
        }
    }
   
    static class ApiTest implements HttpHandler {
        public void handle(HttpExchange exchange) throws IOException {
            exchange.sendResponseHeaders(200, 10);
            OutputStream os = exchange.getResponseBody();
            os.write(10);
            os.close();
        }
    }

    static class FormHandler implements HttpHandler {
        public void handle(HttpExchange exchange) throws IOException {
            File file = new File("racikankopi.html");
            byte[] response = new byte[(int) file.length()];
            new FileInputStream(file).read(response);
            exchange.sendResponseHeaders(200, response.length);
            OutputStream os = exchange.getResponseBody();
            os.write(response);
            os.close();
        }
    }

    static class OrderHandler implements HttpHandler {
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod())) {
                InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
                BufferedReader reader = new BufferedReader(isr);
                StringBuilder buf = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    buf.append(line);
                }

                Map<String, String> formData = parseFormData(buf.toString());
                String nama = formData.get("nama");
                String email = formData.get("email");
                String kopi = formData.get("kopi");
                int jumlah = Integer.parseInt(formData.get("jumlah"));

                DBHelper.saveOrder(nama, email, kopi, jumlah);

                String response = "Pesanan atas nama " + nama + " berhasil dikirim!";
                exchange.sendResponseHeaders(200, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        }

        private Map<String, String> parseFormData(String data) throws UnsupportedEncodingException {
            Map<String, String> map = new HashMap<>();
            for (String pair : data.split("&")) {
                String[] parts = pair.split("=");
                String key = URLDecoder.decode(parts[0], "UTF-8");
                String value = URLDecoder.decode(parts[1], "UTF-8");
                map.put(key, value);
            }
            return map;
        }
    }
}