/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package put.oop.marketsimulator.Assets;
import java.text.DecimalFormat;
import java.util.*;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;

/**
 *
 * @author roboc
 */
public class Commodity implements Asset{
    private String name;
    private String unit;
    private String trading_currency;
    private Float current_price;
    private Float max_price;
    private Float min_price;
    private final Float open_price;
    private TimeSeries price_hist = null;
    private TimeSeries perc_price_hist = null;

    /**
     * Generates fields of Commodity
     */
    public Commodity() {
        Random rd=new Random();
        
        int len=rd.nextInt(8)+2;
        String chars="abcdefghijklmnopqrstuvwxyz";
        StringBuilder nam=new StringBuilder();
        nam.append(Character.toUpperCase(chars.charAt(rd.nextInt(chars.length()))));
        for(int i=0;i<len;i++)
            nam.append(chars.charAt(rd.nextInt(chars.length())));
        this.name = nam.toString();
        
        nam.setLength(0);
        for(int i=0;i<3;i++)
            nam.append(chars.charAt(rd.nextInt(chars.length())));
        this.unit = nam.toString().toUpperCase();
        
        this.trading_currency = "United States Dollar";
        this.current_price = (float)2.5+rd.nextFloat()*100;
        open_price = current_price;
        this.max_price = this.current_price;
        this.min_price = this.current_price;
        
        price_hist = new TimeSeries(name);
        perc_price_hist = new TimeSeries(name);
    }

    /**
     * 
     * @return Extended description of Commodity
     */
    @Override
    public String longString() {
        return "Commodity " + name + "\nunit=" + unit + "\ntrading_currency=" + trading_currency + "\ncurrent_price=" + current_price + "\nmax_price=" + max_price + "\nmin_price=" + min_price;
    }

    /**
     * 
     * @return Short description of Commodity
     */
    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        return "Commodity "+ name+", "+ df.format(current_price);
    }

    /**
     *  
     * @return Commodity name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     *  
     * @return current sell price
     */
    @Override
    public float getSellPrice() {
        return current_price;
    }

    /**
     *  
     * @return current purchase price
     */
    @Override
    public float getBuyPrice() {
        return current_price;
    }

    /**
     * Change current price according to percentage_change
     * Update max and min prices
     * @param percantage_change
     */
    @Override
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
     * Update price history of Commodity
     * (in both regular and percentage scale)
     * @param date
     */
    @Override
    public void add_to_price_hist(Date date) {
        price_hist.add(new Day(date), current_price);
        perc_price_hist.add(new Day(date), current_price/open_price);
    }

    /**
     *  
     * @return price history in regular scale
     */
    @Override
    public TimeSeries get_price_history() {
        return price_hist;
    }

    /**
     * 
     * @return price history in percentage scale
     */
    @Override
    public TimeSeries get_perc_price_history() {
        return perc_price_hist;
    }

    /**
     * Abstract method used only in case of Stock
     * @param f
     */
    @Override
    public void add_total_sales(float f) {}

    
}
