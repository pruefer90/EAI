package dhbw.eai.background;

import android.util.Log;
import dhbw.eai.Const;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.filter.Filter;
import net.fortuna.ical4j.filter.PeriodRule;
import net.fortuna.ical4j.filter.Rule;
import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.model.component.CalendarComponent;
import org.joda.time.DateTime;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;

final class NextLessonProvider {

    private static final String RAPLA_URL = "https://rapla.dhbw-karlsruhe.de/rapla?key=6fVJKx_Dtdo50BamguknALlCl0uus2dLpVS89HNLYK4"; //TODO Make configurable

    private NextLessonProvider() {
    }

    static DateTime getTimeOfFirstLesson(final DateTime date) throws IOException, ParserException {
        final InputStream stream = getRapla();
        final Calendar calendar = parseICS(stream);
        return getFirstLesson(calendar, date);
    }

    private static InputStream getRapla() throws IOException {
        final URL url = new URL(RAPLA_URL);

        final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.connect();
        final int response = conn.getResponseCode();
        Log.d(Const.TAG,"Response code: " + response);
        return conn.getInputStream();
    }

    private static Calendar parseICS(final InputStream stream) throws IOException, ParserException {
        return new CalendarBuilder().build(stream);
    }

    private static DateTime getFirstLesson(final Calendar calendar, DateTime date){
        date = new DateTime(2017,06,14,0,0);
        Period period = new Period(new net.fortuna.ical4j.model.DateTime(date.withTime(0,0,0,0).toDate()),new Dur(1,0,0,0));
        Filter<CalendarComponent> filter = new Filter<>(new Rule[]{new PeriodRule<>(period)}, Filter.MATCH_ALL);

        Collection<CalendarComponent> components = filter.filter(calendar.getComponents(Component.VEVENT));
        Log.d(Const.TAG,"Found components: " + components.size());
        for (CalendarComponent calendarComponent : components) {
            Log.d(Const.TAG,"Component: " + calendarComponent.getName());
            for (Property property : calendarComponent.getProperties()) {
                Log.d(Const.TAG,"Property [" + property.getName() + ", " + property.getValue() + "]");
            }
        }

        final DateTime now = new DateTime();
        return now; //TODO Implement
    }

}
