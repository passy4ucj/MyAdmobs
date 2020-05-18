package com.example.goldearn;

import com.example.goldearn.utils.misc.Configs;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class BreakGame extends AppCompatActivity {

    private static final int START_LEVEL = 1;
    private int mLevel;
    private Button mNextLevelButton;
    private InterstitialAd mInterstitialAd;
    private TextView mLevelTextView;

    private static Context appContext = App.getContext();

    // Screen Size boolean Check
    private static boolean isTelevision;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_break_game);

        // Create the next level button, which tries to show an interstitial when clicked.
        mNextLevelButton = findViewById(R.id.next_level_button);
        mNextLevelButton.setEnabled(false);
        mNextLevelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInterstitial();
            }
        });

        // Create the text view to show the level number.
        mLevelTextView = findViewById(R.id.level);
        mLevel = START_LEVEL;

        // Create the InterstitialAd and set the adUnitId (defined in values/strings.xml).
        mInterstitialAd = new InterstitialAd(this);
        initInterstitialAd();

        isTelevision = Configs.isTelevision(this);
    }

    public void initInterstitialAd() {
        if (BuildConfig.DEBUG) {
            mInterstitialAd.setAdUnitId(getResources().getString(R.string.test_interstitial_ad_unit_id));
        } else {
            mInterstitialAd.setAdUnitId(getResources().getString(R.string.interstitial_ad_unit_id));
        }
        requestNewInterstitial();
        runAdEvents();
    }

    private void runAdEvents() {
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                mNextLevelButton.setEnabled(true);
                requestNewInterstitial();
                Toast.makeText(appContext, "Interstitial Ad Loaded", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                mNextLevelButton.setEnabled(true);
                requestNewInterstitial();
                Toast.makeText(appContext, "Interstitial Failed to Load", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdClosed() {
                // Proceed to the next level.
                goToNextLevel();
                Toast.makeText(appContext, "Interstitial Ad Closed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void requestNewInterstitial() {
        if (null != mInterstitialAd) {
            mInterstitialAd.loadAd(new AdRequest.Builder().build());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_break_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showInterstitial() {
        // Show the ad if it's ready. Otherwise toast and reload the ad.
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Toast.makeText(this, "Ad did not load", Toast.LENGTH_SHORT).show();
            goToNextLevel();
        }
    }

    private void goToNextLevel() {
        // Show the next level and reload the ad to prepare for the level after.
        mLevelTextView.setText("Level " + (++mLevel));
        requestNewInterstitial();
    }

    public static boolean isTelevision() {
        return isTelevision;
    }

    /** Called when leaving the activity */
    @Override
    public void onPause() {
        super.onPause();
    }

    /** Called when returning to the activity */
    @Override
    public void onResume() {
        super.onResume();
    }

    /** Called before the activity is destroyed */
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    //pascal added
}
