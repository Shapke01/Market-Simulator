/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package put.oop.marketsimulator;
import java.text.DecimalFormat;
import java.util.*;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;

/**
 *
 * @author roboc
 */
public class Currency implements Asset{
    private String name;
    private Float buy_price;
    private Float sell_price;
    private final Float open_price;
    private Set<String> countries;
    private TimeSeries price_hist = null;
    private TimeSeries perc_price_hist = null;

    /**
     * Generate fields on Currency
     */
    public Currency() {
        String chars="abcdefghijklmnopqrstuvwxyz";
        List<String> list = Arrays.asList("Argentina","Austria","Belgium","Belarus","Canada","Denmark","France","Hungary","Italy","Mexico","Netherlands","Poland","Portugal","Spain","Sweden","Russia");
        Random rd=new Random();
        int len=rd.nextInt(8)+2;
        StringBuilder nam=new StringBuilder();
        nam.append(Character.toUpperCase(chars.charAt(rd.nextInt(chars.length()))));
        for(int i=0;i<len;i++)
            nam.append(chars.charAt(rd.nextInt(chars.length())));
        this.name = nam.toString();
        this.buy_price = (float)0.5+rd.nextFloat()*(float)6;
        this.open_price = buy_price;
        this.sell_price = (float)buy_price - Math.min(rd.nextFloat()/(float)5,buy_price * (float)0.05);
        this.countries=new HashSet<>();
        len=rd.nextInt(6)+1;
        for(int i=0;i<len;i++){
            this.countries.add(list.get(rd.nextInt(list.size()))); 
        }
        price_hist = new TimeSeries(name);
        perc_price_hist = new TimeSeries(name);
    }

    /**
     * 
     * @return Extended description of Currency 
     */
    @Override
    public String longString() {
        return "Currency " + name + "\nbuy_price=" + buy_price + "\nsell_price=" + sell_price + "\ncountries=" + countries;
    }

    /**
     * 
     * @return Short description of Currency
     */
    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        return "Currency " + name + ": " + df.format(buy_price) + ", " + df.format(sell_price);
    }
    
    /**
     *  
     * @return Currency name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Set Currency name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set purchase price of Currency
     * @param buy_price
     */
    public void setBuy_price(Float buy_price) {
        this.buy_price = buy_price;
    }

    /**
     * Set sell price of Currency
     * @param sell_price
     */
    public void setSell_price(Float sell_price) {
        this.sell_price = sell_price;
    }

    /**
     * Set countries in which some Currency is used
     * @param countries
     */
    public void setCountries(Set<String> countries) {
        this.countries = countries;
    }

    /**
     *   
     * @return sell price
     */
    public float getSellPrice() {
        return sell_price;
    }

    /**
     *  
     * @return purchase price
     */
    @Override
    public float getBuyPrice() {
        return buy_price;
    }

    /**
     * Update purchase and sell price
     * @param percantage_change
     */
    public void change_price(float percantage_change) {
        sell_price += sell_price * percantage_change / 5;
        buy_price += buy_price * percantage_change / 5;
    }

    /**
     * Update price history of Commodity
     * (in both regular and percentage scale)
     * @param date
     */
    @Override
    public void add_to_price_hist(Date date) {
        price_hist.add(new Day(date), buy_price);
        perc_price_hist.add(new Day(date), buy_price/open_price);
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
