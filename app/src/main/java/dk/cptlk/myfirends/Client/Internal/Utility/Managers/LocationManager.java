package dk.cptlk.myfirends.Client.Internal.Utility.Managers;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.location.Criteria;
import android.widget.Toast;

import dk.cptlk.myfirends.Core.Entities.Location;

public class LocationManager {

    public static Location getCurrentLocation(Activity context) {
        android.location.LocationManager locationManager = (android.location.LocationManager) context.getSystemService(context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();

        String provider = locationManager.getBestProvider(criteria, false);
        if (provider != null && !provider.equals("")) {

            @SuppressLint("MissingPermission") android.location.Location androidLocation = locationManager.getLastKnownLocation(provider);
            if (androidLocation != null)
            {
                Location location = new Location(androidLocation.getLongitude(), androidLocation.getLatitude());
                return location;

            } else{
                Toast.makeText(context.getBaseContext(), "Location can't be retrieved", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context.getBaseContext(), "No Provider Found", Toast.LENGTH_SHORT).show();
        }
        return null;
    }
}
