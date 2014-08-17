package com.sasekulic.paylevencodingchallenge;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;

import com.sasekulic.paylevencodingchallenge.adapters.FragmentProductsExpandableLVAdapter;
import com.sasekulic.paylevencodingchallenge.api.data.Product;
import com.sasekulic.paylevencodingchallenge.api.data.ProductCategory;
import com.sasekulic.paylevencodingchallenge.api.responses.PaylevenTestResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.experimental.Accessors;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

@Accessors(prefix = "m")
public class FragmentProducts extends Fragment {

    private SettingsManager mSettingsManager;
    @Getter private View mView;
    @Getter private ProgressBar mLoadingProgressBar;
    @Getter private ExpandableListView mCategoriesAndProductsLV;
    private Subscription mProductsSubscription;
    @Getter private Map<String, Product> mIndexedProducts;
    @Getter private List<ProductCategory> mCategoriesWithProducts;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mSettingsManager = MainApplication.instance.getSettingsManager();
        mSettingsManager.getLogger().debug("");
        mView = inflater.inflate(R.layout.fragment_products, container, false);
        mLoadingProgressBar = (ProgressBar) mView.findViewById(R.id.loading_products_progress);
        mCategoriesAndProductsLV = (ExpandableListView) mView.findViewById(R.id.products_list);

        if (mCategoriesWithProducts!=null) {
            FragmentProductsExpandableLVAdapter adapter = new FragmentProductsExpandableLVAdapter(mSettingsManager, mCategoriesWithProducts);
            mCategoriesAndProductsLV.setAdapter(adapter);
        } else
            startLoadingProductsAndCategories ();

        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSettingsManager.getLogger().debug("");
    }

    private void startLoadingProductsAndCategories () {

        mLoadingProgressBar.setVisibility(View.VISIBLE);
        mCategoriesAndProductsLV.setVisibility(View.GONE);

        mProductsSubscription = mSettingsManager.getPaylevenApiService().getTestItems()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<PaylevenTestResponse, Boolean>() {
                    @Override
                    public Boolean call(PaylevenTestResponse paylevenTestResponse) {
                        try {
                            setIndexedProducts(paylevenTestResponse.getProducts());
                            mCategoriesWithProducts = new ArrayList<ProductCategory>();

                            for (ProductCategory category : paylevenTestResponse.getCategories()) {
                                category.setRealProducts(getIndexedProducts());
                                mCategoriesWithProducts.add(category);
                            }
                            Collections.sort(mCategoriesWithProducts);
                            return mCategoriesWithProducts.size() > 0;
                        } catch (Exception e) {
                            return false;
                        }
                    }
                })
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onCompleted() { }

                    @Override
                    public void onError(Throwable e) {
                        mLoadingProgressBar.setVisibility(View.GONE);
                        mCategoriesAndProductsLV.setVisibility(View.GONE);
                        //TODO error mgmt!
                    }

                    @Override
                    public void onNext(Boolean result) {
                        if (result) {
                            mLoadingProgressBar.setVisibility(View.GONE);
                            mCategoriesAndProductsLV.setVisibility(View.VISIBLE);
                            if (mCategoriesWithProducts!=null) {
                                FragmentProductsExpandableLVAdapter adapter = new FragmentProductsExpandableLVAdapter(mSettingsManager, mCategoriesWithProducts);
                                mCategoriesAndProductsLV.setAdapter(adapter);
                            }
                        } else {
                            mLoadingProgressBar.setVisibility(View.GONE);
                            mCategoriesAndProductsLV.setVisibility(View.GONE);
                            //TODO error mgmt!
                        }
                    }
                });
    }

    private void setIndexedProducts (List<Product> productList) {
        mIndexedProducts = new HashMap<String, Product>();
        for (Product product : productList) {
            mIndexedProducts.put(product.getId(), product);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mProductsSubscription != null) {
            mProductsSubscription.unsubscribe();
        }
    }
}
