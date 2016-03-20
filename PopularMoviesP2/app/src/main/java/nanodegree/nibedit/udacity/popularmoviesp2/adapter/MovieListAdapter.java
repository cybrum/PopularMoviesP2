package nanodegree.nibedit.udacity.popularmoviesp2.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import nanodegree.nibedit.udacity.popularmoviesp2.fragment.R;
import nanodegree.nibedit.udacity.popularmoviesp2.model.MovieDetails;
import nanodegree.nibedit.udacity.popularmoviesp2.utils.Constants;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Description :
 * Created on : 2/7/2016
 * Author     : Nibedit Dey
 */
public class MovieListAdapter extends BaseAdapter  {
    private Context mContext;
    private List<MovieDetails> movieDetailsList;


    public MovieListAdapter(Context c, List<MovieDetails> movieDetailsList) {
        mContext = c;
        this.movieDetailsList = movieDetailsList;
    }

    @Override
    public int getCount() {

        return movieDetailsList.size();
    }

    @Override
    public Object getItem(int position) {
        return movieDetailsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final MovieDetails currentMovieDetails = (MovieDetails) getItem(position);
        View row = convertView;
        final ViewHolder holder ;
        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(R.layout.grid_item_layout, parent, false);
            holder = new ViewHolder();
            holder.image = (ImageView) row.findViewById(R.id.poster);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        String URL = Constants.MOVIE_DB_IMAGE_BASE_URL + currentMovieDetails.getImageThumbnail();
        Picasso.with(mContext).load(URL).into(holder.image);
        Picasso.with(mContext).load(URL).into(holder.image, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {

            }
        });

        return row;
    }

    static class ViewHolder {
        ImageView image;
    }
}
