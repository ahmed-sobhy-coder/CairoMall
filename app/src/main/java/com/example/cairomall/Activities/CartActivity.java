package com.example.cairomall.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.cairomall.Databse.CartSqliteDatabase;
import com.example.cairomall.Fragments.FirstCartFragment;
import com.example.cairomall.Fragments.SecondCartFragment;
import com.example.cairomall.Fragments.ThirdCartFragment;
import com.example.cairomall.Model.CartItemModel;
import com.example.cairomall.Model.GroceryItemModel;
import com.example.cairomall.Model.OrderModel;
import com.example.cairomall.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    Toolbar cartActivityToolBar;
    BottomNavigationView bottomNavMenu;
    FirstCartFragment firstCartFragment;
    SecondCartFragment secondCartFragment;
    ThirdCartFragment thirdCartFragment;
    CartSqliteDatabase db;
    static final String CART_ITEM="add item to cart";
    static final String FROM_KEY="activity";
    double cost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        viewInit();
        toolbarInit();
        setBottomNavigationMenu();
        Intent intent=getIntent();
        db=new CartSqliteDatabase(this);
        if(intent!=null){
            String activityName=intent.getStringExtra(FROM_KEY);
            if(activityName==null){
                    GroceryItemModel groceryItem= intent.getParcelableExtra(CART_ITEM);
                    db.insertIntoDB(new CartItemModel(groceryItem.getId(),groceryItem.getName(),groceryItem.getPrice(),groceryItem.getAvailableAmount()));
            }
        }
    }

    void toolbarInit(){
        setSupportActionBar(cartActivityToolBar);
    }
    void viewInit(){
        cartActivityToolBar=findViewById(R.id.Cart_Activity_Toolbar);
        bottomNavMenu=findViewById(R.id.Cart_Activity_bottom_nav_view);

        firstCartFragment=new FirstCartFragment();
        secondCartFragment =new SecondCartFragment();
        thirdCartFragment= new ThirdCartFragment();
        FragmentTransaction transaction= getSupportFragmentManager().beginTransaction();
        transaction.addToBackStack(null);
        transaction.replace(R.id.Cart_Activity_container,firstCartFragment);
        transaction.commit();
        firstCartFragment.setOnBtnClickedListener(new FirstCartFragment.OnBtnClickedListener() {
            @Override
            public void nextBtnIsClicked(ArrayList<CartItemModel> carts, double cost) {
                FragmentTransaction transaction= getSupportFragmentManager().beginTransaction();
                transaction.addToBackStack("first fragment");
                transaction.replace(R.id.Cart_Activity_container,secondCartFragment);
                Bundle bundle= new Bundle();
                bundle.putParcelableArrayList(SecondCartFragment.CARTS_KEY,carts);
                bundle.putDouble(SecondCartFragment.TOTAL_COST_KEY,cost);
                secondCartFragment.setArguments(bundle);
                transaction.commit();
            }
        });
        secondCartFragment.setOnBtnClickedListener(new SecondCartFragment.OnBtnClickedListener() {
            @Override
            public void nextBtnIsClicked(String order) {
                FragmentTransaction transaction= getSupportFragmentManager().beginTransaction();
                transaction.addToBackStack("second fragment");
                transaction.replace(R.id.Cart_Activity_container,thirdCartFragment);
                Bundle bundle=new Bundle();
                bundle.putString(ThirdCartFragment.ORDER_KEY,order);
                thirdCartFragment.setArguments(bundle);
                transaction.commit();

            }

            @Override
            public void backBtnIsClicked() {
              getSupportFragmentManager().popBackStack();
            }
        });
        thirdCartFragment.setOnBtnClickedListener(new ThirdCartFragment.OnBtnClickedListener() {
            @Override
            public void checkoutBtnIsClicked(OrderModel order, String paymentMethod) {
                // send your request to retrofit
              if(paymentMethod.equalsIgnoreCase("PayPal")){

              }else if(paymentMethod.equalsIgnoreCase("Credit Card")){

              }
              //cart
            }

            @Override
            public void backBtnIsClicked() {
/*                FragmentTransaction transaction= getSupportFragmentManager().beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.Cart_Activity_container,secondCartFragment);
                Bundle bundle= new Bundle();
                bundle.putParcelableArrayList(SecondCartFragment.CARTS_KEY,carts);
                bundle.putDouble(SecondCartFragment.TOTAL_COST_KEY,cost);
                secondCartFragment.setArguments(bundle);
                transaction.commit();*/
                Toast.makeText(CartActivity.this,"clickec",Toast.LENGTH_LONG).show();
                getSupportFragmentManager().popBackStack();

            }
        });
    }
    void setBottomNavigationMenu(){
        bottomNavMenu.setSelectedItemId(R.id.bottom_cart_item);
        bottomNavMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent=null;
                switch (item.getItemId()){
                    case R.id.bottom_home_item:
                        intent=new Intent(CartActivity.this,MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;
                    case R.id.bottom_search_item:
                        intent=new Intent(CartActivity.this,SearchActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });
    }
}