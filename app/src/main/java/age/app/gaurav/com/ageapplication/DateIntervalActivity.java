package age.app.gaurav.com.ageapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

public class DateIntervalActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    EditText etDob, etTodayDate;
    Button clear, calculate, btnTodayDate, btnDob;
    int flag=0;

    View rootView;

    private InterstitialAd mInterstitialAd;

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        this.finish();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_interval);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etDob=(EditText)findViewById(R.id.et_dob);
        etTodayDate=(EditText)findViewById(R.id.et_todaydate);
        clear=(Button) findViewById(R.id.clear);
        calculate=(Button) findViewById(R.id.calculate);
        btnTodayDate=(Button) findViewById(R.id.btn_todayd);
        btnDob=(Button) findViewById(R.id.btn_dob);

        rootView = getWindow().getDecorView().getRootView();

        //Todays date
        Calendar today = Calendar.getInstance();
        int year = today.get(Calendar.YEAR);
        int monthOfYear = today.get(Calendar.MONTH);
        int dayOfMonth = today.get(Calendar.DAY_OF_MONTH);
        String date = dayOfMonth+" - "+(++monthOfYear)+" - "+year;
        etTodayDate.setText(date);

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etDob.setText("");
                //set default todays date
                Calendar today = Calendar.getInstance();
                int year = today.get(Calendar.YEAR);
                int monthOfYear = today.get(Calendar.MONTH);
                int dayOfMonth = today.get(Calendar.DAY_OF_MONTH);
                String date = dayOfMonth+" - "+(++monthOfYear)+" - "+year;
                etTodayDate.setText(date);
            }
        });

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){

                    showInterstitial();

                    Intent resultIntent = new Intent(getApplicationContext(), ResultActivity.class);
                    resultIntent.putExtra("from", "DateIntervalActivity");
                    resultIntent.putExtra("dateOne", etTodayDate.getText().toString().replaceAll("\\s",""));
                    resultIntent.putExtra("dateTwo", etDob.getText().toString().replaceAll("\\s",""));
                    startActivity(resultIntent);
                    finish();
                }
            }
        });

        btnTodayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag=1;
                if(etTodayDate.getText().toString()==null || etTodayDate.getText().toString().equals("") ){

                    Calendar now = Calendar.getInstance();
                    DatePickerDialog dpd = DatePickerDialog.newInstance(
                            DateIntervalActivity.this,
                            now.get(Calendar.YEAR),
                            now.get(Calendar.MONTH),
                            now.get(Calendar.DAY_OF_MONTH)
                    );
                    dpd.vibrate(true);
                    dpd.dismissOnPause(true);
                    dpd.setVersion(true ? DatePickerDialog.Version.VERSION_2 : DatePickerDialog.Version.VERSION_1);
                    dpd.show(getFragmentManager(), "Datepickerdialog");

                }else{

                    String[] args = etTodayDate.getText().toString().split(" - ");
                    int mDay = Integer.parseInt(args[0]);
                    int mMonth = Integer.parseInt(args[1]) - 1;
                    int mYear = Integer.parseInt(args[2]);

                    DatePickerDialog dpd = DatePickerDialog.newInstance(
                            DateIntervalActivity.this,
                            mYear,
                            mMonth,
                            mDay
                    );
                    dpd.vibrate(true);
                    dpd.dismissOnPause(true);
                    dpd.setVersion(true ? DatePickerDialog.Version.VERSION_2 : DatePickerDialog.Version.VERSION_1);
                    dpd.show(getFragmentManager(), "Datepickerdialog");

                }

            }
        });

        btnDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag=2;
                if(etDob.getText().toString()==null || etDob.getText().toString().equals("") ){
                    Calendar now = Calendar.getInstance();
                    DatePickerDialog dpd = DatePickerDialog.newInstance(
                            DateIntervalActivity.this,
                            now.get(Calendar.YEAR),
                            now.get(Calendar.MONTH),
                            now.get(Calendar.DAY_OF_MONTH)
                    );
                    dpd.vibrate(true);
                    dpd.dismissOnPause(true);
                    dpd.setVersion(true ? DatePickerDialog.Version.VERSION_2 : DatePickerDialog.Version.VERSION_1);
                    dpd.show(getFragmentManager(), "Datepickerdialog");
                }else{
                    String[] args = etDob.getText().toString().split(" - ");
                    int mDay = Integer.parseInt(args[0]);
                    int mMonth = Integer.parseInt(args[1]) - 1;
                    int mYear = Integer.parseInt(args[2]);

                    DatePickerDialog dpd = DatePickerDialog.newInstance(
                            DateIntervalActivity.this,
                            mYear,
                            mMonth,
                            mDay
                    );
                    dpd.vibrate(true);
                    dpd.dismissOnPause(true);
                    dpd.setVersion(true ? DatePickerDialog.Version.VERSION_2 : DatePickerDialog.Version.VERSION_1);
                    dpd.show(getFragmentManager(), "Datepickerdialog");
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

    @Override
    public void onResume() {
        super.onResume();
        DatePickerDialog dpd = (DatePickerDialog) getFragmentManager().findFragmentByTag("Datepickerdialog");
        if(dpd != null) dpd.setOnDateSetListener(this);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = dayOfMonth+" - "+(++monthOfYear)+" - "+year;
        if(flag==1){
            etTodayDate.setText(date);
        }else if(flag==2){
            etDob.setText(date);
        }

    }

    boolean validate(){

        boolean status=true;

        if (etDob.getText().toString().length()<=0 || etTodayDate.getText().toString().length()<=0){
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
}
