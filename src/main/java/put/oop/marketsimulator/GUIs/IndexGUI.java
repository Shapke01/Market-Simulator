/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package put.oop.marketsimulator.GUIs;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import static java.lang.Math.min;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import put.oop.marketsimulator.Markets.Market;
import put.oop.marketsimulator.Users.Company;

/**
 *
 * @author roboc
 */
public class IndexGUI extends Thread{
    private final JFrame f=new JFrame("Index");
    private DefaultListModel<Company> companyListModel = null;
    private DefaultListModel<Company> additionalListModel = null;
    private JList<Company> companyList = new JList<>();
    private ArrayList<Company> list = new ArrayList<>();
    boolean finish = false;
    private Market market = null;
              
    /**
     * Create an interface to display index of 5 richest companies on given Market
     * Run a thread which updates the index
     * @param companylistmodel
     * @param m
     */
    public IndexGUI(DefaultListModel<Company> companylistmodel, Market m) {
        companyListModel = companylistmodel;
        additionalListModel = new DefaultListModel<>();
        market = m;
        JLabel assetsLabel=new JLabel("TOP 5 companies on "+market.getName()+" market",SwingConstants.CENTER);
        assetsLabel.setBounds(20,20,270,40);
        assetsLabel.setFont(new Font("Serif", Font.PLAIN, 15));
        assetsLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        assetsLabel.setOpaque(true);
        assetsLabel.setBackground(Color.WHITE);
        f.add(assetsLabel);        
        
        companyList.setModel(additionalListModel);
        JScrollPane scrollableAssetArea = new JScrollPane(companyList);  
        scrollableAssetArea.setBounds(20,60,270,100);
        f.add(scrollableAssetArea);
        companyList.getSelectionModel().addListSelectionListener(e->{
            Company company = companyList.getSelectedValue();
            JOptionPane.showMessageDialog(companyList, company.longString());
        });
        

        f.setSize(330,210);
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent e) {
                finish = true;
            }
        });
        this.start();
    } 

    /**
     * Sorts the list of all companies in the market, 
     * selects the 5 richest and sets them in additionalListModel which is displayed.
     * @param x
     */
    public void sortList(DefaultListModel<Company> x){
        list.clear();
        for(int i=0;i<x.getSize();i++){
            Company a=x.getElementAt(i);
            if(market.containCompany(a)){
                list.add(a);
            }
            
        }
        Collections.sort(list, (a,b)->a.getCapital()>b.getCapital() ? -1: a.getCapital()==b.getCapital() ? 0 : 1);
        for(int i=0;i<min(5,list.size());i++){
            try {
                sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(IndexGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            Company a=list.get(i);
            if(additionalListModel.getSize()>i){
                additionalListModel.setElementAt(a, i);
            }else{
                additionalListModel.addElement(a);
            }
        }
    }

    /**
     * Constantly update the list of top 5 companies 
     */
    public void run() {
        while(!finish){
            if(companyListModel.getSize()>0){
                sortList(companyListModel);
            }
        }
    }
}