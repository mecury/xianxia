package com.mecuryli.xianxia.cache.table;

/**
 * Created by 海飞 on 2016/5/26.
 */
public class DailyTable {
    public static final String NAME="daily_table";
    public static final String COLLECTION_NAME = "collection_daily_table";

    public static final String TITLE = "title";
    //public static final String INFO = "info";
    public static final String IMAGE = "image";
    //public static final String DESCRIPTION = "description";
    public static final String IS_COLLECTED = "is_collected";

    public static final int ID_TITLE = 0;
    //public static final int ID_INFO = 1;
    public static final int ID_IMAGE = 1;
    //public static final int ID_DESCRIPTION = 3;
    public static final int ID_IS_COLLETED = 2;

    public static final String CREATE_TABLE = "create table " + NAME +
            "(" + TITLE + " text," +
           // INFO + " text," +
            IMAGE + " text," +
           // DESCRIPTION + " text," +
            IS_COLLECTED + " integer)";

    public static final String CREATE_COLLECTION_TABLE = "create table " + COLLECTION_NAME +
            "(" + TITLE + " text," +
           // INFO + " text," +
            IMAGE + " text)";
            //DESCRIPTION + " text)";

    public static final String SQL_INIT_COLLECTION_FLAG = "update " + NAME +
            " set " + IS_COLLECTED + " =1 where " + TITLE + " in ( select " + TITLE +
            " from " + COLLECTION_NAME +")";

    public static String updateCollectionFlag(String title, int flag){
        return "update " + NAME + " set " + IS_COLLECTED + " =" +flag +
                " where " + TITLE + "=\'" + title + "\'";
    }
    public static String deleteCollectionFlag(String title){
        return "delete from " + COLLECTION_NAME + " where title=\'"+title + "\'";
    }
}
