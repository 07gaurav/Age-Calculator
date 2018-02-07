package age.app.gaurav.com.ageapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class DateInfoResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_info_result);

        Intent intent= getIntent();
        String mydate = (intent.getStringExtra("date")).trim();

        Log.d("TAG","mydate> "+mydate);

    }
}
