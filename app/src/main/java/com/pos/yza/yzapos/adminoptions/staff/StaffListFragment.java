package com.pos.yza.yzapos.adminoptions.staff;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.pos.yza.yzapos.R;
import com.pos.yza.yzapos.data.representations.Staff;

import java.util.ArrayList;
import java.util.List;

public class StaffListFragment extends Fragment implements StaffListContract.View {
    private StaffAdapter mListAdapter;
    private StaffListContract.Presenter mPresenter;
    private StaffListListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (StaffListFragment.StaffListListener) context;
        } catch (ClassCastException castException){

        }
    }

    public StaffListFragment(){

    }

    public static StaffListFragment newInstance(){
        return new StaffListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mListAdapter = new StaffAdapter(new ArrayList<Staff>());
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(@NonNull StaffListContract.Presenter presenter) {
        mPresenter = presenter;
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
                mPresenter.addStaffMember();
            }
        });

        Spinner spinner = (Spinner) root.findViewById(R.id.spinner);
        // Apply the adapter to the spinner
//        spinner.setAdapter(mSpinnerAdapter);

        return root;
    }

    @Override
    public void showStaff(List<Staff> staffList) {
        mListAdapter.setList(staffList);
        mListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showAddStaffMember() {
        listener.addStaff();
    }

    @Override
    public void showEditStaffMember() {

    }

    @Override
    public void showDeleteStaffMember() {

    }

    private class StaffAdapter extends BaseAdapter {

        private List<Staff> mStaffList;

        public StaffAdapter(List<Staff> staffList) {
            this.mStaffList = staffList;
        }

        @Override
        public int getCount() {
            return mStaffList.size();
        }

        public void setList(List<Staff> staffList){
            mStaffList = staffList;
        }

        @Override
        public Staff getItem(int i) {
            return mStaffList.get(i);
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
            final Staff staffMember = getItem(i);

            TextView titleTV = (TextView) rowView.findViewById(R.id.title);
            titleTV.setText(staffMember.getName());

            ImageButton deleteButton = (ImageButton) rowView.findViewById(R.id.button_del_item);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPresenter.deleteStaffMember(staffMember);
                }
            });


            return rowView;
        }
    }

    public interface StaffListListener{
        void addStaff();
    }
}
