package com.example.cairomall.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cairomall.Databse.CartSqliteDatabase;
import com.example.cairomall.Model.CartItemModel;
import com.example.cairomall.Model.OrderModel;
import com.example.cairomall.R;
import com.google.gson.Gson;

import java.util.ArrayList;


public class SecondCartFragment extends Fragment {
    private EditText edtAddress, edtZipCode, edtPhoneNumber, edtEmail;
    private Button btnNext, btnBack;
    private TextView txtWarning;
    CartSqliteDatabase cartsDatabase;
    Context context;
    View view;
    OnBtnClickedListener listener;
    ArrayList<CartItemModel>carts;
    public static final String TOTAL_COST_KEY="total cost";
    public static final String CARTS_KEY="carts";
    public static final String ORDER_KEY="order";

    double cost;
    public interface OnBtnClickedListener {
        void nextBtnIsClicked(String order);
        void backBtnIsClicked();
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
        View view=inflater.inflate(R.layout.fragment_second_cart, container, false);
        this.view=view;
        initViews();
        Bundle bundle=getArguments();
        if(bundle!=null){
            cost=bundle.getDouble(TOTAL_COST_KEY);
            carts=bundle.getParcelableArrayList(CARTS_KEY);
        }else{
            databaseInit();
        }
        Toast.makeText(getActivity(),"hey started",Toast.LENGTH_LONG).show();
        return view;
    }
    void databaseInit(){
        cartsDatabase=new CartSqliteDatabase(getActivity());
        carts=cartsDatabase.getAllCarts();
    }
    private void initViews() {
        edtAddress = view.findViewById(R.id.Second_Fragment_edtAddress);
        edtZipCode = view.findViewById(R.id.Second_Fragment_edtZipCode);
        edtPhoneNumber = view.findViewById(R.id.Second_Fragment_edtPhoneNumber);
        edtEmail = view.findViewById(R.id.Second_Fragment_edtEmail);
        btnNext = view.findViewById(R.id.Second_Fragment_btnNext);
        btnBack = view.findViewById(R.id.Second_Fragment_btnBack);
        txtWarning = view.findViewById(R.id.Second_Fragment_tvtWarning);
        btnBackInit();
        btnNextInit();
    }
    void btnNextInit(){
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address= edtAddress.getText().toString();
                String phoneNumber=edtPhoneNumber.getText().toString();
                String zipCode =edtZipCode.getText().toString();
                String email=edtEmail.getText().toString();
                if(address.isEmpty()){
                    edtAddress.setError("address is missing");
                    return;
                }else if(phoneNumber.isEmpty()){
                    edtPhoneNumber.setError("phone number is missing");
                    return;
                }else if(zipCode.isEmpty()){
                    edtZipCode.setError("zip code is missing");
                    return;
                }else if(email.isEmpty()){
                    edtEmail.setError("email is missing");
                    return;
                }
                if(carts.size()>0){
                    OrderModel order= new OrderModel( carts,address,zipCode,phoneNumber,email,cost,null,false);
                    Gson gson= new Gson();
                    String jsonOrder= gson.toJson(order);
                      listener.nextBtnIsClicked(jsonOrder);
                }


            }
        });
    }
    void btnBackInit(){
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.backBtnIsClicked();
            }
        });
    }

    @Override
    public void onResume() {

        super.onResume();
    }
}