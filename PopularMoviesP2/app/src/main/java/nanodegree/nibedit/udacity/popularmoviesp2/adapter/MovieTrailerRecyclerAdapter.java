package nanodegree.nibedit.udacity.popularmoviesp2.adapter;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import nanodegree.nibedit.udacity.popularmoviesp2.model.MovieTrailers;
import nanodegree.nibedit.udacity.popularmoviesp2.fragment.onRecyclerItemViewClickListener;
import nanodegree.nibedit.udacity.popularmoviesp2.fragment.R;
import com.squareup.picasso.Picasso;
import nanodegree.nibedit.udacity.popularmoviesp2.utils.Constants;

import java.util.List;

/**
 * Description :
 * Created on : 2/7/2016
 * Author     : Nibedit Dey
 */
public class MovieTrailerRecyclerAdapter extends RecyclerView.Adapter<MovieTrailerRecyclerAdapter.ViewHolder> implements View.OnClickListener {

    private List<MovieTrailers> items;
    private int itemLayout;
    private onRecyclerItemViewClickListener<MovieTrailers> itemClickListener;

    public MovieTrailerRecyclerAdapter(List<MovieTrailers> items, int itemLayout) {
        this.itemLayout = itemLayout;
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        v.setOnClickListener(this);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final MovieTrailers trailersItem = items.get(position);
        holder.itemView.setTag(trailersItem);
        int trailerNo=position+1;
        holder.trailerNo.setText("Trailer " + trailerNo);
        Picasso.with(holder.trailer.getContext()).load(Constants.YOUTUBE_TRAILER_THUMBNAIL.replace("$", trailersItem.getKey())).into(holder.trailer);
        holder.trailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = trailersItem.getKey();
                String site = trailersItem.getSite();
                Uri.Builder builder = new Uri.Builder();
                builder.scheme("http")
                        .authority("www.youtube.com")
                        .appendPath("watch")
                        .appendQueryParameter("v", key);
                String myUrl = builder.build().toString();
                v.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(myUrl)));
                Log.i("Tag", myUrl);
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onClick(View v) {
        if (itemClickListener != null) {
            MovieTrailers model = (MovieTrailers) v.getTag();
            itemClickListener.onItemClick(v, model);
        }

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView trailer;
        public TextView trailerNo;

        public ViewHolder(View view) {
            super(view);
            trailer = (ImageView) view.findViewById(R.id.trailer_list);
            trailerNo = (TextView) view.findViewById(R.id.trailer_list_number);


        }

    }
}

