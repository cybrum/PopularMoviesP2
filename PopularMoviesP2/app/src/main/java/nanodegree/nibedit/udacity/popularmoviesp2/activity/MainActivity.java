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
    private int sortType = 0;
    private static final String FRAGMENT_TYPE = "";
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (savedInstanceState == null) {
            if (new CheckConnectivity(getApplicationContext()).isConnectedToInternet()) {
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_popular) {
            id = 0;
        }
        else if(id == R.id.action_highest_rated){
            id = 1;
        } else if(id == R.id.action_fav){
            id = 2;
        }


        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, getFragmentType(id))
                .addToBackStack(null)
                .commit();

        return true;
    }
    private Fragment getFragmentType(int sortType) {
        switch (sortType) {
            case 0:
                return PopularMovieFragment.newInstance();
            case 1:
                return TopRatedMovieFragment.newInstance();
            case 2:
                return FavouriteMovieFragment.newInstance();

        }
        return null;
    }
}
