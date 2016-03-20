package nanodegree.nibedit.udacity.popularmoviesp2.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;

import nanodegree.nibedit.udacity.popularmoviesp2.activity.MovieDetailsActivity;
import nanodegree.nibedit.udacity.popularmoviesp2.adapter.MovieListAdapter;
import nanodegree.nibedit.udacity.popularmoviesp2.model.MovieDetails;
import nanodegree.nibedit.udacity.popularmoviesp2.utils.Constants;
import nanodegree.nibedit.udacity.popularmoviesp2.utils.URIBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Description :
 * Created on : 2/7/2016
 * Author     : Nibedit Dey
 */
public abstract class BaseFragment extends Fragment implements AdapterView.OnItemClickListener {
    int pageCount = 1;
    @Bind(R.id.movie_grid_list)
    GridView movieGridList;
    List<MovieDetails> movieDetailsList = new ArrayList<>();
    private MovieListAdapter movieListAdapter;
    private int itemClicked = 0;
    private int changeFrag = -1;
    boolean tabletSize;
    Activity activity;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        tabletSize = getResources().getBoolean(R.bool.isTablet);
        ButterKnife.bind(this, rootView);
        activity = getActivity();
        movieGridList.setOnItemClickListener(this);
        movieGridList.setOnScrollListener(new GridViewScrollListener());
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Configuration.ORIENTATION_LANDSCAPE == getResources().getConfiguration().orientation) {
            movieGridList.setNumColumns(3);
        } else {
            movieGridList.setNumColumns(GridView.AUTO_FIT);
        }
    }

    protected abstract void onScrollCompleted(int pageCount);

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null) {
            movieDetailsList = savedInstanceState.getParcelableArrayList("moviesList");
        }
        movieListAdapter = new MovieListAdapter(getContext(), movieDetailsList);
        movieGridList.setAdapter(movieListAdapter);

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("moviesList", (ArrayList<? extends Parcelable>) movieDetailsList);

    }

    protected void jsonRequest(String sortType, String pageNumber) {
        String URL = new URIBuilder().buildURI(sortType, pageNumber);
        //final ProgressDialog progressDialog = new ProgressDialog(getContext());
        //progressDialog.show();
        FragmentController fragmentController = new FragmentController();
        fragmentController.jsonRequest(URL, getContext(), new onTaskCompleted() {
            @Override
            public void onSuccess(Object object) {
                if (object != null) {
                    movieDetailsList.addAll((List<MovieDetails>) object);
                    movieListAdapter.notifyDataSetChanged();
                    //progressDialog.hide();
                   /* if(tabletSize &&(itemClicked!=changeFrag)){
                        changeFrag=itemClicked;
                        MovieDetailsFragment fragment = MovieDetailsFragment.newInstance(movieDetailsList.get(itemClicked));
                        getFragmentManager().beginTransaction()
                                .replace(R.id.movie_detail_container, fragment)
                                .commit();
                    }*/
                }
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //boolean tabletSize = getResources().getBoolean(R.bool.isTablet);
        itemClicked = position;
        movieGridList.setItemChecked(position, true);
        if (!tabletSize) {
            Intent intent = new Intent(view.getContext(), MovieDetailsActivity.class);
            Bundle mBundle = new Bundle();
            mBundle.putParcelable(Constants.PARCELABLE_KEY, movieDetailsList.get(position));
            intent.putExtras(mBundle);
            startActivity(intent);
        } else {
            MovieDetailsFragment fragment = MovieDetailsFragment.newInstance(movieDetailsList.get(position));
            getFragmentManager().beginTransaction()
                    .replace(R.id.movie_detail_container, fragment)
                    .commit();
        }
    }

    public final class GridViewScrollListener implements AbsListView.OnScrollListener {
        public int currentScrollState;
        public int currentFirstVisibleItem;
        public int currentVisibleItemCount;

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            this.currentScrollState = scrollState;
            this.isScrollCompleted();
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (firstVisibleItem + visibleItemCount >= totalItemCount) {
                this.currentFirstVisibleItem = firstVisibleItem;
                this.currentVisibleItemCount = visibleItemCount;
            }

        }

        private void isScrollCompleted() {
            if (this.currentVisibleItemCount > 0 && this.currentScrollState == SCROLL_STATE_IDLE) {
                pageCount++;
                onScrollCompleted(pageCount);

            }
        }
    }

}
