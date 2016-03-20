package nanodegree.nibedit.udacity.popularmoviesp2.fragment;

import android.view.View;

/**
 * Description :
 * Created on : 2/7/2016
 * Author     : Nibedit Dey
 */
public interface onRecyclerItemViewClickListener<Model> {
    void onItemClick(View view,Model model);
}
