package chapter6;

//Author Name:Alex Porter
//Date: 9/29/2020
//Program Name: Chapter6AssignmentClass
//Purpose: Implement the Fibonacci function in both a recursive and iterative fashion. What’s the runtime efficiency of each?

import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;

import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.category.DefaultCategoryDataset;

public class Chapter6AssignmentClass extends ApplicationFrame {

    public static final String X_AXIS_VALUE = "Nanoseconds";
    public static final String Y_AXIS_VALUE = "Nth Elements";
    public static final boolean SHOW_GRAPH = false;

    //======= The Recursive Method for the Fibonacci Sequence =======
    public static int fibonacciRecursion(int nthNumber) {
        if (nthNumber == 0) {
            return 0;
        }
        else if (nthNumber == 1) {
            return 1;
        }
        return fibonacciRecursion(nthNumber - 1) + fibonacciRecursion(nthNumber - 2);
    }

    //======= The Iterative Method for the Fibonacci Sequence =======
    public static int fibonacciLoop(int nthNumber) {
        int previouspreviousNumber, previousNumber = 0, currentNumber = 1;
        for (int i = 1; i < nthNumber ; i++) {
            previouspreviousNumber = previousNumber;
            previousNumber = currentNumber;
            currentNumber = previouspreviousNumber + previousNumber;
        }
        return currentNumber;
    }

    //======= Constructor for the Line Graph =======
    public Chapter6AssignmentClass(String applicationTitle, String chartTitle, long recursiveValues[], long iterativeValues[]) {
        super(applicationTitle);
        //This is using JFreeChart, creating the ChartFactory to build out chart elements
        JFreeChart lineChart = ChartFactory.createLineChart(
                chartTitle,
                Y_AXIS_VALUE,X_AXIS_VALUE,
                createDataset(recursiveValues, iterativeValues),
                PlotOrientation.VERTICAL,
                true,true,false);

        //Start creating the panel to be displayed with default dimensions
        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560 , 367));
        setContentPane(chartPanel);

    }

    private DefaultCategoryDataset createDataset(long recursiveValues[], long iterativeValues[]) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        //dataset.addValue( 15 , "schools" , "1970" );
        for(int i = 0; i < 10; i++) {
            dataset.addValue((double)iterativeValues[i], "Iterative", String.valueOf(i));
            dataset.addValue((double)recursiveValues[i], "Recursive", String.valueOf(i));
        }

        return dataset;
    }

    public static void main( String[ ] args ) {
        if (SHOW_GRAPH) {

        }

        //======= Setup the Arrays to be Passed to the Dataset
        long totalTimeRecursive[] = new long[10];
        long totalTimeIterative[] = new long[10];

        //======= Recursive Data =======
        long endTimesRecursive[] = new long[10];
        long startTimeRecursive = System.nanoTime();
        for(int i = 0; i < 10; i++) {
            fibonacciRecursion(i);
            endTimesRecursive[i] = System.nanoTime();
        }

        //======= Iterative Data =======
        long endTimesIterative[] = new long[10];
        long startTimeIterative = System.nanoTime();
        for(int i = 0; i < 10; i++) {
            fibonacciLoop(i);
            endTimesIterative[i] = System.nanoTime();
        }

        //======= Parse both Datasets =======
        for(int i = 0; i < 10; i++) {
            totalTimeRecursive[i] = endTimesRecursive[i] - startTimeRecursive;
            totalTimeIterative[i] = endTimesIterative[i] - startTimeIterative;

            System.out.println("Recursive: " + totalTimeRecursive[i] + " Iterative: " + totalTimeIterative[i]);
        }

        Chapter6AssignmentClass chart = new Chapter6AssignmentClass(
                "Fibonacci Method vs Time",
                "Fibonacci Method vs Time",
                totalTimeRecursive,
                totalTimeIterative);

        chart.pack();
        chart.setVisible(true);

    }

}
