/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package put.oop.marketsimulator.Users;

import java.util.*;

import put.oop.marketsimulator.Additional.Wallet;
import put.oop.marketsimulator.Additional.World;
import put.oop.marketsimulator.Markets.Market;

/**
 *
 * @author roboc
 */
public abstract class User extends Thread {
    private String first_name;
    private String last_name;
    private List<Market> markets;
    private Object lock;
    private World world;
    private Random rd=new Random();
    
    /**
     * Generates fields of User
     * @param synch
     * @param wrld
     */
    public User(Object synch,World wrld){

        int len=rd.nextInt(8)+2;
        String chars="abcdefghijklmnopqrstuvwxyz";
        StringBuilder nam=new StringBuilder();
        nam.append(Character.toUpperCase(chars.charAt(rd.nextInt(chars.length()))));
        for(int i=0;i<len;i++)
            nam.append(chars.charAt(rd.nextInt(chars.length())));
        first_name = nam.toString();
        
        nam=new StringBuilder();
        nam.append(Character.toUpperCase(chars.charAt(rd.nextInt(chars.length()))));
        for(int i=0;i<len;i++)
            nam.append(chars.charAt(rd.nextInt(chars.length())));
        last_name = nam.toString();
               
        world=wrld;
        
        markets=world.getMarketList();
        
        lock=synch;       
    }

    /**
     *  
     * @return Users first name
     */
    public String getFirst_name() {
        return first_name;
    }

    /**
     *  
     * @return Users last name
     */
    public String getLast_name() {
        return last_name;
    }
    
    /**
     * Based on the amount of a given currency in the market, 
     * investor's cash capabilities and the price of asset, 
     * it generates a random number of assets to buy
     * @param amount_on_market
     * @param user_possibility
     * @param price
     * @return amount on asset to buy
     */
    public float some_value(float amount_on_market, float user_possibility, float price){
        float random_amount = rd.nextFloat()* 1000 / price + 10;
        float rand = rd.nextFloat();
        if(rand > 0.5){
            random_amount = Math.min(user_possibility / 3, amount_on_market / 10);
        }else if(rand < 0.01){
            random_amount = Math.min(user_possibility, amount_on_market / 10);
        }
        if(random_amount > Math.min(amount_on_market, user_possibility)){
            return Math.min(amount_on_market, user_possibility);
        }
        return random_amount;
    }
    
    /**
     * Remove from market with index idx asset with index idx2
     * @param idx
     * @param idx2
     */
    public void deleteFromMarket(int idx, int idx2){
        Market m=markets.get(idx);
        m.deleteAsset(idx2); 
    }

    /**
     * 
     * @return Extended description of User
     */
    public String longString() {
        return "name=" + first_name + " " + last_name;
    }
    
    /**
     *
     * @return Short description of User
     */
    @Override
    public String toString() {
        return first_name + " " + last_name;
    }

    /**
     *  
     * @return Wallet
     */
    public Wallet getWallet(){return null;}

    /**
     * Get lock to synchronize investors transactions
     * @return lock
     */
    public Object getLock() {
        return lock;
    }

    /**
     * Get world (to check if thread of user should be terminated)
     * @return world
     */ 
    public World getWorld() {
        return world;
    }
    
    
}
