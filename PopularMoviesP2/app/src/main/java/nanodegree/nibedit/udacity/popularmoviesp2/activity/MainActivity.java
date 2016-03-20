package nanodegree.nibedit.udacity.popularmoviesp2.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import nanodegree.nibedit.udacity.popularmoviesp2.fragment.ConnectivityFragment;
import nanodegree.nibedit.udacity.popularmoviesp2.fragment.FavouriteMovieFragment;
import nanodegree.nibedit.udacity.popularmoviesp2.fragment.PopularMovieFragment;
import nanodegree.nibedit.udacity.popularmoviesp2.fragment.R;
import nanodegree.nibedit.udacity.popularmoviesp2.fragment.TopRatedMovieFragment;
import nanodegree.nibedit.udacity.popularmoviesp2.utils.CheckConnectivity;

/**
 * Description :
 * Created on : 12/25/2015
 * Author     : Nibedit Dey
 */
public class MainActivity extends BaseActivity {

    private boolean mTwoPane;
    String[] sortByItems = {"Popularity", "Rating", "Favourite"};
    private int sortType = 0;
    private static final String FRAGMENT_TYPE = "fragmenttype";
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (savedInstanceState == null) {
            if (new CheckConnectivity(getApplicationContext()).isConnectedToInternet()) {
                toolbar.setTitle("Popular");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, PopularMovieFragment.newInstance()).commit();

            } else {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new ConnectivityFragment()).commit();
            }
            mTwoPane = findViewById(R.id.movie_detail_container) != null;
        }
        else
        {
            sortType=savedInstanceState.getInt(FRAGMENT_TYPE);
        }


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(FRAGMENT_TYPE, sortType);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_sort) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setItems(sortByItems, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (sortType != which) {
                        sortType = which;
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, getFragmentType(sortType))
                                .addToBackStack(null)
                                .commit();
                    }
                }
            }).create().show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private Fragment getFragmentType(int sortType) {
        switch (sortType) {
            case 0:
                toolbar.setTitle("Popular");
                return PopularMovieFragment.newInstance();
            case 1:
                toolbar.setTitle("Top Rated");
                return TopRatedMovieFragment.newInstance();
            case 2:
                toolbar.setTitle("Favourite");
                return FavouriteMovieFragment.newInstance();

        }
        return null;
    }

}
