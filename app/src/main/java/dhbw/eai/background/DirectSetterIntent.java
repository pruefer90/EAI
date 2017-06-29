package dhbw.eai.background;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import dhbw.eai.Const;
import io.reactivex.functions.Consumer;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

public class DirectSetterIntent extends IntentService {

    public DirectSetterIntent() {
        super("AlarmSetter");
    }

    @Override
    protected void onHandleIntent(@NonNull final Intent intent) {
        Log.d(Const.TAG, "onHandleIntent");
        WakeTimeProvider.getWakeTime(this, new LocalDate().plusDays(1)).subscribe(new Consumer<DateTime>() {
            @Override
            public void accept(@NonNull @io.reactivex.annotations.NonNull final DateTime wakeTime) throws Exception {
                Log.d(Const.TAG, "Wake Time: " + wakeTime);
                ClockSetter.setAlarmClock(DirectSetterIntent.this, wakeTime);
            }
        });
    }
}
