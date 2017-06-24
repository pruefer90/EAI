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
            //            final LocalDate today = new LocalDate();

            final LocalDate today = new LocalDate(2017, 6, 21);
            final Maybe<DateTime> nextLessonTime = NextLessonProvider.getTimeOfFirstLesson(SaveSharedPreference.getPrefLink(context), today).subscribeOn(Schedulers.io());
            final Single<Location> currentLocation = LocationProvider.getCurrentLocation(context).subscribeOn(Schedulers.io());
            nextLessonTime.zipWith(currentLocation.toMaybe(), new BiFunction<DateTime, Location, DateTime>() {
                @Override
                public DateTime apply(@io.reactivex.annotations.NonNull final DateTime nextLessonTime, @io.reactivex.annotations.NonNull final Location currentLocation) throws Exception {
                    Log.d(Const.TAG, "Next lesson: " + nextLessonTime);
                    Log.d(Const.TAG, "Location: " + currentLocation);
                    return DepartureTimeCalculator.calculateDepartureTime(context, currentLocation, DHBW_KARLSUHE_ID, nextLessonTime);
                }
            }).observeOn(Schedulers.computation()).subscribe(new MaybeObserver<DateTime>() {
                @Override
                public void onSubscribe(final Disposable d) {

                }

                @Override
                public void onSuccess(final DateTime departureTime) {

                    Log.d(Const.TAG, "Departure Time: " + departureTime);
                    final DateTime wakeTime = departureTime.minusMinutes(SaveSharedPreference.getPrefTime(context));
                    Log.d(Const.TAG, "Wake Time: " + wakeTime);

                    //todo setAlarmClock

                    BackgroundScheduler.setNextAlarm(context, BackgroundScheduler.getNextAlarmTime(new DateTime(), departureTime));
                }

                @Override
                public void onError(final Throwable e) {
                    e.printStackTrace();
                }

                @Override
                public void onComplete() {
                    Log.d(Const.TAG, "No lessons today");
                }
            });
        } catch (@NonNull IOException e) {
            e.printStackTrace();
        }
    }

}
