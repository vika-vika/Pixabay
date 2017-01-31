package net.vnnz.app.pixabay.model.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SearchResult {

    private String total;

    @SerializedName("hits")
    private ArrayList<Image> images;

    private String totalHits;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public ArrayList<Image> getImages() {
        return images;
    }

    public void setImages(ArrayList<Image> images) {
        this.images = images;
    }

    public String getTotalHits() {
        return totalHits;
    }

    public void setTotalHits(String totalHits) {
        this.totalHits = totalHits;
    }

    @Override
    public String toString() {
        return "SearchResult [total = " + total + ", images = " + images + ", totalHits = " + totalHits + "]";
    }

}
