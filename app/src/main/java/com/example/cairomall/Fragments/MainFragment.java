package com.example.cairomall.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.cairomall.Activities.CartActivity;
import com.example.cairomall.Activities.SearchActivity;
import com.example.cairomall.Model.GroceryItemModel;
import com.example.cairomall.Adapters.GroceryItemsAdapter;
import com.example.cairomall.Databse.GrocerySqliteDatabase;
import com.example.cairomall.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainFragment extends Fragment  {
    private Context mContext;
    private View layout;
    private BottomNavigationView bottomNavigationView;
    RecyclerView mNewItemsRecView,mPopularItemsRecView,mSuggestedItemRecView;
    GroceryItemsAdapter mNewItemsAdapter,mPopularItemsAdapter,mSuggestedItemAdapter;
    GrocerySqliteDatabase mDatabase;
    ArrayList<GroceryItemModel> mNewItems;
    ArrayList<GroceryItemModel> mPopularItems;
    ArrayList<GroceryItemModel> mSuggestedItems;
    GroceryItemModel item;
    int position;
    private OnMainFragmentItemClickedListener mListener;
    public MainFragment() {
        // Required empty public constructor
    }

    public interface OnMainFragmentItemClickedListener{
        void GroceryItemClicked(GroceryItemModel groceryItem,int position);
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener= (OnMainFragmentItemClickedListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        layout=inflater.inflate(R.layout.fragment_main, container, false);
        viewsInit();

        mDatabase=new GrocerySqliteDatabase(container.getContext());

        mNewItemsAdapter=new GroceryItemsAdapter();
        mPopularItemsAdapter=new GroceryItemsAdapter();
        mSuggestedItemAdapter=new GroceryItemsAdapter();
        mNewItemsRecView.setAdapter(mNewItemsAdapter);
        mPopularItemsRecView.setAdapter(mPopularItemsAdapter);
        mSuggestedItemRecView.setAdapter(mSuggestedItemAdapter);
        mNewItemsRecView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        mPopularItemsRecView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        mSuggestedItemRecView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        mNewItemsAdapter.setOnGrocerItemClickListener(new GroceryItemsAdapter.OnGrocerItemClickListener() {
            @Override
            public void clickedItem(GroceryItemModel item, int position) {
                mListener.GroceryItemClicked(item,position);
            }
        });
        mPopularItemsAdapter.setOnGrocerItemClickListener(new GroceryItemsAdapter.OnGrocerItemClickListener() {
            @Override
            public void clickedItem(GroceryItemModel item, int position) {
                mListener.GroceryItemClicked(item,position);
            }
        });
        mSuggestedItemAdapter.setOnGrocerItemClickListener(new GroceryItemsAdapter.OnGrocerItemClickListener() {
            @Override
            public void clickedItem(GroceryItemModel item, int position) {
                mListener.GroceryItemClicked(item,position);
            }
        });
        return layout;
    }

    @Override
    public void onResume() {
        super.onResume();
        mNewItems=mDatabase.getAllItems();
        mNewItemsAdapter.setItems(mNewItems);
        mPopularItems=mDatabase.getAllItems();
        if(mPopularItems!=null){
              /*     Method 2: Using comparator interface- Comparator interface is used to order the
                objects of user-defined class. This interface is present in java.util
                package and contains 2 methods compare(Object obj1, Object obj2) and
                equals(Object element). Using comparator, we can sort the elements
                based on data members. For instance it may be on rollno, name, age or
                anything else.*/

            Comparator<GroceryItemModel> popularItemsComparator=new Comparator<GroceryItemModel>() {
                @Override
                public int compare(GroceryItemModel o1, GroceryItemModel o2) {
                    return o1.getPopularityPoint()-o2.getPopularityPoint();//it will sort ASC
                }
            };
            Collections.sort(mPopularItems, Collections.reverseOrder(popularItemsComparator));//reversed to sort DESC
            mPopularItemsAdapter.setItems(mPopularItems);
        }
        mSuggestedItems=mDatabase.getAllItems();
        if(mSuggestedItems!=null){
            Comparator<GroceryItemModel> suggestedItemsComparator=new Comparator<GroceryItemModel>() {
                @Override
                public int compare(GroceryItemModel o1, GroceryItemModel o2) {
                    return o1.getUserPoint()-o2.getUserPoint();//it will sort ASC
                }
            };
            Collections.sort(mSuggestedItems, Collections.reverseOrder(suggestedItemsComparator));//reversed to sort DESC
            mSuggestedItemAdapter.setItems(mSuggestedItems);
        }

    }

    void viewsInit(){
        mNewItemsRecView=layout.findViewById(R.id.mainFragment_newItemsRecyclerView);
        mPopularItemsRecView=layout.findViewById(R.id.mainFragment_popularItemsRecView);
        mSuggestedItemRecView=layout.findViewById(R.id.mainFragment_suggestedItemsRecView);
    }
}