package com.example.darkmode;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.darkmode.utils.DarkModeActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class MainActivity extends AppCompatActivity {
    public static final int MODE_NIGHT_AUTO = 0;
    public static final int MODE_NIGHT_NO = 1;
    public static final int MODE_NIGHT_YES = 2;
    TextView txtClick, txtThem0, txtThem1, txtThem2;
    ImageView imgAuto, imgLight, imgDark, imgLogo;
    RelativeLayout revHome;
    int modeSelected;
    AdView mAdView;
    InterstitialAd mInterstitialAd;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });
        initView();
        initControl();
        imgAuto.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                modeSelected = MODE_NIGHT_AUTO;
                selectedMode(MODE_NIGHT_AUTO);
            }
        });
        imgLight.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                modeSelected = MODE_NIGHT_NO;
                selectedMode(MODE_NIGHT_NO);
            }
        });
        imgDark.setOnClickListener(new View.OnClickListener() {
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
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
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
                DarkModeActivity.changeUIMode(view.getContext(), modeSelected);
            }
        });
    }

    private void initControl() {

    }

    private void initView() {
        txtClick = findViewById(R.id.txtButton);
        txtThem0 = findViewById(R.id.txtThem0);
        txtThem1 = findViewById(R.id.txtThem1);
        txtThem2 = findViewById(R.id.txtThem2);
        revHome = findViewById(R.id.revHome);
        imgAuto = findViewById(R.id.imgAuto);
        imgLight = findViewById(R.id.imgLight);
        imgDark = findViewById(R.id.imgDark);
        imgLogo = findViewById(R.id.imgLogo);
        //bannner
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //init
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.interKey));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    void selectedMode(int mode) {
        switch (mode) {
            case MODE_NIGHT_AUTO: {
                imgAuto.setBackground(getDrawable(R.drawable.cutsom_image));
                imgLight.setBackground(null);
                imgDark.setBackground(null);
                setImgLogo();
                break;
            }
            case MODE_NIGHT_NO: {
                imgLight.setBackground(getDrawable(R.drawable.cutsom_image));
                imgAuto.setBackground(null);
                imgDark.setBackground(null);
                setImgLogo(mode);


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    txtClick.setTextColor(getColor(R.color.black));
                    txtThem0.setTextColor(getColor(R.color.black));
                    txtThem1.setTextColor(getColor(R.color.black));
                    txtThem2.setTextColor(getColor(R.color.black));
                }
                break;
            }
            case MODE_NIGHT_YES: {
                imgDark.setBackground(getDrawable(R.drawable.cutsom_image));
                imgAuto.setBackground(null);
                imgLight.setBackground(null);
                setImgLogo(mode);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    txtClick.setTextColor(getColor(R.color.white));
                    txtThem0.setTextColor(getColor(R.color.white));
                    txtThem1.setTextColor(getColor(R.color.white));
                    txtThem2.setTextColor(getColor(R.color.white));
                }
                break;
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setImgLogo(int mode) {
        if (mode == MODE_NIGHT_YES) {
            imgLogo.setImageDrawable(getDrawable(R.drawable.ic_dark));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                revHome.setBackgroundColor(getColor(R.color.black));
            }
        } else {
            imgLogo.setImageDrawable(getDrawable(R.drawable.ic_light));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                revHome.setBackgroundColor(getColor(R.color.white));
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setImgLogo() {
        int mode = DarkModeActivity.curentUiMode(this);
        if (mode == MODE_NIGHT_YES) {
            imgLogo.setImageDrawable(getDrawable(R.drawable.ic_dark));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                revHome.setBackgroundColor(getColor(R.color.black));
            }
        } else {
            imgLogo.setImageDrawable(getDrawable(R.drawable.ic_light));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                revHome.setBackgroundColor(getColor(R.color.white));
            }
        }
    }
}
