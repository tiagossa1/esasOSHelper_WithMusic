package pt.esas.bibliadolinuxv2;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Time;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SplashScreenActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
            Format formatter = new SimpleDateFormat("MMMM");
            String s = formatter.format(new Date());

            databaseReference = FirebaseDatabase.getInstance().getReference("loggedDevices");
            Calendar cal = Calendar.getInstance();

            String model = Build.MODEL;
            String aVersion = Build.VERSION.RELEASE;
            String time = format.format(cal.getTime());

            DateFormat df = new SimpleDateFormat("dd/MM/yy");
            String date = df.format(new Date());

            Telemetry telemetry = new Telemetry(model, aVersion, time, date);
            databaseReference.child(s).push();
        } catch (Exception e) {
            e.printStackTrace();
        }

        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(homeIntent);
                finish();
            }
        },SPLASH_TIME_OUT);
    }
}
