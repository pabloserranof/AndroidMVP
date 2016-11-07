package com.pabloserrano.androidmvp.api;

import com.pabloserrano.androidmvp.model.Book;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    String ENDPOINT = "http://www.mocky.io/v2/57ee2ca8260000f80e1110fa/";

    @GET(".")
    Call<List<Book>> getBooks();
}
