package com.sasekulic.paylevencodingchallenge.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.sasekulic.paylevencodingchallenge.R;
import com.sasekulic.paylevencodingchallenge.SettingsManager;
import com.sasekulic.paylevencodingchallenge.api.data.Product;
import com.sasekulic.paylevencodingchallenge.api.data.ProductCategory;

import java.util.List;

public class FragmentProductsExpandableLVAdapter extends BaseExpandableListAdapter {

    private final List<ProductCategory> productCategories;
    private final SettingsManager mSettingsManager;
    public LayoutInflater inflater;
    public Activity activity;

    public FragmentProductsExpandableLVAdapter(SettingsManager mgr, List<ProductCategory> categories) {
        mSettingsManager = mgr;
        activity = mgr.getParentActivity();
        productCategories = categories;
        inflater = activity.getLayoutInflater();
    }

    @Override
    public Product getChild(int groupPosition, int childPosition) {
        return productCategories.get(groupPosition).getRealProducts().get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fragment_products_product_item, null);
        }

        final Product child = getChild(groupPosition, childPosition);

        TextView productName = (TextView) convertView.findViewById(R.id.productName);
        productName.setText(child.getName());

        TextView productPrice = (TextView) convertView.findViewById(R.id.productPrice);
        productPrice.setText(Integer.toString(child.getPrice()));

        TextView productQuantityInBasket = (TextView) convertView.findViewById(R.id.productQuantityInBasket);
        productQuantityInBasket.setText(Integer.toString(mSettingsManager.getShoppingBasketManager().getQuantityForProduct(child)));

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, getChild(groupPosition, childPosition).getName() + " added to basket!",
                        Toast.LENGTH_SHORT).show();
                int quantity = mSettingsManager.getShoppingBasketManager().addItemToBasket(getChild(groupPosition, childPosition));
                TextView productQuantityInBasket = (TextView) v.findViewById(R.id.productQuantityInBasket);
                productQuantityInBasket.setText(Integer.toString(mSettingsManager.getShoppingBasketManager().getQuantityForProduct(child)));
            }
        });
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return productCategories.get(groupPosition).getRealProducts().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return productCategories.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return productCategories.size();
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fragment_products_category_item, null);
        }
        ProductCategory group = (ProductCategory) getGroup(groupPosition);
        CheckedTextView categoryName = (CheckedTextView) convertView.findViewById(R.id.categoryName);
        categoryName.setText(group.getName());
        categoryName.setChecked(isExpanded);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
