/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package put.oop.marketsimulator.Markets;

import java.util.ArrayList;
import java.util.List;

import put.oop.marketsimulator.Assets.Asset;
import put.oop.marketsimulator.Assets.Stock;
import put.oop.marketsimulator.Users.Company;

/**
 *
 * @author roboc
 */
public class StockMarket extends Market{

    private List<Asset> assets;
    private List<Float> amounts;

    /**
     * Generates fields of Stock Market
     */
    public StockMarket() {
        super();
        this.assets = new ArrayList<>();
        this.amounts = new ArrayList<>();
    }

    /**
     * Add to Market amount b of Stock a
     * @param a
     * @param b
     */
    @Override
    public void addAsset(Asset a,Float b) {
        assets.add((Stock) a);
        amounts.add(b);
        
    }
    
    /**
     * Remove Stock with index a 
     * @param a
     */
    @Override
    public void deleteAsset(int a) {
        assets.remove(a);
        amounts.remove(a);
        
    }

    /**
     *  
     * @return list of Stocks
     */
    @Override
    public List<Asset> getAssets() {
        return assets;
    }

    /**
     *   
     * @return list of Stocks amounts
     */
    @Override
    public List<Float> getAmounts() {
        return amounts;
    }

    /**
     * 
     * @return Short description of Stock Market
     */
    @Override
    public String toString() {
        return "Stock Market "+super.toString();
    }
    
    /**
     * 
     * @return Extended description of Stock Market
     */
    @Override
    public String longString() {
        String nam="Stock Market "+super.toString();
        for(int i=0;i<assets.size();i++){
            nam+="\n      Amount: "+ Math.round(amounts.get(i));
            nam+=" of "+assets.get(i);
        }
        nam+="\n";
        return nam;
    }

    /**
     * Decrease by x the number of Stock items under the index asset_idx
     * @param asset_idx
     * @param x
     */
    @Override
    public void decreaceAssetBy(int asset_idx, float x) {
        float before = amounts.get(asset_idx);
        amounts.set(asset_idx, before - x);
    }
    
    /**
     * Increase by x the number of Stock items under the index asset_idx
     * @param asset_idx
     * @param x
     */
    @Override
    public void increaseAssetBy(int asset_idx, float x) {
        float before = amounts.get(asset_idx);
        amounts.set(asset_idx, before + x);
    }

    /**
     * Checks whether Company a has placed its shares on the market
     * @param a
     * @return true if there is the company on the market, else false
     */
    @Override
    public boolean containCompany(Company a) {
        for(Asset asset: assets){
            if(asset.getName().equals(a.getCompanyName())){
                return true;
            }
        }
        return false;
    }
}
