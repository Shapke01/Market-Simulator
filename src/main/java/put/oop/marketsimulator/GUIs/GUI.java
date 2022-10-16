/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package put.oop.marketsimulator.GUIs;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import javax.swing.*;  
import javax.swing.event.ListSelectionEvent;

import put.oop.marketsimulator.Additional.World;
import put.oop.marketsimulator.Assets.Asset;
import put.oop.marketsimulator.Markets.CommodityMarket;
import put.oop.marketsimulator.Markets.CurrencyMarket;
import put.oop.marketsimulator.Markets.Market;
import put.oop.marketsimulator.Markets.StockMarket;
import put.oop.marketsimulator.Users.Company;
import put.oop.marketsimulator.Users.User;

/**
 *
 * @author roboc
 */
public class GUI {  
    private final JFrame f=new JFrame("Market Simulator");
    private JList<Asset> assetList=new JList<>();
    private DefaultListModel<Asset> assetListModel=new DefaultListModel<>();
    private JList<User> userList=new JList<>();
    private DefaultListModel<User> userListModel=new DefaultListModel<>();
    private JList<Market> marketList=new JList<>();
    private DefaultListModel<Market> marketListModel=new DefaultListModel<>();
    private DefaultListModel<Company> companyListModel=new DefaultListModel<>();
    
    World world;
    
    /**
     * Creates the main interface
     * @param w
     */
    public GUI(World w){  
        world=w;
        w.setLists(assetListModel,userListModel,marketListModel,companyListModel);
        
        
        JLabel title=new JLabel("Welcome to Market Simulator");
        title.setBounds(50, 0, 700, 100);
        title.setFont(new Font("Serif", Font.PLAIN, 40));
        f.add(title);
        
        JLabel usersLabel=new JLabel("USERS",SwingConstants.CENTER);
        usersLabel.setBounds(500,120,220,30);
        usersLabel.setFont(new Font("Serif", Font.PLAIN, 25));
        usersLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        usersLabel.setOpaque(true);
        usersLabel.setBackground(Color.WHITE);
        f.add(usersLabel);
        
        JLabel marketsLabel=new JLabel("MARKETS",SwingConstants.CENTER);
        marketsLabel.setBounds(500,350,220,30);
        marketsLabel.setFont(new Font("Serif", Font.PLAIN, 25));
        marketsLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        marketsLabel.setOpaque(true);
        marketsLabel.setBackground(Color.WHITE);
        f.add(marketsLabel);
        
        JLabel assetsLabel=new JLabel("ASSETS",SwingConstants.CENTER);
        assetsLabel.setBounds(270,120,220,30);
        assetsLabel.setFont(new Font("Serif", Font.PLAIN, 25));
        assetsLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        assetsLabel.setOpaque(true);
        assetsLabel.setBackground(Color.WHITE);
        f.add(assetsLabel);
        
        JLabel optionsLabel=new JLabel("OPTIONS",SwingConstants.CENTER);
        optionsLabel.setBounds(10,120,250,30);
        optionsLabel.setFont(new Font("Serif", Font.PLAIN, 25));
        optionsLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        optionsLabel.setOpaque(true);
        optionsLabel.setBackground(Color.WHITE);
        f.add(optionsLabel);
        
        JLabel bearBullLabel=new JLabel("Bear/Bull",SwingConstants.CENTER);
        bearBullLabel.setBounds(10,480,120,15);
        bearBullLabel.setFont(new Font("Serif", Font.PLAIN, 12));
        f.add(bearBullLabel);
        
        JLabel nrTransLabel=new JLabel("No of transactions",SwingConstants.CENTER);
        nrTransLabel.setBounds(140,480,120,15);
        nrTransLabel.setFont(new Font("Serif", Font.PLAIN, 12));
        f.add(nrTransLabel);
        
        assetList.setModel(assetListModel);
        JScrollPane scrollableAssetArea = new JScrollPane(assetList);  
        scrollableAssetArea.setBounds(270,150,220,400);
        f.add(scrollableAssetArea);
        assetList.getSelectionModel().addListSelectionListener(e->{
            Asset a=assetList.getSelectedValue();
            JOptionPane.showMessageDialog(assetList, a.longString());
        });
        
        userList.setModel(userListModel);
        JScrollPane scrollableUserArea = new JScrollPane(userList);  
        scrollableUserArea.setBounds(500,150,220,200);
        f.add(scrollableUserArea);
        userList.getSelectionModel().addListSelectionListener(e->{
            User a=userList.getSelectedValue();
            JOptionPane.showMessageDialog(userList, a.longString());
        });
        
        marketList.setModel(marketListModel);
        JScrollPane scrollableMarketArea = new JScrollPane(marketList);  
        scrollableMarketArea.setBounds(500,380,220,170);
        f.add(scrollableMarketArea);
        marketList.getSelectionModel().addListSelectionListener((ListSelectionEvent e)->{
            Market a=marketList.getSelectedValue();
            if("StockMarket".equals(a.getClass().getSimpleName())){
                JOptionPane.showMessageDialog(marketList, a.longString());
                new IndexGUI(companyListModel,a);
            }else{
                JOptionPane.showMessageDialog(marketList, a.longString());
            }
            
        });
        
        JButton newCurrMarketButton=new JButton("ADD CURRENCY MARKET");
        newCurrMarketButton.setBounds(10,150,250,40);  
        f.add(newCurrMarketButton);
        newCurrMarketButton.addActionListener(e->world.addCurrMarket(new CurrencyMarket()));
        
        JButton newCommMarketButton=new JButton("ADD COMMODITY MARKET");
        newCommMarketButton.setBounds(10,190,250,40);  
        f.add(newCommMarketButton);
        newCommMarketButton.addActionListener(e->world.addCommMarket(new CommodityMarket()));
        
        JButton newStockMarketButton=new JButton("ADD STOCK MARKET");
        newStockMarketButton.setBounds(10,230,250,40);  
        f.add(newStockMarketButton);
        newStockMarketButton.addActionListener(e->world.addStockMarket(new StockMarket()));
        
        JButton newCurrencyButton=new JButton("ADD CURRENCY");
        newCurrencyButton.setBounds(10,270,250,40);  
        f.add(newCurrencyButton);
        newCurrencyButton.addActionListener(e->world.addCurrency());
        
        JButton newCommodityButton=new JButton("ADD COMMODITY");
        newCommodityButton.setBounds(10,310,250,40);  
        f.add(newCommodityButton);
        newCommodityButton.addActionListener(e->world.addCommodity());
        
        JButton newCompanyButton=new JButton("ADD COMPANY");
        newCompanyButton.setBounds(10,350,250,40);  
        f.add(newCompanyButton);
        newCompanyButton.addActionListener(e->world.addCompany());
                
        JButton newIndexButton=new JButton("COMPANIES");
        newIndexButton.setBounds(10,390,250,40);  
        f.add(newIndexButton);
        newIndexButton.addActionListener(e->new CompaniesGUI(companyListModel));
        
        JButton newChartButton=new JButton("DISPLAY CHARTS");
        newChartButton.setBounds(10,430,250,40);  
        f.add(newChartButton);
        newChartButton.addActionListener(e->new ChartGUI(assetListModel));
        
        JSlider bearBullSlider = new JSlider(JSlider.HORIZONTAL, -5, 5, 0);
        bearBullSlider.setBounds(10,500,120,50);  
        f.add(bearBullSlider);
        bearBullSlider.addChangeListener(e->world.changeBearBull(bearBullSlider.getValue()));
        bearBullSlider.setMajorTickSpacing(5);
        bearBullSlider.setMinorTickSpacing(1);
        bearBullSlider.setPaintTicks(true);
        bearBullSlider.setPaintLabels(true);
        
        JSlider transactionsSlider = new JSlider(JSlider.HORIZONTAL, 0, 200, 20);
        transactionsSlider.setBounds(140,500,120,50);  
        f.add(transactionsSlider);
        transactionsSlider.addChangeListener(e->world.changeTransactions(transactionsSlider.getValue()));
        transactionsSlider.setMajorTickSpacing(100);
        transactionsSlider.setMinorTickSpacing(10);
        transactionsSlider.setPaintTicks(true);
        transactionsSlider.setPaintLabels(true);
        

        f.setSize(750,600);
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                w.finished();
                System.out.println("Finish");
            }
        });
    }  

}  
