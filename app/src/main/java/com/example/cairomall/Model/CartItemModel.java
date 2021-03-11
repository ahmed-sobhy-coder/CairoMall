package com.example.cairomall.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class CartItemModel implements Parcelable {
    int id;
    int itemId;
    String name;
    double price;
    int availableAmount;

    public CartItemModel(int itemId, String name, double price, int availableAmount) {
        this.itemId = itemId;
        this.name = name;
        this.price = price;
        this.availableAmount = availableAmount;
    }

    protected CartItemModel(Parcel in) {
        id = in.readInt();
        itemId = in.readInt();
        name = in.readString();
        price = in.readDouble();
        availableAmount = in.readInt();
    }

    public static final Creator<CartItemModel> CREATOR = new Creator<CartItemModel>() {
        @Override
        public CartItemModel createFromParcel(Parcel in) {
            return new CartItemModel(in);
        }

        @Override
        public CartItemModel[] newArray(int size) {
            return new CartItemModel[size];
        }
    };

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAvailableAmount() {
        return availableAmount;
    }

    public void setAvailableAmount(int availableAmount) {
        this.availableAmount = availableAmount;
    }
    public void setId(int id){
        this.id=id;
    }
    public int getId(){
        return id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(itemId);
        dest.writeString(name);
        dest.writeDouble(price);
        dest.writeInt(availableAmount);
    }
}
