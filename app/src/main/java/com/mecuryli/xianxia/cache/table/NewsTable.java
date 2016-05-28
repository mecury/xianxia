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
    public static final String CATEGORY = "category";
    public static final String IS_COLLECTED = "is_collected";

    public static final int ID_TITLE = 0;
    public static final int ID_PUBTIME = 1;
    public static final int ID_DESCRIPTION = 2;
    public static final int ID_CATEGORY = 3;
    public static final int ID_IS_COLLECTED = 4;

    public static final String CREATE_TABLE = "create " + NAME +
            "(" + TITLE + " text," +
            PUBTIME + " text," +
            DESCRIPTION + " text," +
            CATEGORY + " text," +
            IS_COLLECTED + " integer)";
    public static final String CREATE_COLLECTION_TABLE = "create " + NAME +
            "(" + COLLECTION_NAME + " text primary key," +
            PUBTIME + " text," +
            DESCRIPTION + " text," +
            CATEGORY + " text)";

    public static final String SQL_INIT_COLLECTION_FLAG = "update " + NAME +
            " set " + IS_COLLECTED + "=1 where " + TITLE + " in ( select " +
            TITLE + " from " + COLLECTION_NAME + ")";

    public static String updateCollectionFlag(String title, int flag){
        return "update "+NAME + " set " + IS_COLLECTED + " =" + flag +
                "where " + TITLE +"=" + title;
    }
    public static String deleteCollectionFlag(String title){
        return "delete from " + COLLECTION_NAME + " where title=" + title;
    }
}
