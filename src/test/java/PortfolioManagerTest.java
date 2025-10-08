import finsight.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class PortfolioManagerTest {
    @Test
    public void testBuyStockSuccess() {
        PortfolioManager pm = new PortfolioManager();
        double price = 100.0;
        boolean success = pm.buyStock("AAPL", 2, price);
        assertTrue(success);
        assertEquals(9800.0, pm.getBalance(), 0.01);
        assertEquals(2, (int) pm.getHoldings().get("AAPL"));
    }

    @Test
    public void testBuyStockFailInsufficientBalance() {
        PortfolioManager pm = new PortfolioManager();
        double price = 6000.0;
        boolean success = pm.buyStock("GOOG", 2, price);
        assertFalse(success);
        assertNull(pm.getHoldings().get("GOOG"));
    }

    @Test
    public void testPortfolioHistoryLogging() {
        PortfolioManager pm = new PortfolioManager();
        pm.buyStock("MSFT", 1, 100.0);
        pm.buyStock("AAPL", 1, 200.0);
        assertEquals(2, pm.getPortfolioHistory().size());
    }

    @Test
    public void testCSVExportAndImport() {
        PortfolioManager pm = new PortfolioManager();
        pm.buyStock("TSLA", 5, 100.0);
        pm.exportToCSV("test.csv");

        PortfolioManager newPM = new PortfolioManager();
        newPM.importFromCSV("test.csv");
        assertEquals(5, (int) newPM.getHoldings().get("TSLA"));
    }
}
