package com.example.darkmode;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.darkmode.utils.DarkModeActivity;
import com.example.darkmode.utils.SharedPrefsUtils;
import com.example.ratedialog.RatingDialog;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class MainActivity extends AppCompatActivity implements RatingDialog.RatingDialogInterFace {
    public static final int MODE_NIGHT_AUTO = 0;
    public static final int MODE_NIGHT_NO = 1;
    public static final int MODE_NIGHT_YES = 2;
    TextView txtClick, txtThem0, txtThem1, txtThem2;
    ImageView imgAuto, imgLight, imgDark, imgLogo;
    LinearLayout linner0, linner1, linner2;
    RelativeLayout revLoading;
    LinearLayout revHome;
    Integer modeSelected;
    AdView mAdView;
    InterstitialAd mInterstitialAd;
    private String magnet;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("onCreate: ", "OKOK");
        rateAuto();
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });
        initView();
        initControl();
        linner0.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                modeSelected = MODE_NIGHT_AUTO;
                selectedMode(MODE_NIGHT_AUTO);
            }
        });
        linner1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                modeSelected = MODE_NIGHT_NO;
                selectedMode(MODE_NIGHT_NO);
            }
        });
        linner2.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                modeSelected = MODE_NIGHT_YES;
                selectedMode(MODE_NIGHT_YES);
            }
        });
        txtClick.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
                if(modeSelected != null) {
                    revLoading.setVisibility(View.VISIBLE);
                    mInterstitialAd.loadAd(new AdRequest.Builder().build());
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Please select mode", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void initControl() {
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                mInterstitialAd.show();
                revLoading.setVisibility(View.GONE);
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                DarkModeActivity.changeUIMode(getApplicationContext(), modeSelected);
                switch (modeSelected) {
                    case MODE_NIGHT_AUTO: {
                        Toast.makeText(MainActivity.this, "Set Auto Mode", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case MODE_NIGHT_NO: {
                        Toast.makeText(MainActivity.this, "Set Light Mode", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case MODE_NIGHT_YES: {
                        Toast.makeText(MainActivity.this, "Set Dark Mode", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
            }
        });


    }

    private void initView() {
        txtClick = findViewById(R.id.txtButton);
        txtThem0 = findViewById(R.id.txtThem0);
        txtThem1 = findViewById(R.id.txtThem1);
        txtThem2 = findViewById(R.id.txtThem2);
        revHome = findViewById(R.id.revHome);
        revLoading = findViewById(R.id.revLoading);
        imgAuto = findViewById(R.id.imgAuto);
        imgLight = findViewById(R.id.imgLight);
        imgDark = findViewById(R.id.imgDark);
        imgLogo = findViewById(R.id.imgLogo);
        linner0 = findViewById(R.id.linner0);
        linner1 = findViewById(R.id.linner1);
        linner2 = findViewById(R.id.linner2);
        //bannner
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //init
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.interKey));
//        mInterstitialAd.loadAd(new AdRequest.Builder().build());

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    void selectedMode(int mode) {
        switch (mode) {
            case MODE_NIGHT_AUTO: {
                imgAuto.setBackground(getDrawable(R.drawable.cutsom_image));
                imgLight.setBackground(getDrawable(R.drawable.custom_image_null));
                imgDark.setBackground(getDrawable(R.drawable.custom_image_null));
                setImgLogo();
                break;
            }
            case MODE_NIGHT_NO: {
                imgLight.setBackground(getDrawable(R.drawable.cutsom_image));
                imgAuto.setBackground(getDrawable(R.drawable.custom_image_null));
                imgDark.setBackground(getDrawable(R.drawable.custom_image_null));
                setImgLogo(mode);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    txtClick.setTextColor(getColor(R.color.black));
                    txtThem0.setTextColor(getColor(R.color.black));
                    txtThem1.setTextColor(getColor(R.color.colorblue));
                    txtThem2.setTextColor(getColor(R.color.black));
                }
                break;
            }
            case MODE_NIGHT_YES: {
                imgDark.setBackground(getDrawable(R.drawable.cutsom_image));
                imgAuto.setBackground(getDrawable(R.drawable.custom_image_null));
                imgLight.setBackground(getDrawable(R.drawable.custom_image_null));
                setImgLogo(mode);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    txtClick.setTextColor(getColor(R.color.white));
                    txtThem0.setTextColor(getColor(R.color.white));
                    txtThem1.setTextColor(getColor(R.color.white));
                    txtThem2.setTextColor(getColor(R.color.colorblue));
                }
                break;
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setImgLogo(int mode) {
        if (mode == MODE_NIGHT_YES) {
            imgLogo.setImageDrawable(getDrawable(R.drawable.ic_dark1));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                revHome.setBackgroundColor(getColor(R.color.black));

            }
        } else {
            imgLogo.setImageDrawable(getDrawable(R.drawable.ic_light1));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                revHome.setBackgroundColor(getColor(R.color.white));

            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setImgLogo() {
        int mode = DarkModeActivity.curentUiMode(this);
        if (mode == MODE_NIGHT_YES) {
            imgLogo.setImageDrawable(getDrawable(R.drawable.ic_dark1));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                revHome.setBackgroundColor(getColor(R.color.black));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    txtClick.setTextColor(getColor(R.color.white));
                    txtThem0.setTextColor(getColor(R.color.colorblue));
                    txtThem1.setTextColor(getColor(R.color.white));
                    txtThem2.setTextColor(getColor(R.color.white));
                }
            }
        } else {
            imgLogo.setImageDrawable(getDrawable(R.drawable.ic_light1));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                revHome.setBackgroundColor(getColor(R.color.white));
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                txtClick.setTextColor(getColor(R.color.black));
                txtThem0.setTextColor(getColor(R.color.colorblue));
                txtThem1.setTextColor(getColor(R.color.black));
                txtThem2.setTextColor(getColor(R.color.black));
            }
        }
    }

    void moveToNewApp(String appId) {
        Intent intent = new Intent(new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://play.google.com/store/apps/details?id=" + appId)));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    void open(String magnet) {
        this.magnet = magnet;
        Intent browserIntent = new Intent(Intent.ACTION_VIEW);
        browserIntent.setData(Uri.parse(magnet));
        try {
            startActivity(browserIntent);
        } catch (ActivityNotFoundException ex) {
            Log.d("dddddd", "abcd");
        }
    }

    void goToMarket() {
        Intent goToMarket = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("market://search?q=torrent clients"));
        startActivity(goToMarket);
    }

    public static void rateApp(Context context) {
        Intent intent = new Intent(new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://play.google.com/store/apps/details?id=" + context.getPackageName())));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public void rateAuto() {
        int rate = SharedPrefsUtils.getInstance(this).getInt("rate");
        if (rate < 1) {
            RatingDialog ratingDialog = new RatingDialog(this);
            ratingDialog.setRatingDialogListener((RatingDialog.RatingDialogInterFace) this);
            ratingDialog.showDialog();
        }
    }

    void rateManual() {
        RatingDialog ratingDialog = new RatingDialog(this);
        ratingDialog.setRatingDialogListener(this);
        ratingDialog.showDialog();
    }

    @Override
    public void onDismiss() {
    }

    @Override
    public void onSubmit(float rating) {
        if (rating > 3) {
            rateApp(this);
            SharedPrefsUtils.getInstance(this).putInt("rate", 5);
        }
    }

    @Override
    public void onRatingChanged(float rating) {
    }
}
