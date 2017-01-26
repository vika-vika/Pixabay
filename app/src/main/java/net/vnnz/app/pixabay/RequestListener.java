package net.vnnz.app.pixabay;


import retrofit2.Response;

public abstract interface RequestListener<T> {

    void onSuccess(Response<T> response);

    void onFailure(Throwable t);
}