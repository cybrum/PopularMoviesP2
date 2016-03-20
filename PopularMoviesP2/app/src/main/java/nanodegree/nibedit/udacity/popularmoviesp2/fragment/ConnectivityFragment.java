package nanodegree.nibedit.udacity.popularmoviesp2.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import nanodegree.nibedit.udacity.popularmoviesp2.utils.CheckConnectivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Description :
 * Created on : 2/7/2016
 * Author     : Nibedit Dey
 */
public class ConnectivityFragment extends Fragment {


    @Bind(R.id.ConnectivityText)
    TextView connectivityText;
    @Bind(R.id.refresh_main_page)
    Button refresh_page;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.activity_display_no_connectivity, container, false);
        final ConnectivityFragment connectivityFragment = this;
        ButterKnife.bind(this, rootView);
        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar_connectivity);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        refresh_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (new CheckConnectivity(rootView.getContext()).isConnectedToInternet()) {

                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new PopularMovieFragment(), "").commit();

                } else {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, connectivityFragment);
                }

            }
        });
        return rootView;
    }
}
