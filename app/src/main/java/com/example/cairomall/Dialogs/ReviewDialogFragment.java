package com.example.cairomall.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.cairomall.Activities.GroceryItemActivity;
import com.example.cairomall.Model.GroceryItemModel;
import com.example.cairomall.Model.ReviewModel;
import com.example.cairomall.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ReviewDialogFragment extends DialogFragment {
    EditText edtUserName;
    EditText edtReview;
    Button btnAddReview;
    TextView txtDescription;
    TextView txtItemName;
    GroceryItemModel mGroceryItem;
    OnAddReviewResultListener mListener;
    public interface OnAddReviewResultListener{
        void dialogReviewResult(ReviewModel review);
    }
    public void setOnAddReviewResultListener(OnAddReviewResultListener listener){
        mListener=listener;
    }
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater=LayoutInflater.from(getActivity());
        View dialogView=inflater.inflate(R.layout.dialog_layout,null,false);
        edtReview=dialogView.findViewById(R.id.Dialog_Review_reviewText);
        edtUserName=dialogView.findViewById(R.id.Dialog_Review_userName);
        btnAddReview=dialogView.findViewById(R.id.Dialog_Review_btn);
        txtDescription=dialogView.findViewById(R.id.Dialog_Review_description);
        txtItemName=dialogView.findViewById(R.id.Dialog_Review_itemName);

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.review_title);
        builder.setView(dialogView);

/*
        You have a method called getArguments() that belongs to Fragment class.
*/
        Bundle bundle= getArguments();
        if(bundle!=null){
            mGroceryItem=bundle.getParcelable(GroceryItemActivity.ITEM_EXTRA);
            if(mGroceryItem!=null){
                txtItemName.setText(mGroceryItem.getName());
            }
        }
        btnAddReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName= edtUserName.getText().toString();
                String review = edtReview.getText().toString();
                String date= getCurrentDate();
                if(userName.isEmpty()){
                    edtUserName.setError("you name is required");
                }else if(review.isEmpty()){
                    edtReview.setError("you must add review");
                }else{
                    if(mListener==null){
                        mListener= (OnAddReviewResultListener) getActivity();
                    }
                    mListener.dialogReviewResult(new ReviewModel(mGroceryItem.getId(),userName,review,date));
                    dismiss();
                }
            }
        });
        return builder.create();
    }
    String getCurrentDate(){
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat simpleDateFormat=  new SimpleDateFormat("DD-MM-YYYY");
        return simpleDateFormat.format(calendar.getTime());
    }
}
