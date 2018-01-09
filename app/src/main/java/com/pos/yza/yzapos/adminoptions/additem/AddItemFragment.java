package com.pos.yza.yzapos.adminoptions.additem;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.pos.yza.yzapos.R;
import com.pos.yza.yzapos.data.representations.Item;

import java.util.List;

public class AddItemFragment extends DialogFragment implements AddItemContract.View {
    private AddItemContract.Presenter mPresenter;

    public AddItemFragment(){

    }

    public static AddItemFragment newInstance(){
        return new AddItemFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(@NonNull AddItemContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.fragment_add_item, container,
                false);

        Spinner spinner = (Spinner) root.findViewById(R.id.spinner);

        final EditText unitOfMeasure = (EditText) root.findViewById(R.id.unit_of_measure);
        final EditText unitPrice = (EditText) root.findViewById(R.id.unit_price);


        Button button = (Button) root.findViewById(R.id.button_add_item);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("button", "button pressed");
                mPresenter.confirmItem(unitOfMeasure.getText().toString(), unitPrice.getText().toString());
            }
        });

        return root;
    }

    /**
     * Listener for clicks on items in the ListView
     */

    ItemListener mItemListener = new ItemListener() {
        @Override
        public void onItemClick(Item clickedItem) {
            Log.i("Item", "clicked" + clickedItem.getName());
        }
    };

    @Override
    public void showItemProperties() {

    }


    private static class ItemAdapter extends BaseAdapter {

        private List<Item> mItems;
        private ItemListener mItemListener;

        public ItemAdapter(List<Item> items, ItemListener itemListener) {
            this.mItems = items;
            this.mItemListener = itemListener;
        }

        @Override
        public int getCount() {
            return mItems.size();
        }

        @Override
        public Item getItem(int i) {
            return mItems.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View rowView = view;
            if (rowView == null) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                rowView = inflater.inflate(R.layout.list_item,
                        viewGroup, false);
            }
            final Item item = getItem(i);

            TextView titleTV = (TextView) rowView.findViewById(R.id.title);
            titleTV.setText(item.getName());

            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemListener.onItemClick(item);
                }
            });

            return rowView;
        }
    }

    public interface ItemListener {
        void onItemClick(Item clickedItem);
    }
}
