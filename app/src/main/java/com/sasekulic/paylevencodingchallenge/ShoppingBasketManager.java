package com.sasekulic.paylevencodingchallenge;

import com.sasekulic.paylevencodingchallenge.api.data.Product;

import java.util.Map;

public class ShoppingBasketManager {

    private final SettingsManager mSettingsManager;
    private Map<Product, Integer> mShoppingBasketItems;

    public ShoppingBasketManager (SettingsManager mgr) {
        mSettingsManager = mgr;
    }
}
