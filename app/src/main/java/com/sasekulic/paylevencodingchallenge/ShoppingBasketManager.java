package com.sasekulic.paylevencodingchallenge;

import android.widget.TextView;

import com.google.gson.internal.LinkedTreeMap;
import com.sasekulic.paylevencodingchallenge.adapters.FragmentBasketLVAdapter;
import com.sasekulic.paylevencodingchallenge.api.data.Product;

import java.util.ArrayList;
import java.util.Map;

import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(prefix="m")
public class ShoppingBasketManager {

    private final SettingsManager mSettingsManager;
    private final ArrayList<Product> mShoppingBasketItemArray;
    @Getter private FragmentBasketLVAdapter mShoppingBasketAdapter;
    private Map<Product, Integer> mShoppingBasketItems;
    @Getter private int mShoppingBasketTotal = 0;
    private TextView mTotalView;

    public ShoppingBasketManager(SettingsManager mgr) {
        mSettingsManager = mgr;
        mShoppingBasketItems = new LinkedTreeMap<Product, Integer>();
        mShoppingBasketItemArray = new ArrayList<Product>();
        mShoppingBasketAdapter = new FragmentBasketLVAdapter(mSettingsManager, mShoppingBasketItemArray);
    }

    public void setTotalView (TextView totalView) {
        mTotalView = totalView;
    }

    public void updateTotal (int total) {
        mSettingsManager.getLogger().info("total = " + mShoppingBasketTotal);
        mShoppingBasketTotal = total;
        if (mTotalView!=null)
            mTotalView.setText(Integer.toString(mShoppingBasketTotal));
    }

    public int addItemToBasket(Product product) {
        if (!mShoppingBasketItems.containsKey(product))
            mShoppingBasketItems.put(product, 0);

        int quantity_in_basket = mShoppingBasketItems.get(product);
        quantity_in_basket++;
        mShoppingBasketItems.put(product, quantity_in_basket);
        updateTotal (getShoppingBasketTotal() + product.getPrice());
        mSettingsManager.getLogger().info("added product = " + product.toString() + ", quantity = " + quantity_in_basket + ", total = " + mShoppingBasketTotal);
        updateBasketItemArray();
        return quantity_in_basket;
    }

    public void updateBasketItemArray() {
        mShoppingBasketItemArray.clear();
        mShoppingBasketItemArray.addAll(mShoppingBasketItems.keySet());
        mShoppingBasketAdapter.notifyDataSetChanged();
    }

    public int deleteItemFromBasket(Product product) {
        if (!mShoppingBasketItems.containsKey(product))
            return 0;
        else {
            int quantity_in_basket = mShoppingBasketItems.get(product);
            quantity_in_basket--;
            if (quantity_in_basket > 0)
                mShoppingBasketItems.put(product, quantity_in_basket);
            else
                mShoppingBasketItems.remove(product);
            updateTotal (getShoppingBasketTotal() - product.getPrice());
            mSettingsManager.getLogger().info("removed product = " + product.toString() + ", quantity = " + quantity_in_basket + ", total = " + mShoppingBasketTotal);
            updateBasketItemArray();
            return quantity_in_basket;
        }
    }

    public int getQuantityForProduct (Product product) {
        if (!mShoppingBasketItems.containsKey(product)) {
            mSettingsManager.getLogger().info("product = " + product.toString() + ", NOT in basket!");
            return 0;
        }
        else {
            mSettingsManager.getLogger().info("product = " + product.toString() + ", quantity = " + mShoppingBasketItems.get(product));
            return mShoppingBasketItems.get(product);
        }
    }
}
