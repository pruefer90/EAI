package dhbw.eai.background;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import com.google.android.gms.location.LocationRequest;
import com.patloew.rxlocation.RxLocation;
import io.reactivex.Single;

import java.util.concurrent.TimeUnit;

final class LocationProvider {

    private LocationProvider() {
    }

    @NonNull
    static Single<Location> getCurrentLocation(@NonNull final Context context) {
        final RxLocation rxLocation = new RxLocation(context);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            throw new IllegalStateException("Permission for GPS not granted");
        }
        final LocationRequest request = new LocationRequest();
        request.setNumUpdates(1);
        request.setInterval(100);
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return rxLocation.location().updates(request, 10, TimeUnit.SECONDS).timeout(10, TimeUnit.SECONDS).firstOrError();
    }

}
