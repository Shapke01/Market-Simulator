/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package put.oop.marketsimulator.Additional;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

import put.oop.marketsimulator.Assets.Asset;
import put.oop.marketsimulator.Assets.Commodity;
import put.oop.marketsimulator.Assets.Currency;
import put.oop.marketsimulator.Assets.Stock;
import put.oop.marketsimulator.Markets.CommodityMarket;
import put.oop.marketsimulator.Markets.CurrencyMarket;
import put.oop.marketsimulator.Markets.Market;
import put.oop.marketsimulator.Markets.StockMarket;
import put.oop.marketsimulator.Users.Company;
import put.oop.marketsimulator.Users.InvestmentFund;
import put.oop.marketsimulator.Users.Investor;
import put.oop.marketsimulator.Users.User;

/**
 *
 * @author roboc
 */
public class World extends Thread{
    private DefaultListModel<Asset> assetListModel=null;
    private DefaultListModel<User> userListModel=null;
    private DefaultListModel<Market> marketListModel=null;
    private DefaultListModel<Company> companyListModel=null;
    
    private List<Market> marketList =new ArrayList<>();
    private List<CurrencyMarket> currencyMarketList =new ArrayList<>();
    private List<CommodityMarket> commodityMarketList =new ArrayList<>();
    private List<StockMarket> stockMarketList =new ArrayList<>();
    
    private List<Asset> assetList =new ArrayList<>();
    private List<Currency> currencyList =new ArrayList<>();
    private List<Commodity> commodityList =new ArrayList<>();
    private List<Stock> stockList =new ArrayList<>();
    
    private List<User> usersList =new ArrayList<>();
    private final List<Company> companyList = new ArrayList<>();
    
    private final Object synch=new Object();
    private final Random rd=new Random();
    private boolean finish=false;
    private int nr_of_transactions=20;
    private int bearBullRatio = 0;
    
    private Date date = new Date();
    
    /**
     * - Adds investors 
     * - Updates asset prices according to bear bull ratio
     * - Adds price data of assets on a given day to their price history
     * - updates date
     */
    public void run() {
        long start_time = System.currentTimeMillis();
        while(true){
            if(usersList.size()==0 && assetList.size()>0){
                addUser();
            }
            if(usersList.size()>=1 && usersList.size()<=assetList.size()/2){
                addUser();
            }
            
            
            try {
                sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(World.class.getName()).log(Level.SEVERE, null, ex);
            }
            prices_computation();
            add_data_to_charts();
            if(System.currentTimeMillis() - start_time >= 1000){
                start_time = System.currentTimeMillis();
                date.setDate(date.getDate() + 1);
            }
            if(finish){
                break;
            }
        }
    }
    
    /**
     * Add market m to the appropriate market list
     * @param m
     */
    public void addCurrMarket(CurrencyMarket m){
        currencyMarketList.add(m);
        marketList.add(m);
        marketListModel.addElement(m);
    }

    /**
     * Add market m to the appropriate market list
     * @param m
     */
    public void addCommMarket(CommodityMarket m){
        commodityMarketList.add(m);
        marketList.add(m);
        marketListModel.addElement(m);
    }

    /**
     * Add market m to the appropriate market list
     * @param m
     */
    public void addStockMarket(StockMarket m){
        stockMarketList.add(m);
        marketList.add(m);
        marketListModel.addElement(m);
    }

    /**
     * - creates a Currency and adds random amount to the Currency Market
     */
    public void addCurrency() {
        int x=currencyMarketList.size();
        if(x>0){
            Currency a=new Currency();
            assetList.add(a);
            currencyList.add(a);
            Random rd=new Random();
            CurrencyMarket market=currencyMarketList.get(rd.nextInt(x));
            market.addAsset(a, 10000 + rd.nextFloat()*10000);
            assetListModel.addElement(a);
        }else{
            JOptionPane.showMessageDialog(null, "No Currency Market");
        }
    }
    
    /**
     * - creates a Commodity and adds random amount to the Commodity Market
     */
    public void addCommodity() {
        int x=commodityMarketList.size();
        if(x>0){
            Commodity a=new Commodity();
            assetList.add(a);
            commodityList.add(a);
            Random rd=new Random();
            CommodityMarket market=commodityMarketList.get(rd.nextInt(x));
            market.addAsset(a, 10000 + rd.nextFloat()*10000);
            assetListModel.addElement(a);
        }else{
            JOptionPane.showMessageDialog(null, "No Commodity Market");
        }        
    }
    
    /**
     * - Creates a new company
     * - Creates a thread that breaks only when the Stock Market becomes available 
     * - creates a Stock and adds random amount to the Stock Market
     */
    public void addCompany() {
        Company c=new Company(date, this);
        c.start();
        companyList.add(c);
        companyListModel.addElement(c);
        Thread t=new Thread(()->{
           while(stockMarketList.size()<1){
               try {
                   sleep(3000);
               } catch (InterruptedException ex) {
                   Logger.getLogger(World.class.getName()).log(Level.SEVERE, null, ex);
               }
               if(finish){
                   break;
               }
           }
           if(!finish){
               Stock a=new Stock(c);
                assetList.add(a);
                stockList.add(a);
                Random rd=new Random();
                StockMarket market=stockMarketList.get(rd.nextInt(stockMarketList.size()));
                market.addAsset(a, (float) c.getTrading_volume() );
                assetListModel.addElement(a);
           }  
        });
        t.start();       
    }


    private void addUser() {
        User a;
        if(rd.nextFloat()>0.3 || usersList.size()<4){
            a=new Investor(synch,this);
        }else{
            a=new InvestmentFund(synch,this);
        }
        a.start();
        userListModel.addElement(a);
        usersList.add(a);
    }

    /**
     * Set Model Lists
     * @param a1
     * @param a2
     * @param a3
     * @param a4
     */
    public void setLists(DefaultListModel<Asset> a1, DefaultListModel<User> a2, DefaultListModel<Market> a3, DefaultListModel<Company> a4) {
        assetListModel=a1;
        userListModel=a2;
        marketListModel=a3;
        companyListModel=a4;
    }

    /**
     * Update finish value if main GUI is closed
     */
    public void finished() {
        finish = true;
    }

    /**
     * Return if the interface is closed
     * @return true if interface is closed, else false
     */
    public boolean isFinish() {
        return finish;
    }

    /**
     *  
     * @return Matket list
     */
    public List<Market> getMarketList() {
        return marketList;
    }

    /**
     * 
     * @return object to synchronize transactions
     */
    public Object getSynch() {
        return synch;
    }


    /**
     * Update bear Bull/Ratio
     * @param value
     */
    public void changeBearBull(int value) {
        bearBullRatio = value;
    }

    /**
     * Update number of transactions per day
     * @param value
     */
    public void changeTransactions(int value) {
        nr_of_transactions = value;
    }

    /**
     *  
     * @return DefaultListModel of Assets
     */
    public DefaultListModel<Asset> getAssetListModel() {
        return assetListModel;
    }

    /**
     *  
     * @return DefaultListModel of Users
     */
    public DefaultListModel<User> getUserListModel() {
        return userListModel;
    }

    /**
     *  
     * @return DefaultListModel of Markets
     */
    public DefaultListModel<Market> getMarketListModel() {
        return marketListModel;
    }

    /**
     * Get number of transactions
     * (Divided by the number of traders to get 
     * the number of trades a trader makes per day)
     * @return number of transactions of single user per day
     */
    public int getNr_of_transactions() {
        int x = usersList.size();
        if(x == 0){
            return 0;
        }
        return nr_of_transactions/usersList.size();
    }

    /**
     * @return Bear/Bull Ratio
     */
    public int getBearBullRatio() {
        return bearBullRatio;
    }

    private void prices_computation() {
        for(int i = 0; i< assetList.size(); i++){
            Asset cur_asset = assetList.get(i);
            cur_asset.change_price((float)bearBullRatio/50);
            assetListModel.setElementAt(cur_asset, i);
        }
    }

    private void add_data_to_charts() {
        for(int i = 0; i< assetList.size(); i++){
            Asset cur_asset = assetList.get(i);
            cur_asset.add_to_price_hist(date);
        }
    }

    /**
     *  
     * @return DefaultListModel of Companies
     */
    public DefaultListModel<Company> getCompanyListModel() {
        return companyListModel;
    }

    /**
     *  
     * @return list of all Users
     */
    public List<User> getUsersList() {
        return usersList;
    }
    
    
}
