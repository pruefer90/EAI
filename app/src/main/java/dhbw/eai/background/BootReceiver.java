package dhbw.eai.background;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import org.joda.time.LocalDate;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(@NonNull final Context context, @NonNull final Intent intent) {
        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction()) && SaveSharedPreference.getPrefActive(context)) {
            AlarmSetterService.setAlarm(context, new LocalDate());
        }
    }
}
