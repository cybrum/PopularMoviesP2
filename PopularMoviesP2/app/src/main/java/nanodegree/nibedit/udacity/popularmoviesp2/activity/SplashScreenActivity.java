package nanodegree.nibedit.udacity.popularmoviesp2.activity;

import nanodegree.nibedit.udacity.popularmoviesp2.fragment.R;
import android.os.Bundle;
import android.content.Intent;
import android.os.Handler;

/**
 * Description : Splash Screen
 * Created on : 1/10/2016
 * Author     : Nibedit Dey
 */

public class SplashScreenActivity extends BaseActivity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

}