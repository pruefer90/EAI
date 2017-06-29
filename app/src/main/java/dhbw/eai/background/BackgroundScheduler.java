package dhbw.eai.background;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import dhbw.eai.Const;
import org.joda.time.ReadableInstant;

public final class BackgroundScheduler {

    private static final int BACKGROUND_ID = 42;

    private BackgroundScheduler() {
    }

    static void setNextAlarm(@NonNull final Context context, @NonNull final ReadableInstant nextAlarm) {
        Log.d(Const.TAG, "Next Background service: " + nextAlarm);
        final AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        cancelOutstandingIntents(context, alarmMgr);
        final Intent intent = new Intent(context, AlarmSetterService.class);
        final PendingIntent alarmIntent = PendingIntent.getBroadcast(context, BACKGROUND_ID, intent, 0);
        alarmMgr.set(AlarmManager.RTC_WAKEUP, nextAlarm.getMillis(), alarmIntent);
    }

    public static void cancelOutstandingIntents(@NonNull final Context context, @NonNull final AlarmManager alarmMgr) {
        final Intent intent = new Intent(context, AlarmSetterService.class);
        final PendingIntent alarmIntent = PendingIntent.getBroadcast(context, BACKGROUND_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmMgr.cancel(alarmIntent);
    }

}
