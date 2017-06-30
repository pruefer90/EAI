package dhbw.eai.background;

import android.content.Context;
import android.location.Location;
import android.support.annotation.NonNull;
import android.util.Log;
import com.google.maps.errors.ApiException;
import dhbw.eai.Const;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import java.io.IOException;

/**
 * Created by mathz on 29.06.2017.
 */

final class WakeTimeProvider {
    private static final String DHBW_KARLSUHE_ID = "place_id:ChIJ15FBwAYHl0cRwn_nSiwjXWI";

    private WakeTimeProvider() {
    }

    static Maybe<DateTime> getWakeTime(@NonNull final Context context, @NonNull final LocalDate dateToCheck) {
        final Maybe<DateTime> nextLessonTime = NextLessonProvider.getTimeOfFirstLesson(SaveSharedPreference.getPrefLink(context), dateToCheck).subscribeOn(Schedulers.io());
        final Single<Location> currentLocation = LocationProvider.getCurrentLocation(context).subscribeOn(Schedulers.io());
        return nextLessonTime.zipWith(currentLocation.toMaybe(), new BiFunction<DateTime, Location, DateTime>() {
            @NonNull
            @Override
            public DateTime apply(@NonNull @io.reactivex.annotations.NonNull final DateTime nextLessonTime, @NonNull @io.reactivex.annotations.NonNull final Location currentLocation) throws InterruptedException, ApiException, IOException {
                Log.d(Const.TAG, "Next lesson: " + nextLessonTime);
                Log.d(Const.TAG, "Location: " + currentLocation);
                return DepartureTimeCalculator.calculateDepartureTime(context, currentLocation, DHBW_KARLSUHE_ID, nextLessonTime);
            }
        }).observeOn(Schedulers.computation()).map(new Function<DateTime, DateTime>() {
            @NonNull
            @Override
            public DateTime apply(@NonNull @io.reactivex.annotations.NonNull final DateTime departureTime) throws Exception {
                Log.d(Const.TAG, "Departure Time: " + departureTime);
                return departureTime.minusMinutes(SaveSharedPreference.getPrefTime(context));
            }
        });
    }
}
