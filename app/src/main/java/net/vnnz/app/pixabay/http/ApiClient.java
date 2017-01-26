package net.vnnz.app.pixabay.http;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import net.vnnz.app.pixabay.model.pojo.SearchResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static final String TAG = ApiClient.class.getSimpleName();
    public final String BASE_URL = "https://pixabay.com/api/";
    private Retrofit retrofit = null;
    private Handler handler;
    private Context context;

    private Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public void doSearch (String searchValue) {
        PixabayService apiService = getClient().create(PixabayService.class);
        Call<SearchResult> call = apiService.doSearch(searchValue);

        call.enqueue(new Callback<SearchResult>() {
            @Override
            public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
                Log.e(TAG, response.body().toString());
            }

            @Override
            public void onFailure(Call<SearchResult> call, Throwable t) {

            }
        });
    }
}
