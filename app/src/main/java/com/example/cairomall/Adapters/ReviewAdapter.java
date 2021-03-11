package com.example.cairomall.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cairomall.Model.ReviewModel;
import com.example.cairomall.R;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewItemViewHolder> {
    private Context mContext;
    private ArrayList<ReviewModel> reviews;
    public ReviewAdapter(){
    }
    @NonNull
    @Override
    public ReviewAdapter.ReviewItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext=parent.getContext();
        LayoutInflater inflater= LayoutInflater.from(mContext);
        View view= inflater.inflate(R.layout.review_item,parent,false);
        return new ReviewItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.ReviewItemViewHolder holder, int position) {
        holder.userName.setText(reviews.get(position).getUserName());
        holder.reviewText.setText(reviews.get(position).getText());
        holder.reviewDate.setText(reviews.get(position).getDate());
    }
    public void setReviews(ArrayList<ReviewModel> reviews){
        this.reviews=reviews;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        if(reviews==null){
            return 0;
        }
        return reviews.size();
    }

    public class ReviewItemViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        TextView userName;
        TextView reviewText;
        TextView reviewDate;
        public ReviewItemViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView=itemView;
            userName=itemView.findViewById(R.id.review_layout_username);
            reviewText=itemView.findViewById(R.id.review_layout_review_text);
            reviewDate=itemView.findViewById(R.id.review_layout_review_date);

        }
    }
}
