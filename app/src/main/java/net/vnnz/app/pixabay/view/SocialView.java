package net.vnnz.app.pixabay.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.vnnz.app.pixabay.R;
import net.vnnz.app.pixabay.model.pojo.Image;

public class SocialView extends LinearLayout {

    private TextView likes;
    private TextView comments;
    private TextView faves;

    public SocialView(Context context) {
        super(context);
        initView(context, null);
    }

    public SocialView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public SocialView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        View view = inflate(getContext(), R.layout.view_social, this);

        if (!isInEditMode()) {
            likes = (TextView) view.findViewById(R.id.likes_count);
            comments = (TextView) view.findViewById(R.id.comments_count);
            faves = (TextView) view.findViewById(R.id.faves_count);
        }
    }

    public void setLikes (String text){
        likes.setText(format(text));
    }

    public void setComments (String text){
        comments.setText(format(text));
    }

    public void setFavourits (String text){
        faves.setText(format(text));
    }

    private String format (String str){
        return ((str == null) || str.isEmpty())  ? "0" : str;
    }

    public void setImage(Image image) {
        setLikes(image.getLikes());
        setComments(image.getComments());
        setFavourits(image.getFavorites());
    }
}
