package com.example.cairomall.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.MenuItem;

import com.example.cairomall.Dialogs.AllCategoriesDialog;
import com.example.cairomall.Fragments.MainFragment;
import com.example.cairomall.Model.GroceryItemModel;
import com.example.cairomall.Databse.GrocerySqliteDatabase;
import com.example.cairomall.R;
import com.example.cairomall.Utils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,MainFragment.OnMainFragmentItemClickedListener {
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    Toolbar MainActivityToolbar;
    Fragment mFragment;
    GrocerySqliteDatabase mDatabase;
    BottomNavigationView btmNavView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainActivityToolbar=(Toolbar)findViewById(R.id.toolbar);
        mNavigationView=(NavigationView)findViewById(R.id.nav_view);
        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer);
        btmNavView=findViewById(R.id.Main_Activity_bottom_nav_view);
        navigationDrawerSetup();
        setBottomNavigationMenu();
        mNavigationView.setNavigationItemSelectedListener(this);
        mFragment= new MainFragment();
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container,mFragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.addToBackStack(null);
        transaction.commit();
        mDatabase= new GrocerySqliteDatabase(this);
        mDatabase.clearTable(); //try and catch
        mDatabase.insertListIntoDB(Utils.allItemsInit());

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        switch (item.getItemId()){
            case R.id.cart_item:
                Intent intent=new Intent(MainActivity.this, CartActivity.class);
                intent.putExtra(CartActivity.FROM_KEY,"home");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case R.id.categories_item:
                AllCategoriesDialog allCategoriesDialog=new AllCategoriesDialog();
                Bundle bundle=new Bundle();
                ArrayList<String> categories= Utils.getCategories(this);
                bundle.putStringArrayList(AllCategoriesDialog.CATEGORIES_KEY,categories);
                allCategoriesDialog.setArguments(bundle);
                allCategoriesDialog.setOnCategoryClickListener(new AllCategoriesDialog.OnCategoryClickListener() {
                    @Override
                    public void categoryClicked(String category,int position) {
                        Intent intent =new Intent(MainActivity.this,SearchActivity.class);
                        intent.putExtra(AllCategoriesDialog.CATEGORY_KEY,category);
                        intent.putExtra(AllCategoriesDialog.CATEGORY_POSITION_KEY,position);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                      /*  switch (position){
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
                        }*/
                        allCategoriesDialog.dismiss();
                    }
                });
                allCategoriesDialog.show(getSupportFragmentManager(),"categories dialog");
                break;
            case R.id.about_us_item:

                break;
            case R.id.terms_item:

                break;
            case R.id.licenses_item:

                break;
            default:
                throw new IllegalStateException("Unexpected value: " + item.getItemId());
        }
            transaction.replace(R.id.container,mFragment);
            transaction.commit();
        return true;
    }

    @Override
    public void GroceryItemClicked(GroceryItemModel groceryItem, int position) {
        Intent intent=new Intent(this,GroceryItemActivity.class);
        intent.putExtra(GroceryItemActivity.POSITION_EXTRA,position);
        intent.putExtra(GroceryItemActivity.ITEM_EXTRA,(Parcelable) groceryItem);
        startActivity(intent);
    }
    void navigationDrawerSetup(){
        ActionBarDrawerToggle toggleBtn= new ActionBarDrawerToggle(this,
                mDrawerLayout,MainActivityToolbar,R.string.nav_open_drawer,R.string.nav_close_drawer);
        toggleBtn.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));/*change toggle color to white*/
        mDrawerLayout.addDrawerListener(toggleBtn);
        toggleBtn.syncState();/*it will add animation to toggle btn when drawer is opened or closed*/
    }
    void setBottomNavigationMenu(){
        btmNavView.setSelectedItemId(R.id.bottom_home_item);/*make home item is the default item*/
        btmNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent=null;
                switch (item.getItemId()){
                    case R.id.bottom_search_item:
                        intent=new Intent(MainActivity.this, SearchActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;
                    case R.id.bottom_cart_item:
                        intent=new Intent(MainActivity.this, CartActivity.class);
                        intent.putExtra(CartActivity.FROM_KEY,"home");
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });
    }
}