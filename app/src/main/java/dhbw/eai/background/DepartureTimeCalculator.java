package dhbw.eai.background;

import android.location.Location;
import android.support.annotation.NonNull;
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.Duration;
import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;
import org.joda.time.DateTime;

import java.io.IOException;

final class DepartureTimeCalculator {

    private static final String API_KEY = "AIzaSyCh-t7A7WcaZ-Ybm9hVSV7Ki57JvHTSy7o";

    private DepartureTimeCalculator() {
    }

    @NonNull
    static DateTime calculateDepartureTime(@NonNull final Location from, @NonNull final String to, @NonNull final DateTime arrivalTime) throws InterruptedException, ApiException, IOException {
        final GeoApiContext context = new GeoApiContext().setApiKey(API_KEY);

        final DirectionsApiRequest req = DirectionsApi.newRequest(context);
        req.origin(new LatLng(from.getLatitude(),from.getLongitude()));
        req.destination(to);
        req.arrivalTime(arrivalTime);
        req.mode(TravelMode.DRIVING); //TODO make configurable

        final DirectionsResult res = req.await();
        final Duration duration = res.routes[0].legs[0].duration;
        return arrivalTime.withDurationAdded(new org.joda.time.Duration(duration.inSeconds * 1000),-1);
    }

}
