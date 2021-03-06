/*
 * MIT License
 *
 * Copyright (c) 2017 Attiq ur Rehman
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.attiqrao.systemsltd.list_to_do;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.attiqrao.systemsltd.list_to_do.view.MainView;
import com.attiqrao.systemsltd.list_to_do.view.SplashView;

import java.util.Random;

public class SplashScreen extends AppCompatActivity {

    private ViewGroup mMainView;
    private SplashView mSplashView;
    private Handler mHandler = new Handler();
    private String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mMainView = new MainView(getApplicationContext());
        mSplashView = (SplashView) findViewById(R.id.splash_view);

        startLoadingData();

        findViewById(R.id.custom_signin_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SplashScreen.this, MainActivity.class));
                finish();
            }
        });
    }

    private void startLoadingData() {
        // finish "loading data" in a random time between 1 and 3 seconds
        Random random = new Random();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                onLoadingDataEnded();
            }
        }, 1000 + random.nextInt(2000));
    }

    private void onLoadingDataEnded() {
//        Context context = getApplicationContext();

        // start the splash animation
        mSplashView.splashAndDisappear(new SplashView.ISplashListener() {
            @Override
            public void onStart() {
                // log the animation start event
                if (BuildConfig.DEBUG) {
                    Log.d(TAG, "splash started");
                }
            }

            @Override
            public void onUpdate(float completionFraction) {
                // log animation update events
                if (BuildConfig.DEBUG) {
                    Log.d(TAG, "splash at " + String.format("%.2f", (completionFraction * 100)) + "%");
                }
            }

            @Override
            public void onEnd() {
                // log the animation end event
                if (BuildConfig.DEBUG) {
                    Log.d(TAG, "splash ended");
                }
                // free the view so that it turns into garbage
                mSplashView = null;
//                if(!DO_XML){
                // if inflating from code we will also have to free the reference in MainView as well
                // otherwise we will leak the View, this could be done better but so far it will suffice
//                    ((MainView) mMainView).unsetSplashView();

                // now that our data is loaded we can initialize the content view
//                }
            }
        });
    }
}