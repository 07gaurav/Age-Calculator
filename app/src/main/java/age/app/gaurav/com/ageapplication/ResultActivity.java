package age.app.gaurav.com.ageapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ResultActivity extends AppCompatActivity {

    TextView tvAge, tvYears, tvMonths, tvWeeks, tvDays, tvHours, tvMinutes, tvSeconds, headerAge;


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        this.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent= getIntent();
        String fromIntent = (intent.getStringExtra("from")).trim();
        String dateOne = (intent.getStringExtra("dateOne")).trim();
        String dateTwo = (intent.getStringExtra("dateTwo")).trim();

        tvAge=(TextView)findViewById(R.id.tv_age);
        tvYears=(TextView)findViewById(R.id.total_years);
        tvMonths=(TextView)findViewById(R.id.total_months);
        tvWeeks=(TextView)findViewById(R.id.total_weeks);
        tvDays=(TextView)findViewById(R.id.total_days);
        tvHours=(TextView)findViewById(R.id.total_hours);
        tvMinutes=(TextView)findViewById(R.id.total_minutes);
        tvSeconds=(TextView)findViewById(R.id.total_seconds);

        headerAge=(TextView)findViewById(R.id.header_age);

        if(fromIntent.equals("AgeActivity")){
            headerAge.setText(R.string.age);
        }else if(fromIntent.equals("DateIntervalActivity")){
            headerAge.setText(R.string.duration);
        }

        calculate(dateOne, dateTwo);
    }

    private void calculate(String dateO, String dateT){

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

        String age = dateCaculator.getYear() + " Years " + dateCaculator.getMonth() + " Months " + dateCaculator.getDay()+ " Days";
        tvAge.setText(age);

        tvYears.setText(""+dateCaculator.getYear());

        int num_months = dateCaculator.getYear()*12 + dateCaculator.getMonth();
        tvMonths.setText(""+num_months);

        int num_weeks = (int) dateCaculator.getTotalDay()/7;
        tvWeeks.setText(""+num_weeks);

        tvDays.setText(""+dateCaculator.getTotalDay());

        long duration  = dateTwo.getTime() - dateOne.getTime();
        long diffInSeconds = Math.abs(TimeUnit.MILLISECONDS.toSeconds(duration));
        long diffInMinutes = Math.abs(TimeUnit.MILLISECONDS.toMinutes(duration));
        long diffInHours = Math.abs(TimeUnit.MILLISECONDS.toHours(duration));

        tvHours.setText(""+diffInHours);
        tvMinutes.setText(""+diffInMinutes);
        tvSeconds.setText(""+diffInSeconds);
    }


}
