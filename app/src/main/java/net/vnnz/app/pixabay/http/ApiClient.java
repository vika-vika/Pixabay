package net.vnnz.app.pixabay.http;

import android.util.Log;

import net.vnnz.app.pixabay.model.pojo.SearchResult;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static final String TAG = ApiClient.class.getSimpleName();
    public final String BASE_URL = "https://pixabay.com/api/";
    private Retrofit retrofit = null;
    private OkHttpClient httpClient = new OkHttpClient();

    private Retrofit getClient() {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(httpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient)
                    .build();
        }
        return retrofit;
    }

    public void doSearch (String searchValue, Callback<SearchResult> callback) {

        try {
            searchValue = URLEncoder.encode(searchValue, "utf-8");
            PixabayService apiService = getClient().create(PixabayService.class);
            Call<SearchResult> call = apiService.doSearch(searchValue);

            Log.e(TAG, call.request().toString());

            if (call.isExecuted()) {
                call.cancel();
            }
            call.enqueue(callback);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public OkHttpClient getHttpClient() {
        return httpClient;
    }
}
