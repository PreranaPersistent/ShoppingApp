package com.shoppingapp.com.shoppingapp.modes.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.shoppingapp.com.shoppingapp.db.DataBaseHelper;
import com.shoppingapp.models.HandBag;

import java.util.ArrayList;

/**
 * Created by prerana_katyarmal on 2/18/2016.
 */
public class HandbagUtil {

    public static void addProductToDataBase(HandBag handBag, Context ctx) {
        String query;
        DataBaseHelper.init(ctx);
        if (!isProductAlreadyInDB(handBag, ctx)) {

            query =
                    "INSERT INTO ProductInfo (Product_ID,Image_URL,ProductName)VALUES "
                            + "(?,?,?)";
            SQLiteDatabase db = DataBaseHelper.getSqliteDatabase();
            SQLiteStatement statement = db.compileStatement(query);
            statement = bindValuesToStatement(handBag, statement);
            statement.execute();
            statement.close();
        }

    }

    private static boolean isProductAlreadyInDB(HandBag productId, Context ctx) {
        return isProductInDB(productId.getId(), ctx);
    }

    public static boolean isProductInDB(int productId, Context ctx) {
        String query = "Select Product_ID from ProductInfo where Product_ID=" + productId ;
        SQLiteDatabase db = DataBaseHelper.getSqliteDatabase();
        Cursor cursor = DataBaseHelper.executeSelectQuery(db, query, null);
        if (cursor.getCount() <= 0) {
            return false;
        }
        cursor.close();
        return true;
    }

    /* private static void updateProduct(HandBag productInfo) {
         String query;
         SQLiteDatabase db = DataBaseHelper.getSqliteDatabase();
         query = "UPDATE ProductInfo set Image_URL=?,ProductName=?,Quantity=?,Product_price=?,Max_Quantity=?,Available_Quantity=?,Category_ID=?,SKU=?,Product_Alcohol=? WHERE Product_ID=?";
         SQLiteStatement statement = db.compileStatement(query);
         statement.bindString(1, String.valueOf(productInfo.getImageURL()));
         statement.bindString(2, String.valueOf(productInfo.getProductName()));
         statement.bindDouble(3, productInfo.getQuantity());
         statement.bindDouble(4, productInfo.getProductPrice());
         statement.bindDouble(5, productInfo.getMaxQuantity());
         statement.bindDouble(6, productInfo.getAvailableQuantity());
         statement.bindString(7, String.valueOf(productInfo.getProductId()));
         statement.bindDouble(8, productInfo.getCategoryID());
         statement.bindString(9, productInfo.getSku());
         statement.bindString(10, productInfo.getmAlcohol());
         statement.execute();
         statement.close();
     }
 */
    public static void addProductID(String productID) {
        String query;
        query = "INSERT INTO ProductInfo (Product_ID_CART)VALUES"
                + "(?)";
        SQLiteDatabase db = DataBaseHelper.getSqliteDatabase();
        SQLiteStatement statement = db.compileStatement(query);
        statement = bindValuesToStatementProduct(productID, statement);
        statement.execute();
        statement.close();
    }

    private static SQLiteStatement bindValuesToStatementProduct(String productID,
                                                                SQLiteStatement statement) {
        try {
            statement.bindString(1, productID);
        } catch (Exception e) {
            //todo
        }
        return statement;
    }

    private static SQLiteStatement bindValuesToStatement(HandBag productInfo,
                                                         SQLiteStatement statement) {
        try {
            statement.bindDouble(1, productInfo.getId());
            statement.bindDouble(2, productInfo.getDrawableImage());
            statement.bindString(3, productInfo.getBagName());
        } catch (Exception e) {
            //todo
        }
        return statement;
    }


    public static ArrayList<HandBag> getProducts(Context ctx) {
        DataBaseHelper.init(ctx);

        ArrayList<HandBag> productList = new ArrayList<>();
        String query = "Select * from ProductInfo";
        SQLiteDatabase db = DataBaseHelper.getSqliteDatabase();
        Cursor cur = DataBaseHelper.executeSelectQuery(db, query, null);
        while (cur.moveToNext()) {
            HandBag product = new HandBag();
            product.setId(cur.getInt(cur.getColumnIndexOrThrow("Product_ID")));
            product.setDrawableImage(cur.getInt(cur.getColumnIndexOrThrow("Image_URL")));
            product.setBagName(cur.getString(cur.getColumnIndexOrThrow("ProductName")));
            productList.add(product);
        }
        cur.close();
        return productList;
    }

    public static ArrayList<String> getProductCartID() {
        ArrayList<String> productList = new ArrayList<>();
        String query = "SELECT * FROM ProductInfo";
        SQLiteDatabase db = DataBaseHelper.getSqliteDatabase();
        Cursor cur = DataBaseHelper.executeSelectQuery(db, query, null);
        while (cur.moveToNext()) {
            productList.add(cur.getString(cur.getColumnIndexOrThrow("Product_ID_CART")));
        }
        cur.close();
        return productList;
    }


    public static void removeAll(Context ctx) {
        SQLiteDatabase db = DataBaseHelper.getSqliteDatabase();
        String query = "Delete from ProductInfo";
        db.execSQL(query);
    }


    public static void deleteProductFromDatabase(int productID, Context ctx) {
        SQLiteDatabase db = DataBaseHelper.getSqliteDatabase();
        String query = "Delete from ProductInfo WHERE Product_ID= "+ productID;
        db.execSQL(query);
    }


}
