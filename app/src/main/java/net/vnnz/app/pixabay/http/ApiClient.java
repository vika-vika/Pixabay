package net.vnnz.app.pixabay.http;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import net.vnnz.app.pixabay.model.pojo.SearchResult;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static final String TAG = ApiClient.class.getSimpleName();
    public final String BASE_URL = "https://pixabay.com/api/";
    private Retrofit retrofit = null;
    private OkHttpClient httpClient = new OkHttpClient();
    private Handler handler;
    private Context context;

    private Retrofit getClient() {

        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                  //  .(new OkClient(httpClient))
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient)
                    .build();
        }
        return retrofit;
    }

    public void doSearch (String searchValue, Callback<SearchResult> callback) {
        PixabayService apiService = getClient().create(PixabayService.class);
        Call<SearchResult> call = apiService.doSearch(searchValue);
      //  call.request().tag()
        if (call.isExecuted()) {
            call.cancel();
        }
        call.enqueue(callback);
    }

    public OkHttpClient getHttpClient() {
        return httpClient;
    }
}
