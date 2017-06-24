package dhbw.eai;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.security.acl.Group;

import static dhbw.eai.R.id.link;
import static dhbw.eai.R.id.save;
import static dhbw.eai.R.id.thing_proto;

/**
 * Created by mathz on 19.06.2017.
 */

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText timeText;
    private EditText linkText;
    private RadioGroup wayGroup;
    private RadioButton wayButton1;
    private RadioButton wayButton2;
    private RadioButton wayButton3;
    private RadioButton wayButton4;
    private Button saveButton;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        timeText = (EditText) findViewById(R.id.time);
        linkText = (EditText) findViewById(link);
        wayGroup = (RadioGroup) findViewById(R.id.waygroup);
        wayButton1 = (RadioButton) findViewById(R.id.way1);
        wayButton2 = (RadioButton) findViewById(R.id.way2);
        wayButton3 = (RadioButton) findViewById(R.id.way3);
        wayButton4 = (RadioButton) findViewById(R.id.way4);
        saveButton = (Button) findViewById(R.id.save);

        saveButton.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull final MenuItem item){
        switch (item.getItemId()){
            case R.id.action_cancel:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == saveButton){
            String time = timeText.getText().toString();
            if (!time.isEmpty()){
                String link = linkText.getText().toString();
                if (link.isEmpty()){
                    int select = wayGroup.getCheckedRadioButtonId();

                    switch (select){
                        case 1:

                            break;
                    }
                } else {
                    // Link eingeben
                }
            } else {
                // Zeit eingeben
            }
        }
    }
}
