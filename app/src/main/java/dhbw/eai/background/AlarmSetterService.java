package dhbw.eai.background;

import android.content.Context;
import android.location.Location;
import android.support.annotation.NonNull;
import android.util.Log;
import dhbw.eai.Const;
import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import java.io.IOException;

final class AlarmSetterService {

    private static final String DHBW_KARLSUHE_ID = "place_id:ChIJ15FBwAYHl0cRwn_nSiwjXWI";

    private AlarmSetterService() {
    }

    static void setAlarm(@NonNull final Context context) {
        try {
            Log.d(Const.TAG, "Setting Alarm");
            final LocalDate today = new LocalDate();
            final Maybe<DateTime> nextLessonTime = NextLessonProvider.getTimeOfFirstLesson(SaveSharedPreference.getPrefLink(context), today).subscribeOn(Schedulers.io());
            final Single<Location> currentLocation = LocationProvider.getCurrentLocation(context).subscribeOn(Schedulers.io());
            nextLessonTime.zipWith(currentLocation.toMaybe(), new BiFunction<DateTime, Location, DateTime>() {
                @NonNull
                @Override
                public DateTime apply(@NonNull @io.reactivex.annotations.NonNull final DateTime nextLessonTime, @NonNull @io.reactivex.annotations.NonNull final Location currentLocation) throws InterruptedException, com.google.maps.errors.ApiException, IOException {
                    Log.d(Const.TAG, "Next lesson: " + nextLessonTime);
                    Log.d(Const.TAG, "Location: " + currentLocation);
                    return DepartureTimeCalculator.calculateDepartureTime(context, currentLocation, DHBW_KARLSUHE_ID, nextLessonTime);
                }
            }).observeOn(Schedulers.computation()).subscribe(new MaybeObserver<DateTime>() {
                @Override
                public void onSubscribe(final Disposable d) {
                }

                @Override
                public void onSuccess(@NonNull final DateTime departureTime) {

                    Log.d(Const.TAG, "Departure Time: " + departureTime);
                    final DateTime wakeTime = departureTime.minusMinutes(SaveSharedPreference.getPrefTime(context));
                    Log.d(Const.TAG, "Wake Time: " + wakeTime);
                    final DateTime now = new DateTime();
                    if(wasLastBeforeAlarm(wakeTime, now)) {
                        Log.d(Const.TAG,"Setting alarm");
                        ClockSetter.setAlarmClock(context, wakeTime);
                        BackgroundScheduler.setNextAlarm(context, now.plusDays(1).withHourOfDay(1));
                    } else {
                        BackgroundScheduler.setNextAlarm(context, now.plusHours(1));
                    }
                }

                private boolean wasLastBeforeAlarm(final DateTime wakeTime, final DateTime now) {
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
        } catch (@NonNull final IOException e) {
            e.printStackTrace();
        }
    }

}
