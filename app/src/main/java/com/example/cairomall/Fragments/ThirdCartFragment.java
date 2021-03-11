package com.example.cairomall.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cairomall.Databse.CartSqliteDatabase;
import com.example.cairomall.Model.CartItemModel;
import com.example.cairomall.Model.OrderModel;
import com.example.cairomall.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;


public class ThirdCartFragment extends Fragment {
   public static final String ORDER_KEY="order";
    Context context;
    View view;
    private Button btnBack, btnCheckout;
    private TextView tvItems;
    private TextView tvAddress;
    private TextView tvPhoneNumber;
    private TextView tvTotalPrice;
    private RadioGroup rgPayment;
    OnBtnClickedListener listener;
    String jsonOrder;
    OrderModel order;
    CartItemModel carts;
    CartSqliteDatabase db;
    public interface OnBtnClickedListener {
        void checkoutBtnIsClicked(OrderModel order,String paymentMethod);
        void backBtnIsClicked();
    }
    public void setOnBtnClickedListener(OnBtnClickedListener listener){
        this.listener=listener;
    }
    public ThirdCartFragment()
    {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_third_cart, container, false);
        initViews(view);
        Bundle bundle=getArguments();
        if(bundle!=null){
            jsonOrder =bundle.getString(ORDER_KEY);
            Gson gson= new Gson();
            Type type= new TypeToken<OrderModel>(){}.getType(); //it will return class com.example.CairoMoll.Model.OrderModell
            gson.fromJson(jsonOrder,type);
            order=gson.fromJson(jsonOrder,type);
        }
        if(order!=null){
            for(CartItemModel cart:order.getCarts()){
                tvItems.append("\n\t"+cart.getName());
            }
            tvTotalPrice.setText(String.valueOf("\t"+order.getTotalPrice()));
            tvAddress.append("\t"+order.getAddress());
            tvPhoneNumber.append("\t"+order.getPhoneNumber());
        }

        return view;
    }
    private void initViews(View view) {
        rgPayment = view.findViewById(R.id.Third_Fragment_rgPaymentMethod);
        tvAddress = view.findViewById(R.id.Third_Fragment_tvAddress);
        tvPhoneNumber = view.findViewById(R.id.txtPhoneNumber);
        tvItems = view.findViewById(R.id.Third_Fragment_itemsNames);
        tvTotalPrice = view.findViewById(R.id.Third_Fragment_tvPrice);
        btnBack = view.findViewById(R.id.Third_Fragment_btnBack);
        btnCheckout = view.findViewById(R.id.Third_Fragment_btnCheckout);
        btnBackInit();
        btnCheckoutInit();
    }
    void btnBackInit(){
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.backBtnIsClicked();
            }
        });
    }
    void btnCheckoutInit(){
        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String paymentMethod=null;
                switch (rgPayment.getCheckedRadioButtonId()){
                    case R.id.Third_Fragment_rbPayPal:
                        paymentMethod="PayPal";
                        break;
                    case R.id.Third_Fragment_rbCreditCard:
                        paymentMethod=("CreditC");
                        break;
                }
                order.setSuccess(true);
                listener.checkoutBtnIsClicked(order,paymentMethod);
            }
        });

    }


}