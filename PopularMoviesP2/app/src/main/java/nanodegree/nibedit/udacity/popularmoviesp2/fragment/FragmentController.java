package nanodegree.nibedit.udacity.popularmoviesp2.fragment;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import nanodegree.nibedit.udacity.popularmoviesp2.data.MovieDBContract;
import nanodegree.nibedit.udacity.popularmoviesp2.model.MovieDetails;
import nanodegree.nibedit.udacity.popularmoviesp2.model.MovieTrailers;
import nanodegree.nibedit.udacity.popularmoviesp2.utils.Constants;
import nanodegree.nibedit.udacity.popularmoviesp2.utils.JsonNetworkManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Description :
 * Created on : 2/7/2016
 * Author     : Nibedit Dey
 */
public class FragmentController {
    List<MovieDetails> movieDetailsList = new ArrayList<>();



    public void jsonRequest(String URL, final Context context, final onTaskCompleted onTaskCompleted) {
        {


            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                    URL, null,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray array = response.getJSONArray("results");
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject movieDetailsObject = array.getJSONObject(i);
                                    MovieDetails movieDetails = new MovieDetails();
                                    movieDetails.setMovieID(movieDetailsObject.getInt(Constants.KEY_ID));
                                    movieDetails.setImageThumbnail(movieDetailsObject.getString(Constants.KEY_POSTER_PATH));
                                    movieDetails.setMovieDate(movieDetailsObject.getString(Constants.KEY_RELEASE_DATE));
                                    movieDetails.setMoviePlot(movieDetailsObject.getString(Constants.KEY_OVERVIEW));
                                    movieDetails.setMovieRating(movieDetailsObject.getDouble(Constants.KEY_VOTE_AVEARGE));
                                    movieDetails.setMovieOriginalTitle(movieDetailsObject.getString(Constants.KEY_ORIGINAL_TITLE));
                                    movieDetails.setImageBackDrop(movieDetailsObject.getString(Constants.KEY_BACKDROP_PATH));
                                    movieDetails.setOriginalLangauge(movieDetailsObject.getString(Constants.KEY_ORIGINAL_LANGUAGE));
                                    movieDetails.setVoteCount(movieDetailsObject.getInt(Constants.KEY_VOTE_COUNT));
                                    movieDetails.setPopularity(movieDetailsObject.getInt(Constants.KEY_POPULARITY));
                                    movieDetails.setAdultType(movieDetailsObject.getString(Constants.KEY_ADULT));
                                    movieDetails.setVideoAvailable(movieDetailsObject.getString(Constants.KEY_VIDEO));
                                    Uri uri = MovieDBContract.MovieEntry.CONTENT_URI.buildUpon().appendPath(movieDetails.getMovieID() + "").build();
                                    Cursor movieCursor = context.getContentResolver().query(uri, null, null, null, null);
                                    if (movieCursor.moveToNext() != true) {
                                        movieDetails.setIsFavourite(false);
                                    } else {
                                        movieDetails.setIsFavourite(true);
                                    }
                                    movieDetailsList.add(movieDetails);
                                    Log.i("Object", movieDetailsObject.getString("original_title"));
                                }


                            } catch (Exception e) {
                                Log.i("Exception", e + "");
                            }
                            onTaskCompleted.onSuccess(movieDetailsList);



                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    //VolleyLog.d(TAG, "Error: " + error.getMessage());

                }
            });
            JsonNetworkManager.getInstance(context).addToRequestQueue(jsonObjReq);

        }
    }

    public void trailerJsonRequest(String URL, final Context context, final onTaskCompleted onTaskCompleted) {
        final List<MovieTrailers> movieTrailersList = new ArrayList<>();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (movieTrailersList.size() > 0) {
                                movieTrailersList.clear();
                            }
                            JSONArray array = response.getJSONArray("results");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject movieTrailersObject = array.getJSONObject(i);
                                MovieTrailers movieTrailers = new MovieTrailers();
                                movieTrailers.setKey(movieTrailersObject.getString(Constants.KEY_MOVIE_TRAILER_KEY));
                                movieTrailers.setSite(movieTrailersObject.getString(Constants.KEY_MOVIE_TRAILER_SITE));
                                movieTrailersList.add(movieTrailers);
                            }
                        } catch (Exception e) {

                        }
                        onTaskCompleted.onSuccess(movieTrailersList);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                // hide the progress dialog

            }
        });
        JsonNetworkManager.getInstance(context).addToRequestQueue(jsonObjReq);

    }

}

