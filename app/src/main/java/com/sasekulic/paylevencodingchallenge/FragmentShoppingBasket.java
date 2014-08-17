package com.sasekulic.paylevencodingchallenge;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class FragmentShoppingBasket extends Fragment {

    private View mView;
    private ListView mShoppingBasketLV;
    private SettingsManager mSettingsManager;
    private View mShoppingBasketFooter;
    private View mShoppingBasketHeader;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mSettingsManager = MainApplication.instance.getSettingsManager();
        mSettingsManager.getLogger().debug("");
        mView = inflater.inflate(R.layout.fragment_basket, container, false);

        mShoppingBasketLV = (ListView) mView.findViewById(R.id.basket_list);
        mShoppingBasketFooter = inflater.inflate(R.layout.fragment_basket_footer, null);
        mShoppingBasketHeader = inflater.inflate(R.layout.fragment_basket_header, null);
        mShoppingBasketLV.addHeaderView(mShoppingBasketHeader);
        mShoppingBasketLV.addFooterView(mShoppingBasketFooter);
        mShoppingBasketLV.setAdapter(mSettingsManager.getShoppingBasketManager().getShoppingBasketAdapter());

        TextView totalView = (TextView) mShoppingBasketFooter.findViewById(R.id.totalBasketPrice);
        mSettingsManager.getShoppingBasketManager().setTotalView (totalView);

        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mSettingsManager.getShoppingBasketManager().updateTotal(mSettingsManager.getShoppingBasketManager().getShoppingBasketTotal());
    }
}
