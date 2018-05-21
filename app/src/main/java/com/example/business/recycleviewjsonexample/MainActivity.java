package com.example.business.recycleviewjsonexample;

import android.app.DownloadManager;
import android.app.SearchManager;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ExampleAdapter.OnItemClickListener {

    public static final String EXTRA_URL = "imageURL";
    public static final String EXTRA_CREATOR = "creatorName";
    public static final String EXTRA_LIKES = "likeCount";

    private ShoppingDbHelper db;

    Button btn;
    EditText edt;
    TextView txtView;
    Button cart;


    private RecyclerView mRecyclerView;
    private ExampleAdapter mExampleAdapter;
    private CartActivity mCartActivity;
    private ArrayList<ExampleItem> mExampleList;
    private RequestQueue mRequestQueue;
    public static final String BASE_URL = "http://api.walmartlabs.com/v1/search?apiKey=3kgshhj4nr73xrzfmcbthwwb&query=";

    private SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new ShoppingDbHelper(this);




        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        btn = (Button) findViewById(R.id.button2);
        edt = (EditText) findViewById(R.id.editText);

        mExampleList = new ArrayList<>();

        mSearchView = (SearchView) findViewById(R.id.button);

        mRequestQueue = Volley.newRequestQueue(this);
        //parseJSON();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                parseJSON();// TODO Auto-generated method stub
            }
        });
    }

    private void parseJSON() {
        String url = "http://api.walmartlabs.com/v1/search?apiKey=3kgshhj4nr73xrzfmcbthwwb&query=" + edt.getText();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("items");

                            mExampleList.clear();
                            for (int i = 0; i < jsonArray.length(); i++ ) {
                                JSONObject item = jsonArray.getJSONObject(i);
//""items": {itemId,parentItemId, name, etc}
                                String name = item.getString("name");
                                String items = item.getString("thumbnailImage");
                                int salePrice = item.getInt("salePrice");
                                int itemId = item.getInt("itemId");

                                mExampleList.add(new ExampleItem(items, name, salePrice, itemId));
                            }

                            mExampleAdapter = new ExampleAdapter(MainActivity.this, mExampleList);
                            mRecyclerView.setAdapter(mExampleAdapter);
                            mExampleAdapter.setOnItemClickListener(MainActivity.this);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mRequestQueue.add(request);
    }

    @Override
    public void OnItemClick(int position) {
        Intent detailIntent = new Intent(this, DetailActivity.class);
        ExampleItem clickedItem = mExampleList.get(position);

        detailIntent.putExtra(EXTRA_URL, clickedItem.getImageUrl());
        detailIntent.putExtra(EXTRA_CREATOR, clickedItem.getCreator());
        detailIntent.putExtra(EXTRA_LIKES, clickedItem.getLikeCount());

        startActivity(detailIntent);
    }

    @Override
    public void onItemClick(int position) {
        final Intent detailIntent = new Intent(this, DetailActivity.class);
        ExampleItem clickedItem = mExampleList.get(position);
        long returnid = db.addItem(clickedItem.getmNumber(), clickedItem.getImageUrl());
        detailIntent.putExtra(EXTRA_URL, clickedItem.getImageUrl());
        detailIntent.putExtra(EXTRA_CREATOR, clickedItem.getCreator());
        detailIntent.putExtra(EXTRA_LIKES, clickedItem.getLikeCount());
        String message = "Item Already Added";
        if (returnid > 0) {message = "Item Added";}
        Snackbar.make(mRecyclerView, message, Snackbar.LENGTH_LONG)

                .setAction("Action", null).addCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar snackbar, int event) {
                startActivity(detailIntent);
            }
        }).show();

    }






}
