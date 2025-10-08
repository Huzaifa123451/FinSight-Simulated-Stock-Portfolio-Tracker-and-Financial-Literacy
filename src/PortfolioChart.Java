package finsight;


import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.data.category.*;


import javax.swing.*;
import java.util.*;


public class PortfolioChart {
    public static void showChart(List<Double> history) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < history.size(); i++) {
            dataset.addValue(history.get(i), "Value", "Day " + (i + 1));
        }
        JFreeChart lineChart = ChartFactory.createLineChart(
                "Portfolio Value Over Time",
                "Day",
                "Value ($)",
                dataset,
                PlotOrientation.VERTICAL,
                false, true, false);


        ChartPanel chartPanel = new ChartPanel(lineChart);
        JFrame chartFrame = new JFrame("Portfolio Chart");
        chartFrame.setContentPane(chartPanel);
        chartFrame.setSize(600, 400);
        chartFrame.setVisible(true);
    }
}