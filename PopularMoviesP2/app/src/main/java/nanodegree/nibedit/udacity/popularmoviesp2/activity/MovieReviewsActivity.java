package nanodegree.nibedit.udacity.popularmoviesp2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import nanodegree.nibedit.udacity.popularmoviesp2.model.MovieReviews;
import nanodegree.nibedit.udacity.popularmoviesp2.fragment.R;
import nanodegree.nibedit.udacity.popularmoviesp2.adapter.movieReviewsRecyclerAdapter;
import nanodegree.nibedit.udacity.popularmoviesp2.utils.Constants;
import nanodegree.nibedit.udacity.popularmoviesp2.utils.JsonNetworkManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Description :
 * Created on : 2/7/2016
 * Author     : Nibedit Dey
 */
public class MovieReviewsActivity extends BaseActivity {
    String TAG=MovieReviewsActivity.class.getSimpleName();
    @Bind(R.id.RecyclerReviewList)RecyclerView recyclerViewReview;
    List<MovieReviews> reviewList=new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_reviews_recycler_layout);
        Intent in=getIntent();
        Bundle bundle=in.getExtras();
        int movieID= bundle.getInt("MovieID");
        final Toolbar toolbar = (Toolbar) findViewById(R.id.review_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        jsonRequestHandler(Constants.MOVIE_DB_REVIEWS_URL.replace("$",movieID+""));

    }

    public void jsonRequestHandler(String URL){
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                URL, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (reviewList.size() > 0) {
                                reviewList.clear();
                            }
                            JSONArray array = response.getJSONArray("results");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject movieReviewsObject = array.getJSONObject(i);
                                MovieReviews movieReviews = new MovieReviews();
                                movieReviews.setId(movieReviewsObject.getString(Constants.KEY_MOVIE_REVIEW_ID));
                                movieReviews.setAuthor(movieReviewsObject.getString(Constants.KEY_MOVIE_REVIEW_AUTHOR));
                                movieReviews.setContent(movieReviewsObject.getString(Constants.KEY_MOVIE_REVIEW_CONTENT));
                                reviewList.add(movieReviews);
                            }
                            Log.d(TAG, response.toString());
                        } catch (Exception e) {

                        }
                        recyclerViewReview.setAdapter(new movieReviewsRecyclerAdapter(reviewList,R.layout.movie_reviews_design));
                        final LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        recyclerViewReview.setLayoutManager(layoutManager);
                        Log.d(TAG, response.toString());

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                // hide the progress dialog

            }
        });
        JsonNetworkManager.getInstance(getApplicationContext()).addToRequestQueue(jsonObjReq);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
