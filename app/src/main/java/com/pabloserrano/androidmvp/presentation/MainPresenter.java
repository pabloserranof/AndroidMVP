package com.pabloserrano.androidmvp.presentation;

import com.pabloserrano.androidmvp.view.MainView;

public interface MainPresenter {
    void setView(MainView view);
    void getBooks();
}
