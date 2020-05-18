package com.example.goldearn.ui;

import android.content.Context;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.example.goldearn.BuildConfig;
import com.example.goldearn.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.util.Objects;

import static com.example.goldearn.BreakGame.isTelevision;


/**
 * @author Created by Emmanuel Nwokoma (Founder and CEO at Gigabyte Developers) on 05/18/2020
 **/
public class AdWrapper extends FrameLayout {

    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    private boolean showInterstiatial = true;

    public AdWrapper(Context context) {
        super(context);
        init(context);
    }

    public AdWrapper(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AdWrapper(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        // Ads
        if(!isTelevision() && !BuildConfig.DEBUG) {
            LayoutInflater.from(context).inflate(R.layout.ads_wrapper, this, true);
            initAd();
        } else if (!isTelevision()) {
            LayoutInflater.from(context).inflate(R.layout.ads_wrapper_test, this, true);
            initAd();
        } else {
            mInterstitialAd = new InterstitialAd(context);
            initInterstitialAd();
        }
    }

    public void initInterstitialAd() {
        if (BuildConfig.DEBUG) {
            mInterstitialAd.setAdUnitId(getResources().getString(R.string.test_interstitial_ad_unit_id));
        } else {
            mInterstitialAd.setAdUnitId(getResources().getString(R.string.interstitial_ad_unit_id));
        }
        requestNewInterstitial();
    }

    public void initAd() {
        if (BuildConfig.DEBUG) {
            // Show Test Banner Ads
            mAdView = findViewById(R.id.adViewTest);
            AdListener adListener = new AdListener() {
                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    mAdView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAdFailedToLoad(int errorCode) {
                    super.onAdFailedToLoad(errorCode);
                    mAdView.setVisibility(View.GONE);
                    mAdView.loadAd(new AdRequest.Builder().build());
                }
            };
            mAdView.setAdListener(adListener);
        } else {
            // Show Banner Ads
            mAdView = findViewById(R.id.adView);
            AdListener adListener = new AdListener() {
                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    mAdView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAdFailedToLoad(int errorCode) {
                    super.onAdFailedToLoad(errorCode);
                    mAdView.setVisibility(View.GONE);
                    mAdView.loadAd(new AdRequest.Builder().build());
                }
            };
            mAdView.setAdListener(adListener);
        }
    }

    private void requestNewInterstitial() {
        if (null != mInterstitialAd) {
            mInterstitialAd.loadAd(new AdRequest.Builder().build());
        }
    }

    private void showInterstitial() {
        if (showInterstiatial && null != mInterstitialAd) {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
                requestNewInterstitial();
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        showInterstitial();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        showAd();
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        showInterstiatial = false;
        return super.onSaveInstanceState();
    }

    private void showAd() {
        if(isInEditMode()) {
            return;
        }
        // Fixes GPS AIOB Exception
        try {
            if (null != mAdView) {
                mAdView.loadAd(new AdRequest.Builder().build());
            }
        } catch (Exception e) {
            // Log Ad Errors
            Log.e("Ad Error", Objects.requireNonNull(e.getMessage()));
        }
    }
}
