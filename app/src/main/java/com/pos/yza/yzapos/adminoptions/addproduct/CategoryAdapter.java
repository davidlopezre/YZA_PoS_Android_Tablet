package com.pos.yza.yzapos.adminoptions.addproduct;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.pos.yza.yzapos.data.representations.ProductCategory;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Dlolpez on 11/1/18.
 */

public class CategoryAdapter extends ArrayAdapter<ProductCategory> {
    // Your sent context
    private Context context;
    // Your custom mCategories for the spinner (User)
    private List<ProductCategory> mCategories;

    public CategoryAdapter(Context context, int textViewResourceId,
                       List<ProductCategory> mCategories) {
        super(context, textViewResourceId, mCategories);
        this.context = context;
        setList(mCategories);
    }

    public void replaceData(List<ProductCategory> categories) {
        setList(categories);
        notifyDataSetChanged();
    }

    private void setList(List<ProductCategory> categories) {
        mCategories = checkNotNull(categories);
    }

    @Override
    public int getCount(){
        return mCategories.size();
    }

    @Override
    public ProductCategory getItem(int position){
        return mCategories.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    // And the "magic" goes here
    // This is for the "passive" state of the spinner
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        // Then you can get the current item using the mCategories array (Users array) and the current position
        // You can NOW reference each method you has created in your bean object (User class)
        label.setText(mCategories.get(position).getName());
        // And finally return your dynamic (or custom) view for each spinner item
        return label;
    }

    // And here is when the "chooser" is popped up
    // Normally is the same view, but you can customize it if you want
    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        label.setText(mCategories.get(position).getName());

        return label;
    }
}
