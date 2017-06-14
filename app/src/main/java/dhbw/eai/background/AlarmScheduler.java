package dhbw.eai.background;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;
import java.util.GregorianCalendar;

final class AlarmScheduler {

    private AlarmScheduler() {
    }

    static void setNextAlarm(final Context context){
        final AlarmManager alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        final Intent intent = new Intent(context, AlarmSetterService.class);
        final PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        alarmMgr.set(AlarmManager.RTC_WAKEUP, getNextAlarmTime().getTimeInMillis(), alarmIntent);
    }

    private static Calendar getNextAlarmTime(){
        return new GregorianCalendar(); //TODO
    }

}
