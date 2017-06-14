package dhbw.eai.background;

import android.util.Log;
import dhbw.eai.Const;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import org.joda.time.DateTime;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

final class NextLessonProvider {

    private static final String RAPLA_URL = "https://rapla.dhbw-karlsruhe.de/rapla?key=6fVJKx_Dtdo50BamguknALlCl0uus2dLpVS89HNLYK4"; //TODO Make configurable

    private NextLessonProvider() {
    }

    static DateTime getNextLessonTime() throws IOException, ParserException {
        final InputStream stream = getRapla();
        final Calendar calendar = parseICS(stream);
        return getNextLesson(calendar);
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

    private static DateTime getNextLesson(final Calendar calendar){
        final DateTime now = new DateTime();
        return now; //TODO Implement
    }

}
