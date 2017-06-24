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
import io.reactivex.SingleSource;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

final class LocationProvider {

    private LocationProvider() {
    }

    @NonNull
    static Single<Location> getCurrentLocation(@NonNull final Context context) {


        return Single.defer(new Callable<SingleSource<Location>>() {
            @Override
            public SingleSource<Location> call() throws IllegalStateException {
                final RxLocation rxLocation = new RxLocation(context);
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    throw new IllegalStateException("Permission for GPS not granted");
                }
                final LocationRequest request = new LocationRequest();
                request.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
                return rxLocation.location().updates(request, 10, TimeUnit.SECONDS).firstOrError();
            }
        });


    }

}
