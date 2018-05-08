package com.jk.poll;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by jkp on 2018-04-06.
 */

public class DBHelper extends SQLiteOpenHelper{

    final static String DB_NAME = "PollDB";
    final static String TB_NAME_POLL = "Poll";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            String CREATE_TABLE = "CREATE TABLE " +
                    TB_NAME_POLL + " (Id INTEGER AUTO_INCREMENT," +
                    "PollTitle VARCHAR(30) PRIMARY KEY, " +
                    "Question VARCHAR(100)," +
                    "Option1 VARCHAR(50), " +
                    "Option2 VARCHAR(50)," +
                    "Option3 VARCHAR(50)," +
                    "Option4 VARCHAR(50)," +
                    "CountOption1 INTEGER," +
                    "CountOption2 INTEGER," +
                    "CountOption3 INTEGER," +
                    "CountOption4 INTEGER)" ;
            Log.v("On create table : ",CREATE_TABLE);
            db.execSQL(CREATE_TABLE);

        }catch (Exception e){
            Log.e("DBHelper",e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int olderVersion, int newerVersion) {
        try {
            db.execSQL("DROP TABLE IF EXISTS " + TB_NAME_POLL);
            onCreate(db);
        }catch (Exception e){
            Log.e("DBHelper",e.getMessage());

        }
    }
}
