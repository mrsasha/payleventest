package com.sasekulic.paylevencodingchallenge.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.sasekulic.paylevencodingchallenge.R;
import com.sasekulic.paylevencodingchallenge.SettingsManager;
import com.sasekulic.paylevencodingchallenge.api.data.Product;

import java.util.List;

public class FragmentBasketLVAdapter extends BaseAdapter {

    private final SettingsManager mSettingsManager;
    private List<Product> mBasketItems;

    static class ViewHolder {
        public TextView productName;
        public TextView productPrice;
        public TextView productQuantity;
    }

    public FragmentBasketLVAdapter(SettingsManager mgr, List<Product> basketItems) {
        mSettingsManager = mgr;
        mBasketItems = basketItems;
    }

    @Override
    public int getCount() {
        return mBasketItems.size();
    }

    @Override
    public Product getItem(int position) {
        return mBasketItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        // reuse views
        if (rowView == null) {
            LayoutInflater inflater = mSettingsManager.getParentActivity().getLayoutInflater();
            rowView = inflater.inflate(R.layout.fragment_basket_product_item, null);
            // configure view holder
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.productName = (TextView) rowView.findViewById(R.id.productName);
            viewHolder.productPrice = (TextView) rowView.findViewById(R.id.productPrice);
            viewHolder.productQuantity = (TextView) rowView.findViewById(R.id.productQuantityInBasket);
            rowView.setTag(viewHolder);
        }

        // fill data
        ViewHolder holder = (ViewHolder) rowView.getTag();
        holder.productName.setText(getItem(position).getName());
        holder.productPrice.setText(Integer.toString(getItem(position).getPrice()));
        holder.productQuantity.setText(Integer.toString(mSettingsManager.getShoppingBasketManager().getQuantityForProduct(getItem(position))));

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mSettingsManager.getParentActivity(), getItem(position).getName() + " removed from basket!",
                        Toast.LENGTH_SHORT).show();
                int quantity = mSettingsManager.getShoppingBasketManager().deleteItemFromBasket(getItem(position));
//                TextView productQuantityInBasket = (TextView) v.findViewById(R.id.productQuantityInBasket);
//                productQuantityInBasket.setText(Integer.toString(mSettingsManager.getShoppingBasketManager().getQuantityForProduct(child)));
            }
        });

        return rowView;
    }

}
