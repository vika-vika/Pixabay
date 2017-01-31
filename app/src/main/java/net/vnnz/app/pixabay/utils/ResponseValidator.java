package net.vnnz.app.pixabay.utils;


import android.content.Context;

import net.vnnz.app.pixabay.R;

import net.vnnz.app.pixabay.model.pojo.SearchResult;

import retrofit2.Response;

public class ResponseValidator {

    // validate the response, add 400 status code check etc

    public static String validateSearch (Context context, Response<SearchResult> response){
        String error = "";
        if (response.isSuccessful()) {
            if (response.body().getImages().size() == 0) {
                error = context.getString(R.string.no_images);
            }
        } else {
            error = context.getString(R.string.common_error);
        }
        return error;
    }
}
