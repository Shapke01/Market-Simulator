/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package put.oop.marketsimulator;
import java.util.*;
/**
 *
 * @author roboc
 */
public abstract class Market {
    private String name;
    private String country;
    private String trading_currency;
    private String city;
    private String address;
    private Float cost_of_operation;

    /**
     * Generates fields of Market
     */
    public Market() {
        Random rd=new Random();
         
        int len=rd.nextInt(8)+2;
        String chars="abcdefghijklmnopqrstuvwxyz";
        StringBuilder nam=new StringBuilder();
        nam.append(Character.toUpperCase(chars.charAt(rd.nextInt(chars.length()))));
        for(int i=0;i<len;i++)
            nam.append(chars.charAt(rd.nextInt(chars.length())));
        this.name = nam.toString();
        
        List<String> list = Arrays.asList("Argentina","Austria","Belgium","Belarus","Canada","Denmark","France","Hungary","Italy","Mexico","Netherlands","Poland","Portugal","Spain","Sweden","Russia");
        int nr=rd.nextInt(list.size());
        this.country=list.get(nr);
        
        this.trading_currency = "United States Dollar";
        
        list = Arrays.asList("Buenos Aires","Vienna","Brussels","Minsk","Ottawa","Copenhagen","Paris","Budapest","Rome","Mexico","Amsterdam","Warsaw","Lisbon","Madrid","Stockholm","Moscow");
        this.city = list.get(nr);
        
        nam=new StringBuilder();
        nam.append(Character.toUpperCase(chars.charAt(rd.nextInt(chars.length()))));
        for(int i=0;i<len;i++)
            nam.append(chars.charAt(rd.nextInt(chars.length())));
        nam.append(" ");
        nam.append(String.valueOf(rd.nextInt(100)));
        this.address = nam.toString();
        
        this.cost_of_operation = (float)0.001+rd.nextFloat()/50;
        
    }

    /**
     *  
     * @return Market name
     */
    public String getName() {
        return name;
    }   

    /**
     * Add to Market amount b of Asset a
     * @param a
     * @param b
     */
    public abstract void addAsset(Asset a, Float b);

    /**
     * Remove Asset with index a 
     * @param idx2
     */
    public abstract void deleteAsset(int idx2);

    /**
     *  
     * @return list of Assets 
     */
    public abstract List<Asset> getAssets();

    /**
     *  
     * @return list of Assets amounts
     */
    public abstract List<Float> getAmounts();

    /**
     * Decrease by x the number of Asset items under the index asset_idx
     * @param asset_idx
     * @param buy_this_much
     */
    public abstract void decreaceAssetBy(int asset_idx, float buy_this_much);

    /**
     * Increase by x the number of Asset items under the index asset_idx
     * @param idx
     * @param amount_to_sell
     */
    public abstract void increaseAssetBy(int idx, float amount_to_sell);

    /**
     * Abstract method used only in case of Stock Market
     * to check whether Company a has placed its shares on the market
     * @param a
     * @return true if there is the company on the market, else false
     */
    public abstract boolean containCompany(Company a);
    
    /**
     * 
     * @return Extended description of Market
     */
    public String longString() {
        return "Market " + name + "\ncountry=" + country + "\ntrading_currency=" + trading_currency + "\ncity=" + city + "\naddress=" + address + "\ncost_of_operation=" + cost_of_operation;
    }

    /**
     * 
     * @return Short description of Market
     */
    public String toString() {
        return name;
    }


    
    
    
    
}
