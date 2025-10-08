package finsight;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PortfolioManager portfolioManager = new PortfolioManager();
            new FinSightGUI(portfolioManager);
        });
    }

}