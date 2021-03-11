package com.example.cairomall.Databse;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.cairomall.Model.CartItemModel;
import com.example.cairomall.Model.GroceryItemModel;

import java.util.ArrayList;

public class CartSqliteDatabase extends SQLiteOpenHelper {
    protected final static String TABLE_NAME = "CartTable";
    protected static final int DB_VERSION = 1;
    private String ID = "id";
    private String ITEM_ID = "itemId";
    private String NAME = "name";
    private String AVAILABLE_AMOUNT = "availableAmount";
    private String PRICE = "price";
    private SQLiteDatabase db;

    public CartSqliteDatabase(@Nullable Context context) {
        super(context, TABLE_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ITEM_ID + " INTEGER,"+
                NAME + " TEXT," +
                AVAILABLE_AMOUNT + " INTEGER," +
                PRICE + " REAL )"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public boolean insertIntoDB(CartItemModel item) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ITEM_ID, item.getItemId());
        values.put(NAME, item.getName());
        values.put(AVAILABLE_AMOUNT, item.getAvailableAmount());
        values.put(PRICE, item.getPrice());
        boolean isInserted = db.insert(TABLE_NAME, null, values) > 0;
        db.close();
        return isInserted;
    }
    public ArrayList<CartItemModel> getAllCarts() {
        SQLiteDatabase db = getWritableDatabase();
        ArrayList<CartItemModel> carts = new ArrayList<>();
        if(db!=null){
            Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY ID DESC ", null);
            if (cursor.moveToFirst()) {
                do {
                    CartItemModel cart = new CartItemModel(
                            cursor.getInt(cursor.getColumnIndex(ITEM_ID)),
                            cursor.getString(cursor.getColumnIndex(NAME)),
                            cursor.getDouble(cursor.getColumnIndex(PRICE)),
                            cursor.getInt(cursor.getColumnIndex(AVAILABLE_AMOUNT))

                    );
                    cart.setId(cursor.getInt(cursor.getColumnIndex(ID)));
                    carts.add(cart);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        }
        return carts;
    }
    public CartItemModel readGroceryItem(int itemId) {
        SQLiteDatabase db = getWritableDatabase();
        CartItemModel item = null;
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + ID + " = " + itemId, null);
        if (cursor.moveToFirst()) {
            item = new CartItemModel(
                    cursor.getInt(cursor.getColumnIndex(ITEM_ID)),
                    cursor.getString(cursor.getColumnIndex(NAME)),
                    cursor.getDouble(cursor.getColumnIndex(PRICE)),
                    cursor.getInt(cursor.getColumnIndex(AVAILABLE_AMOUNT))
            );
        }
        db.close();
        cursor.close();
        return item;
    }

    public void deleteTable() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.close();
    }

    public void clearTable() {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        int id;
        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex(ID));
                deleteCart(id);
            } while (cursor.moveToNext());
        }
        db.close();
    }

    public boolean deleteCart(int itemId) {
        SQLiteDatabase db = getWritableDatabase();
        boolean isDeleted = db.delete(TABLE_NAME, ID + " = " + itemId, null) > 0;
        db.close();
        return isDeleted;
    }
}
