package com.pabloserrano.androidmvp;

import com.pabloserrano.androidmvp.module.AppModule;
import com.pabloserrano.androidmvp.module.NetModule;
import com.pabloserrano.androidmvp.module.RepositoryModule;
import com.pabloserrano.androidmvp.view.activity.DetailsScreenActivity;
import com.pabloserrano.androidmvp.view.adapter.AdapterGrid;
import com.pabloserrano.androidmvp.view.adapter.AdapterList;
import com.pabloserrano.androidmvp.view.fragment.MainFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, NetModule.class, RepositoryModule.class})
public interface AppComponent {
    void inject(MainFragment target);
    void inject(AdapterList target);
    void inject(AdapterGrid target);
    void inject(DetailsScreenActivity target);
}
