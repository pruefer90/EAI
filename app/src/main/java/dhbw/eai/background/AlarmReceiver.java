package dhbw.eai.background;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import org.joda.time.LocalDate;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(@NonNull final Context context, final Intent intent) {
        AlarmSetterService.setAlarm(context, new LocalDate());
    }
}
