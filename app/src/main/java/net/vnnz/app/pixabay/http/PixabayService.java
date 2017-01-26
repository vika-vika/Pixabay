package net.vnnz.app.pixabay.http;

import net.vnnz.app.pixabay.model.pojo.SearchResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PixabayService {

    @GET("?key=4362477-3b7ccbefba6f8144c9795178e")
    Call<SearchResult> doSearch(@Query("q") String searchValue);
}
