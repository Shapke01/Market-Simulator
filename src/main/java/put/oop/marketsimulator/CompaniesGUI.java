/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package put.oop.marketsimulator;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;


/**
 *
 * @author roboc
 */
public class CompaniesGUI {
    private final JFrame f=new JFrame("Companies");
    private DefaultListModel<Company> companyListModel = null;
    private JList<Company> companyList = new JList();
    private Company choosenCompany = null;
    private float percent = 0;
              
    /**
     * Create an interface enabling to force a company to buy its own shares from the market.
     * @param companylistmodel
     */
    public CompaniesGUI(DefaultListModel<Company> companylistmodel) {
        companyListModel = companylistmodel;
        
        JLabel assetsLabel=new JLabel("Companies",SwingConstants.CENTER);
        assetsLabel.setBounds(20,20,270,40);
        assetsLabel.setFont(new Font("Serif", Font.PLAIN, 25));
        assetsLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        assetsLabel.setOpaque(true);
        assetsLabel.setBackground(Color.WHITE);
        f.add(assetsLabel);        
        
        companyList.setModel(companyListModel);
        JScrollPane scrollableAssetArea = new JScrollPane(companyList);  
        scrollableAssetArea.setBounds(20,60,270,250);
        f.add(scrollableAssetArea);
        companyList.getSelectionModel().addListSelectionListener(e->{
            choosenCompany = companyList.getSelectedValue();      
        });
        
        JLabel percentLabel=new JLabel("Percent of stocks",SwingConstants.CENTER);
        percentLabel.setBounds(20,310,270,40);
        percentLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        percentLabel.setOpaque(true);
        f.add(percentLabel);  
        
        JButton buyButton=new JButton("BUY OWN SHARES");
        buyButton.setBounds(20,400,270,40);  
        f.add(buyButton);
        buyButton.addActionListener((ActionEvent e) -> {
            if(choosenCompany == null){
                JOptionPane.showMessageDialog(companyList, "Choose Company");
            }else{
                int x = choosenCompany.buyOwnShares(percent);
                if(x==0){
                    JOptionPane.showMessageDialog(companyList, "It's impossible to buy this much shares");
                }else{
                    JOptionPane.showMessageDialog(companyList, "Succesfully bought "+x+" shares");
                }
            }
        });
        
        JSlider percentSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
        percentSlider.setBounds(20,350,270,50);  
        f.add(percentSlider);
        percentSlider.addChangeListener((ChangeEvent e) -> {
            percent = (float) percentSlider.getValue()/ (float)100;
        });
        percentSlider.setMajorTickSpacing(50);
        percentSlider.setMinorTickSpacing(10);
        percentSlider.setPaintTicks(true);
        percentSlider.setPaintLabels(true);
        
        
                

        f.setSize(330,500);
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
    } 

}
