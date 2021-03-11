package com.example.cairomall.Databse;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.cairomall.Model.GroceryItemModel;
import com.example.cairomall.Model.ReviewModel;

import java.util.ArrayList;

public class ReviewSqliteDatabase extends SQLiteOpenHelper {
    private final static String TABLE_NAME="ReviewTable";
    private final static int VERSION=1;
    private final String ID ="id";
    private final String ITEM_ID ="groceryItemId";
    private final String USER_NAME="userName";
    private final String TEXT="text";
    private final String DATE="date";
    public ReviewSqliteDatabase(@Nullable Context context) {
        super(context,TABLE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" CREATE TABLE IF NOT EXISTS "+TABLE_NAME+" ("+
                ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                ITEM_ID+" INTEGER , "+
                USER_NAME+" TEXT,"+
                TEXT+" TEXT,"+
                DATE+" TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
    }
    public boolean insertIntoDB(ReviewModel itemModel){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put((ITEM_ID),itemModel.getGroceryItemId());
        values.put(USER_NAME,itemModel.getUserName());
        values.put(TEXT,itemModel.getText());
        values.put(DATE,itemModel.getDate());
        boolean isInserted=db.insert(TABLE_NAME,null,values)>0;
        db.close();
        return isInserted;
    }
    public ArrayList<ReviewModel> getAllItems(int itemId){
        SQLiteDatabase db=getWritableDatabase();

        ArrayList<ReviewModel> items=new ArrayList<>();
        Cursor cursor=db.rawQuery("SELECT * FROM "+ TABLE_NAME + " Where "+ITEM_ID+" = "+itemId +" ORDER BY ID DESC ",null);
        if(cursor.moveToFirst()){
            do{
                ReviewModel item= new ReviewModel(
                        cursor.getInt(cursor.getColumnIndex(ITEM_ID)),
                        cursor.getString(cursor.getColumnIndex(USER_NAME)),
                        cursor.getString(cursor.getColumnIndex(TEXT)),
                        cursor.getString(cursor.getColumnIndex(DATE)));
                items.add(item);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return items;
    }
}
