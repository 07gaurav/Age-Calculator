package age.app.gaurav.com.ageapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class HomeActivity extends AppCompatActivity {

    // Remove the below line after defining your own ad unit ID.
    private static final String TOAST_TEXT = "Test ads are being shown. "
            + "To show live ads, replace the ad unit ID in res/values/strings.xml with your own ad unit ID.";

    CardView cvDateInterval, cvAge, cvDayCountdown, cvDateInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        cvDateInterval = (CardView) findViewById(R.id.cv_date_interval);
        cvAge = (CardView) findViewById(R.id.cardview_age);
        cvDayCountdown = (CardView) findViewById(R.id.cv_countdown);
        cvDateInfo = (CardView) findViewById(R.id.cv_date_info);

        cvAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ageIntent=new Intent(getApplicationContext(), AgeActivity.class);
                startActivity(ageIntent);
            }
        });

        cvDateInterval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ageIntent=new Intent(getApplicationContext(), DateIntervalActivity.class);
                startActivity(ageIntent);
            }
        });

        cvDayCountdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ageIntent=new Intent(getApplicationContext(), DaysCountDown.class);
                startActivity(ageIntent);
            }
        });

        cvDateInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ageIntent=new Intent(getApplicationContext(), DateInfoActivity.class);
                startActivity(ageIntent);
            }
        });

        // Load an ad into the AdMob banner view.
        AdView adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        adView.loadAd(adRequest);

    }


}
