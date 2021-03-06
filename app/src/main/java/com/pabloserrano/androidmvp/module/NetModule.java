package com.pabloserrano.androidmvp.module;

import android.app.Application;
import android.net.Uri;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.pabloserrano.androidmvp.api.ApiService;
import com.pabloserrano.androidmvp.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

@Module
public class NetModule {

    private static final int CACHE_SIZE = 10 * 1024 * 1024; // 10MB

    @Provides
    @Singleton
    Cache provideHttpCache(Application application) {
        return new Cache(application.getCacheDir(), CACHE_SIZE);
    }

    @Provides
    @Singleton
    OkHttpClient provideOkhttpClient(final Cache cache, final Application application) {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.cache(cache).addInterceptor(new Interceptor() {
            @Override public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                // Read from the cache when there is no network
                if (NetworkUtils.isNetworkAvailable(application)) {
                    request = request.newBuilder().header("Cache-Control", "public, max-age=" + 60).build();
                } else {
                    request = request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build();
                }
                return chain.proceed(request);
            }
        });
        return client.build();
    }

    @Provides @Singleton
    Picasso providePicasso(Application app, OkHttpClient client) {
        return new Picasso.Builder(app)
                .downloader(new OkHttp3Downloader(client))
                .listener(new Picasso.Listener() {
                    @Override
                    public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                        Timber.e("onImageLoadFailed: " + uri.toString());
                    }
                })
                .indicatorsEnabled(true)
                .loggingEnabled(true)
                .build();
    }

    @Provides
    @Singleton
    public Retrofit provideRestAdapter(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(ApiService.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @Provides
    public ApiService provideApiService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }
}
