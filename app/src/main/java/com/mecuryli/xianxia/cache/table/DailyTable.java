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

    //用于存储正常加载的缓存
    public static final String CREATE_TABLE = "create table " + NAME +
            "(" + TITLE + " text," +
           // INFO + " text," +
            IMAGE + " text," +
           // DESCRIPTION + " text," +
            IS_COLLECTED + " integer)";

    //用于缓存收藏的内容
    public static final String CREATE_COLLECTION_TABLE = "create table " + COLLECTION_NAME +
            "(" + TITLE + " text," +
           // INFO + " text," +
            IMAGE + " text)";
            //DESCRIPTION + " text)";

    //用于从“收藏”中查找内容
    public static final String SELECT_ALL_FROM_COLLECTION="select * from " + COLLECTION_NAME;


    //更新缓存中的收藏的标志位
    public static final String SQL_INIT_COLLECTION_FLAG = "update " + NAME +
            " set " + IS_COLLECTED + " =1 where " + TITLE + " in ( select " + TITLE +
            " from " + COLLECTION_NAME +")";

    //更新“收藏”标志位为flag
    public static String updateCollectionFlag(String title, int flag){
        return "update " + NAME + " set " + IS_COLLECTED + " =" +flag +
                " where " + TITLE + "=\'" + title + "\'";
    }

    //由“收藏”表中，删除指定的title这一项
    public static String deleteCollectionFlag(String title){
        return "delete from " + COLLECTION_NAME + " where title=\'"+title + "\'";
    }
}
