package dhbw.eai.background;

import android.net.Uri;
import android.util.Log;
import dhbw.eai.Const;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

final class NextLessonProvider {

    private static final String RAPLA_URL = "https://rapla.dhbw-karlsruhe.de/rapla?key=ah9tAVphicaj4FqCtMVJcgTuZsSn_DzllupDbBF8dLg1Y789fgGBIG3-7NKc8ZNSblTZag8NWNhDUUKfFeSuvg"; //TODO Make configurable
    private static final DateTimeFormatter pattern = DateTimeFormat.forPattern("dd.MM.yy HH:mm").withLocale(Locale.GERMANY);

    private NextLessonProvider() {
    }

    static DateTime getTimeOfFirstLesson(final LocalDate date) throws IOException {
        final Document raplaHtml = getRapla(date);
        List<DateTime> lessons = parseLessons(raplaHtml);
        return getFirstLessonOfDay(lessons,date);
    }

    private static Document getRapla(final LocalDate date) throws IOException {
        final Uri uri = Uri.parse(RAPLA_URL);
        final Uri withDate = uri.buildUpon().appendQueryParameter("day",String.valueOf(date.getDayOfMonth()))
                .appendQueryParameter("month",String.valueOf(date.getMonthOfYear()))
                .appendQueryParameter("year",String.valueOf(date.getYear())).build();
        Log.d(Const.TAG,"Url: " + withDate);
        final URL url = new URL(withDate.toString());

        return Jsoup.parse(url,30000);
    }

    private static String cutOffDayAndEndTime(String dateWithEndTime){
        return dateWithEndTime.substring(3,dateWithEndTime.indexOf('-'));
    }

    private static List<DateTime> parseLessons(Document raplaHtml){
        Elements lessons = raplaHtml.select("span.tooltip");
        List<DateTime> times = new ArrayList<>();
        for (Element lesson : lessons) {
            if("Lehrveranstaltung".equals(lesson.select("strong").first().text())) {
                String lessonDate = lesson.select("div").get(1).text();
                times.add(pattern.parseDateTime(cutOffDayAndEndTime(lessonDate)));
            }
        }
        return times;
    }

    private static DateTime getFirstLessonOfDay(List<DateTime> lessons, LocalDate date){
        DateTime firstLesson = null;

        for (DateTime lesson : lessons) {
            if(lesson.toLocalDate().equals(date)){
                if(firstLesson == null || lesson.compareTo(firstLesson) < 0){
                    firstLesson = lesson;
                }
            }
        }

        return firstLesson;
    }

}
