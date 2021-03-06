package net.vnnz.app.pixabay.adapter;

import android.app.Activity;
import android.content.res.Configuration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.vnnz.app.pixabay.R;
import net.vnnz.app.pixabay.model.pojo.Image;
import net.vnnz.app.pixabay.utils.WindowUtils;

import java.util.ArrayList;

public class GridLayoutAdapter extends RecyclerView.Adapter<GridLayoutAdapter.Holder> implements View.OnClickListener {
    private Activity activity;
    private ArrayList<Image> images;

    private int imgWidth;

    public GridLayoutAdapter(Activity activity, ArrayList<Image> images, int configuration) {
        this.activity = activity;
        this.images = images;
        int columnsCount = (configuration == Configuration.ORIENTATION_LANDSCAPE) ? 3 : 2;
        this.imgWidth = WindowUtils.getScreenWidth(activity) / columnsCount;
    }

    public void setImageWidth (int imgWidth) {
        this.imgWidth = imgWidth;
    }


    @Override
    public  Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.card_image_small, parent, false);
        Holder dataObjectHolder = new Holder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final Holder  holder, final int position) {
        final Holder myHolder = (Holder) holder;

        Picasso.with(activity)
                .load(images.get(position).getPreviewURL())
                .error(R.drawable.no_image_placeholder_big)
                .placeholder(R.drawable.no_image_placeholder_big)
                .resize(imgWidth, 300)
                .centerCrop()
                .into((myHolder.images));

        myHolder.username.setText(images.get(position).getUser());
        myHolder.tags.setText(images.get(position).getTags());
        myHolder.getItemView().setOnClickListener(this);
        myHolder.getItemView().setTag(images.get(position));
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    @Override
    public void onClick(View view) {
        if (activity instanceof OnImageClickListener) {
            Image hit = (Image) view.getTag();
            ((OnImageClickListener) activity).onImageClicked(hit);
        }
    }

    public class Holder extends  RecyclerView.ViewHolder  {

        private ImageView images;
        private TextView username;
        private TextView tags;

        public Holder(View itemView) {
            super(itemView);
            images = (ImageView) itemView.findViewById(R.id.card_preview_img);
            username = (TextView) itemView.findViewById(R.id.card_preview_name);
            tags = (TextView) itemView.findViewById(R.id.card_preview_tags);
        }

        public View getItemView() {
            return itemView;
        }
    }
}
