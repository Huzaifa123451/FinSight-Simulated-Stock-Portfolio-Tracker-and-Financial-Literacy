package finsight;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Map;


public class FinSightGUI {
    private final PortfolioManager manager;
    private final JTextArea portfolioArea = new JTextArea();
    private final JLabel balanceLabel = new JLabel();


    public FinSightGUI(PortfolioManager manager) {
        this.manager = manager;


        JFrame frame = new JFrame("FinSight - Portfolio Tracker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);


        JPanel panel = new JPanel(new BorderLayout());
        balanceLabel.setText("Balance: $" + String.format("%.2f", manager.getBalance()));
        panel.add(balanceLabel, BorderLayout.NORTH);


        portfolioArea.setEditable(false);
        panel.add(new JScrollPane(portfolioArea), BorderLayout.CENTER);


        JPanel inputPanel = new JPanel();
        JTextField tickerField = new JTextField(6);
        JTextField sharesField = new JTextField(5);
        JButton buyButton = new JButton("Buy");
        JButton newsButton = new JButton("News");
        JButton glossaryButton = new JButton("Glossary");
        JButton exportButton = new JButton("Export CSV");
        JButton importButton = new JButton("Import CSV");


        buyButton.addActionListener(e -> {
            try {
                String ticker = tickerField.getText().toUpperCase();
                int shares = Integer.parseInt(sharesField.getText());
                double price = StockAPI.fetchPrice(ticker);
                if (price <= 0) throw new Exception("Invalid price");


                if (manager.buyStock(ticker, shares, price)) {
                    updateDisplay();
                } else {
                    JOptionPane.showMessageDialog(frame, "Insufficient balance.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid input or API error.");
            }
        });


        newsButton.addActionListener(e -> {
            String ticker = tickerField.getText().toUpperCase();
            String news = StockAPI.fetchNews(ticker);
            JOptionPane.showMessageDialog(frame, news, "News for " + ticker, JOptionPane.INFORMATION_MESSAGE);
        });


        glossaryButton.addActionListener(e -> showGlossary());


        exportButton.addActionListener(e -> manager.exportToCSV("portfolio.csv"));
        importButton.addActionListener(e -> {
            manager.importFromCSV("portfolio.csv");
            updateDisplay();
        });


        inputPanel.add(new JLabel("Ticker:"));
        inputPanel.add(tickerField);
        inputPanel.add(new JLabel("Shares:"));
        inputPanel.add(sharesField);
        inputPanel.add(buyButton);
        inputPanel.add(newsButton);
        inputPanel.add(glossaryButton);
        inputPanel.add(exportButton);
        inputPanel.add(importButton);
        panel.add(inputPanel, BorderLayout.SOUTH);


        frame.add(panel);
        frame.setVisible(true);


        updateDisplay();

    }
    private void updateDisplay() {
        balanceLabel.setText("Balance: $" + String.format("%.2f", manager.getBalance()));
        StringBuilder sb = new StringBuilder("Current Holdings:\n");
        for (Map.Entry<String, Integer> entry : manager.getHoldings().entrySet()) {
            double price = StockAPI.fetchPrice(entry.getKey());
            double value = price * entry.getValue();
            sb.append(entry.getKey()).append(" - ").append(entry.getValue())
                    .append(" shares ($").append(String.format("%.2f", value)).append(")\n");
        }
        portfolioArea.setText(sb.toString());
    }
    private void showGlossary() {
        Map<String, String> glossary = Glossary.getGlossary();
        StringBuilder sb = new StringBuilder("Financial Glossary:\n\n");
        for (Map.Entry<String, String> entry : glossary.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString(), "Glossary", JOptionPane.INFORMATION_MESSAGE);
    }

}
