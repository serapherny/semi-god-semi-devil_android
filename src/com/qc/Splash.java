package com.qc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Splash extends Activity {

    private final int SPLASH_DISPLAY_LENGHT = 1000;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.splashscreen);

        /*
         * New Handler to start the Menu-Activity
         *
         * and close this Splash-Screen after some seconds.
         */

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(Splash.this, SemiPrototypeActivity.class);
                startActivity(mainIntent);
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }

        }, SPLASH_DISPLAY_LENGHT);
    }
}
