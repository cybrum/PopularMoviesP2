package nanodegree.nibedit.udacity.popularmoviesp2.activity;

import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;

import nanodegree.nibedit.udacity.popularmoviesp2.fragment.MovieDetailsFragment;
import nanodegree.nibedit.udacity.popularmoviesp2.fragment.R;
import nanodegree.nibedit.udacity.popularmoviesp2.model.MovieDetails;
import nanodegree.nibedit.udacity.popularmoviesp2.utils.Constants;

import butterknife.ButterKnife;

/**
 * Description :
 * Created on : 2/7/2016
 * Author     : Nibedit Dey
 */
public class MovieDetailsActivity extends BaseActivity{

    MovieDetails movieDetails;
    private ShareActionProvider mShareActionProvider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);
        movieDetails = getIntent().getParcelableExtra(Constants.PARCELABLE_KEY);
        if (savedInstanceState == null) {
            MovieDetailsFragment fragment = MovieDetailsFragment.newInstance(movieDetails);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.movie_detail_container, fragment)
                    .commit();
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
        outState.putParcelable(Constants.PARCELABLE_KEY, movieDetails);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

}
