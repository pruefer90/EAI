package dhbw.eai.background;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import org.joda.time.DateTime;
import org.joda.time.ReadableInstant;

final class BackgroundScheduler {

    private BackgroundScheduler() {
    }

    static void setNextAlarm(@NonNull final Context context, @NonNull final ReadableInstant nextAlarm) {
        final AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        final Intent intent = new Intent(context, AlarmSetterService.class);
        final PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        alarmMgr.set(AlarmManager.RTC_WAKEUP, nextAlarm.getMillis(), alarmIntent);
    }

    @NonNull
    static ReadableInstant getNextAlarmTime(@NonNull final DateTime now, final ReadableInstant departureTime) {
        return now.plusHours(1).compareTo(departureTime) <= 0 ? now.plusHours(1) : now.plusDays(1).withHourOfDay(0).withMinuteOfHour(1);
    }

}
