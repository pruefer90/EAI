package dhbw.eai;

import android.Manifest;
import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import dhbw.eai.background.AlarmSetterIntent;
import dhbw.eai.background.BackgroundScheduler;
import dhbw.eai.background.DirectSetterIntent;
import dhbw.eai.background.SaveSharedPreference;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private SwitchCompat startSwitch;
    private Button synchroButton;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startSwitch = (SwitchCompat) findViewById(R.id.switchCompat);
        synchroButton = (Button) findViewById(R.id.synchro);

        startSwitch.setChecked(SaveSharedPreference.getPrefActive(this) && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);

        startSwitch.setOnClickListener(this);
        synchroButton.setOnClickListener(this);
    }

    @Override
    public void onRequestPermissionsResult(final int requestCode,
                                           @NonNull final String[] permissions, @NonNull final int[] grantResults) {

        // If request is cancelled, the result arrays are empty.
        if (grantResults.length <= 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            startSwitch.setChecked(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull final MenuItem item) {
        final Intent intent;
        switch (item.getItemId()) {
            case R.id.action_settings:
                intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(final View v) {
        if (v == startSwitch) {
            Log.d("DEBUG_EAI", "SWITCH");
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                SaveSharedPreference.setPrefActive(this,startSwitch.isChecked());
                if(startSwitch.isChecked()){
                    callBackgroundService(AlarmSetterIntent.class);
                }else{
                    final AlarmManager alarmMgr = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
                    BackgroundScheduler.cancelOutstandingIntents(this, alarmMgr);
                }
            }else{
                final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            }
        }
        if (v == synchroButton && startSwitch.isChecked()) {
            Log.d("DEBUG_EAI", "BUTTON");
            callBackgroundService(DirectSetterIntent.class);
        }
    }

    private void callBackgroundService(Class<?> intent){
        final Intent alarmSetter = new Intent(getApplicationContext(), intent);
        startService(alarmSetter);
    }
}
