package dk.cptlk.myfirends.Client.Internal.Controller;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.GregorianCalendar;

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

public class CreateActivity extends AppCompatActivity {

    private Button btnCreate, btnCancel, btnGetLocation;
    private TextView tvLat, tvLong;
    private EditText etNewName, etNewAddress, etNewPhone, etNewEmail, etNewWebUrl;
    private DatePicker dpBirthday;

    private Location location;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(Tag.CREATE_TAG, "Creating layout");
        this.setTitle("Add Friend");
        this.setContentView(R.layout.activity_create);

        setupLayout();
        setupEvents();
    }

    private void setupEvents() {
        this.btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBtnCreate();
            }
        });

        this.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCancel();
            }
        });

        this.btnGetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onGetCurrentLocation();
            }
        });
    }

    private void onBtnCreate() {
        Log.d(Tag.CREATE_TAG, "Creating Friend");
        GregorianCalendar gc = new GregorianCalendar(dpBirthday.getYear(), dpBirthday.getMonth(), dpBirthday.getDayOfMonth());

        Friend newFriend = new Friend(
                etNewName.getText().toString(),
                etNewAddress.getText().toString(),
                etNewPhone.getText().toString(),
                etNewEmail.getText().toString(),
                "http://www." + etNewWebUrl.getText().toString(),
                "".getBytes(),
                gc.getTime(),
                location
        );

        try {
            IModel model = ModelFactory.BuildModel(ANDROID_INTERNAL, PERSISTENT_INTERNAL, this);
            model.friendCreate(newFriend);
            Toast.makeText(this,
                    "Friend Added", Toast.LENGTH_SHORT).show();
            Log.d(Tag.CREATE_TAG, "Friend was Created");
            finish();

        } catch (Exception e) {
            e.printStackTrace();
            Log.d(Tag.CREATE_TAG, "Friend was not Created");
        }


    }

    private void onCancel() {
        onBackPressed();
    }

    private void onGetCurrentLocation() {
        if (!PermissionsManager.isGrantedPermission(Manifest.permission.ACCESS_COARSE_LOCATION, this)) {
            PermissionsManager.askPermission(Manifest.permission.ACCESS_COARSE_LOCATION, RequestCodes.REQUEST_ACCESS_COARSE_LOCATION_PERMISSION, this);
        } else {
            Location location = LocationManager.getCurrentLocation(this);
            tvLat.setText(location.getLatitude() + "");
            tvLong.setText(location.getLongitude() + "");
        }
    }

    private void setupLayout() {
        this.btnCreate = findViewById(R.id.btnCreate);
        this.btnCancel = findViewById(R.id.btnCancelCre);
        this.btnGetLocation = findViewById(R.id.btnGetLocation);

        this.etNewName = findViewById(R.id.etNewName);
        this.etNewAddress = findViewById(R.id.etNewAddress);
        this.etNewPhone = findViewById(R.id.etNewPhone);
        this.etNewEmail = findViewById(R.id.etNewEmail);
        this.etNewWebUrl = findViewById(R.id.etNewUrl);

        this.tvLat = findViewById(R.id.tvLat);
        this.tvLong = findViewById(R.id.tvLong);

        this.dpBirthday = findViewById(R.id.dpNewBirthDay);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RequestCodes.REQUEST_ACCESS_COARSE_LOCATION_PERMISSION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onGetCurrentLocation();
                }
                return;
            }
            default: {
                Log.d(Tag.CREATE_TAG, "onPermissionRequestResult not setup for " + permissions[0]);
            }
        }
    }
}
