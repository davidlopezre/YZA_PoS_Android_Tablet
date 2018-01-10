package com.pos.yza.yzapos.adminoptions.item;

import com.pos.yza.yzapos.data.representations.Item;
import com.pos.yza.yzapos.util.BasePresenter;
import com.pos.yza.yzapos.util.BaseView;

import java.util.List;

/**
 * Created by Dlolpez on 31/12/17.
 */

public interface ItemListContract {
    interface View extends BaseView<Presenter> {
        void setUpSpinnerAdapter(List<String> content);
        void showItems(List<Item> items);
        void showItemDetailsUi(String itemId);
        void showFilteringPopUpMenu();
        void showAddItem();
        void showEditItem();
    }

    interface Presenter extends BasePresenter {
        void loadItems();
        void addNewItem();
        void deleteItem(Item item);
        void editItem();
        void openItemDetails();
        void setFiltering();
    }
}
