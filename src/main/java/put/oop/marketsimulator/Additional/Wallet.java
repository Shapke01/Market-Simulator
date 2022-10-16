/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package put.oop.marketsimulator.Additional;
import java.util.*;

import put.oop.marketsimulator.Assets.Asset;
import put.oop.marketsimulator.Assets.Currency;
/**
 *
 * @author roboc
 */
public class Wallet {
    private List<Asset> assets;
    private List<Float> amounts;

    /**
     * Generates fields of Wallet
     * @param x
     */
    public Wallet(Float x) {
        this.amounts=new ArrayList<>();
        this.assets=new ArrayList<>();
        Currency dol=new Currency();
        dol.setName("United States Dollar");
        dol.setCountries(new HashSet<>(Arrays.asList("Ekwador","Salwador","Haiti","Panama","USA")));
        dol.setBuy_price((float)1);
        dol.setSell_price((float)1);
        this.assets.add(dol);
        this.amounts.add(x);
    }
    
    /**
     * Add to Wallet amount b of Currency a
     * @param e
     * @param x
     */
    public void addAsset(Asset e, float x){
        this.assets.add(e);
        this.amounts.add(x);
    }
    
    /**
     * Remove Asset with index a 
     * @param x
     */
    public void deleteAsset(int x){
        this.assets.remove(x);
        this.amounts.remove(x);
    }
    
    /**
     * If the asset is not yet in the wallet, it adds a new one, 
     * and if it is, it increases the amount.
     * Subtracts the appropriate amount of dollars.
     * @param choosen_asset
     * @param buy_this_much
     */
    public void buyAsset(Asset choosen_asset, float buy_this_much) {
        int idx = assets.indexOf(choosen_asset);
        if(idx < 0){
            this.addAsset(choosen_asset, buy_this_much);
        }
        else{
            float before = amounts.get(idx);
            amounts.set(idx, before + buy_this_much);
        }
        float bef_dol = amounts.get(0);
        amounts.set(0, bef_dol - buy_this_much * choosen_asset.getBuyPrice() );
    }
    
    /**
     * Decreases the number of assets in the wallet, 
     * if the number is 0 then it removes the asset from the wallet.
     * Subtracts the appropriate amount of dollars.
     * @param choosen_asset
     * @param sell_this_much
     */
    public void sellAsset(Asset choosen_asset, float sell_this_much) {
        int idx = assets.indexOf(choosen_asset);
        float amount_in_wallet = amounts.get(idx);
        if(amount_in_wallet == sell_this_much){
            this.deleteAsset(idx);
        }
        else{
            float before = amounts.get(idx);
            amounts.set(idx, before - sell_this_much);
        }
        float bef_dol = amounts.get(0);
        amounts.set(0, bef_dol + sell_this_much * choosen_asset.getSellPrice() );
    }

   
    /**
     * 
     * @return Description of Walles
     */
    public String longString() {
        String str="";
        for(int i=0;i<this.amounts.size();i++){
            str+="       amount "+this.amounts.get(i)+" of "+this.assets.get(i)+"\n";
        }
        return str;
    }
    
    /**
     *  
     * @return amount of User's dollars
     */
    public Float getDolla(){
         return amounts.get(0);
    }
    
    /**
     * Set amount of Users dollars
     * @param x
     */
    public void setDolla(float x){
        amounts.set(0, x);
    }

    /**
     *  
     * @return list of Assets in Wallet 
     */
    public List<Asset> getAssets() {
        return assets;
    }

    /**
     *  
     * @return list of Assets amounts in Wallet
     */
    public List<Float> getAmounts() {
        return amounts;
    }
     
    
    
}
