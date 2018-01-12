package com.pos.yza.yzapos.adminoptions.item;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.pos.yza.yzapos.R;
import com.pos.yza.yzapos.data.representations.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemListFragment extends Fragment implements ItemListContract.View {
    private ItemAdapter mListAdapter;
    private ItemListContract.Presenter mPresenter;
    private ArrayAdapter<String> mSpinnerAdapter;
    private ItemListListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (ItemListListener) context;
        } catch (ClassCastException castException) {
            /** The activity does not implement the listener. */
        }
    }

    public ItemListFragment(){

    }

    public static ItemListFragment newInstance(){
        return new ItemListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mListAdapter = new ItemAdapter(new ArrayList<Item>());
        mSpinnerAdapter =
                new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_list_item_1);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(@NonNull ItemListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    public void setUpSpinnerAdapter(List<String> content){
        mSpinnerAdapter.addAll(content);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.fragment_item, container,
                false);

        // Set up the items view
        ListView listView = (ListView) root.findViewById(R.id.list_view);
        listView.setAdapter(mListAdapter);

        FloatingActionButton fab =
                (FloatingActionButton) getActivity().findViewById(R.id.fab_add);
        fab.setImageResource(R.drawable.ic_add2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assert(mPresenter != null);
                mPresenter.addNewItem();
            }
        });

        Spinner spinner = (Spinner) root.findViewById(R.id.spinner);
        // Apply the adapter to the spinner
        spinner.setAdapter(mSpinnerAdapter);

        return root;
    }

    @Override
    public void showItems(List<Item> items) {
        mListAdapter.setList(items);
        mListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showItemDetailsUi(String itemId) {

    }

    @Override
    public void showFilteringPopUpMenu() {

    }

    @Override
    public void showAddItem() {
        listener.addItem();
    }

    @Override
    public void showEditItem() {

    }

    private class ItemAdapter extends BaseAdapter {

        private List<Item> mItems;

        public ItemAdapter(List<Item> items) {
            this.mItems = items;
        }

        @Override
        public int getCount() {
            return mItems.size();
        }

        public void setList(List<Item> items){
            mItems = items;
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

            ImageButton deleteButton = (ImageButton) rowView.findViewById(R.id.button_del_item);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPresenter.deleteItem(item);
                }
            });


            return rowView;
        }
    }

    public interface ItemListListener{
        void addItem();
    }
}
