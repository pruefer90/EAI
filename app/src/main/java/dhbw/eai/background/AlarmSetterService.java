package dhbw.eai.background;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import com.google.maps.errors.ApiException;
import net.fortuna.ical4j.data.ParserException;
import org.joda.time.DateTime;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class AlarmSetterService extends BroadcastReceiver {

    private static final String DHBW_KARLSUHE_ID = "ChIJ15FBwAYHl0cRwn_nSiwjXWI";

    @Override
    public void onReceive(final Context context, final Intent intent) {
        try {
            final DateTime nextLessonTime = NextLessonProvider.getNextLessonTime();
            final Location currentLocation = LocationProvider.getCurrentLocation(context);
            final DateTime departureTime = DepartureTimeCalculator.calculateDepartureTime(currentLocation,DHBW_KARLSUHE_ID,nextLessonTime);
        } catch (InterruptedException | ApiException | IOException | ParserException | ExecutionException e) {
            e.printStackTrace();
        }

        //TODO substract time to prepare

        //todo setCalendar

    }
}
