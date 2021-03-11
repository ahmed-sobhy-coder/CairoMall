package com.example.cairomall.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.cairomall.R;

import java.util.ArrayList;

public class AllCategoriesDialog extends DialogFragment {
    ListView categoriesList;
    public static String CATEGORIES_KEY="categories";
    public static String CATEGORY_KEY="category";
    public static String CATEGORY_POSITION_KEY="category";

    private ArrayList<String> categories;
    ArrayAdapter<String> adapter;
    OnCategoryClickListener mListener;
    public interface OnCategoryClickListener{
        void categoryClicked(String category,int position);
    }
    public void setOnCategoryClickListener(OnCategoryClickListener listener){
        mListener=listener;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater= LayoutInflater.from(getActivity());
        View view=inflater.inflate(R.layout.all_categories_dialog,null,false);
        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        builder.setView(view);
        categoriesList=view.findViewById(R.id.all_categories_dialog);
        Bundle bundle= getArguments();
        if(bundle!=null){
            categories =bundle.getStringArrayList(CATEGORIES_KEY);
            adapter=new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1,categories);
            categoriesList.setAdapter(adapter);
            categoriesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                  mListener.categoryClicked(categories.get(position),position);
                }
            });
        }
        return builder.create();
    }
}
