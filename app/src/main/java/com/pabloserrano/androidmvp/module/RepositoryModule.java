package com.pabloserrano.androidmvp.module;

import com.pabloserrano.androidmvp.api.ApiService;
import com.pabloserrano.androidmvp.presentation.MainPresenter;
import com.pabloserrano.androidmvp.presentation.MainPresenterImpl;
import com.pabloserrano.androidmvp.repository.BooksRepository;
import com.pabloserrano.androidmvp.repository.BooksRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    @Provides
    @Singleton
    public BooksRepository provideItemRepository(ApiService apiService) {
        return new BooksRepositoryImpl(apiService);
    }

    @Provides
    public MainPresenter provideUserPresenter(BooksRepository booksRepository) {
        return new MainPresenterImpl(booksRepository);
    }
}
