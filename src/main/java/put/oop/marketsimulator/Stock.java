/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package put.oop.marketsimulator;

import java.text.DecimalFormat;
import java.util.Date;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;


/**
 *
 * @author roboc
 */
public class Stock implements Asset{
    private final Company company;
    private TimeSeries price_hist = null;
    private TimeSeries perc_price_hist = null;

    /**
     * Generates fields of Stock
     * @param comp
     */
    public Stock(Company comp) {
        this.company = comp;
        price_hist = new TimeSeries(company.getCompanyName());
        perc_price_hist = new TimeSeries(company.getCompanyName());
    }

    /**
     * 
     * @return Extended description of Stock
     */
    @Override
    public String longString() {
        return "Stock of " + company.longString();
    }

    /**
     * 
     * @return Short description of Stock
     */
    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        return "Stock " + company.getCompanyName()+", "+df.format(company.getCurrent_price());
    }

    /**
     *  
     * @return current sell price
     */
    @Override
    public float getSellPrice() {
        return company.getCurrent_price();
    }

    /**
     *  
     * @return current purchase price
     */
    @Override
    public float getBuyPrice() {
        return company.getCurrent_price();
    }

    /**
     * Change current price according to percentage_change
     * @param percantage_change
     */
    @Override
    public void change_price(float percantage_change) {
        company.change_price(percantage_change);
    }

    /**
     * Update price history of Commodity
     * (in both regular and percentage scale)
     * @param date
     */
    @Override
    public void add_to_price_hist(Date date) {
        price_hist.add(new Day(date), company.getCurrent_price());
        perc_price_hist.add(new Day(date), company.getCurrent_price()/company.getShare_value_IPO());
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
     *  
     * @return name of Company owning the Stock
     */
    @Override
    public String getName() {
        return company.getCompanyName();
    }

    /**
     * Update total sales of Company owning the Stock
     * @param f
     */
    @Override
    public void add_total_sales(float f) {
        company.add_total_sales(f);
    }
    
    
}
