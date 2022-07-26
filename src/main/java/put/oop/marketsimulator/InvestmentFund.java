/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package put.oop.marketsimulator;
import static java.lang.Thread.sleep;
import java.text.DecimalFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author roboc
 */
public class InvestmentFund extends User{
    private String fund_Name;
    private List<User> investors;
    private Wallet wallet;
    private Random rd = new Random();

    /**
     * Generates fields of Investment Fund
     * @param synch
     * @param wrld
     */
    public InvestmentFund(Object synch, World wrld) {
        super(synch,wrld);
        Random rd=new Random();
        
        int len=rd.nextInt(8)+2;
        String chars="abcdefghijklmnopqrstuvwxyz";
        StringBuilder nam=new StringBuilder();
        nam.append(Character.toUpperCase(chars.charAt(rd.nextInt(chars.length()))));
        for(int i=0;i<len;i++)
            nam.append(chars.charAt(rd.nextInt(chars.length())));
        this.fund_Name = nam.toString();
        
        this.wallet = new Wallet((float)100+rd.nextFloat()*1000000);
        
        investors=new ArrayList<>();
        List<User> list = super.getWorld().getUsersList();
        for(int i=0; i<list.size();i++){
            User a = list.get(i);
            float f=(float)3/(list.size()+2);
            if(rd.nextFloat()<f){
                investors.add(a);
            }
        }
    }

    /**
     * 
     * @return Short description of Investment Fund
     */
    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        return "Fund " + fund_Name + ": "+ df.format(wallet.getDolla());
    }

    /**
     * 
     * @return Extended description of Investment Fund
     */
    @Override
    public String longString() {
        return "InvestmentFund " + fund_Name + "\nOwners " + super.longString() + "\nwallet:\n" + wallet.longString();
    }

    /**
     *- It selects a random asset from one of the markets,
     * generates a random value to add to the wallet 
     * (the selection of this value must take into account 
     * the amount of a given currency in the market,
     * investor's cash capabilities and the price of asset)
     * (additionally for stock it has to be an integer 
     * because we can't buy just a fraction of stock)
     * - reduces the amount of asset on the market
     * - adds appropriate amount of asset to wallet 
     * - updates the price of the asset based on how much 
     * of the asset was bought from the market
     * - updates AssetListModel and UserListModel
     */
    public void purchaseAsset(){
        List<Market> markets = super.getWorld().getMarketList();
        int nr_of_markets = markets.size();
        int market_idx = rd.nextInt(nr_of_markets);
        Market choosen_market = markets.get(market_idx);
        
        List<Asset> asset_list = choosen_market.getAssets();
        int nr_of_assets = asset_list.size();
        if(nr_of_assets > 0){
            int asset_idx = rd.nextInt(nr_of_assets);
            Asset choosen_asset = asset_list.get(asset_idx);
            float amount_on_market = choosen_market.getAmounts().get(asset_idx);
            float price_of_asset = choosen_asset.getBuyPrice();
            float users_dollars = wallet.getDolla();
            float buy_this_much = this.some_value(amount_on_market, users_dollars / price_of_asset, price_of_asset);
            if("Stock".equals(choosen_asset.getClass().getSimpleName())){
                buy_this_much = (float) Math.floor(buy_this_much);
            }
            choosen_market.decreaceAssetBy(asset_idx, buy_this_much);
            
            wallet.buyAsset(choosen_asset, buy_this_much);
            
            int user_idx = super.getWorld().getUserListModel().indexOf(this);
            super.getWorld().getUserListModel().setElementAt(this, user_idx);
            
            float percantage_change;
            if(buy_this_much==0 || amount_on_market==0){
                percantage_change = 0;
            }else{
                percantage_change = buy_this_much / (amount_on_market + buy_this_much);
            }
            choosen_asset.change_price(percantage_change);
            
            int asset_model_idx = super.getWorld().getAssetListModel().indexOf(choosen_asset);
            super.getWorld().getAssetListModel().setElementAt(choosen_asset, asset_model_idx);
        }
    }
    
    /**
     *- It selects a random asset from wallet,
     * generates a random amount to sell 
     * (additionally for stock it has to be an integer 
     * because we can't buy just a fraction of stock)
     * - ramoves appropriate amount of asset from wallet 
     * - increases the amount of asset on the market
     * - updates the price of the asset based on how much 
     * of the asset was sold to the market
     * - updates AssetListModel and UserListModel
     */
    public void sellAsset(){
        if(wallet.getAssets().size() > 1){
            int asset_idx = rd.nextInt(1, wallet.getAssets().size() );
            Asset choosen_asset = wallet.getAssets().get(asset_idx);
            float amount_in_wallet = wallet.getAmounts().get(asset_idx);
            float amount_to_sell = rd.nextFloat() * amount_in_wallet;
            if( rd.nextFloat() > 0.9){
                amount_to_sell = amount_in_wallet;
            }
            if("Stock".equals(choosen_asset.getClass().getSimpleName())){
                amount_to_sell = (float) Math.floor(amount_to_sell);
                choosen_asset.add_total_sales(amount_to_sell * choosen_asset.getSellPrice());
            }
            wallet.sellAsset(choosen_asset , amount_to_sell);

            List<Market> markets = super.getWorld().getMarketList();
            for (Market market: markets){
                int idx = market.getAssets().indexOf(choosen_asset);
                
                if(idx >= 0){
                    market.increaseAssetBy(idx, amount_to_sell);
                    float amount_on_market = market.getAmounts().get(idx);
                    float percantage_change;
                    if(amount_to_sell==0 || amount_on_market==0){
                        percantage_change = 0;
                    }else{
                        percantage_change = (float)1.03*amount_to_sell / (amount_on_market + amount_to_sell);
                    }

                    choosen_asset.change_price(-percantage_change);
                    int asset_model_idx = super.getWorld().getAssetListModel().indexOf(choosen_asset);
                    super.getWorld().getAssetListModel().setElementAt(choosen_asset, asset_model_idx);
                    break;
                }
            }
            int user_idx = super.getWorld().getUserListModel().indexOf(this);
            super.getWorld().getUserListModel().setElementAt(this, user_idx);
            
            
        }  
    }

    /**
     * Add x dollars to wallet
     * @param x
     */
    public void transferMoneyToWallet(float x){
        wallet.getAmounts().set(0, wallet.getAmounts().get(0) + x);
    }
    
    /**
     *In the loop, it buys and sells assets 
     * and sometimes increases the investor's budget by a random amount of dollars
     * Additionally, it pays some of the money for some of the investors
     */
    @Override
    public void run() {
        while(true){
            try {
                sleep(1000/(super.getWorld().getNr_of_transactions()+1));
            } catch (InterruptedException ex) {
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
            synchronized(super.getLock()){
                this.purchaseAsset();
            }
            synchronized(super.getLock()){
                this.sellAsset();
            }
            moneyForInvestors();
            
            if(rd.nextFloat()>0.999){
                transferMoneyToWallet(rd.nextFloat()*10000);
            }
            if(super.getWorld().isFinish()){
                break;
            }
        }
        
    }
    
    /**
     *  
     * @return Wallet
     */
    public Wallet getWallet() {
        return wallet;
    }
    
    private void moneyForInvestors() {
        int n = investors.size();
        float amountToGive = wallet.getDolla()/1000/n;
        for(User user: investors){
            Wallet w = user.getWallet();
            w.setDolla(amountToGive + w.getDolla());
        }
    }
}
