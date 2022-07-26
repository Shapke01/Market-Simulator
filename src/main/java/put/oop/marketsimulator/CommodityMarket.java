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
public class CommodityMarket extends Market {
    private List<Commodity> assets;
    private List<Float> amounts;

    /**
     * Generates fields of Commodity Market
     */
    public CommodityMarket() {
        super();
        this.assets = new ArrayList();
        this.amounts = new ArrayList();
    }
    
    /**
     *  
     * @return list of Commodities 
     */
    @Override
    public List getAssets() {
        return assets;
    }

    /**
     *  
     * @return list of Commodities amounts
     */
    @Override
    public List<Float> getAmounts() {
        return amounts;
    }

    /**
     * Add to Market amount b of Commodity a
     * @param a
     * @param b
     */
    @Override
    public void addAsset(Asset a,Float b) {
        assets.add((Commodity) a);
        amounts.add(b);
        
    }
    
    /**
     * Remove Commodity with index a 
     * @param a
     */
    @Override
    public void deleteAsset(int a) {
        assets.remove(a);
        amounts.remove(a);
        
    }

    /**
     * 
     * @return Short description of Commodity Market
     */
    @Override
    public String toString() {
        return "Commodity Market "+super.toString();
    }
    
    /**
     * 
     * @return Extended description of Commodity Market
     */
    @Override
    public String longString() {
        String nam="Commodity Market "+super.toString();
        for(int i=0;i<assets.size();i++){
            nam+="\n      Amount: "+amounts.get(i);
            nam+=" of "+assets.get(i);
        }
        nam+="\n";
        return nam;
    }

    /**
     * Decrease by x the number of Commodity items under the index asset_idx
     * @param asset_idx
     * @param x
     */
    @Override
    public void decreaceAssetBy(int asset_idx, float x) {
        float before = amounts.get(asset_idx);
        amounts.set(asset_idx, before - x);
    }
    
    /**
     * Increase by x the number of Commodity items under the index asset_idx
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