package dhbw.eai.background;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.NonNull;
import org.joda.time.LocalDate;

public class AlarmSetterIntent extends IntentService {

    public AlarmSetterIntent() {
        super("AlarmSetter");
    }

    @Override
    protected void onHandleIntent(@NonNull final Intent intent) {
        AlarmSetterService.setAlarm(this, new LocalDate());
    }
}
