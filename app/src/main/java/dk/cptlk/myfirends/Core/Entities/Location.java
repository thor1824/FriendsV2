package dk.cptlk.myfirends.Core.Entities;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

public
class Location  implements Serializable {
    private double longitude;
    private double latitude;

    public Location(double longitude, double latitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public LatLng asLatLng() {
        return new LatLng(latitude, longitude);
    }
}
