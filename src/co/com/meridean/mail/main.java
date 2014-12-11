package co.com.meridean.mail;


import co.com.meridean.utils.Indicators;
import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;

public class main {



    public static void main(String[] args) throws Exception {
        Indicators indicators = new Indicators();
        indicators.generateIndicators();
    }
}
