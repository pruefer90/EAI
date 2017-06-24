package dhbw.eai.background;

import android.content.Context;
import android.location.Location;
import android.support.annotation.NonNull;
import android.util.Log;
import com.google.maps.errors.ApiException;
import dhbw.eai.Const;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

final class AlarmSetterService {

    private static final String DHBW_KARLSUHE_ID = "place_id:ChIJ15FBwAYHl0cRwn_nSiwjXWI";

    private AlarmSetterService() {
    }

    static void setAlarm(@NonNull final Context context) {
        try {
            Log.d(Const.TAG,"Setting Alarm");
            final LocalDate today = new LocalDate();
            final DateTime nextLessonTime = NextLessonProvider.getTimeOfFirstLesson(today);
            //TODO there is a slight chance that there's no lessons today...
            Log.d(Const.TAG,"Next lesson: " + nextLessonTime);
            final Location currentLocation = LocationProvider.getCurrentLocation(context);
            Log.d(Const.TAG,"Location: " + currentLocation);
            final DateTime departureTime = DepartureTimeCalculator.calculateDepartureTime(currentLocation,DHBW_KARLSUHE_ID,nextLessonTime);
            Log.d(Const.TAG,"Departure Time: " + departureTime);

            //TODO substract time to prepare

            //todo setAlarmClock

            BackgroundScheduler.setNextAlarm(context,BackgroundScheduler.getNextAlarmTime(new DateTime(),departureTime));

        } catch (@NonNull InterruptedException | ApiException | IOException | ExecutionException e) {
            e.printStackTrace();
        }
    }

}
