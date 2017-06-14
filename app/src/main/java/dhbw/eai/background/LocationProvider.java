package dhbw.eai.background;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Tasks;

import java.util.concurrent.ExecutionException;

final class LocationProvider {

    private LocationProvider() {
    }

    static Location getCurrentLocation(final Context context) throws ExecutionException, InterruptedException {
        final FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(context);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            throw new IllegalStateException("Permission for GPS not granted");
        }
        return Tasks.await(client.getLastLocation());
    }
}
