package net.vnnz.app.pixabay.model.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class Image implements Parcelable{
    private String tags;

    private String imageHeight;

    private String webformatHeight;

    private String previewHeight;

    private String previewURL;

    private String favorites;

    private String type;

    private String previewWidth;

    private String downloads;

    private String userImageURL;

    private String pageURL;

    private String id;

    private String views;

    private String likes;

    private String user_id;

    private String webformatWidth;

    private String webformatURL;

    private String user;

    private String imageWidth;

    private String comments;

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(String imageHeight) {
        this.imageHeight = imageHeight;
    }

    public String getWebformatHeight() {
        return webformatHeight;
    }

    public void setWebformatHeight(String webformatHeight) {
        this.webformatHeight = webformatHeight;
    }

    public String getPreviewHeight() {
        return previewHeight;
    }

    public void setPreviewHeight(String previewHeight) {
        this.previewHeight = previewHeight;
    }

    public String getPreviewURL() {
        return previewURL;
    }

    public void setPreviewURL(String previewURL) {
        this.previewURL = previewURL;
    }

    public String getFavorites() {
        return favorites;
    }

    public void setFavorites(String favorites) {
        this.favorites = favorites;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPreviewWidth() {
        return previewWidth;
    }

    public void setPreviewWidth(String previewWidth) {
        this.previewWidth = previewWidth;
    }

    public String getDownloads() {
        return downloads;
    }

    public void setDownloads(String downloads) {
        this.downloads = downloads;
    }

    public String getUserImageURL() {
        return userImageURL;
    }

    public void setUserImageURL(String userImageURL) {
        this.userImageURL = userImageURL;
    }

    public String getPageURL() {
        return pageURL;
    }

    public void setPageURL(String pageURL) {
        this.pageURL = pageURL;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getWebformatWidth() {
        return webformatWidth;
    }

    public void setWebformatWidth(String webformatWidth) {
        this.webformatWidth = webformatWidth;
    }

    public String getWebformatURL() {
        return webformatURL;
    }

    public void setWebformatURL(String webformatURL) {
        this.webformatURL = webformatURL;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(String imageWidth) {
        this.imageWidth = imageWidth;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Image [tags = " + tags + ", imageHeight = " + imageHeight + ", webformatHeight = " + webformatHeight + ", previewHeight = " + previewHeight + ", previewURL = " + previewURL + ", favorites = " + favorites + ", type = " + type + ", previewWidth = " + previewWidth + ", downloads = " + downloads + ", userImageURL = " + userImageURL + ", pageURL = " + pageURL + ", id = " + id + ", views = " + views + ", likes = " + likes + ", user_id = " + user_id + ", webformatWidth = " + webformatWidth + ", webformatURL = " + webformatURL + ", user = " + user + ", imageWidth = " + imageWidth + ", comments = " + comments + "]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(tags);
        parcel.writeString(previewURL);
        parcel.writeString(favorites);
        parcel.writeString(downloads);
        parcel.writeString(userImageURL);
        parcel.writeString(id);
        parcel.writeString(views);
        parcel.writeString(likes);
        parcel.writeString(user_id);
        parcel.writeString(webformatURL);
        parcel.writeString(user);
        parcel.writeString(comments);
    }

    protected Image(Parcel in) {
        tags = in.readString();
        previewURL = in.readString();
        favorites = in.readString();
        downloads = in.readString();
        userImageURL = in.readString();
        id = in.readString();
        views = in.readString();
        likes = in.readString();
        user_id = in.readString();
        webformatURL = in.readString();
        user = in.readString();
        comments = in.readString();
    }

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };
}