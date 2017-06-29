package dhbw.eai.background;

import android.content.Context;
import android.content.Intent;
import android.provider.AlarmClock;
import android.support.annotation.NonNull;
import org.joda.time.ReadableDateTime;

final class ClockSetter {

    private ClockSetter() {
    }

    static void setAlarmClock(@NonNull final Context context, @NonNull final ReadableDateTime wakeTime) {
        final Intent i = new Intent(AlarmClock.ACTION_SET_ALARM);
        i.putExtra(AlarmClock.EXTRA_HOUR, wakeTime.getHourOfDay());
        i.putExtra(AlarmClock.EXTRA_MINUTES, wakeTime.getMinuteOfHour());
        i.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
        context.startActivity(i);
    }
}
