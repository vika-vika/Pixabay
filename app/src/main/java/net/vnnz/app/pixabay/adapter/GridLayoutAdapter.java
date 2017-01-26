package net.vnnz.app.pixabay.adapter;

import android.app.Activity;
import android.content.res.Configuration;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.vnnz.app.pixabay.R;
import net.vnnz.app.pixabay.model.pojo.Hits;
import net.vnnz.app.pixabay.utils.WindowUtils;

import java.util.ArrayList;

public class GridLayoutAdapter extends RecyclerView.Adapter<GridLayoutAdapter.Holder> {
    private Activity activity;
    private ArrayList<Hits> images;

    private int imgWidth;

    public GridLayoutAdapter(Activity activity, ArrayList<Hits> images, int configuration) {
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
        myHolder.holder.setTag(images.get(position));
        myHolder.holder.setOnClickListener((View.OnClickListener) activity);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class Holder extends  RecyclerView.ViewHolder  {

        private ImageView images;
        private TextView username;
        private TextView tags;
        private CardView holder;

        public Holder(View itemView) {
            super(itemView);
            images = (ImageView) itemView.findViewById(R.id.card_preview_img);
            username = (TextView) itemView.findViewById(R.id.card_preview_name);
            tags = (TextView) itemView.findViewById(R.id.card_preview_tags);
            holder = (CardView) itemView.findViewById(R.id.card_holder);
        }
    }
}
