package com.example.cairomall.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cairomall.Adapters.ReviewAdapter;
import com.example.cairomall.Databse.CartSqliteDatabase;
import com.example.cairomall.Databse.GrocerySqliteDatabase;
import com.example.cairomall.Databse.ReviewSqliteDatabase;
import com.example.cairomall.Model.GroceryItemModel;
import com.example.cairomall.Model.ReviewModel;
import com.example.cairomall.R;
import com.example.cairomall.Dialogs.ReviewDialogFragment;

import java.util.ArrayList;

import static androidx.recyclerview.widget.LinearLayoutManager.VERTICAL;

public class GroceryItemActivity extends AppCompatActivity {
    TextView mDescription;
    TextView mName;
    TextView mPrice;
    TextView mAddReview;
    RecyclerView reviewsRecView;
    int position;
    public static final String ITEM_EXTRA="Grocery_item";
    public static final String POSITION_EXTRA="Grocery_item_position";
    private GroceryItemModel mGroceryItem;
    ReviewAdapter mAdapter;
    ArrayList<ReviewModel> reviews;
    ImageView mItemImage;
    Button addToCart;
    RatingBar mRatingBar;
    GrocerySqliteDatabase mGrocerySqliteDatabase;
    CartSqliteDatabase mCartSqliteDatabase;
    ReviewDialogFragment mDialogFragment;
    ReviewSqliteDatabase mReviewSqliteDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_item);
        viewInit();
        Intent intent=getIntent();
        if(intent!=null){
            mGroceryItem= (GroceryItemModel) intent.getParcelableExtra(ITEM_EXTRA);
            position=intent.getIntExtra(POSITION_EXTRA,0);
            mName.setText(mGroceryItem.getName());
            mPrice.setText(mGroceryItem.getPrice()+" $");
            mDescription.setText(mGroceryItem.getDescription());
            initializeDatabases();
            mRatingBar.setRating(mGroceryItem.getRate());
            Glide.with(this).asBitmap().load(mGroceryItem.getImgUrl()).into(mItemImage);
            reviews=mReviewSqliteDatabase.getAllItems(mGroceryItem.getId());
            mAdapter=new ReviewAdapter();
            reviewsRecView.setAdapter(mAdapter);
            reviewsRecView.setLayoutManager(new LinearLayoutManager(this, VERTICAL,false));

            if(reviews!=null){
                if(reviews.size()>0){
                    mAdapter.setReviews(reviews);
                }
            }
            addToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent= new Intent(GroceryItemActivity.this,CartActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(CartActivity.CART_ITEM, (Parcelable) mGroceryItem);
                    startActivity(intent);

                }
            });
            mAddReview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialogFragment=new ReviewDialogFragment();
                    Bundle bundle=new Bundle();
                    bundle.putParcelable(ITEM_EXTRA,mGroceryItem);
                    mDialogFragment.setArguments(bundle);
                    mDialogFragment.show(getSupportFragmentManager(),"ADD REVIEW");
                    mDialogFragment.setOnAddReviewResultListener(new ReviewDialogFragment.OnAddReviewResultListener() {
                        @Override
                        public void dialogReviewResult(ReviewModel review) {
                            if(mReviewSqliteDatabase.insertIntoDB(review)){
                                Toast.makeText(GroceryItemActivity.this,"item is added successfully",Toast.LENGTH_LONG).show();
                                mAdapter.setReviews(mReviewSqliteDatabase.getAllItems(mGroceryItem.getId()));
                            }
                        }
                    });
                }
            });
            mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    mGroceryItem.setRate(rating);
                    mGrocerySqliteDatabase.updateGroceryItem(mGroceryItem);
                }
            });
        }


    }
    void viewInit(){
        mDescription=findViewById(R.id.Grocery_Item_Activity_description);
        mName=findViewById(R.id.Grocery_Item_Activity_name);
        mPrice=findViewById(R.id.Grocery_Item_Activity_price);
        mAddReview=findViewById(R.id.Grocery_Item_Activity_add_Review);
        mItemImage=findViewById(R.id.Grocery_Item_Activity_img);
        reviewsRecView=findViewById(R.id.Grocery_Item_Activity_review_RecView);
        addToCart=findViewById(R.id.Grocery_item_Add_to_Cart_Buttn);
        mRatingBar=findViewById(R.id.Grocery_Item_Activity_ratingBar);
    }
    @Override
    protected void onResume() {
        super.onResume();

    }
    private void initializeDatabases(){
        mGrocerySqliteDatabase =new GrocerySqliteDatabase(this);
        mReviewSqliteDatabase =new ReviewSqliteDatabase(this);
        mCartSqliteDatabase =new CartSqliteDatabase(this);

    }
}