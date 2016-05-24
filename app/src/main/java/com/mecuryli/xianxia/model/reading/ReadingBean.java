package com.mecuryli.xianxia.model.reading;

import java.io.Serializable;

/**
 * Created by 海飞 on 2016/5/13.
 */
public class ReadingBean implements Serializable{

    private BookBean books[];

    public BookBean[] getBooks() {
        return books;
    }

    public void setBooks(BookBean[] books) {
        this.books = books;
    }
}
