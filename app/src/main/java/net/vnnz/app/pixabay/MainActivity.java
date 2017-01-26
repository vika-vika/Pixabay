package net.vnnz.app.pixabay;


import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import net.vnnz.app.pixabay.adapter.CustomRecyclerViewAdapter;
import net.vnnz.app.pixabay.adapter.GridLayoutAdapter;
import net.vnnz.app.pixabay.http.ApiClient;
import net.vnnz.app.pixabay.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //https://pixabay.com/api/?key=4362477-3b7ccbefba6f8144c9795178e&q=kitten

    private CustomRecyclerViewAdapter adapter;

    public static final String[] IMAGES = new String[]{
            // Heavy images
            "https://cdn.pixabay.com/photo/2015/08/30/10/58/cat-914110_150.jpg",
            "https://cdn.pixabay.com/photo/2014/05/07/06/44/animal-339400_150.jpg",
            "https://cdn.pixabay.com/photo/2016/02/10/16/37/cat-1192026_150.jpg",
            "https://cdn.pixabay.com/photo/2016/09/05/21/37/cat-1647775_150.jpg",
            "https://cdn.pixabay.com/photo/2014/01/17/14/53/cat-246933_150.jpg",
            "https://lh4.googleusercontent.com/-WIuWgVcU3Qw/URqubRVcj4I/AAAAAAAAAbs/YvbwgGjwdIQ/s1024/Antelope%252520Walls.jpg",
            "https://lh6.googleusercontent.com/-UBmLbPELvoQ/URqucCdv0kI/AAAAAAAAAbs/IdNhr2VQoQs/s1024/Apre%2525CC%252580s%252520la%252520Pluie.jpg",
            "https://lh3.googleusercontent.com/-s-AFpvgSeew/URquc6dF-JI/AAAAAAAAAbs/Mt3xNGRUd68/s1024/Backlit%252520Cloud.jpg",
            "https://lh5.googleusercontent.com/-bvmif9a9YOQ/URquea3heHI/AAAAAAAAAbs/rcr6wyeQtAo/s1024/Bee%252520and%252520Flower.jpg",
            "https://lh5.googleusercontent.com/-n7mdm7I7FGs/URqueT_BT-I/AAAAAAAAAbs/9MYmXlmpSAo/s1024/Bonzai%252520Rock%252520Sunset.jpg",
            "https://lh6.googleusercontent.com/-4CN4X4t0M1k/URqufPozWzI/AAAAAAAAAbs/8wK41lg1KPs/s1024/Caterpillar.jpg",
            "https://lh3.googleusercontent.com/-rrFnVC8xQEg/URqufdrLBaI/AAAAAAAAAbs/s69WYy_fl1E/s1024/Chess.jpg",
            "https://lh5.googleusercontent.com/-WVpRptWH8Yw/URqugh-QmDI/AAAAAAAAAbs/E-MgBgtlUWU/s1024/Chihuly.jpg",
            "https://lh5.googleusercontent.com/-0BDXkYmckbo/URquhKFW84I/AAAAAAAAAbs/ogQtHCTk2JQ/s1024/Closed%252520Door.jpg",
            "https://lh3.googleusercontent.com/-PyggXXZRykM/URquh-kVvoI/AAAAAAAAAbs/hFtDwhtrHHQ/s1024/Colorado%252520River%252520Sunset.jpg",
            "https://lh3.googleusercontent.com/-ZAs4dNZtALc/URquikvOCWI/AAAAAAAAAbs/DXz4h3dll1Y/s1024/Colors%252520of%252520Autumn.jpg",
            "https://lh4.googleusercontent.com/-GztnWEIiMz8/URqukVCU7bI/AAAAAAAAAbs/jo2Hjv6MZ6M/s1024/Countryside.jpg",
            "https://lh4.googleusercontent.com/-bEg9EZ9QoiM/URquklz3FGI/AAAAAAAAAbs/UUuv8Ac2BaE/s1024/Death%252520Valley%252520-%252520Dunes.jpg",
    };

    private RecyclerView recyclerViewMain;

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

        recyclerViewMain = (RecyclerView) findViewById(R.id.cards_container_recycler_view);

        RecyclerView.LayoutManager mLayoutManager =  new GridLayoutManager(this, 2);
        recyclerViewMain.setHasFixedSize(true);
        recyclerViewMain.setLayoutManager(mLayoutManager);

        adapter = new GridLayoutAdapter(this, getImages());
        recyclerViewMain.setAdapter(adapter);

        new ApiClient().doSearch("fruits");
    }

    private ArrayList<String> getImages() {
        ArrayList<String> images = new ArrayList<>();
        for (int i = 0; i < IMAGES.length; i++) {
            images.add(IMAGES[i]);
        }
        return images;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();

       /*     RecyclerView.LayoutManager mLayoutManager =  new GridLayoutManager(this, 3);
            recyclerViewMain.setLayoutManager(mLayoutManager);
            adapter = new GridLayoutAdapter(this, getImages());
            recyclerViewMain.setAdapter(adapter);*/

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }
    }
}
