package finsight;


import java.io.*;
import java.net.*;
import org.json.*;


public class StockAPI {
    private static final String API_KEY = "demo"; // Replace with your actual key


    public static double fetchPrice(String ticker) {
        try {
            String urlString = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=" + ticker + "&apikey=" + API_KEY;
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");


            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();


            JSONObject json = new JSONObject(response.toString());
            JSONObject quote = json.getJSONObject("Global Quote");
            return Double.parseDouble(quote.getString("05. price"));


        } catch (Exception e) {
            System.err.println("Error fetching price for " + ticker + ": " + e.getMessage());
            return -1.0;
        }
    }


    public static String fetchNews(String ticker) {
        try {
// Placeholder for real news API
            return "Recent headlines for " + ticker + ":\n- Market opens higher today\n- Earnings report exceeds expectations.";
        } catch (Exception e) {
            return "Failed to fetch news.";
        }
    }
}