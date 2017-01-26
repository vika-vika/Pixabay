package net.vnnz.app.pixabay.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.vnnz.app.pixabay.R;
import net.vnnz.app.pixabay.model.pojo.Hits;

import java.util.ArrayList;

public class GridLayoutAdapter extends CustomRecyclerViewAdapter {
    private Activity activity;
    private ArrayList<Hits> images;

    private int imgWidth = 500;

    public GridLayoutAdapter(Activity activity, ArrayList<Hits> images) {
        this.activity = activity;
        this.images = images;
    }

    public void setImageWidth (int imgWidth) {
        this.imgWidth = imgWidth;
    }


    @Override
    public CustomRecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.card_image_small, parent, false);
        Holder dataObjectHolder = new Holder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final CustomRecycleViewHolder holder, final int position) {
        final Holder myHolder = (Holder) holder;

     /*   BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(images.get(position), opts);
        opts.inJustDecodeBounds = false;*/

        Picasso.with(activity)
                .load(images.get(position).getPreviewURL())
                .error(R.drawable.no_image_placeholder_big)
                .placeholder(R.drawable.no_image_placeholder_big)
                .resize(imgWidth, 300)
                .centerCrop()
                .into((myHolder.images));

        myHolder.username.setText(images.get(position).getUser());
        myHolder.tags.setText(images.get(position).getTags());


    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class Holder extends CustomRecycleViewHolder {
        private ImageView images;
        private TextView username;
        private TextView tags;

        public Holder(View itemView) {
            super(itemView);
            images = (ImageView) itemView.findViewById(R.id.card_preview_img);
            username = (TextView) itemView.findViewById(R.id.card_preview_name);
            tags = (TextView) itemView.findViewById(R.id.card_preview_tags);
        }
    }
}
