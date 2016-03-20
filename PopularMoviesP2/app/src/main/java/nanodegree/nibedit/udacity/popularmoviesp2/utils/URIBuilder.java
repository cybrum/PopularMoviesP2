package nanodegree.nibedit.udacity.popularmoviesp2.utils;

import android.net.Uri;

import nanodegree.nibedit.udacity.popularmoviesp2.model.MovieTrailers;

/**
 * Description :
 * Created on : 2/7/2016
 * Author     : Nibedit Dey
 */
public class URIBuilder {

    public String buildURI(String queryType,String queryPageNumber){
        Uri.Builder builder=null;
        builder=Uri.parse(Constants.MOVIE_DB_SORT_BASE_URL).buildUpon()
                .appendQueryParameter("api_key", Constants.MOVIE_DB_API_KEY)
                .appendQueryParameter("page", queryPageNumber)
                .appendQueryParameter("sort_by",queryType);
        Uri uri=builder.build();
        return uri.toString();
    }

    public String buildURI(String queryType){
        Uri.Builder builder=null;
        builder=Uri.parse(Constants.MOVIE_DB_SORT_BASE_URL).buildUpon()
                .appendQueryParameter("api_key", Constants.MOVIE_DB_API_KEY)
                .appendQueryParameter("sort_by",queryType);
        Uri uri=builder.build();
        return uri.toString();
    }

    public String buildTrailerURL(MovieTrailers trailersItem){
        String key = trailersItem.getKey();
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("www.youtube.com")
                .appendPath("watch")
                .appendQueryParameter("v", key);
        return  builder.build().toString();
    }

}
