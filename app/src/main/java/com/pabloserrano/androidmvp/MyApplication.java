package com.pabloserrano.androidmvp;

import android.app.Application;

import com.pabloserrano.androidmvp.module.AppModule;
import com.pabloserrano.androidmvp.module.NetModule;
import com.pabloserrano.androidmvp.module.RepositoryModule;

import timber.log.Timber;

public class MyApplication extends Application {

    private AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule())
                .repositoryModule(new RepositoryModule())
                .build();

        Timber.plant(new Timber.DebugTree());
    }

    public AppComponent getComponent() {
        return  component;
    }
}
