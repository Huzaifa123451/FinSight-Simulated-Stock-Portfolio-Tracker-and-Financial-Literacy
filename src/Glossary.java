package finsight;
import java.util.*;

public class Glossary {
    public static Map<String, String> getGlossary() {
        Map<String, String> glossary = new LinkedHashMap<>();
        glossary.put("P/E Ratio", "Price to Earnings Ratio. Indicates how expensive a stock is relative to its earnings.");
        glossary.put("Dividend", "A portion of company earnings paid to shareholders.");
        glossary.put("Volatility", "A measure of how much a stock's price fluctuates.");
        glossary.put("Diversification", "Spreading investments across different assets to reduce risk.");
        glossary.put("Market Cap", "The total value of a company’s outstanding shares.");
        return glossary;
    }
}