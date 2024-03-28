package ru.mavist.urava;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.yandex.mobile.ads.banner.BannerAdSize;
import com.yandex.mobile.ads.banner.BannerAdView;
import com.yandex.mobile.ads.common.*;
import com.yandex.mobile.ads.rewarded.*;

import static java.lang.Math.sqrt;

public class MainActivity extends AppCompatActivity {

    @Nullable
    private RewardedAd mRewardedAd = null;
    @Nullable
    private RewardedAdLoader mRewardedAdLoader = null;
    public MainActivity() {
        super(R.layout.activity_main);
    }
    TextView itogo;
    EditText a11, b11, c11;
    long d, a1, b1, c1;
    double x1, x2;
    String ans;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        itogo = findViewById(R.id.itogo);
        a11 = findViewById(R.id.editTextNumber);
        b11 = findViewById(R.id.editTextNumber2);
        c11 = findViewById(R.id.editTextNumber3);
        setreklama("R-M-5227679-2", 500);
        MobileAds.initialize(this, new InitializationListener() {
            @Override
            public void onInitializationCompleted() {}
        });


        // Ads loading should occur after initialization of the SDK.
        // Initialize SDK as early as possible, for example in Application.onCreate or Activity.onCreate
        mRewardedAdLoader = new RewardedAdLoader(this);
        mRewardedAdLoader.setAdLoadListener(new RewardedAdLoadListener() {
            @Override
            public void onAdLoaded(@NonNull final RewardedAd rewardedAd) {
                mRewardedAd = rewardedAd;
                // The ad was loaded successfully. Now you can show loaded ad.
            }

            @Override
            public void onAdFailedToLoad(@NonNull final AdRequestError adRequestError) {
                // Ad failed to load with AdRequestError.
                // Attempting to load a new ad from the onAdFailedToLoad() method is strongly discouraged.
            }
        });
        loadRewardedAd();
    }

    private void showAd() {
        if (mRewardedAd != null) {
            mRewardedAd.setAdEventListener(new RewardedAdEventListener() {
                @Override
                public void onAdShown() {
                    // Called when ad is shown.
                }

                @Override
                public void onAdFailedToShow(@NonNull final AdError adError) {
                    // Called when an ad failed to show.

                    // Clean resources after Ad failed to show
                    if (mRewardedAd != null) {
                        mRewardedAd.setAdEventListener(null);
                        mRewardedAd = null;
                    }

                    // Now you can preload the next ad.
                    loadRewardedAd();
                }

                @Override
                public void onAdDismissed() {
                    // Called when ad is dismissed.
                    // Clean resources after Ad dismissed
//                    itogo.setText("Вы не посмотрели рекламу");
                    if (mRewardedAd != null) {
                        mRewardedAd.setAdEventListener(null);
                        mRewardedAd = null;
                    }

                    // Now you can preload the next ad.
                    loadRewardedAd();
                }

                @Override
                public void onAdClicked() {
                    // Called when a click is recorded for an ad.
                }

                @Override
                public void onAdImpression(@Nullable final ImpressionData impressionData) {
                    // Called when an impression is recorded for an ad.
                }

                @Override
                public void onRewarded(@NonNull final Reward reward) {
                    // Called when the user can be rewarded.
                    try {
                        a1 = Long.parseLong(String.valueOf(a11.getText()));
                        b1 = Long.parseLong(String.valueOf(b11.getText()));
                        c1 = Long.parseLong(String.valueOf(c11.getText()));
                        answer(a1, b1, c1);
                    } catch (Exception e){
                        itogo.setText("Ошибка вычисления. Возможно вы ввели не все значения. \nЕсли что-то равно нулю - впишите это");
                    }

                }
            });
            mRewardedAd.show(this);
        }
    }

    private void loadRewardedAd() {
        if (mRewardedAdLoader != null ) {
            final AdRequestConfiguration adRequestConfiguration =
                    new AdRequestConfiguration.Builder("R-M-5227679-1").build();
            mRewardedAdLoader.loadAd(adRequestConfiguration);
        }
    }

    public void clc(View v){
        try {
            a1 = Long.parseLong(String.valueOf(a11.getText()));
            b1 = Long.parseLong(String.valueOf(b11.getText()));
            c1 = Long.parseLong(String.valueOf(c11.getText()));
            answer(a1, b1, c1);
        } catch (Exception e){
            itogo.setText("Ошибка значений. Возможно вы ввели не все значения. \nЕсли что-то равно нулю - впишите это");
        }

        int a = rand(0, 15);
        if(a >= 13){
            showAd();
            Log.d("RANDOM", String.valueOf(a));
        }
    }

    public void answer(long a, long b, long c){

        try {
            d = (b*b)+(-4*a*c);
        } catch (Exception e){
            itogo.setText("Ошибка вычисления. Возможно вы ввели не все значения. \nЕсли что-то равно нулю - впишите это");
        }

        if(d < 0){
            itogo.setText("Нет корней. \nДискриминант равен "+d);
        } else if (d == 0) {
            x1 = (b * -1) / (2*a);
            itogo.setText("Один корень, Дискриминант равен 0. \nx1 = "+x1);
        } else {
//        Log.wtf("b2", String.valueOf(b*b));
//        Log.wtf("a", String.valueOf(a));
//        Log.wtf("c", String.valueOf(c));
//        Log.wtf("-4ac", String.valueOf(-4*a*c));
//        Log.wtf("D", String.valueOf(d));
        x1 = (b * -1 + sqrt(d) ) / (2*a);
        x2 = (b * -1 - sqrt(d) ) / (2*a);
        ans = "x1: "+x1+"\n"+"x2: "+x2;
        itogo.setText(ans);
        }
    }

    public void inf(View v){
        Intent intent = new Intent(this, What.class);
        startActivity(intent);
    }

    public void setreklama(String baner, int size){
        BannerAdView mBannerAdView = findViewById(R.id.bannerAdView);
        mBannerAdView.setAdUnitId(baner);
        mBannerAdView.setAdSize(BannerAdSize.stickySize(this, size));
        final AdRequest adRequest = new AdRequest.Builder().build();
        mBannerAdView.loadAd(adRequest);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mRewardedAdLoader != null) {
            mRewardedAdLoader.setAdLoadListener(null);
            mRewardedAdLoader = null;
        }
        destroyRewardedAd();
    }

    private void destroyRewardedAd() {
        if (mRewardedAd != null) {
            mRewardedAd.setAdEventListener(null);
            mRewardedAd = null;
        }
    }

    public static int rand(int a, int b) {
        return a + (int) (Math.random() * b);
    }

}