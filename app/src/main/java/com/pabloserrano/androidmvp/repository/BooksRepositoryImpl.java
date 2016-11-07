package com.pabloserrano.androidmvp.repository;

import com.pabloserrano.androidmvp.api.ApiService;
import com.pabloserrano.androidmvp.model.Book;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BooksRepositoryImpl implements BooksRepository {

    @Inject
    ApiService apiService;

    public BooksRepositoryImpl(ApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public void getBooks(final GetBooksCallback getBooksCallback) {

        apiService.getBooks().enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                if(response.isSuccessful()){
                    getBooksCallback.onBooksLoaded(response.body());
                }
                else{
                    getBooksCallback.onDataNotAvailable();
                }
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                getBooksCallback.onDataNotAvailable();
            }
        });
    }
}
