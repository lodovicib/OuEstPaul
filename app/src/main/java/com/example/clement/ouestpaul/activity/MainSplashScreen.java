package com.example.clement.ouestpaul.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.clement.ouestpaul.R;

/**
 * Created by Adrien on 14/01/2015.
 */
public class MainSplashScreen extends Activity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.welcome);

// METHOD 1

            /****** Create Thread that will sleep for 5 seconds *************/
            Thread background = new Thread() {
                public void run() {

                    try {
                        // Thread will sleep for 5 seconds
                        sleep(4*1000);

                        // After 5 seconds redirect to another intent
                    Intent i=new Intent(getBaseContext(),MenuActivity.class);
                        startActivity(i);

                        //Remove activity
                        finish();

                    } catch (Exception e) {

                    }
                }
            };

            // start thread
            background.start();
        }

        @Override
        protected void onDestroy() {

            super.onDestroy();

        }
    }
