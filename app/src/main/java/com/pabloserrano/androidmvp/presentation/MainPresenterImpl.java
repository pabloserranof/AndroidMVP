package com.pabloserrano.androidmvp.presentation;

import com.pabloserrano.androidmvp.model.Book;
import com.pabloserrano.androidmvp.repository.BooksRepository;
import com.pabloserrano.androidmvp.view.MainView;

import java.util.List;

public class MainPresenterImpl implements MainPresenter, BooksRepository.GetBooksCallback {

    private MainView view;
    private BooksRepository booksRepository;

    public MainPresenterImpl(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    @Override
    public void setView(MainView view) {
        this.view = view;
        getBooks();
    }

    @Override
    public void getBooks() {
        booksRepository.getBooks(this);
    }

    @Override
    public void onBooksLoaded(List<Book> books) {
        view.loadBooks(books);
    }

    @Override
    public void onDataNotAvailable() {
        view.showBooksNotFound();
    }
}
