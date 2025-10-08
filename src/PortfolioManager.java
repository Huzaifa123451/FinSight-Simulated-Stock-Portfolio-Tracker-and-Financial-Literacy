package finsight;


import java.io.*;
import java.util.*;


public class PortfolioManager {
    private double balance = 10000.0;
    private final Map<String, Integer> holdings = new HashMap<>();


    public double getBalance() {
        return balance;
    }


    public Map<String, Integer> getHoldings() {
        return holdings;
    }


    public boolean buyStock(String ticker, int shares, double price) {
        double cost = shares * price;
        if (cost > balance) return false;
        balance -= cost;
        holdings.put(ticker, holdings.getOrDefault(ticker, 0) + shares);
        return true;
    }


    public void exportToCSV(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("Ticker,Shares");
            for (Map.Entry<String, Integer> entry : holdings.entrySet()) {
                writer.println(entry.getKey() + "," + entry.getValue());
            }
        } catch (IOException e) {
            System.err.println("Failed to export CSV: " + e.getMessage());
        }
    }


    public void importFromCSV(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line = reader.readLine(); // skip header
            holdings.clear();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                holdings.put(parts[0], Integer.parseInt(parts[1]));
            }
        } catch (IOException e) {
            System.err.println("Failed to import CSV: " + e.getMessage());
        }
    }
}