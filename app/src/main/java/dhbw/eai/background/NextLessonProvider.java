package dhbw.eai.background;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import dhbw.eai.Const;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
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
import java.util.Locale;
import java.util.concurrent.Callable;

final class NextLessonProvider {

    private static final DateTimeFormatter pattern = DateTimeFormat.forPattern("dd.MM.yy HH:mm").withLocale(Locale.GERMANY);

    private NextLessonProvider() {
    }

    @NonNull
    static Maybe<DateTime> getTimeOfFirstLesson(@NonNull final String rapla_URL, @NonNull final LocalDate date) {
        return Single.fromCallable(new Callable<Document>() {
            @NonNull
            @Override
            public Document call() throws Exception {
                return getRapla(rapla_URL, date);
            }
        }).flatMapObservable(new Function<Document, ObservableSource<DateTime>>() {
            @NonNull
            @Override
            public ObservableSource<DateTime> apply(@NonNull @io.reactivex.annotations.NonNull final Document document) throws Exception {
                return parseLessons(document);
            }
        }).filter(new Predicate<DateTime>() {
            @Override
            public boolean test(@NonNull @io.reactivex.annotations.NonNull final DateTime dateTime) throws Exception {
                return dateTime.toLocalDate().equals(date);
            }
        }).sorted().firstElement();
    }

    @NonNull
    private static Document getRapla(@NonNull final String rapla_URL, @NonNull final LocalDate date) throws IOException {
        final Uri uri = Uri.parse(rapla_URL);
        final Uri withDate = uri.buildUpon().appendQueryParameter("day", String.valueOf(date.getDayOfMonth()))
                .appendQueryParameter("month", String.valueOf(date.getMonthOfYear()))
                .appendQueryParameter("year", String.valueOf(date.getYear())).build();
        Log.d(Const.TAG, "Url: " + withDate);
        final URL url = new URL(withDate.toString());

        return Jsoup.parse(url, 30000);
    }

    @NonNull
    private static ObservableSource<DateTime> parseLessons(@NonNull final Document raplaHtml) {
        final Elements lessons = raplaHtml.select("span.tooltip");

        return Observable.fromIterable(lessons).filter(new Predicate<Element>() {
            @Override
            public boolean test(@NonNull @io.reactivex.annotations.NonNull final Element lesson) throws Exception {
                return "Lehrveranstaltung".equals(lesson.select("strong").first().text());
            }
        }).map(new Function<Element, DateTime>() {
            @Override
            public DateTime apply(@NonNull @io.reactivex.annotations.NonNull final Element lesson) throws Exception {
                final String lessonDate = lesson.select("div").get(1).text();
                return pattern.parseDateTime(cutOffDayAndEndTime(lessonDate));
            }
        });
    }

    @NonNull
    private static String cutOffDayAndEndTime(@NonNull final String dateWithEndTime) {
        return dateWithEndTime.substring(3, dateWithEndTime.indexOf('-'));
    }

}
