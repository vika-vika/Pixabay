package net.vnnz.app.pixabay;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.vnnz.app.pixabay.model.pojo.Hits;


public class ImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_image_full);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Hits hit = getIntent().getParcelableExtra("Hits");

        TextView text = (TextView) findViewById(R.id.card_username);
        text.setText(hit.getUser());

        ImageView cardImage = (ImageView) findViewById(R.id.card_image);
        Log.e("TAG", "hit.getWebformatURL()" + hit.getWebformatURL());

        Picasso.with (ImageActivity.this)
                .load(hit.getWebformatURL())
                .placeholder (R.drawable.no_image_placeholder_big)
                .error (R.drawable.search_icon)
                .fit()
                .into(cardImage);

        TextView likes = (TextView) findViewById(R.id.likes_count);
        likes.setText(hit.getLikes().isEmpty() ? "0" : hit.getLikes());

        TextView comments = (TextView) findViewById(R.id.comments_count);
        comments.setText(hit.getComments());
    }
}
