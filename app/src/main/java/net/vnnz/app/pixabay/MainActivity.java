package net.vnnz.app.pixabay;

import android.content.Intent;
import android.content.res.Configuration;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.Toolbar;
import android.util.Log;

import android.view.View;
import android.widget.EditText;

import net.vnnz.app.pixabay.adapter.GridLayoutAdapter;
import net.vnnz.app.pixabay.dialog.ConfirmationDialog;
import net.vnnz.app.pixabay.http.ApiClient;

import net.vnnz.app.pixabay.model.pojo.Hits;
import net.vnnz.app.pixabay.model.pojo.SearchResult;
import net.vnnz.app.pixabay.utils.WindowUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RequestListener<SearchResult>,  View.OnClickListener,  ConfirmationDialog.OnDialogFragmentClickListener {

    private GridLayoutAdapter adapter;
    private ArrayList<Hits>  images = new ArrayList<>();

    private RecyclerView recyclerViewMain;
    private ApiClient client;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.search_icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setEnabled(true);
                editText.requestFocus();
            }
        });

        editText = (EditText) findViewById(R.id.search_value);
        recyclerViewMain = (RecyclerView) findViewById(R.id.cards_container_recycler_view);

        int columnCount = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 3 : 2;

        RecyclerView.LayoutManager mLayoutManager =  new GridLayoutManager(this, columnCount);
        recyclerViewMain.setHasFixedSize(true);
        recyclerViewMain.setLayoutManager(mLayoutManager);

        adapter = new GridLayoutAdapter(this, images, getResources().getConfiguration().orientation);
        recyclerViewMain.setAdapter(adapter);

        client = new ApiClient();
        client.doSearch("fruits", new RequestCallback<SearchResult>(this));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        int columnCount = newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE ? 3 : 2;
        adapter.setImageWidth(WindowUtils.getScreenWidth(this) / columnCount);
        RecyclerView.LayoutManager mLayoutManager =  new GridLayoutManager(this, columnCount);
        recyclerViewMain.setLayoutManager(mLayoutManager);

    }

    @Override
    public void onSuccess(Response<SearchResult> response) {

        if (response.isSuccessful()) {
            images = response.body().getHits();
            adapter = new GridLayoutAdapter(this, images, getResources().getConfiguration().orientation);
            recyclerViewMain.setAdapter(adapter);
        }
    }

    @Override
    public void onFailure(Call<SearchResult> call, Throwable t) {
        if (call.isCanceled()) {
            Log.e("TAG", "is cancelled " + call.request().url());
        }

    }

    public void onToolbarIconClick(View view) {
        client.getHttpClient().dispatcher().cancelAll();
       // client.doSearch(editable.toString(), new RequestCallback<SearchResult>(this));
    }

    @Override
    public void onClick(View view) {
        Hits hits = (Hits) view.getTag();
        Log.e("TAG", hits.toString());

        ConfirmationDialog dialogFrag = ConfirmationDialog.newInstance(hits);
        dialogFrag.show(getSupportFragmentManager(), "dialog");
    }

    @Override
    public void onPositiveClicked(Object data) {
        Intent i = new Intent(this, ImageActivity.class);
        i.putExtra("Hits", (Hits) data);
        startActivity(i);
    }

    @Override
    public void onNegativeClicked() {

    }
}
