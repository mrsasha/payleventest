package com.sasekulic.paylevencodingchallenge.api.data;

import lombok.Getter;
import lombok.ToString;

@ToString
public class Product implements Comparable<Product> {

    @Getter public String id;
    @Getter public String name;
    @Getter public int price;

    @Override
    public int compareTo(Product another) {
        return this.name.compareTo(another.name);
    }
}
