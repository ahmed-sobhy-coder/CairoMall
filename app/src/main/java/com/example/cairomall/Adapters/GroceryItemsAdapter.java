package com.example.cairomall.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cairomall.Model.GroceryItemModel;
import com.example.cairomall.R;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class GroceryItemsAdapter extends RecyclerView.Adapter <GroceryItemsAdapter.GroceryItemViewHolder> {
    Context mContext;
    ArrayList<GroceryItemModel> items;
    OnGrocerItemClickListener mListener;
    public interface OnGrocerItemClickListener{
        void clickedItem(GroceryItemModel item,int position);
    }
    public void setOnGrocerItemClickListener(OnGrocerItemClickListener itemClickListener){
        this.mListener=itemClickListener;
    }
    public GroceryItemsAdapter() {
    }
    public void setItems(ArrayList<GroceryItemModel>items){
        this.items=items;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public GroceryItemsAdapter.GroceryItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext=parent.getContext();
        LayoutInflater inflater=LayoutInflater.from(mContext);
        MaterialCardView materialCardView= (MaterialCardView) inflater.inflate(R.layout.grocery_item_layout,parent,false);
        return new GroceryItemViewHolder(materialCardView);
    }

    @Override
    public void onBindViewHolder(@NonNull GroceryItemsAdapter.GroceryItemViewHolder holder, int position) {
        holder.itemName.setText(items.get(position).getName());
        holder.itemPrice.setText(String.valueOf(items.get(position).getPrice())+"$");
        Glide.with(mContext).load(items.get(position).getImgUrl()).into(holder.itemImg);
       holder.mMaterialCardView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               mListener.clickedItem(items.get(position),position);
           }
       });
    }

    @Override
    public int getItemCount() {
        if(items==null){
            return 0;
        }
        return items.size();
    }

    public class GroceryItemViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImg;
        TextView itemPrice;
        TextView itemName;
        MaterialCardView mMaterialCardView;
        public GroceryItemViewHolder(@NonNull View cardView) {
            super(cardView);
            this.mMaterialCardView= (MaterialCardView) cardView;
            itemImg= cardView.findViewById(R.id.item_img);
            itemName= cardView.findViewById(R.id.item_name);
            itemPrice= cardView.findViewById(R.id.item_price);
        }
    }
}
