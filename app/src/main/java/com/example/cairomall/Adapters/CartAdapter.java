package com.example.cairomall.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cairomall.Model.CartItemModel;
import com.example.cairomall.R;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    Context context;
    ArrayList<CartItemModel> carts;
    OnCartItemDeleteListener listener;
    public interface OnCartItemDeleteListener{
        void cartDelete(CartItemModel cart,int position);
    }
    public void setOnCartDeleteListener(OnCartItemDeleteListener listener){
        this.listener= listener;
    }
    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        LayoutInflater inflater=LayoutInflater.from(context);
        View cartItem=inflater.inflate(R.layout.cart_item,parent,false);
        return new CartViewHolder(cartItem);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        holder.tvItemName.setText(carts.get(position).getName());
        String price=carts.get(position).getPrice()+"$";
        holder.tvItemPrice.setText(price);
        holder.tvItemDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.cartDelete(carts.get(position),position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(carts==null){
            return 0;
        }
        return carts.size();
    }
    public void setItems(ArrayList<CartItemModel> carts){
        this.carts=carts;
        notifyDataSetChanged();
    }
    public class CartViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemName;
        TextView tvItemPrice;
        TextView tvItemDelete;
        View cartView;
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            cartView= itemView;
            tvItemName =itemView.findViewById(R.id.Cart_Item_name);
            tvItemPrice =itemView.findViewById(R.id.Cart_Item_price);
            tvItemDelete =itemView.findViewById(R.id.Cart_Item_delete);

        }
    }
}
