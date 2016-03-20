package nanodegree.nibedit.udacity.popularmoviesp2.fragment;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import nanodegree.nibedit.udacity.popularmoviesp2.activity.MovieReviewsActivity;
import nanodegree.nibedit.udacity.popularmoviesp2.adapter.MovieTrailerRecyclerAdapter;
import nanodegree.nibedit.udacity.popularmoviesp2.data.MovieDBContract;
import nanodegree.nibedit.udacity.popularmoviesp2.model.MovieDetails;
import nanodegree.nibedit.udacity.popularmoviesp2.model.MovieTrailers;
import nanodegree.nibedit.udacity.popularmoviesp2.utils.Constants;
import nanodegree.nibedit.udacity.popularmoviesp2.utils.URIBuilder;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Description :
 * Created on : 2/7/2016
 * Author     : Nibedit Dey
 */
public class MovieDetailsFragment extends Fragment implements View.OnClickListener, ShareActionProvider.OnShareTargetSelectedListener {
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @Bind(R.id.plot_synopsis)
    TextView plot;
    @Bind(R.id.movieOriginalTitle)
    TextView movieTitle;
    @Bind(R.id.image_backdrop)
    ImageView header;
    @Bind(R.id.ratingGiven)
    TextView ratingGiven;
    @Bind(R.id.releaseDate)
    TextView releaseDate;
    @Bind(R.id.poster_details_activity)
    ImageView posterView;
    @Bind(R.id.favourite_btn)
    FloatingActionButton fav_btn;
    @Bind(R.id.btn_all_review)
    Button btnSeeReviews;
    @Bind(R.id.RecyclerTrailerList)
    RecyclerView recyclerViewTrailerList;
    List<MovieTrailers> movieTrailersList = new ArrayList<>();
    MovieDetails movieDetails;
    private ShareActionProvider mShareActionProvider;

    public static MovieDetailsFragment newInstance(MovieDetails movieDetails) {
        MovieDetailsFragment movieDetailsFragment = new MovieDetailsFragment();
        Bundle arguments = new Bundle();
        arguments.putParcelable(Constants.PARCELABLE_KEY, movieDetails);
        movieDetailsFragment.setArguments(arguments);
        return movieDetailsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.movie_details_layout, container, false);
        ButterKnife.bind(this, rootView);
        final Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.anim_toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        final boolean tabletSize = getResources().getBoolean(R.bool.isTablet);
        activity.setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
        if (!tabletSize) {
            movieDetails = activity.getIntent().getParcelableExtra(Constants.PARCELABLE_KEY);
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } else {
            // toolbar.inflateMenu(R.menu.menu_movie_details);
            Bundle bundle = getArguments();
            movieDetails = bundle.getParcelable(Constants.PARCELABLE_KEY);
        }
        collapsingToolbar.setTitle(movieDetails.getMovieOriginalTitle());
        changeFabBackGrndtoFav();
        ratingGiven.setText(movieDetails.getMovieRating() + "");
        releaseDate.setText(movieDetails.getMovieDate());
        movieTitle.setText(movieDetails.getMovieOriginalTitle());
        String posterURL = Constants.MOVIE_DB_IMAGE_BASE_URL + movieDetails.getImageThumbnail();
        String URL = Constants.MOVIE_DB_IMAGE_BASE_URL_w500 + movieDetails.getImageBackDrop();
        Picasso.with(rootView.getContext()).load(posterURL).into(posterView);
        Picasso.with(rootView.getContext()).load(URL).into(header, new Callback() {
            @Override
            public void onSuccess() {
                if (!tabletSize) {
                    Bitmap bitmap = ((BitmapDrawable) header.getDrawable()).getBitmap();
                    Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                        public void onGenerated(Palette palette) {
                            applyPalette(palette);
                        }
                    });
                }
            }

            @Override
            public void onError() {

            }
        });
        plot.setText(movieDetails.getMoviePlot());

        jsonRequestTrailer(Constants.MOVIE_DB_TRAILERS_URL.replace("$", movieDetails.getMovieID() + ""));
        btnSeeReviews.setOnClickListener(this);
        fav_btn.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @SuppressWarnings("unchecked")
    private void jsonRequestTrailer(String URL) {
        {
            new FragmentController().trailerJsonRequest(URL, getContext(), new onTaskCompleted() {
                @Override
                public void onSuccess(Object object) {
                    if (object != null) {
                        movieTrailersList.addAll((List<MovieTrailers>) object);
                        recyclerViewTrailerList.setNestedScrollingEnabled(false);
                        recyclerViewTrailerList.setHasFixedSize(false);
                        recyclerViewTrailerList.setAdapter(new MovieTrailerRecyclerAdapter(movieTrailersList, R.layout.movie_trailer_design));
                        final WrappingLinearLayoutManager layoutManager = new WrappingLinearLayoutManager(getActivity());
                        layoutManager.setOrientation(WrappingLinearLayoutManager.HORIZONTAL);
                        recyclerViewTrailerList.setLayoutManager(layoutManager);
                    }
                }
            });

        }

    }

    private void applyPalette(Palette palette) {
        Palette.Swatch vibrantDark = palette.getDarkVibrantSwatch();
        if (vibrantDark != null) {
            //Palette.Swatch vibrantLight=palette.;
            collapsingToolbar.setContentScrimColor(vibrantDark.getRgb());
            collapsingToolbar.setStatusBarScrimColor(vibrantDark.getRgb());
        }

    }

    private ContentValues generateContentValues(MovieDetails movie) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MovieDBContract.MovieEntry.COLUMN_MOVIES_ID, movie.getMovieID());
        contentValues.put(MovieDBContract.MovieEntry.COLUMN_MOVIE_NAME, movie.getMovieOriginalTitle());
        contentValues.put(MovieDBContract.MovieEntry.COLUMN_RATING, movie.getMovieRating());
        contentValues.put(MovieDBContract.MovieEntry.COLUMN_RELEASE_DATE, movie.getMovieDate());
        contentValues.put(MovieDBContract.MovieEntry.COLUMN_IMAGE_URL, movie.getImageThumbnail());
        contentValues.put(MovieDBContract.MovieEntry.COLUMN_LANGUAGE, movie.getOriginalLangauge());
        contentValues.put(MovieDBContract.MovieEntry.COLUMN_ADULT, movie.getAdultType());
        contentValues.put(MovieDBContract.MovieEntry.COLUMN_OVERVIEW, movie.getMoviePlot());
        contentValues.put(MovieDBContract.MovieEntry.COLUMN_BACKDROP_PATH, movie.getImageBackDrop());
        contentValues.put(MovieDBContract.MovieEntry.COLUMN_POPULARITY, movie.getPopularity());
        contentValues.put(MovieDBContract.MovieEntry.COLUMN_VOTE_COUNT, movie.getVoteCount());
        contentValues.put(MovieDBContract.MovieEntry.COLUMN_VIDEO, movie.getVideoAvailable());

        return contentValues;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.favourite_btn) {
            Uri uri = MovieDBContract.MovieEntry.CONTENT_URI.buildUpon().appendPath(movieDetails.getMovieID() + "").build();
            //Cursor movieCursor = getActivity().getContentResolver().query(uri, null, null, null, null);
            if (!isFavourite(movieDetails)) {
                ContentValues contentValues = generateContentValues(movieDetails);
                Uri insertedUri = getActivity().getContentResolver().insert(MovieDBContract.MovieEntry.CONTENT_URI, contentValues);
                long id = ContentUris.parseId(insertedUri);
                if (id != -1) {
                    changeFabBackGrndtoFav();
                    showSnackBarLong(coordinatorLayout, "Added to favourites");
                }
            } else {
                int id = getActivity().getContentResolver().delete(uri, null, null);
                if (id != -1) {
                    changeFabBackGrndtoFav();
                }
                showSnackBarLong(coordinatorLayout, "Removed from favorites");
            }
        } else if (v.getId() == R.id.btn_all_review) {
            Intent intent = new Intent(v.getContext(), MovieReviewsActivity.class);
            intent.putExtra("MovieID", movieDetails.getMovieID());
            startActivity(intent);
        }
    }

    private void changeFabBackGrndtoFav() {
        String uri = isFavourite(movieDetails) ? "@drawable/ic_favourite_selected" : "@drawable/ic__mark_favourite";
        int imageResource = getResources().getIdentifier(uri, null, getActivity().getPackageName());
        Drawable res = getResources().getDrawable(imageResource);
        fav_btn.setImageDrawable(res);

    }

    protected boolean isFavourite(MovieDetails movieDetails) {
        Uri uri = MovieDBContract.MovieEntry.CONTENT_URI.buildUpon().appendPath(movieDetails.getMovieID() + "").build();
        Cursor movieCursor = null;
        try {
            movieCursor = getActivity().getContentResolver().query(uri, null, null, null, null);
            if (movieCursor != null) {
                return movieCursor.moveToNext();
            }
        } finally {
            movieCursor.close();
        }

        return false;
    }

    protected void showSnackBarLong(CoordinatorLayout coordinatorLayout, String message) {
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_movie_details, menu);
        MenuItem item = menu.findItem(R.id.action_share);
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(sharevideoIntent());
            mShareActionProvider.setOnShareTargetSelectedListener(this);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return id == R.id.action_settings;
    }

    private Intent sharevideoIntent() {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setAction(Intent.ACTION_SEND);
        String URL = "";
        if (movieTrailersList.size() > 0) {
            URL = new URIBuilder().buildTrailerURL(movieTrailersList.get(0));
        }
        intent.putExtra(Intent.EXTRA_TEXT, URL);
        intent.setType("text/plain");
        return intent;
    }

    @Override
    public boolean onShareTargetSelected(ShareActionProvider source, Intent inten) {
        mShareActionProvider.setShareIntent(sharevideoIntent());
        return true;
    }
}
