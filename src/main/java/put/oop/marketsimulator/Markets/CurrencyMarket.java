/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package put.oop.marketsimulator.Markets;

import java.util.*;

import put.oop.marketsimulator.Assets.Asset;
import put.oop.marketsimulator.Assets.Currency;
import put.oop.marketsimulator.Users.Company;

/**
 *
 * @author roboc
 */
public class CurrencyMarket extends Market {
    private List<Asset> assets;
    private List<Float> amounts;

    /**
     * Generates fields of Currency Market
     */
    public CurrencyMarket() {
        super();
        this.assets = new ArrayList<>();
        this.amounts = new ArrayList<>();
    }

    /**
     * Add to Market amount b of Currency a
     * @param a
     * @param b
     */
    @Override
    public void addAsset(Asset a,Float b) {
        assets.add((Currency) a);
        amounts.add(b);
        
    }
    
    /**
     * Remove Currency with index a 
     * @param a
     */
    @Override
    public void deleteAsset(int a) {
        assets.remove(a);
        amounts.remove(a);
        
    }

    /**
     *  
     * @return list of Currencies 
     */
    @Override
    public List<Asset> getAssets() {
        return assets;
    }

    /**
     *  
     * @return list of Currencies amounts
     */
    @Override
    public List<Float> getAmounts() {
        return amounts;
    }

    /**
     * 
     * @return Short description of Commodity Market
     */
    @Override
    public String toString() {
        return "Currency Market "+super.toString();
    }
    
    /**
     *
     * @return Extended description of Commodity Market
     */
    @Override
    public String longString() {
        String nam="Currency Market "+super.toString();
        for(int i=0;i<assets.size();i++){
            nam+="\n      Amount: "+amounts.get(i);
            nam+=" of "+assets.get(i);
        }
        nam+="\n";
        return nam;
    }

    /**
     * Decrease by x the number of Currency items under the index asset_idx
     * @param asset_idx
     * @param x
     */
    @Override
    public void decreaceAssetBy(int asset_idx, float x) {
        float before = amounts.get(asset_idx);
        amounts.set(asset_idx, before - x);
    }
    
    /**
     * Increase by x the number of Currency items under the index asset_idx
     * @param asset_idx
     * @param x
     */
    @Override
    public void increaseAssetBy(int asset_idx, float x) {
        float before = amounts.get(asset_idx);
        amounts.set(asset_idx, before + x);
    }

    /**
     * Abstract method used only in case of Stock Market
     * @param a
     * @return false
     */
    @Override
    public boolean containCompany(Company a) {
        return false;
    }
}
