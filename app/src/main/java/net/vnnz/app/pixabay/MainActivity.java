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
import android.widget.ProgressBar;
import android.widget.TextView;

import net.vnnz.app.pixabay.adapter.GridLayoutAdapter;
import net.vnnz.app.pixabay.adapter.OnImageClickListener;
import net.vnnz.app.pixabay.dialog.ConfirmationDialog;
import net.vnnz.app.pixabay.http.ApiClient;

import net.vnnz.app.pixabay.http.RequestCallback;
import net.vnnz.app.pixabay.http.RequestListener;
import net.vnnz.app.pixabay.model.pojo.Image;
import net.vnnz.app.pixabay.model.pojo.SearchResult;
import net.vnnz.app.pixabay.utils.ResponseValidator;

import java.util.ArrayList;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RequestListener<SearchResult>, View.OnClickListener, ConfirmationDialog.OnDialogFragmentClickListener, OnImageClickListener {

    private GridLayoutAdapter adapter;
    private ArrayList<Image> images = new ArrayList<>();

    private RecyclerView recyclerViewMain;
    private LinearLayout searchLt;
    private EditText searchEt;
    private TextView searchText;
    private Toolbar toolbar;
    private ProgressBar progressBar;

    private String DEFAULT_REQUEST = "fruits";

    @Inject ApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchLt = (LinearLayout) findViewById(R.id.search_layout);
        searchText = (TextView) findViewById(R.id.search_text);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.search_icon);
        toolbar.setNavigationOnClickListener(this);

        searchEt = (EditText) findViewById(R.id.search_value);
        recyclerViewMain = (RecyclerView) findViewById(R.id.cards_container_recycler_view);
        setGridLayout();

        adapter = new GridLayoutAdapter(this, images, getResources().getConfiguration().orientation);
        recyclerViewMain.setAdapter(adapter);

        ApiClientComponent component = DaggerApiClientComponent.builder().apiClient(new ApiClient()).build();
        component.inject(this);

        client.doSearch(this, DEFAULT_REQUEST, new RequestCallback<SearchResult>(this));
    }

    private void setGridLayout (){
        int columnCount = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 3 : 2;
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, columnCount);
        recyclerViewMain.setHasFixedSize(true);
        recyclerViewMain.setLayoutManager(mLayoutManager);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setGridLayout();
    }

    @Override
    public void onSuccess(Response<SearchResult> response) {
        String error = ResponseValidator.validateSearch(this, response);

        if (error.isEmpty()) {
            images = response.body().getImages();
            adapter = new GridLayoutAdapter(this, images, getResources().getConfiguration().orientation);
            recyclerViewMain.setAdapter(adapter);
            recyclerViewMain.setVisibility(View.VISIBLE);
        } else {
            showError(error);
            recyclerViewMain.setVisibility(View.GONE);
        }
    }

    @Override
    public void onFailure(Call<SearchResult> call, Throwable t) {
        showError(getString(R.string.common_error));
    }

    @Override
    public void showProgress(boolean b) {
        progressBar.setVisibility(b ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onClick(View view) {
        enableSearchLayout (searchLt.getVisibility() != View.VISIBLE);
    }

    private void enableSearchLayout(boolean isEnabled) {
        searchLt.setVisibility(isEnabled ? View.VISIBLE : View.GONE);
        toolbar.setNavigationIcon(isEnabled  ? R.drawable.abc_ic_ab_back_material : R.drawable.search_icon);
        searchText.setVisibility(isEnabled ? View.GONE : View.VISIBLE);
        if (isEnabled) {
            searchEt.requestFocus();
        }
    }

    private void showError(String error) {
        TextView errorTv = (TextView) findViewById(R.id.error_placeholder);
        errorTv.setText(error);
    }

    @Override
    public void onPositiveClicked(Object data) {
        Intent i = new Intent(this, ImageActivity.class);
        i.putExtra(ImageActivity.EXTRA_HITS, (Image) data);
        startActivity(i);
    }

    @Override
    public void onNegativeClicked() {
    }

    public void onSearchClick(View view) {
        client.doSearch(this, searchEt.getText().toString(), new RequestCallback<SearchResult>(this));
        searchText.setText(searchEt.getText().toString());
        searchEt.setText("");
        enableSearchLayout (false);
    }

    @Override
    public void onImageClicked(Image hit) {
        ConfirmationDialog dialogFrag = ConfirmationDialog.newInstance(hit);
        dialogFrag.show(getSupportFragmentManager(), "dialog");
    }
}
