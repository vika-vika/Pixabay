package net.vnnz.app.pixabay.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import net.vnnz.app.pixabay.model.pojo.SearchResult;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static final String TAG = ApiClient.class.getSimpleName();
    public final String BASE_URL = "https://pixabay.com/api/";
    private Retrofit retrofit = null;
    private OkHttpClient httpClient = new OkHttpClient();
    private Context context;

    private Retrofit getClient(Context context) {
        this.context = context;

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(getOkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient)
                    .build();
        }
        return retrofit;
    }

    public void doSearch (Context context, String searchValue, RequestCallback<SearchResult> callback) {

        callback.showProgress(true);

        try {
            searchValue = URLEncoder.encode(searchValue, "utf-8");
            PixabayService apiService = getClient(context).create(PixabayService.class);
            Call<SearchResult> call = apiService.doSearch(searchValue);

            if (call.isExecuted()) {
                call.cancel();
            }
            call.enqueue(callback);
        } catch (UnsupportedEncodingException e) {
            callback.onFailure(null, e.fillInStackTrace());
        }
    }

    public OkHttpClient getOkHttpClient() {

        File httpCacheDirectory = new File(context.getCacheDir(), "responses");
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(httpCacheDirectory, cacheSize);

        httpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(REWRITE_RESPONSE_INTERCEPTOR)
                .addInterceptor(OFFLINE_INTERCEPTOR)
                .cache(cache)
                .build();
        return httpClient;
    }

    private final Interceptor REWRITE_RESPONSE_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Response originalResponse = chain.proceed(chain.request());
            String cacheControl = originalResponse.header("Cache-Control");

            if (cacheControl == null || cacheControl.contains("no-store") || cacheControl.contains("no-cache") ||
                    cacheControl.contains("must-revalidate") || cacheControl.contains("max-age=0")) {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, max-age=" + 10)
                        .build();
            } else {
                return originalResponse;
            }
        }
    };

    private final Interceptor OFFLINE_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            if (!isOnline()) {
                Log.d(TAG, "rewriting request. Reason : offline");

                int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                request = request.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
            return chain.proceed(request);
        }

    };

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
