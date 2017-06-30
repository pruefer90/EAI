package dhbw.eai.background;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import dhbw.eai.Const;
import io.reactivex.MaybeObserver;
import io.reactivex.disposables.Disposable;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.ReadableInstant;

final class AlarmSetterService {

    private AlarmSetterService() {
    }

    static void setAlarm(@NonNull final Context context, @NonNull final LocalDate dateToCheck) {
        Log.d(Const.TAG, "Setting Alarm");
        WakeTimeProvider.getWakeTime(context, dateToCheck).subscribe(new MaybeObserver<DateTime>() {
            @Override
            public void onSubscribe(final Disposable d) {
            }

            @Override
            public void onSuccess(@NonNull final DateTime wakeTime) {
                Log.d(Const.TAG, "Wake Time: " + wakeTime);
                final DateTime now = new DateTime();
                if (wasLastBeforeAlarm(wakeTime, now)) {
                    Log.d(Const.TAG, "Setting alarm");
                    ClockSetter.setAlarmClock(context, wakeTime);
                    BackgroundScheduler.setNextAlarm(context, now.plusDays(1).withHourOfDay(1));
                } else {
                    BackgroundScheduler.setNextAlarm(context, now.plusHours(1));
                }
            }

            private boolean wasLastBeforeAlarm(@NonNull final ReadableInstant wakeTime, @NonNull final DateTime now) {
                return wakeTime.compareTo(now) > 0 && now.plusHours(1).compareTo(wakeTime) <= 0;
            }

            @Override
            public void onError(@NonNull final Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                Log.d(Const.TAG, "No lessons today");
                final DateTime now = new DateTime();
                BackgroundScheduler.setNextAlarm(context, now.plusDays(1).withHourOfDay(1));
            }
        });
    }

}
