package dhbw.eai.background;

import android.content.Context;
import android.content.Intent;
import android.provider.AlarmClock;
import org.joda.time.DateTime;

final class ClockSetter {

    private ClockSetter() {
    }

    static void setAlarmClock(final Context context, final DateTime wakeTime){
        final Intent i = new Intent(AlarmClock.ACTION_SET_ALARM);
        i.putExtra(AlarmClock.EXTRA_HOUR, wakeTime.getHourOfDay());
        i.putExtra(AlarmClock.EXTRA_MINUTES, wakeTime.getMinuteOfHour());
        i.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
        context.startActivity(i);
    }
}
