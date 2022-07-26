/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package put.oop.marketsimulator;

import java.util.Date;
import org.jfree.data.time.TimeSeries;

/**
 *
 * @author roboc
 */
public interface Asset {

    /**
     * 
     * @return Asset name
     */
    public String getName();

    /**
     * 
     * @return extended description of Asset 
     */
    public String longString();

    /**
     *  
     * @return sell price
     */
    public float getSellPrice();

    /**
     *  
     * @return purchase price
     */
    public float getBuyPrice();

    /**
     * Update Assets prices
     * @param percantage_change
     */
    public void change_price(float percantage_change);

    /**
     *
     * @return price history in regular scale
     */
    public TimeSeries get_price_history();

    /**
     *
     * @return price history in percentage scale
     */
    public TimeSeries get_perc_price_history();

    /**
     * Update price history of Asset
     * @param date
     */
    public void add_to_price_hist(Date date);

    /**
     * Abstract method used only in case of Stock
     * to update total sales of Company owning the Stock
     * @param f
     */
    public void add_total_sales(float f);
    
}
