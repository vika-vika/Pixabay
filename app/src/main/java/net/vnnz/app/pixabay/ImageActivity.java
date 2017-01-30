package net.vnnz.app.pixabay;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.vnnz.app.pixabay.model.pojo.Hits;
import net.vnnz.app.pixabay.view.SocialView;

public class ImageActivity extends AppCompatActivity {

    public static final String EXTRA_HITS = "net.vnnz.app.pixabay.EXTRA_HITS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_image_full);

        Hits hit = getIntent().getParcelableExtra(EXTRA_HITS);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getSupportActionBar().setTitle(hit.getUser());

    /*    TextView text = (TextView) findViewById(R.id.card_username);
        text.setText(hit.getUser());*/

        ImageView cardImage = (ImageView) findViewById(R.id.card_image);

        Picasso.with (ImageActivity.this)
                .load(hit.getWebformatURL())
                .placeholder (R.drawable.no_image_placeholder_big)
                .error (R.drawable.search_icon)
                .fit()
                .centerInside()
                .into(cardImage);

        TextView tags = (TextView) findViewById(R.id.card_tags);
        tags.setText(hit.getTags());

        SocialView socialView = (SocialView) findViewById(R.id.card_social);
        socialView.setLikes(hit.getLikes());
        socialView.setComments(hit.getComments());
        socialView.setFavourits(hit.getFavorites());
    }
}
