package com.pabloserrano.androidmvp.repository;

import com.pabloserrano.androidmvp.model.Book;

import java.util.List;

public interface BooksRepository {

    interface GetBooksCallback {
        void onBooksLoaded(List<Book> items);
        void onDataNotAvailable();
    }

    void getBooks(GetBooksCallback getBooksCallback);
}
