/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package put.oop.marketsimulator;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;

import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

/**
 *
 * @author roboc
 */
public class ChartGUI {
    private final JFrame f=new JFrame("Chart");
    private DefaultListModel<Asset> assetListModel = null;
    private JList<Asset> assetList = new JList();
    
    private TimeSeriesCollection datasets = new TimeSeriesCollection();
    private TimeSeriesCollection percantageDatasets = new TimeSeriesCollection();
    private boolean percentage = false;
                
    /**
     * Create an interface to display assets graphs
     * @param assetlistmodel
     */
    public ChartGUI(DefaultListModel<Asset> assetlistmodel){  
        assetListModel = assetlistmodel;
        
        JLabel assetsLabel=new JLabel("ASSETS",SwingConstants.CENTER);
        assetsLabel.setBounds(20,20,210,40);
        assetsLabel.setFont(new Font("Serif", Font.PLAIN, 25));
        assetsLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        assetsLabel.setOpaque(true);
        assetsLabel.setBackground(Color.WHITE);
        f.add(assetsLabel);
        
        JFreeChart chart = ChartFactory.createXYLineChart("$$$","Date","Value",datasets,PlotOrientation.VERTICAL,true,true,false); 
        chart.setBackgroundPaint(Color.white);
        XYPlot plot = (XYPlot) chart.getPlot();
        DateAxis dateAxis = new DateAxis();
        dateAxis.setDateFormatOverride(new SimpleDateFormat("dd-MM-yyyy")); 
        plot.setDomainAxis(dateAxis);
        NumberAxis rangeAxis1 = (NumberAxis) plot.getRangeAxis();
        DecimalFormat format1 = new DecimalFormat("#0.00$");
        rangeAxis1.setNumberFormatOverride(format1); 
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBounds(240, 20, 400, 320);
        f.add(chartPanel);
        
        JFreeChart percentageChart = ChartFactory.createXYLineChart("%%%","Date","Percentage scale",percantageDatasets,PlotOrientation.VERTICAL,true,true,false); 
        percentageChart.setBackgroundPaint(Color.white);
        XYPlot percentagePlot = (XYPlot) percentageChart.getPlot();
        DateAxis percentageDateAxis = new DateAxis();
        percentageDateAxis.setDateFormatOverride(new SimpleDateFormat("dd-MM-yyyy")); 
        percentagePlot.setDomainAxis(percentageDateAxis);
        NumberAxis rangeAxis2 = (NumberAxis) percentagePlot.getRangeAxis();
        DecimalFormat format2 = new DecimalFormat("#0%");
        rangeAxis2.setNumberFormatOverride(format2); 
        ChartPanel percentageChartPanel = new ChartPanel(percentageChart);
        percentageChartPanel.setBounds(240, 20, 400, 320);

        JButton changeButton = new JButton("% or $");
        changeButton.setBounds(75,310,100,30);  
        f.add(changeButton);
        changeButton.addActionListener((ActionEvent e) -> {
            if(percentage){
                f.remove(percentageChartPanel);
                f.add(chartPanel);
                percentage = false;
            }else{
                f.remove(chartPanel);
                f.add(percentageChartPanel);
                percentage = true;
            }
        });
        
        assetList.setModel(assetListModel);
        JScrollPane scrollableAssetArea = new JScrollPane(assetList);  
        scrollableAssetArea.setBounds(20,60,210,250);
        f.add(scrollableAssetArea);
        assetList.getSelectionModel().addListSelectionListener(e->{
            Asset a=assetList.getSelectedValue();
            TimeSeries dataset1 = a.get_price_history();
            TimeSeries dataset2 = a.get_perc_price_history();
            if(datasets.indexOf(dataset1)>=0){
                datasets.removeSeries(dataset1);
                percantageDatasets.removeSeries(dataset2);
                JOptionPane.showMessageDialog(assetList, a.getName() +" successfully deleted from chart");
            }else{
                datasets.addSeries(dataset1);
                percantageDatasets.addSeries(dataset2);
                JOptionPane.showMessageDialog(assetList, a.getName() +" successfully added to chart");
            }            
        });
                

        f.setSize(660,400);
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
    } 

}
