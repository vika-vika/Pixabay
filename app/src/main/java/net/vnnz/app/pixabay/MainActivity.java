package net.vnnz.app.pixabay;

import android.content.Intent;
import android.content.res.Configuration;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.vnnz.app.pixabay.adapter.GridLayoutAdapter;
import net.vnnz.app.pixabay.adapter.OnImageClickListener;
import net.vnnz.app.pixabay.dialog.ConfirmationDialog;
import net.vnnz.app.pixabay.http.ApiClient;

import net.vnnz.app.pixabay.http.RequestCallback;
import net.vnnz.app.pixabay.http.RequestListener;
import net.vnnz.app.pixabay.model.pojo.Hits;
import net.vnnz.app.pixabay.model.pojo.SearchResult;
import net.vnnz.app.pixabay.utils.WindowUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RequestListener<SearchResult>, View.OnClickListener, ConfirmationDialog.OnDialogFragmentClickListener, OnImageClickListener {

    private GridLayoutAdapter adapter;
    private ArrayList<Hits> images = new ArrayList<>();

    private RecyclerView recyclerViewMain;
    private LinearLayout searchLt;

    private ApiClient client;
    private EditText searchEt;
    private TextView searchText;
    private Toolbar toolbar;

    private String DEFAULT_REQUEST = "fruits";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchLt = (LinearLayout) findViewById(R.id.search_layout);
        searchText = (TextView) findViewById(R.id.search_text);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.search_icon);
        toolbar.setNavigationOnClickListener(this);

        searchEt = (EditText) findViewById(R.id.search_value);
        recyclerViewMain = (RecyclerView) findViewById(R.id.cards_container_recycler_view);

        int columnCount = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 3 : 2;

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, columnCount);
        recyclerViewMain.setHasFixedSize(true);
        recyclerViewMain.setLayoutManager(mLayoutManager);

        adapter = new GridLayoutAdapter(this, images, getResources().getConfiguration().orientation);
        recyclerViewMain.setAdapter(adapter);

        client = new ApiClient();
        client.doSearch(DEFAULT_REQUEST, new RequestCallback<SearchResult>(this));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        int columnCount = newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE ? 3 : 2;
        adapter.setImageWidth(WindowUtils.getScreenWidth(this) / columnCount);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, columnCount);
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
    public void onFailure(Call<SearchResult> call, Throwable t) {}

    @Override
    public void onClick(View view) {
        enableSearchLayout (searchLt.getVisibility() != View.VISIBLE);
    }

    private void enableSearchLayout(boolean isEnabled) {
        searchLt.setVisibility(isEnabled ? View.VISIBLE : View.GONE);
        toolbar.setNavigationIcon(isEnabled  ? R.drawable.abc_ic_ab_back_material : R.drawable.search_icon);
        searchText.setVisibility(isEnabled ? View.GONE : View.VISIBLE);
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

    public void onSearchClick(View view) {
        client.doSearch(searchEt.getText().toString(), new RequestCallback<SearchResult>(this));
        enableSearchLayout (false);
    }

    @Override
    public void onImageClicked(Hits hit) {
        ConfirmationDialog dialogFrag = ConfirmationDialog.newInstance(hit);
        dialogFrag.show(getSupportFragmentManager(), "dialog");
    }
}
