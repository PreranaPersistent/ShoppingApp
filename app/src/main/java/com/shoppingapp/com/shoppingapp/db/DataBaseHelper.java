package com.shoppingapp.com.shoppingapp.db;


import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;

/**
 * Database Helper class for setting up the database
 */
 public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "shoppingapp.sqlite";
    private static final int DB_VERSION = 1;

    private static String PRODUCT_TABLE = "ProductInfo";
    private static String DB_PATH = null;
    private static DataBaseHelper sDbHelper;
    private static SQLiteDatabase sDbInstance = null;


    public static final String SQL_CREATE_PRODUCT_INFO_TABLE = "CREATE TABLE IF NOT EXISTS "
            + PRODUCT_TABLE
            + "(Product_ID INTEGER, Image_URL INTEGER, ProductName VARCHAR)";


    public static DataBaseHelper init(Context context) {
        try {
            Context applicationContext = context.getApplicationContext();
            if(sDbHelper==null) {
                sDbHelper = new DataBaseHelper(applicationContext, DB_VERSION);
                sDbInstance = sDbHelper.getWritableDatabase();
            }
        } catch (SQLException sqe) {
            sDbInstance.close();
            /*
			 * If an exception occurs, the previous connection is closed and new
			 * connection is opened.
			 */
            sDbInstance = sDbHelper.getWritableDatabase();
        }
        return sDbHelper;
    }


    private void createDatabase(Context context) {
        boolean dbExists = databaseExists(context);
        if (!dbExists) {
            sDbInstance = context.openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE, null);
            sDbInstance.execSQL(SQL_CREATE_PRODUCT_INFO_TABLE);
            sDbInstance.close();
        }
    }

    public static SQLiteDatabase getSqliteDatabase() {
        if (sDbInstance == null) {
            throw new RuntimeException(
                    "DatabaseHelper.init() must be called before calling this method.");
        }
        return sDbInstance;
    }

    private DataBaseHelper(Context context, int databaseVersion) {
        super(context, DB_NAME, null, databaseVersion);
        sDbHelper = this;
        if (!databaseExists(context)) {
            try {
                createDatabase(context);
            } catch (Exception e) {
            }
        } else {
        }
    }


    /**
     * Check if the database already copied to the device.
     *
     * @return true if it exists, false if it doesn't
     */
    private boolean databaseExists(Context context) {
        File dbFile = context.getDatabasePath(DB_NAME);
        return dbFile.exists();
    }


    public void deleteDatabase() {
        String outFileName = DB_PATH + DB_NAME;
        File databaseFile = new File(outFileName);
        try {
            databaseFile.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Cursor executeSelectQuery(SQLiteDatabase db, String query, String[] selectionArgs) {
        return db.rawQuery(query, selectionArgs);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub

    }

}
