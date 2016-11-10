package com.pabloserrano.androidmvp;

import android.app.Application;

import com.pabloserrano.androidmvp.module.AppModule;
import com.pabloserrano.androidmvp.module.NetModule;
import com.pabloserrano.androidmvp.module.RepositoryModule;
import com.squareup.leakcanary.LeakCanary;

import timber.log.Timber;

public class MyApplication extends Application {

    private AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);

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
