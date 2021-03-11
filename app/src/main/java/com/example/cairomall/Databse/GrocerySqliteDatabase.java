package com.example.cairomall.Databse;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.cairomall.Model.GroceryItemModel;

import java.util.ArrayList;

public class GrocerySqliteDatabase extends SQLiteOpenHelper {
    protected final static String TABLE_NAME = "ItemsTable";
    protected static final int DB_VERSION = 1;
    private String ID = "id";
    private String NAME = "name";
    private String DESCRIPTION = "description";
    private String IMG_URL = "imgUrl";
    private String CATEGORY = "Category";
    private String AVAILABLE_AMOUNT = "availableAmount";
    private String PRICE = "price";

    private String RATE = "rate";
    private String USER_POINT = "userPoint";
    private String POPULARITY_POINT = "popularityPoint";
    private SQLiteDatabase db;

    public GrocerySqliteDatabase(@Nullable Context context) {
        super(context, TABLE_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NAME + " TEXT," +
                DESCRIPTION + " TEXT," +
                IMG_URL + " TEXT," +
                CATEGORY + " TEXT," +
                AVAILABLE_AMOUNT + " INTEGER," +
                PRICE + " REAL," +
                RATE + " REAL ," +
                USER_POINT + " INTEGER," +
                POPULARITY_POINT + " INTEGER )"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }
    public void insertListIntoDB(ArrayList<GroceryItemModel> items){
        if(items.size()>0){
            for(GroceryItemModel item:items){
                insertIntoDB(item);
            }
        }
    }
    public boolean insertIntoDB(GroceryItemModel itemModel) {
        SQLiteDatabase db = getWritableDatabase();
        boolean isInserted=false;
        if(db!=null){
            ContentValues values = new ContentValues();
            values.put(NAME, itemModel.getName());
            values.put(DESCRIPTION, itemModel.getDescription());
            values.put(IMG_URL, itemModel.getImgUrl());
            values.put(CATEGORY, itemModel.getCategory());
            values.put(AVAILABLE_AMOUNT, itemModel.getAvailableAmount());
            values.put(PRICE, itemModel.getPrice());
            values.put(RATE, itemModel.getRate());
            values.put(USER_POINT, itemModel.getUserPoint());
            values.put(POPULARITY_POINT, itemModel.getPopularityPoint());
            isInserted = db.insert(TABLE_NAME, null, values) > 0;
            db.close();
        }

        return isInserted;
    }

    public ArrayList<GroceryItemModel> getAllItems() {
        SQLiteDatabase db = getWritableDatabase();
        ArrayList<GroceryItemModel> items = null;
        if(db!=null){
            Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY ID DESC ", null);
            if (cursor.moveToFirst()) {
                items = new ArrayList<>();
                do {
                    GroceryItemModel item = new GroceryItemModel(
                            cursor.getString(cursor.getColumnIndex(NAME)),
                            cursor.getString(cursor.getColumnIndex(DESCRIPTION)),
                            cursor.getString(cursor.getColumnIndex(IMG_URL)),
                            cursor.getString(cursor.getColumnIndex(CATEGORY)),
                            cursor.getDouble(cursor.getColumnIndex(PRICE)),
                            cursor.getInt(cursor.getColumnIndex(AVAILABLE_AMOUNT)),
                            cursor.getFloat(cursor.getColumnIndex(RATE)),
                            cursor.getInt(cursor.getColumnIndex(USER_POINT)),
                            cursor.getInt(cursor.getColumnIndex(POPULARITY_POINT))
                    );
                    item.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID))));
                    items.add(item);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        }
        return items;
    }

    public boolean updateGroceryItem(GroceryItemModel item) {
        SQLiteDatabase db = getWritableDatabase();
        boolean isUpdated =false;
        if(db!=null){
            ContentValues values = new ContentValues();
            values.put(NAME, item.getName());
            values.put(DESCRIPTION, item.getDescription());
            values.put(IMG_URL, item.getImgUrl());
            values.put(CATEGORY, item.getCategory());
            values.put(AVAILABLE_AMOUNT, item.getAvailableAmount());
            values.put(PRICE, item.getPrice());
            values.put(RATE, item.getRate());
            values.put(USER_POINT, item.getUserPoint());
            values.put(POPULARITY_POINT, item.getPopularityPoint());
            isUpdated = db.update(TABLE_NAME, values, ID + " = " + item.getId(), null) > 0;
            db.close();
        }

        return isUpdated ;
    }

    public GroceryItemModel readGroceryItem(int itemId) {
        SQLiteDatabase db = getWritableDatabase();
        GroceryItemModel item = null;
        if(db!=null){
            Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + ID + " = " + itemId, null);
            if (cursor.moveToFirst()) {
                item = new GroceryItemModel(
                        cursor.getString(cursor.getColumnIndex(NAME)),
                        cursor.getString(cursor.getColumnIndex(DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndex(IMG_URL)),
                        cursor.getString(cursor.getColumnIndex(CATEGORY)),
                        cursor.getDouble(cursor.getColumnIndex(PRICE)),
                        cursor.getInt(cursor.getColumnIndex(AVAILABLE_AMOUNT)),
                        cursor.getFloat(cursor.getColumnIndex(RATE)),
                        cursor.getInt(cursor.getColumnIndex(USER_POINT)),
                        cursor.getInt(cursor.getColumnIndex(POPULARITY_POINT)));
            }
            item.setId(cursor.getInt(cursor.getColumnIndex(ID)));
            db.close();
            cursor.close();
        }

        return item;
    }

    public void deleteTable() {
        SQLiteDatabase db = getWritableDatabase();
        if(db!=null){
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            db.close();
        }

    }

    public void clearTable() {
        SQLiteDatabase db = getWritableDatabase();
        if(db!=null){
            Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
            int id;
            if (cursor.moveToFirst()) {
                do {
                    id = cursor.getInt(cursor.getColumnIndex(ID));
                    deleteItem(id);
                } while (cursor.moveToNext());
            }
            db.close();
        }
    }

    public boolean deleteItem(int itemId) {
        SQLiteDatabase db = getWritableDatabase();
        boolean isDeleted=false;
        if(db!=null){
            isDeleted= db.delete(TABLE_NAME, ID + " = " + itemId, null) > 0;
            db.close();
        }

        return isDeleted;
    }
}
