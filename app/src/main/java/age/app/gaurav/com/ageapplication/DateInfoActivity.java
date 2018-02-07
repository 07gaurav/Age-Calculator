package age.app.gaurav.com.ageapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

public class DateInfoActivity extends AppCompatActivity  implements DatePickerDialog.OnDateSetListener{

    EditText etDate;
    Button clear, calculate, btnDate;

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        this.finish();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_info);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etDate=(EditText)findViewById(R.id.et_date);
        clear=(Button) findViewById(R.id.clear);
        calculate=(Button) findViewById(R.id.calculate);
        btnDate=(Button) findViewById(R.id.btn_date);

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etDate.setText("");
            }
        });

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //todays date
                Calendar today = Calendar.getInstance();
                int year = today.get(Calendar.YEAR);
                int monthOfYear = today.get(Calendar.MONTH);
                int dayOfMonth = today.get(Calendar.DAY_OF_MONTH);
                String date = dayOfMonth+"-"+(++monthOfYear)+"-"+year;

                if(etDate.getText().toString().length()>0) {
                    Intent resultIntent = new Intent(getApplicationContext(), DateInfoResultActivity.class);
                    resultIntent.putExtra("date", etDate.getText().toString().replaceAll("\\s",""));
                    startActivity(resultIntent);
                }
            }
        });

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etDate.getText().toString()==null || etDate.getText().toString().equals("") ){
                    Calendar now = Calendar.getInstance();
                    DatePickerDialog dpd = DatePickerDialog.newInstance(
                            DateInfoActivity.this,
                            now.get(Calendar.YEAR),
                            now.get(Calendar.MONTH),
                            now.get(Calendar.DAY_OF_MONTH)
                    );
                    dpd.vibrate(true);
                    dpd.dismissOnPause(true);
                    dpd.setVersion(true ? DatePickerDialog.Version.VERSION_2 : DatePickerDialog.Version.VERSION_1);
                    dpd.show(getFragmentManager(), "Datepickerdialog");
                }else{
                    String[] args = etDate.getText().toString().split(" - ");
                    int mDay = Integer.parseInt(args[0]);
                    int mMonth = Integer.parseInt(args[1]) - 1;
                    int mYear = Integer.parseInt(args[2]);

                    DatePickerDialog dpd = DatePickerDialog.newInstance(
                            DateInfoActivity.this,
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

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        String date = dayOfMonth+" - "+(++monthOfYear)+" - "+year;
        etDate.setText(date);
    }
}
