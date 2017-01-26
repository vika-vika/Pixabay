package net.vnnz.app.pixabay;

import android.content.res.Configuration;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import android.view.View;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

import net.vnnz.app.pixabay.adapter.GridLayoutAdapter;
import net.vnnz.app.pixabay.http.ApiClient;

import net.vnnz.app.pixabay.model.pojo.Hits;
import net.vnnz.app.pixabay.model.pojo.SearchResult;
import net.vnnz.app.pixabay.utils.WindowUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RequestListener<SearchResult>, TextWatcher,  View.OnClickListener {

    private GridLayoutAdapter adapter;
    private ArrayList<Hits>  images = new ArrayList<>();

    private RecyclerView recyclerViewMain;
    private ApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewMain = (RecyclerView) findViewById(R.id.cards_container_recycler_view);

        int columnCount = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 3 : 2;

        RecyclerView.LayoutManager mLayoutManager =  new GridLayoutManager(this, columnCount);
        recyclerViewMain.setHasFixedSize(true);
        recyclerViewMain.setLayoutManager(mLayoutManager);

        adapter = new GridLayoutAdapter(this, images, getResources().getConfiguration().orientation);
        recyclerViewMain.setAdapter(adapter);

        client = new ApiClient();
        client.doSearch("fruits", new RequestCallback<SearchResult>(this));

        EditText searchField = (EditText) findViewById(R.id.search_value);
        searchField.addTextChangedListener(this);

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
        Animation in  = AnimationUtils.loadAnimation(this, R.anim.left_to_right_in);
        Animation out = AnimationUtils.loadAnimation(this, R.anim.left_to_right_out);

      /*  ((ImageSwitcher)view).setInAnimation(in);
        ((ImageSwitcher)view).setOutAnimation(out);
        ((ImageSwitcher)view).setImageResource(R.drawable.abc_ic_ab_back_material);*/
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        client.getHttpClient().dispatcher().cancelAll();
        client.doSearch(editable.toString(), new RequestCallback<SearchResult>(this));
    }


    @Override
    public void onClick(View view) {
        Hits hits = (Hits) view.getTag();
        Toast.makeText(this, hits.getId()+"", Toast.LENGTH_LONG).show();
    }

}
