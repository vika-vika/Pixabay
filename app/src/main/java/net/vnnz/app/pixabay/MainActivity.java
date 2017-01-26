package net.vnnz.app.pixabay;


import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import net.vnnz.app.pixabay.adapter.GridLayoutAdapter;
import net.vnnz.app.pixabay.http.ApiClient;
import net.vnnz.app.pixabay.R;
import net.vnnz.app.pixabay.model.pojo.Hits;
import net.vnnz.app.pixabay.model.pojo.SearchResult;

import java.util.ArrayList;

import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RequestListener<SearchResult> {

    private GridLayoutAdapter adapter;
    private ArrayList<Hits> images;

    private RecyclerView recyclerViewMain;
    private int screenWidth;
    private int screenHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_search);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;

        recyclerViewMain = (RecyclerView) findViewById(R.id.cards_container_recycler_view);

        RecyclerView.LayoutManager mLayoutManager =  new GridLayoutManager(this, 2);
        recyclerViewMain.setHasFixedSize(true);
        recyclerViewMain.setLayoutManager(mLayoutManager);
        images = new ArrayList<>();

        adapter = new GridLayoutAdapter(this, images);
        adapter.setImageWidth(screenWidth / 2);
        recyclerViewMain.setAdapter(adapter);

        new ApiClient().doSearch("fruits", new RequestCallback<SearchResult>(this));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            adapter.setImageWidth(screenHeight / 3);
            RecyclerView.LayoutManager mLayoutManager =  new GridLayoutManager(this, 3);
            recyclerViewMain.setLayoutManager(mLayoutManager);

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            adapter.setImageWidth(screenWidth / 2);
            RecyclerView.LayoutManager mLayoutManager =  new GridLayoutManager(this, 2);
            recyclerViewMain.setLayoutManager(mLayoutManager);
        }
    }

    @Override
    public void onSuccess(Response<SearchResult> response) {

        if (response.isSuccessful()) {
            images = response.body().getHits();
            adapter = new GridLayoutAdapter(this, images);
            recyclerViewMain.setAdapter(adapter);
        }
    }

    @Override
    public void onFailure(Throwable t) {

    }
}
