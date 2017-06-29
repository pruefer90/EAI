package dhbw.eai.background;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import dhbw.eai.Const;
import io.reactivex.functions.Consumer;

public class AlarmSetterIntent extends IntentService {

    public AlarmSetterIntent() {
        super("AlarmSetter");
    }

    @Override
    protected void onHandleIntent(@NonNull final Intent intent) {
        AlarmSetterService.setAlarm(this, new LocalDate());
    }
}
