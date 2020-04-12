package dk.cptlk.myfirends.Client.Internal.Controller;

import android.Manifest;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import dk.cptlk.myfirends.Client.Internal.Utility.Globals.ExstraKeys;
import dk.cptlk.myfirends.Client.Internal.Utility.Globals.RequestCodes;
import dk.cptlk.myfirends.Client.Internal.Utility.Managers.LocationManager;
import dk.cptlk.myfirends.Client.Internal.Utility.Managers.PermissionsManager;
import dk.cptlk.myfirends.Core.ClientAdapter.IModel;
import dk.cptlk.myfirends.Core.Entities.Friend;
import dk.cptlk.myfirends.Core.Entities.Location;
import dk.cptlk.myfirends.Core.Utility.Factory.ModelFactory;
import dk.cptlk.myfirends.R;

import static dk.cptlk.myfirends.Core.Utility.Factory.Client.ANDROID_INTERNAL;
import static dk.cptlk.myfirends.Core.Utility.Factory.Data.PERSISTENT_INTERNAL;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private boolean singleMarker;
    private Friend friend;
    private List<Friend> friends;
    private IModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if (!PermissionsManager.isGrantedPermission(Manifest.permission.ACCESS_COARSE_LOCATION, this)) {
            PermissionsManager.askPermission(Manifest.permission.ACCESS_COARSE_LOCATION, RequestCodes.REQUEST_ACCESS_COARSE_LOCATION_PERMISSION, this);
        }
        try {
            model = ModelFactory.BuildModel(ANDROID_INTERNAL, PERSISTENT_INTERNAL, this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (savedInstanceState != null) {
            singleMarker = savedInstanceState.getBoolean(ExstraKeys.FRIEND_MAP_VIEW);
        }
        if (singleMarker) {
            friend = (Friend) savedInstanceState.getSerializable(ExstraKeys.FRIEND);
        } else {
            friends = model.friendReadAll();
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (!PermissionsManager.isGrantedPermission(Manifest.permission.ACCESS_COARSE_LOCATION, this)) {
            PermissionsManager.askPermission(Manifest.permission.ACCESS_COARSE_LOCATION, RequestCodes.REQUEST_ACCESS_COARSE_LOCATION_PERMISSION, this);
        } else {
            mMap = googleMap;
            Location location = LocationManager.getCurrentLocation(this);
            LatLng Me = location.asLatLng();
            mMap.addMarker(new MarkerOptions().position(Me).title("My Location"));
            if (singleMarker) {
                mMap.addMarker(new MarkerOptions().position(friend.getLocation().asLatLng()).title(friend.getName()));
            } else {
                for (Friend f : friends) {
                    mMap.addMarker(new MarkerOptions().position(f.getLocation().asLatLng()).title(f.getName()));
                }
            }

            mMap.moveCamera(CameraUpdateFactory.newLatLng(Me));
        }
    }

}
