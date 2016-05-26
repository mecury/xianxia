package com.mecuryli.xianxia.cache.table;

/**
 * Created by 海飞 on 2016/5/26.
 */
public class NewsTable {
    public static final String NAME = "news_table";
    public static final String COLLECTION_NAME = "collection_news_table";

    public static final String TITLE = "title";
    public static final String PUBTIME = "pubtime";
    public static final String DESCRIPTION = "description";
    public static final String IS_COLLECTION = "is_collected";

    public static final int ID_TITLE = 0;
    public static final int ID_PUBTIME = 1;
    public static final int ID_DESCRIPTION = 2;
    public static final int ID_IS_COLLECTION = 3;

    public static final String CREATE_TABLE = "create " + NAME +
            "(" + TITLE + " text primary key," +
            PUBTIME + " text," +
            DESCRIPTION + " text," +
            IS_COLLECTION + " integer)";
    public static final String CREATE_COLLECTION_TABLE = "create " + NAME +
            "(" + COLLECTION_NAME + " text primary key," +
            PUBTIME + " text," +
            DESCRIPTION + " text)";

    public static final String SQL_INIT_COLLECTION_FLAG = "update " + NAME +
            " set " + IS_COLLECTION + "=1 where " + TITLE + " in ( select " +
            TITLE + " from " + COLLECTION_NAME + ")";

    public static String updateCollectionFlag(String title, int flag){
        return "update "+NAME + " set " + IS_COLLECTION + " =" + flag +
                "where " + TITLE +"=" + title;
    }
    public static String deleteCollectionFlag(String title){
        return "delete from " + COLLECTION_NAME + " where title=" + title;
    }
}
