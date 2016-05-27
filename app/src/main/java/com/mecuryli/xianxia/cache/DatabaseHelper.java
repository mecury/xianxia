package com.mecuryli.xianxia.cache;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mecuryli.xianxia.cache.table.DailyTable;
import com.mecuryli.xianxia.cache.table.NewsTable;
import com.mecuryli.xianxia.cache.table.ReadingTable;
import com.mecuryli.xianxia.cache.table.ScienceTable;

/**
 * Created by 海飞 on 2016/5/26.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "xianxia";
    private static DatabaseHelper instance = null;
    private static final int DB_VERSION = 1;

    public static  final String DROP_TABLE = "drop table if exists ";

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DailyTable.CREATE_TABLE);
        db.execSQL(DailyTable.CREATE_COLLECTION_TABLE);

        db.execSQL(NewsTable.CREATE_TABLE);
        db.execSQL(NewsTable.CREATE_COLLECTION_TABLE);

        db.execSQL(ReadingTable.CREATE_TABLE);
        db.execSQL(ReadingTable.CREATE_COLLECTION_TABLE);

        db.execSQL(ScienceTable.CREATE_TABLE);
        db.execSQL(ScienceTable.CREATE_COLLECTION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public static synchronized DatabaseHelper getInstance(Context context){
        if (instance == null){
            instance = new DatabaseHelper(context,DB_NAME,null,DB_VERSION);
        }
        return instance;
    }
}
