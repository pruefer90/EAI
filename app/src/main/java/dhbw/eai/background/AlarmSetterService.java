package dhbw.eai.background;

import android.content.Context;
import android.location.Location;
import android.util.Log;
import com.google.maps.errors.ApiException;
import dhbw.eai.Const;
import net.fortuna.ical4j.data.ParserException;
import org.joda.time.DateTime;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public final class AlarmSetterService {

    private static final String DHBW_KARLSUHE_ID = "ChIJ15FBwAYHl0cRwn_nSiwjXWI";

    private AlarmSetterService() {
    }

    public static void setAlarm(final Context context) {
        try {
            Log.d(Const.TAG,"Setting Alarm");
            final DateTime now = new DateTime();
            final DateTime nextLessonTime = NextLessonProvider.getTimeOfFirstLesson(now);
            final Location currentLocation = LocationProvider.getCurrentLocation(context);
            final DateTime departureTime = DepartureTimeCalculator.calculateDepartureTime(currentLocation,DHBW_KARLSUHE_ID,nextLessonTime);


            //TODO substract time to prepare

            //todo setAlarmClock

            BackgroundScheduler.setNextAlarm(context,BackgroundScheduler.getNextAlarmTime(now,departureTime));

        } catch (InterruptedException | ApiException | IOException | ParserException | ExecutionException e) {
            e.printStackTrace();
        }
    }

}
