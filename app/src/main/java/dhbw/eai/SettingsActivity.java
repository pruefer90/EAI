package dhbw.eai;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.google.maps.model.TravelMode;
import dhbw.eai.background.SaveSharedPreference;

import static dhbw.eai.R.id.link;

/**
 * Created by mathz on 19.06.2017.
 */

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText timeText;
    private EditText linkText;
    private RadioGroup wayGroup;
    private Button saveButton;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        timeText = (EditText) findViewById(R.id.time);
        linkText = (EditText) findViewById(link);
        wayGroup = (RadioGroup) findViewById(R.id.waygroup);
        final Checkable wayButton1 = (Checkable) findViewById(R.id.way1);
        final Checkable wayButton2 = (Checkable) findViewById(R.id.way2);
        final Checkable wayButton3 = (Checkable) findViewById(R.id.way3);
        final Checkable wayButton4 = (Checkable) findViewById(R.id.way4);
        saveButton = (Button) findViewById(R.id.save);

        saveButton.setOnClickListener(this);

        final int t = SaveSharedPreference.getPrefTime(this);
        final String l = SaveSharedPreference.getPrefLink(this);
        final TravelMode w = SaveSharedPreference.getPrefWay(this);

        if (!l.isEmpty()) {
            timeText.setText(String.valueOf(t));
            linkText.setText(l);
            switch (w) {
                case WALKING:
                    wayButton1.setChecked(true);
                    break;
                case DRIVING:
                    wayButton2.setChecked(true);
                    break;
                case TRANSIT:
                    wayButton3.setChecked(true);
                    break;
                case BICYCLING:
                    wayButton4.setChecked(true);
                    break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_cancel:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(final View v) {
        if (v == saveButton) {
            final String time = timeText.getText().toString();
            if (time.isEmpty()) {
                Log.d("DEBUG_EAI", "Time");
            } else {
                final String link = linkText.getText().toString();
                if (link.isEmpty()) {
                    Log.d("DEBUG_EAI", "Link");
                } else {
                    final int select = wayGroup.getCheckedRadioButtonId();
                    final TravelMode way;
                    switch (select) {
                        case R.id.way1:
                            way = TravelMode.WALKING;
                            break;
                        case R.id.way2:
                            way = TravelMode.DRIVING;
                            break;
                        case R.id.way3:
                            way = TravelMode.TRANSIT;
                            break;
                        case R.id.way4:
                            way = TravelMode.BICYCLING;
                            break;
                        default:
                            way = TravelMode.UNKNOWN;
                            break;
                    }
                    SaveSharedPreference.setAll(this, Integer.parseInt(time), link, way);
                    finish();
                }
            }
        }
    }
}
