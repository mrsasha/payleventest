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
import com.sasekulic.paylevencodingchallenge.api.data.Product;
import com.sasekulic.paylevencodingchallenge.api.data.ProductCategory;

import java.util.List;

public class FragmentProductsExpandableLVAdapter extends BaseExpandableListAdapter {

    private final List<ProductCategory> productCategories;
    public LayoutInflater inflater;
    public Activity activity;

    public FragmentProductsExpandableLVAdapter(Activity act, List<ProductCategory> categories) {
        activity = act;
        productCategories = categories;
        inflater = act.getLayoutInflater();
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

        TextView productName = (TextView) convertView.findViewById(R.id.productName);
        productName.setText(getChild(groupPosition, childPosition).getName());

        TextView productPrice = (TextView) convertView.findViewById(R.id.productPrice);
        productPrice.setText(Integer.toString(getChild(groupPosition, childPosition).getPrice()));

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, getChild(groupPosition, childPosition).getName(),
                        Toast.LENGTH_SHORT).show();
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
