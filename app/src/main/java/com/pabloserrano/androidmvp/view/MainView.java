package com.pabloserrano.androidmvp.view;

import com.pabloserrano.androidmvp.model.Book;

import java.util.List;

public interface MainView {

    void loadBooks(List<Book> items);

    void showBooksNotFound();
}
