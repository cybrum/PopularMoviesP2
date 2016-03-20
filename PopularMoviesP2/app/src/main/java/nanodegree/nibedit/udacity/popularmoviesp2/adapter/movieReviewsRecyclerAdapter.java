package nanodegree.nibedit.udacity.popularmoviesp2.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import nanodegree.nibedit.udacity.popularmoviesp2.model.MovieReviews;
import nanodegree.nibedit.udacity.popularmoviesp2.fragment.onRecyclerItemViewClickListener;
import nanodegree.nibedit.udacity.popularmoviesp2.fragment.R;

import java.util.List;

/**
 * Description :
 * Created on : 2/7/2016
 * Author     : Nibedit Dey
 */
public class movieReviewsRecyclerAdapter extends RecyclerView.Adapter<movieReviewsRecyclerAdapter.ViewHolder> implements View.OnClickListener{

    private List<MovieReviews> items;
    private int itemLayout;
    private onRecyclerItemViewClickListener<MovieReviews> itemClickListener;

    public movieReviewsRecyclerAdapter(List<MovieReviews> items,int itemLayout){
        this.itemLayout=itemLayout;
        this.items=items;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(itemLayout,parent,false);
        v.setOnClickListener(this);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
       MovieReviews reviewItem=items.get(position);
        holder.itemView.setTag(reviewItem);
        holder.reviewAuthor.setText(reviewItem.getAuthor());
        holder.reviewContent.setText(reviewItem.getContent());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onClick(View v) {
        if (itemClickListener != null) {
            MovieReviews model = (MovieReviews) v.getTag();
            itemClickListener.onItemClick(v, model);
        }

    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView reviewAuthor;
        public TextView reviewContent;
        public ViewHolder(View view){
            super(view);
            reviewAuthor=(TextView)view.findViewById(R.id.review_author);
            reviewContent=(TextView)view.findViewById(R.id.review_content);

        }

    }
}
