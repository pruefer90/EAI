package dhbw.eai.background;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.google.maps.errors.ApiException;
import net.fortuna.ical4j.data.ParserException;
import org.joda.time.DateTime;

import java.io.IOException;

public class AlarmSetterService extends BroadcastReceiver {

    private static final String DHBW_KARLSUHE_ID = "ChIJ15FBwAYHl0cRwn_nSiwjXWI";

    @Override
    public void onReceive(final Context context, final Intent intent) {
        try {
            final DateTime nextLessonTime = NextLessonProvider.getNextLessonTime();
            final DateTime departureTime = DepartureTimeCalculator.calculateDepartureTime("",DHBW_KARLSUHE_ID,nextLessonTime); //TODO get from location from GPS
        } catch (InterruptedException | ApiException | IOException | ParserException e) {
            e.printStackTrace();
        }

        //TODO substract time to prepare

        //todo setCalendar

    }
}
