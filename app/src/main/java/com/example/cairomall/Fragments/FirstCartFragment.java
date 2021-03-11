package com.example.cairomall.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.cairomall.Adapters.CartAdapter;
import com.example.cairomall.Databse.CartSqliteDatabase;
import com.example.cairomall.Model.CartItemModel;
import com.example.cairomall.R;

import java.util.ArrayList;

public class FirstCartFragment extends Fragment {

    View view;
    RecyclerView cartRecView;
    TextView tvSum;
    TextView tvNoItems;
    Button btnNext;
    TextView tvTitle;
    ArrayList<CartItemModel> carts;
    Context context;
    CartSqliteDatabase cartsDatabase;
    CartAdapter cartAdapter;
    OnBtnClickedListener listener;
    double sum=0;
    public interface OnBtnClickedListener{
        void nextBtnIsClicked(ArrayList<CartItemModel> carts,double cost);
    }
    public void setOnBtnClickedListener(OnBtnClickedListener listener){
        this.listener=listener;
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context=context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_first_cart, container, false);
        viewsInit();
        recViewInit();
        getCartsFromDB();
        rearrangeLayout();

        return view;
    }
    void getCartsFromDB(){
        cartsDatabase=new CartSqliteDatabase(context);//context or getActivity
        carts=cartsDatabase.getAllCarts();

    }
    void rearrangeLayout(){
        if(carts.size()==0){
            tvNoItems.setVisibility(View.VISIBLE);
            tvSum.setVisibility(View.GONE);
            btnNext.setVisibility(View.GONE);
            cartRecView.setVisibility(View.GONE);
            tvTitle.setVisibility(View.GONE);
        }else{
            cartAdapter.setItems(carts);
            setTvSum();
        }
    }
    void viewsInit(){
        cartRecView=view.findViewById(R.id.First_Fragment_recView);
        tvSum=view.findViewById(R.id.First_Fragment_sum_tv);
        btnNext=view.findViewById(R.id.First_Fragment_btn_next);
        tvNoItems=view.findViewById(R.id.First_Fragment_warning_tv);
        tvTitle=view.findViewById(R.id.First_Fragment_title);
        btnNextInit();
    }
    void recViewInit(){
        cartAdapter=new CartAdapter();
        cartRecView.setAdapter(cartAdapter);
        cartAdapter.setItems(carts);
        cartAdapter.setOnCartDeleteListener(new CartAdapter.OnCartItemDeleteListener() {
            @Override
            public void cartDelete(CartItemModel cart, int position) {
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle("Deleting...")
                        .setMessage("Are you sure you want delete this item ?")
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cartsDatabase.deleteCart(cart.getId());
                        carts=cartsDatabase.getAllCarts();
                        rearrangeLayout();
                    }
                }).create().show();
            }
        });
        cartRecView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));

    }
    void setTvSum(){
        sum=0;
        if(carts.size()>0){
            for(CartItemModel cart:carts){
                sum+=cart.getPrice();
            }
            sum=Math.round(sum*100.0)/100.0;
        }
        tvSum.setText(String.format("sum:%s$", sum));
    }
    void btnNextInit(){
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.nextBtnIsClicked(carts,sum);
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
    }
}