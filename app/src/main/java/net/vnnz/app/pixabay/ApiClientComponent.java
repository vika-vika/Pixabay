package net.vnnz.app.pixabay;

import net.vnnz.app.pixabay.http.ApiClient;

import javax.inject.Singleton;
import dagger.Component;

@Singleton
@Component(modules = {ApiClient.class})
public interface ApiClientComponent {

    public ApiClient getApiClient();

    void inject(MainActivity mainActivity);
}