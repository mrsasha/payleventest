package com.sasekulic.paylevencodingchallenge.api.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import lombok.Getter;

public class ProductCategory implements Comparable<ProductCategory> {

    @Getter public String id;
    @Getter public String name;
    @Getter public List<String> products;
    @Getter public List<Product> realProducts;

    public void setRealProducts (Map<String, Product> indexedProducts) {
        realProducts = new ArrayList<Product>();

        if (products!=null & indexedProducts!= null) {
            for (String product_id : products) {
                realProducts.add(indexedProducts.get(product_id));
            }
        }

        Collections.sort(realProducts);
    }

    @Override
    public int compareTo(ProductCategory another) {
        return this.name.compareTo(another.name);
    }
}
