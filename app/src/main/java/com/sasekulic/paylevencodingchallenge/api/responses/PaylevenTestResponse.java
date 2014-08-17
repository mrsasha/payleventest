package com.sasekulic.paylevencodingchallenge.api.responses;

import com.sasekulic.paylevencodingchallenge.api.data.Product;
import com.sasekulic.paylevencodingchallenge.api.data.ProductCategory;

import java.util.List;

import lombok.Getter;

public class PaylevenTestResponse {

    @Getter List<ProductCategory> categories;
    @Getter List<Product> products;
}
