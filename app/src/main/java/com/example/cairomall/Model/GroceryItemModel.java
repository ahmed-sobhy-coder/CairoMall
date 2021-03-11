package com.example.cairomall.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

public class GroceryItemModel implements Parcelable, Serializable {
    private int id;
    private String name;
    private String description;
    private String imgUrl;
    private String Category;
    private double price;
    private int availableAmount;
    private float rate;
    private int userPoint;
    private int popularityPoint;
    private ArrayList<ReviewModel> mReviews;
    public GroceryItemModel(String name,
                            String description,
                            String imgUrl,
                            String category,
                            double price,
                            int availableAmount,
                            float rate,
                            int userPoint,
                            int popularityPoint) {
        this.name = name;
        this.description = description;
        this.imgUrl = imgUrl;
        Category = category;
        this.price = price;
        this.availableAmount = availableAmount;
        this.rate = rate;
        this.userPoint = userPoint;
        this.popularityPoint = popularityPoint;
    }


    protected GroceryItemModel(Parcel in) {
        id = in.readInt();
        name = in.readString();
        description = in.readString();
        imgUrl = in.readString();
        Category = in.readString();
        price = in.readDouble();
        availableAmount = in.readInt();
        rate = in.readFloat();
        userPoint = in.readInt();
        popularityPoint = in.readInt();
    }

    public static final Creator<GroceryItemModel> CREATOR = new Creator<GroceryItemModel>() {
        @Override
        public GroceryItemModel createFromParcel(Parcel in) {
            return new GroceryItemModel(in);
        }

        @Override
        public GroceryItemModel[] newArray(int size) {
            return new GroceryItemModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public int getAvailableAmount() {
        return availableAmount;
    }

    public void setAvailableAmount(int availableAmount) {
        this.availableAmount = availableAmount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public int getUserPoint() {
        return userPoint;
    }

    public void setUserPoint(int userPoint) {
        this.userPoint = userPoint;
    }

    public int getPopularityPoint() {
        return popularityPoint;
    }

    public void setPopularityPoint(int popularityPoint) {
        this.popularityPoint = popularityPoint;
    }

    public ArrayList<ReviewModel> getReviews() {
        return mReviews;
    }

    public void setReviews(ArrayList<ReviewModel> reviews) {
        mReviews = reviews;
    }

    @Override
    public String toString() {
        return "GroceryItemModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", Category='" + Category + '\'' +
                ", availableAmount=" + availableAmount +
                ", price=" + price +
                ", rate=" + rate +
                ", userPoint=" + userPoint +
                ", popularityPoint=" + popularityPoint +
                ", mReviews=" + mReviews +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(imgUrl);
        dest.writeString(Category);
        dest.writeDouble(price);
        dest.writeInt(availableAmount);
        dest.writeFloat(rate);
        dest.writeInt(userPoint);
        dest.writeInt(popularityPoint);
    }
}
