package dk.cptlk.myfirends.Client.Internal.Controller;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import dk.cptlk.myfirends.Client.Internal.Utility.Factory.AdapterFactory;
import dk.cptlk.myfirends.Client.Internal.Utility.Globals.ExstraKeys;
import dk.cptlk.myfirends.Client.Internal.Utility.Globals.RequestCodes;
import dk.cptlk.myfirends.Client.Internal.Utility.Globals.Tag;
import dk.cptlk.myfirends.Client.Internal.Utility.Managers.LocationManager;
import dk.cptlk.myfirends.Client.Internal.Utility.Managers.PermissionsManager;
import dk.cptlk.myfirends.Core.ClientAdapter.IModel;
import dk.cptlk.myfirends.Core.Entities.Friend;
import dk.cptlk.myfirends.Core.Entities.Location;
import dk.cptlk.myfirends.Core.Utility.Factory.ModelFactory;
import dk.cptlk.myfirends.R;

import static dk.cptlk.myfirends.Core.Utility.Factory.Client.ANDROID_INTERNAL;
import static dk.cptlk.myfirends.Core.Utility.Factory.Data.PERSISTENT_INTERNAL;

public class MainActivity extends AppCompatActivity {


    private IModel model;
    private ListView lvFriends;
    private Button btnAdd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("Friends v2");
        try {
            model = ModelFactory.BuildModel(ANDROID_INTERNAL, PERSISTENT_INTERNAL, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.setContentView(R.layout.activity_main);

        setupListView(model.friendReadAll());
        setupListViewEvent();
        setupButtonEvents();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.name_item:
                Log.d(Tag.MAIN_TAG, "Listview: sort by name");
                setupListView(model.getFriendSortedName());
                return true;
            case R.id.distance_item:
                Log.d(Tag.MAIN_TAG, "Listview: sort by distance");
                onSortByDistance();
                return true;
            case R.id.mapShow_item:
                Log.d(Tag.MAIN_TAG, "Listview: Show as Map");
                onOpenMap();
                return true;
            case R.id.import_item:
                Log.d(Tag.MAIN_TAG, "Listview: Import info from Contacts");
                // do something
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(Tag.MAIN_TAG, "onResult resultCode = " + resultCode);
        System.out.println("requestCode: " + requestCode);
        System.out.println("resultCode: " + resultCode);
        System.out.println("data: " + data);
        if (resultCode == RESULT_OK) {
            if(resultCode == RESULT_OK){
                onUpdate();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RequestCodes.REQUEST_ACCESS_COARSE_LOCATION_MAP_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onOpenMap();
                }
                break;
            case RequestCodes.REQUEST_ACCESS_COARSE_LOCATION_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onSortByDistance();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }


    }

    private void onOpenMap() {
        if (!PermissionsManager.isGrantedPermission(Manifest.permission.ACCESS_COARSE_LOCATION, this)) {
            PermissionsManager.askPermission(Manifest.permission.ACCESS_COARSE_LOCATION, RequestCodes.REQUEST_ACCESS_COARSE_LOCATION_PERMISSION, this);
        } else {
            Intent intent = new Intent(this, MapsActivity.class);
            startActivity(intent);
        }
    }

    public void onUpdate() {
        setupListView(model.friendReadAll());
    }

    private void setupListView(List<Friend> friends) {
        lvFriends = findViewById(R.id.lvFriends);
        ArrayAdapter<Friend> adapter = AdapterFactory.BuildMyArrayAdapter(this, friends);
        lvFriends.setAdapter(adapter);

    }

    private void onSortByDistance() {
        if (!PermissionsManager.isGrantedPermission(Manifest.permission.ACCESS_COARSE_LOCATION, this)) {
            PermissionsManager.askPermission(Manifest.permission.ACCESS_COARSE_LOCATION, RequestCodes.REQUEST_ACCESS_COARSE_LOCATION_PERMISSION, this);
        } else {
            Location location = LocationManager.getCurrentLocation(this);
            setupListView(model.getFriendSortedDistance(location));
        }
    }

    private void setupButtonEvents() {
        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddClick();
            }
        });
    }

    private void setupListViewEvent() {
        lvFriends.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                onFriendClick(i);
            }
        });
    }


    public void onFriendClick(int position) {
        Intent intent = new Intent(this, DetailActivity.class);

        intent.putExtra(ExstraKeys.FRIEND, (Friend) lvFriends.getItemAtPosition(position));
        intent.putExtra(ExstraKeys.FRIEND_POSITION, position);

        startActivityForResult(intent, 10);
    }

    public void onAddClick() {
        Log.d(Tag.MAIN_TAG, "Add: opens add friend Intent");
        Intent intent = new Intent(this, CreateActivity.class);
        startActivityForResult(intent, 10);
    }


}
