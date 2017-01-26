package net.vnnz.app.pixabay;


import retrofit2.Call;
import retrofit2.Response;

public abstract interface RequestListener<T> {

    void onSuccess(Response<T> response);

    void onFailure(Call<T> call, Throwable t);
}