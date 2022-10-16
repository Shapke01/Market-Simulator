/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package put.oop.marketsimulator.Users;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import put.oop.marketsimulator.Additional.World;
import put.oop.marketsimulator.Assets.Asset;
import put.oop.marketsimulator.Markets.Market;
/**
 *
 * @author roboc
 */
public class Company extends Thread{
    private String companyName;
    private final String date_IPO;
    private Float share_value_IPO;
    private Float current_price;
    private Float min_price;
    private Float max_price;
    private Float profit;
    private Float revenue;
    private Float capital;
    private int trading_volume;
    private Float total_sales;
    private World world;
    private final Random rd=new Random();
    
    /**
     * Generates fields of Company
     * @param date
     * @param w
     */
    public Company(Date date, World w) {
        
        
        int len=rd.nextInt(8)+2;
        String chars="abcdefghijklmnopqrstuvwxyz";
        StringBuilder nam=new StringBuilder();
        nam.append(Character.toUpperCase(chars.charAt(rd.nextInt(chars.length()))));
        for(int i=0;i<len;i++)
            nam.append(chars.charAt(rd.nextInt(chars.length())));
        companyName = nam.toString();
        
        date_IPO = new SimpleDateFormat("yyyy-MM-dd").format( new Date(date.getTime()) );
        
        share_value_IPO=100 + rd.nextFloat()*1000;        
        current_price=share_value_IPO;        
        min_price=share_value_IPO;
        max_price=share_value_IPO;
        
        trading_volume=rd.nextInt(4000)+100;
        profit= trading_volume * current_price;        
        revenue= trading_volume * current_price;   
        capital=rd.nextFloat()*1000000+trading_volume * current_price;
        total_sales=(float)0;
        
        world = w;
    }
    
    /**
     * With probability lower than 1% every 1/100 of second
     * increase trading volume by random value and update profit, revenue and capital.
     * Update CompanyListModel from World class
     */
    @Override
    public void run() {
        while(true){
            try {
                sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
            int asset_model_idx = world.getCompanyListModel().indexOf(this);
            world.getCompanyListModel().setElementAt(this, asset_model_idx);
            if(rd.nextFloat()<0.008){
                int x = rd.nextInt(50);
                trading_volume += x;
                profit += x * current_price;        
                revenue += x * current_price;   
                capital += x * current_price;  
            }
            if(world.isFinish()){
                break;
            }
        }
        
    }

    /**
     *  
     * @return Company name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     *  
     * @return IPO price of Company's Stock
     */
    public Float getShare_value_IPO() {
        return share_value_IPO;
    }

    /**
     *  
     * @return current price of Company
     */
    public Float getCurrent_price() {
        return current_price;
    }

    /**
     *   
     * @return capital of Company
     */
    public Float getCapital() {
        return capital;
    }

    /**
     *  
     * @return trading volume of Company
     */
    public int getTrading_volume() {
        return trading_volume;
    }

    /**
     * 
     * @return Extended description of Company
     */
    public String longString() {
        return "Company " + companyName + "\ndate_IPO= " + date_IPO + "\nshare_value_IPO= " + share_value_IPO + "\ncurrent_price= " + current_price + "\nmin_price= " + min_price + "\nmax_price= " + max_price + "\nprofit= " + profit + "\nrevenue= " + revenue + "\ncapital= " + capital + "\ntrading_volume= " + trading_volume + "\ntotal_sales= " + total_sales;
    }

    /**
     * 
     * @return Short description of Company
     */
    @Override
    public String toString() {
        return "Company: " + companyName + ", capital=" + capital;
    }

    /**
     * Change current price according to percentage_change
     * Update max and min prices
     * @param percantage_change
     */
    public void change_price(float percantage_change) {
        current_price += current_price * percantage_change / 5;
        if(current_price > max_price){
            max_price = current_price;
        }
        if(current_price < min_price){
            min_price = current_price;
        }
    }

    /**
     * Update total sales of Company
     * @param f
     */
    public void add_total_sales(float f) {
        total_sales += f;
    }

    /**
     * Force the Company to repurchase a certain percentage of its shares that are in the market
     * Reduce the Company's capital and revenue
     * @param percent
     * @return amount of own shares to buy
     */
    public int buyOwnShares(float percent) {
        List<Market> markets = world.getMarketList();
        for(Market market: markets){
            List<Asset> assets = market.getAssets();
            for(int i = 0; i<assets.size(); i++){
                Asset asset = assets.get(i);
                if(!"Stock".equals(asset.getClass().getSimpleName())){
                    break;
                }
                if(asset.getName() == this.getCompanyName()){
                    float amount = market.getAmounts().get(i);
                    float amountToBuy = Math.round(amount * percent);
                    if(amountToBuy * current_price < capital){
                        capital -= amountToBuy * current_price;
                        profit -= amountToBuy * current_price;
                        return (int) amountToBuy;
                    }
                    return 0;
                }
            }
        }
        return 0;
    }
    
}
