package co.com.meridean.utils;


import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;
import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;

public class Indicators {

    private Hashtable<String, Integer> amountTotalByGoldLabels;
    private Hashtable<String, Integer> amountTotalByEstimatedLabels;
    private Hashtable<String, Integer> amountCorrectByEstimatedLabels;

    private Hashtable<String, Double> precisionIndicatorByLabel;
    private Hashtable<String, Double> recallIndicatorByLabel;
    private Hashtable<String, Double> fscoreIndicatorByLabel;

    private int totalComments;
    private int totalSuccesses;
    private int effectivenes;

    public Indicators() {
        amountTotalByGoldLabels         = new Hashtable<String, Integer>();
        amountTotalByEstimatedLabels    = new Hashtable<String, Integer>();
        amountCorrectByEstimatedLabels  = new Hashtable<String, Integer>();

        precisionIndicatorByLabel       = new Hashtable<String, Double>();
        recallIndicatorByLabel          = new Hashtable<String, Double>();
        fscoreIndicatorByLabel          = new Hashtable<String, Double>();

        totalComments = 0;
        totalSuccesses = 0;
        effectivenes = 0;
    }

    public void generateIndicators(){
        processLinesFileGold();
        fillEmptyLabels();

        calculateIndicatorPrecision();
        calculateIndicatorRecall();
        calculateIndicatorFscore();

        graphIndicators();

        System.out.println(amountTotalByGoldLabels);
        System.out.println(amountTotalByEstimatedLabels);
        System.out.println(amountCorrectByEstimatedLabels);

        System.out.println(precisionIndicatorByLabel);
        System.out.println(recallIndicatorByLabel);
        System.out.println(fscoreIndicatorByLabel);
    }

    private void processLinesFileGold(){
        Util util = new Util();
        ArrayList<String> linesFileGold = util.readGoldFile();
        totalComments = linesFileGold.size();

        for(int i=0; i<linesFileGold.size(); i++){
            String line = linesFileGold.get(i);
            processLine(line);
        }
    }

    private void processLine(String line){
        String goldLabel = "";
        String estimatedLabel = "";
        StringTokenizer stLine = new StringTokenizer(line, "\t");
        int posToken = 0;
        while (stLine.hasMoreTokens()){
            String token = stLine.nextToken();
            if(posToken == 0){
//                System.out.print(token+" | ");
                goldLabel = token;
                if(amountTotalByGoldLabels.containsKey(goldLabel)){
                    int tempAmount = amountTotalByGoldLabels.get(goldLabel);
                    amountTotalByGoldLabels.put(goldLabel, ++tempAmount);
                }
                else{
                    amountTotalByGoldLabels.put(goldLabel, 1);
                }
            }
            else if(posToken == 1){
//                System.out.print(token+" | ");
                estimatedLabel = token;
                if(amountTotalByEstimatedLabels.containsKey(estimatedLabel)){
                    int tempAmount = amountTotalByEstimatedLabels.get(estimatedLabel);
                    amountTotalByEstimatedLabels.put(estimatedLabel, ++tempAmount);
                }
                else{
                    amountTotalByEstimatedLabels.put(estimatedLabel, 1);
                }
            }
            else if(posToken == 2){
//                System.out.print(token+" | ");
                int flag = Integer.parseInt(token);
                if(flag == 1){
                    if(amountCorrectByEstimatedLabels.containsKey(estimatedLabel)){
                        int tempAmount = amountCorrectByEstimatedLabels.get(estimatedLabel);
                        amountCorrectByEstimatedLabels.put(estimatedLabel, ++tempAmount);
                        totalSuccesses++;
                    }
                    else{
                        amountCorrectByEstimatedLabels.put(estimatedLabel, 1);
                        totalSuccesses++;
                    }
                }
            }
            posToken++;
        }
//        System.out.println();
    }

    private void fillEmptyLabels(){
        Enumeration<String> labels = amountTotalByGoldLabels.keys();
        while (labels.hasMoreElements()){
            String label = labels.nextElement();
            if(!amountTotalByEstimatedLabels.containsKey(label)){
                amountTotalByEstimatedLabels.put(label,0);
            }
            if(!amountCorrectByEstimatedLabels.containsKey(label)){
                amountCorrectByEstimatedLabels.put(label,0);
            }
        }
    }

    private void calculateIndicatorPrecision(){
        Enumeration<String> labels = amountTotalByGoldLabels.keys();
        while (labels.hasMoreElements()){
            String label = labels.nextElement();
            double indicatorPrecision = 0;
            double amountCorrect = amountCorrectByEstimatedLabels.get(label) + 0.0;
            double amountTotal   = amountTotalByEstimatedLabels.get(label)   + 0.0;

            indicatorPrecision = amountCorrect / amountTotal;

            precisionIndicatorByLabel.put(label, indicatorPrecision);
        }
    }

    private void calculateIndicatorRecall(){
        Enumeration<String> labels = amountTotalByGoldLabels.keys();
        while (labels.hasMoreElements()){
            String label = labels.nextElement();
            double indicatorRecall = 0;
            double amountCorrect = amountCorrectByEstimatedLabels.get(label) + 0.0;
            double amountGoldTotal   = amountTotalByGoldLabels.get(label)   + 0.0;

            indicatorRecall = amountCorrect / amountGoldTotal;

            recallIndicatorByLabel.put(label, indicatorRecall);
        }
    }

    private void calculateIndicatorFscore(){
        Enumeration<String> labels = amountTotalByGoldLabels.keys();
        while (labels.hasMoreElements()){
            String label = labels.nextElement();
            double indicatorFscore = 0;
            double precision = precisionIndicatorByLabel.get(label);
            double recall    = recallIndicatorByLabel.get(label);

            if((precision+recall) != 0.0){
                indicatorFscore = ( 2 * ( (precision*recall) / (precision+recall) ) );
            }
            else {
                indicatorFscore = 0.0;
            }

            fscoreIndicatorByLabel.put(label, indicatorFscore);
        }
    }

    private void graphIndicators(){

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        Enumeration<String> labels = amountTotalByGoldLabels.keys();
        while (labels.hasMoreElements()){
            String label = labels.nextElement();
            double precision = precisionIndicatorByLabel.get(label) * 100.0;
            double recall    = recallIndicatorByLabel.get(label)    * 100.0;
            double fscore    = fscoreIndicatorByLabel.get(label)    * 100.0;
            dataset.setValue(precision, "Precision", label);
            dataset.setValue(recall,    "Recall",    label);
            dataset.setValue(fscore,    "Fscore",    label);
        }

        effectivenes = (int) Util.getPecentageFromAmountOfTotal(totalSuccesses, totalComments);

        JFreeChart chart = ChartFactory.createBarChart3D("Indicadores (" + effectivenes
                                                        + "% | " + totalSuccesses + " de " + totalComments +  ")",
                                "Categorías",
                                "Porcentaje", dataset, PlotOrientation.VERTICAL, true, true, false);

        ChartPanel panel = new ChartPanel(chart);

        JFrame ventana = new JFrame("Evaluación Clasificador");
        ventana.getContentPane().add(panel);
        ventana.pack();
        ventana.setVisible(true);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
