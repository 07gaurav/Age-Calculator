package age.app.gaurav.com.ageapplication;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DaysCountDown extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    EditText etDob;
    Button clear, calculate, btnDob;
    TextView tvResult;

    private InterstitialAd mInterstitialAd;

    View rootView;

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        this.finish();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_days_count_down);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etDob=(EditText)findViewById(R.id.et_dob);
        clear=(Button) findViewById(R.id.clear);
        calculate=(Button) findViewById(R.id.calculate);
        btnDob=(Button) findViewById(R.id.btn_dob);
        tvResult=(TextView) findViewById(R.id.tv_day_of_week);

        rootView = getWindow().getDecorView().getRootView();

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etDob.setText("");
                tvResult.setText("");
            }
        });

        btnDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etDob.getText().toString()==null || etDob.getText().toString().equals("") ){

                    //Get yesterday's date
                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.DATE, -1);

                    Calendar now = Calendar.getInstance();
                    DatePickerDialog dpd = DatePickerDialog.newInstance(
                            DaysCountDown.this,
                            now.get(Calendar.YEAR),
                            now.get(Calendar.MONTH),
                            now.get(Calendar.DAY_OF_MONTH)
                    );
                    dpd.vibrate(true);
                    dpd.dismissOnPause(true);
                    dpd.setVersion(true ? DatePickerDialog.Version.VERSION_2 : DatePickerDialog.Version.VERSION_1);
                    dpd.show(getFragmentManager(), "Datepickerdialog");
                    dpd.setMinDate(calendar.getInstance());
                }else{
                    //Get yesterday's date
                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.DATE, -1);

                    String[] args = etDob.getText().toString().split(" - ");
                    int mDay = Integer.parseInt(args[0]);
                    int mMonth = Integer.parseInt(args[1]) - 1;
                    int mYear = Integer.parseInt(args[2]);

                    DatePickerDialog dpd = DatePickerDialog.newInstance(
                            DaysCountDown.this,
                            mYear,
                            mMonth,
                            mDay
                    );
                    dpd.vibrate(true);
                    dpd.dismissOnPause(true);
                    dpd.setVersion(true ? DatePickerDialog.Version.VERSION_2 : DatePickerDialog.Version.VERSION_1);
                    dpd.show(getFragmentManager(), "Datepickerdialog");
                    dpd.setMinDate(calendar.getInstance());
                }

            }
        });

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validate()){
                    showInterstitial();

                    //todays date
                    Calendar today = Calendar.getInstance();
                    int year = today.get(Calendar.YEAR);
                    int monthOfYear = today.get(Calendar.MONTH);
                    int dayOfMonth = today.get(Calendar.DAY_OF_MONTH);
                    String date = dayOfMonth+"-"+(++monthOfYear)+"-"+year;

                    calc(date, etDob.getText().toString().replaceAll("\\s",""));
                }
            }
        });

        // Load an ad into the AdMob banner view.
        AdView adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        adView.loadAd(adRequest);

        // Create the InterstitialAd and set the adUnitId (defined in values/strings.xml).
        mInterstitialAd = newInterstitialAd();
        loadInterstitial();
    }

    private void calc(String dateO, String dateT){

        Date dateOne =new Date();
        Date dateTwo= new Date();
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();

        DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        try {
            dateOne = sdf.parse(dateO);
            dateTwo = sdf.parse(dateT);

            startDate.setTime(dateOne);
            endDate.setTime(dateTwo);

        }catch(Exception e){
            e.printStackTrace();
        }

        DateCalculator dateCaculator=DateCalculator.calculateAge(startDate.get(Calendar.DAY_OF_MONTH),startDate.get(Calendar.MONTH)+1,startDate.get(Calendar.YEAR),endDate.get(Calendar.DAY_OF_MONTH),endDate.get(Calendar.MONTH)+1,endDate.get(Calendar.YEAR));

        tvResult.setText(dateCaculator.getTotalDay()+" day's to go!");
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        String date = dayOfMonth+" - "+(++monthOfYear)+" - "+year;
        etDob.setText(date);
    }

    private InterstitialAd newInterstitialAd() {
        InterstitialAd interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {

            }

            @Override
            public void onAdFailedToLoad(int errorCode) {

            }

            @Override
            public void onAdClosed() {

            }
        });
        return interstitialAd;
    }

    private void showInterstitial() {
        // Show the ad if it's ready. Otherwise reload the ad.
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            mInterstitialAd = newInterstitialAd();
            loadInterstitial();
        }
    }

    private void loadInterstitial() {

        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        mInterstitialAd.loadAd(adRequest);
    }

    boolean validate(){

        boolean status=true;

        if (etDob.getText().toString().length()<=0 ){
            status=false;

            Snackbar.make(rootView, "Please select Date", Snackbar.LENGTH_LONG)
                    .setAction("CLOSE", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    })
                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                    .show();
        }
        return status;
    }
}
