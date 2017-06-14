package dhbw.eai.background;

import java.util.Calendar;
import java.util.GregorianCalendar;

final class NextLessonProvider {

    private NextLessonProvider() {
    }

    static Calendar getNextLessonTime(){
        return new GregorianCalendar(); //TODO
    }

}
