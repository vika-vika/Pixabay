package net.vnnz.app.pixabay;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import net.vnnz.app.pixabay.databinding.CardImageFullBinding;
import net.vnnz.app.pixabay.model.pojo.Hits;

public class ImageActivity extends AppCompatActivity {

    public static final String EXTRA_HITS = "net.vnnz.app.pixabay.EXTRA_HITS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CardImageFullBinding binding = DataBindingUtil.setContentView(this, R.layout.card_image_full);

        Hits hit = getIntent().getParcelableExtra(EXTRA_HITS);
        binding.setImage(hit);

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

        Picasso.with (ImageActivity.this)
                .load(hit.getWebformatURL())
                .placeholder (R.drawable.no_image_placeholder_big)
                .error (R.drawable.search_icon)
                .fit()
                .centerCrop()
                .into((ImageView) findViewById(R.id.card_image));
    }
}
