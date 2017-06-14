package dhbw.eai.background;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

public class AlarmSetterService extends BroadcastReceiver {

    private static final String DHBW_KARLSUHE_ID = "ChIJ15FBwAYHl0cRwn_nSiwjXWI";

    @Override
    public void onReceive(final Context context, final Intent intent) {
        final Calendar nextLessonTime = NextLessonProvider.getNextLessonTime();
        final Calendar departureTime = DepartureTimeCalculator.calculateDepartureTime("",DHBW_KARLSUHE_ID,nextLessonTime); //TODO get from location from GPS

        //TODO substract time to prepare

        //todo setCalendar

    }
}
