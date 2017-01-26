package net.vnnz.app.pixabay.model.pojo;

public class SearchResult {

    private String total;

    private Hits[] hits;

    private String totalHits;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public Hits[] getHits() {
        return hits;
    }

    public void setHits(Hits[] hits) {
        this.hits = hits;
    }

    public String getTotalHits() {
        return totalHits;
    }

    public void setTotalHits(String totalHits) {
        this.totalHits = totalHits;
    }

    @Override
    public String toString() {
        return "SearchResult [total = " + total + ", hits = " + hits + ", totalHits = " + totalHits + "]";
    }

}
