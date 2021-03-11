package com.example.cairomall.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cairomall.Adapters.GroceryItemsAdapter;
import com.example.cairomall.Databse.GrocerySqliteDatabase;
import com.example.cairomall.Dialogs.AllCategoriesDialog;
import com.example.cairomall.Model.GroceryItemModel;
import com.example.cairomall.R;
import com.example.cairomall.Utils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    EditText edtSearch;
    ImageView imgSearch;
    TextView tvFirstCategory;
    TextView tvSecondCategory;
    TextView tvThirdCategory;
    TextView tvFourthCategory;
    TextView tvAllCategory;
    RecyclerView recFoundItems;
    BottomNavigationView bottomNavigationView;
    GroceryItemModel groceryItem;
    Toolbar searchActivityToolBar;
    GroceryItemsAdapter adapter;
    ArrayList<GroceryItemModel> allItems;
    GrocerySqliteDatabase mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
       initViews();
       toolbarInit();
       recViewInit();
        setBottomNavigationMenu();
       imgSearch.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               doSearch();
           }
       });
          edtSearch.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {

           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {
               doSearch();
               if(s.length()==0){
                   adapter.setItems(mDatabase.getAllItems());
               }
           }

           @Override
           public void afterTextChanged(Editable s) {

           }
       });

        tvFirstCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        categoriesSetUp();
        categoryItemFromDrawerIsPressedHandleSetUp();
    }

    private void categoryItemFromDrawerIsPressedHandleSetUp() {
        Intent intent=getIntent();
        if(intent!=null){
            String category=intent.getStringExtra(AllCategoriesDialog.CATEGORY_KEY);
            int position= intent.getIntExtra(AllCategoriesDialog.CATEGORY_POSITION_KEY,-1);
            switch (position){
                case 0:
                    tvFirstCategory.performClick();
                    break;
                case 1:
                    tvSecondCategory.performClick();
                    break;
                case 2:
                    tvThirdCategory.performClick();
                    break;
                case 3:
                    tvFourthCategory.performClick();
                    break;
            }
        }

    }

    void doSearch(){
        String itemName=edtSearch.getText().toString();


        boolean alreadyExists=false;
        if(!itemName.isEmpty()){
            if(allItems!=null){

            }
            ArrayList <GroceryItemModel> foundItems=new ArrayList<>();
            for(GroceryItemModel item:allItems){
                if(itemName.equalsIgnoreCase(item.getName())){
                    isInFoundList(item,foundItems);

                }
                else if((item.getName().length()>=itemName.length())&&item.getName().substring(0,itemName.length()).equalsIgnoreCase(itemName)){
                    isInFoundList(item,foundItems);
                }
                if(foundItems.size()>0){
                    adapter.setItems(foundItems);

                }
            }
        }else{
            adapter.setItems(null);
        }

    }
    boolean isInFoundList(GroceryItemModel item,ArrayList<GroceryItemModel> foundItems){
        boolean alreadyExists=false;
        /*check if it is already exist in found items list*/
        for(GroceryItemModel foundItem:foundItems){
            if(foundItem.getId()==item.getId()){
                alreadyExists=true;
                break;
            }
        }
        if(alreadyExists==false){
            foundItems.add(item);
        }
        return alreadyExists;
    }
    void toolbarInit(){
        setSupportActionBar(searchActivityToolBar);
    }
    void recViewInit(){
        adapter=new GroceryItemsAdapter();
        recFoundItems.setAdapter(adapter);
        recFoundItems.setLayoutManager(new GridLayoutManager(this,2));
        adapter.setOnGrocerItemClickListener(new GroceryItemsAdapter.OnGrocerItemClickListener() {
            @Override
            public void clickedItem(GroceryItemModel item, int position) {
                Intent intent=new Intent(SearchActivity.this,GroceryItemActivity.class);
                intent.putExtra(GroceryItemActivity.POSITION_EXTRA,position);
                intent.putExtra(GroceryItemActivity.ITEM_EXTRA,(Parcelable) item);
                startActivity(intent);
            }
        });
        adapter.setItems(allItems);
    }
    void initViews(){
        searchActivityToolBar=findViewById(R.id.Search_Activity_Toolbar);
        recFoundItems=findViewById(R.id.Search_Activity_rec_view);
        edtSearch=findViewById(R.id.Search_Activity_edt_search);
        imgSearch=findViewById(R.id.Search_Activity_img);
        tvFirstCategory=findViewById(R.id.Search_Activity_tv_first_category);
        tvSecondCategory=findViewById(R.id.Search_Activity_tv_second_category);
        tvThirdCategory=findViewById(R.id.Search_Activity_tv_third_category);
        tvFourthCategory=findViewById(R.id.Search_Activity_tv_fourth_category);
        tvAllCategory=findViewById(R.id.Search_Activity_tv_all_categories);
        bottomNavigationView=findViewById(R.id.Search_Activity_bottom_nav_view);
        mDatabase=new GrocerySqliteDatabase(SearchActivity.this);
        allItems=mDatabase.getAllItems();
    }
    void categoriesSetUp(){
        ArrayList<String> categories = Utils.getCategories(this);
        ArrayList<GroceryItemModel> items=mDatabase.getAllItems();
        ArrayList<GroceryItemModel> foundItems=new ArrayList<>();
        tvFirstCategory.setText(categories.get(0));
        tvFirstCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetAllCategoriesTxtColors();
                tvFirstCategory.setPaintFlags(tvFirstCategory.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
                tvFirstCategory.setTextColor(getResources().getColor(R.color.common_yellow));
                for(GroceryItemModel item:allItems){
                    adapter.setItems(Utils.getItemsByCategory(SearchActivity.this,categories.get(0)));
                }
            }
        });
        tvSecondCategory.setText(categories.get(1));
        tvSecondCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetAllCategoriesTxtColors();
                tvSecondCategory.setPaintFlags(tvSecondCategory.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
                tvSecondCategory.setTextColor(getResources().getColor(R.color.common_yellow));
                for(GroceryItemModel item:allItems){
                    adapter.setItems(Utils.getItemsByCategory(SearchActivity.this,categories.get(1)));
                }
            }
        });
        tvThirdCategory.setText(categories.get(2));
        tvThirdCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetAllCategoriesTxtColors();
                tvThirdCategory.setPaintFlags(tvThirdCategory.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
                tvThirdCategory.setTextColor(getResources().getColor(R.color.common_yellow));
                for(GroceryItemModel item:allItems){
                    adapter.setItems(Utils.getItemsByCategory(SearchActivity.this,categories.get(2)));
                }
            }
        });
        tvFourthCategory.setText(categories.get(3));
        tvFourthCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetAllCategoriesTxtColors();
                tvFourthCategory.setPaintFlags(tvFourthCategory.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
                tvFourthCategory.setTextColor(getResources().getColor(R.color.common_yellow));
                for(GroceryItemModel item:allItems){
                    adapter.setItems(Utils.getItemsByCategory(SearchActivity.this,categories.get(3)));
                }
            }
        });
        tvAllCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllCategoriesDialog allCategoriesDialog=new AllCategoriesDialog();
                Bundle bundle=new Bundle();
                bundle.putStringArrayList(AllCategoriesDialog.CATEGORIES_KEY,categories);
                allCategoriesDialog.setArguments(bundle);
                allCategoriesDialog.setOnCategoryClickListener(new AllCategoriesDialog.OnCategoryClickListener() {
                    @Override
                    public void categoryClicked(String category,int position) {
                        switch (position){
                            case 0:
                                tvFirstCategory.performClick();
                                break;
                            case 1:
                                tvSecondCategory.performClick();
                                break;
                            case 2:
                                tvThirdCategory.performClick();
                                break;
                            case 3:
                                tvFourthCategory.performClick();
                                break;
                        }
                        allCategoriesDialog.dismiss();
                    }
                });
                allCategoriesDialog.show(getSupportFragmentManager(),"categories dialog");
            }
        });
    }
    void resetAllCategoriesTxtColors(){
        /*disable underline for all text views*/
        tvFirstCategory.setPaintFlags(tvFourthCategory.getPaintFlags() ^  Paint.LINEAR_TEXT_FLAG);
        tvSecondCategory.setPaintFlags(tvFourthCategory.getPaintFlags() ^ Paint.LINEAR_TEXT_FLAG);
        tvThirdCategory.setPaintFlags(tvFourthCategory.getPaintFlags() ^  Paint.LINEAR_TEXT_FLAG);
        tvFourthCategory.setPaintFlags(tvFourthCategory.getPaintFlags() ^ Paint.LINEAR_TEXT_FLAG);
        /*change text color to white for all textviews*/
        tvFirstCategory.setTextColor(getResources().getColor(R.color.white));
        tvSecondCategory.setTextColor(getResources().getColor(R.color.white));
        tvThirdCategory.setTextColor(getResources().getColor(R.color.white));
        tvFourthCategory.setTextColor(getResources().getColor(R.color.white));

    }
    void setBottomNavigationMenu(){
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent=null;
                switch (item.getItemId()){
                    case R.id.bottom_home_item:
                        intent=new Intent(SearchActivity.this,MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;
                    case R.id.bottom_cart_item:
                        intent=new Intent(SearchActivity.this,CartActivity.class);
                        intent.putExtra(CartActivity.FROM_KEY,"search");
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;
                }

                return true;
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(SearchActivity.this,MainActivity.class);
        startActivity(intent);
    }
}