package dk.cptlk.myfirends.Client.Internal.Controller;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import dk.cptlk.myfirends.Client.Internal.Utility.Converter.BitmapConverter;
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

public class DetailActivity extends AppCompatActivity {
    private Button btnUpdate, btnCancel, btnGetLocation;
    private TextView tvLat, tvLong;
    private EditText etName, etAddress, etPhone, etEmail, etWebUrl;
    private DatePicker dpBirthday;
    private ImageView ivProfile;
    private Friend selectedFriend;
    private boolean isEditEnabled = false;
    private IModel model;
    private Bitmap img;
    private Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            model = ModelFactory.BuildModel(ANDROID_INTERNAL, PERSISTENT_INTERNAL, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setContentView(R.layout.activity_detail2);
        Log.d(Tag.DETAILE_TAG, "Detail Activity started");

        setGUI();
        setButtonEvents();
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RequestCodes.REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            Uri ImageCaptureUri = data.getData();
            cropImage(ImageCaptureUri);

        } else if (requestCode == RequestCodes.REQUEST_PIC_CROP && resultCode == RESULT_OK) {
            Log.d(Tag.DETAILE_TAG, "crop imaged send back");
            Bundle extras = data.getExtras();

            Bitmap imageBitmap = extras.getParcelable("data");

            try {
                if (imageBitmap == null) {
                    selectedFriend.setImage(BitmapConverter.getBytes(imageBitmap));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            model.friendUpdate(selectedFriend);
            ivProfile.setImageBitmap(imageBitmap);
        }
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RequestCodes.REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Log.d(Tag.DETAILE_TAG, "picture taken");
            Bundle extras = data.getExtras();

            Bitmap imageBitmap = extras.getParcelable("data");
            img = imageBitmap;
            ivProfile.setImageBitmap(imageBitmap);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case RequestCodes.REQUEST_CAMERA_PERMISSION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onClickCamera();
                }
                return;
            }
            case RequestCodes.REQUEST_INTERNET_PERMISSION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onClickBrowser();
                }
                return;
            }
            case RequestCodes.REQUEST_PHONE_CALL_PERMISSION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onClickCall();
                }
                return;
            }
            default: {
                Log.d(Tag.DETAILE_TAG, "onPermissionRequestResult not setup for" + permissions[0]);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sms_item:
                Log.d(Tag.DETAILE_TAG, "Sms menu pressed");
                onClickMessage();
                return true;
            case R.id.call_item:
                Log.d(Tag.DETAILE_TAG, "Call menu pressed");
                onClickCall();
                return true;
            case R.id.email_item:
                Log.d(Tag.DETAILE_TAG, "Email menu pressed");
                onClickEmail();
                return true;
            case R.id.webpage_item:
                Log.d(Tag.DETAILE_TAG, "Web menu pressed");
                onClickBrowser();
                return true;
            case R.id.pic_item:
                Log.d(Tag.DETAILE_TAG, "pic menu pressed");
                onClickCamera();
                return true;
            case R.id.map_item:
                Log.d(Tag.DETAILE_TAG, "map menu pressed");
                //TODO Show friend location in map
                return true;
            case R.id.del_item:
                Log.d(Tag.DETAILE_TAG, "delete menu pressed");
                model.friendDelete(selectedFriend.getId());
                setResult(RESULT_OK);
                finish();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void setButtonEvents() {
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickCancel();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickUpdate();
            }
        });

        btnGetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onGetCurretLocation();
            }
        });
    }

    private void setGUI() {
        selectedFriend = (Friend) getIntent().getSerializableExtra(ExstraKeys.FRIEND);
        setTitle(selectedFriend.getName() + " " + selectedFriend.getId());
        this.btnUpdate = findViewById(R.id.btnUpdate);
        this.btnCancel = findViewById(R.id.btnCancel);
        this.btnGetLocation = findViewById(R.id.btnGetLocation);

        this.etName = findViewById(R.id.etName);
        this.etAddress = findViewById(R.id.etAddress);
        this.etPhone = findViewById(R.id.etPhone);
        this.etEmail = findViewById(R.id.etEmail);
        this.etWebUrl = findViewById(R.id.etUrl);
        this.etName.setText(selectedFriend.getName());
        this.etAddress.setText(selectedFriend.getAddress());
        this.etPhone.setText(selectedFriend.getPhone());
        this.etEmail.setText(selectedFriend.getEmail());
        this.etWebUrl.setText(selectedFriend.getWebsite());


        this.tvLat = findViewById(R.id.tvLat);
        this.tvLong = findViewById(R.id.tvLong);
        location = selectedFriend.getLocation();
        this.tvLat.setText(location.getLatitude() + "");
        this.tvLong.setText(location.getLongitude() + "");

        this.dpBirthday = findViewById(R.id.dpBirthDay);
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(selectedFriend.getBirthDay().getTime());
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        this.dpBirthday.init(year, month, day, null);

        this.ivProfile = findViewById(R.id.ivProfile);
        if (selectedFriend.getImage().length > 0) {
            img = BitmapConverter.getImage(selectedFriend.getImage());
            ivProfile.setImageBitmap(img);

        } else {
            String uri = "@drawable/profile_avatar_placeholder";

            int imageResource = getResources().getIdentifier(uri, null, getPackageName());

            Drawable res = getResources().getDrawable(imageResource);
            ivProfile.setImageDrawable(res);
        }

        btnUpdate.setText("Edit");
        enableEditText(false);

    }

    private void onClickCancel() {
        setResult(RESULT_CANCELED);
        finish();
    }

    private void onClickUpdate() {

        if (isEditEnabled) {
            isEditEnabled = false;
            enableEditText(isEditEnabled);


            Log.d(Tag.CREATE_TAG, "Updating Friend");
            GregorianCalendar gc = new GregorianCalendar(dpBirthday.getYear(), dpBirthday.getMonth(), dpBirthday.getDayOfMonth());
            byte[] imgBytes = new byte[0];
            Friend newFriend = null;
            try {
                newFriend = new Friend(
                        selectedFriend.getId(),
                        etName.getText().toString(),
                        etAddress.getText().toString(),
                        etPhone.getText().toString(),
                        etEmail.getText().toString(),
                        etWebUrl.getText().toString(),
                        BitmapConverter.getBytes(img),
                        gc.getTime(),
                        location);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NullPointerException ne) {
                ne.printStackTrace();

                newFriend = new Friend(
                        selectedFriend.getId(),
                        etName.getText().toString(),
                        etAddress.getText().toString(),
                        etPhone.getText().toString(),
                        etEmail.getText().toString(),
                        etWebUrl.getText().toString(),
                        "".getBytes(),
                        gc.getTime(),
                        location);
            }

            try {
                IModel model = ModelFactory.BuildModel(ANDROID_INTERNAL, PERSISTENT_INTERNAL, this);
                model.friendUpdate(newFriend);
                Toast.makeText(this,
                        "Friend Updated", Toast.LENGTH_SHORT).show();
                Log.d(Tag.CREATE_TAG, "Friend was Updated");

            } catch (Exception e) {
                e.printStackTrace();
                Log.d(Tag.CREATE_TAG, "Friend was not Updated");
            }
            btnUpdate.setText("Edit");
            setResult(RESULT_OK);
            finish();

        } else {
            this.btnUpdate.setText("Save");
            isEditEnabled = true;
            enableEditText(isEditEnabled);


        }

    }

    private void onClickCamera() {
        if (!PermissionsManager.isGrantedPermission(Manifest.permission.CAMERA, this)) {
            PermissionsManager.askPermission(Manifest.permission.CAMERA, RequestCodes.REQUEST_CAMERA_PERMISSION, this);
        } else {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, RequestCodes.REQUEST_IMAGE_CAPTURE);
            }
        }

    }

    private void onClickBrowser() {
        if (!PermissionsManager.isGrantedPermission(Manifest.permission.INTERNET, this)) {
            PermissionsManager.askPermission(Manifest.permission.INTERNET, RequestCodes.REQUEST_INTERNET_PERMISSION, this);
        } else {
            Intent i = new Intent(Intent.ACTION_VIEW);
            String url = selectedFriend.getWebsite();
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "http://" + url;
            }
            i.setData(Uri.parse(url));
            startActivity(i);
        }

    }

    private void onClickMessage() {
        if (!PermissionsManager.isGrantedPermission(Manifest.permission.SEND_SMS, this)) {
            PermissionsManager.askPermission(Manifest.permission.SEND_SMS, RequestCodes.REQUEST_SEND_SMS_PERMISSION, this);
        } else {
            /*Intent sendIntent = new Intent(Intent.ACTION_VIEW);
            sendIntent.putExtra("sms_body", "default content");
            sendIntent.setType("vnd.android-dir/mms-sms");
            Log.d(Tag.DETAILE_TAG, (sendIntent.resolveActivity(getPackageManager()) != null) + ": tjek");
            if (sendIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(sendIntent);
            }*/
            Uri uri = Uri.parse("smsto:" + selectedFriend.getPhone());
            Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
            intent.putExtra("sms_body", "");
            startActivity(intent);
        }

    }

    private void onClickEmail() {

        try {
            Intent mailIntent = new Intent(Intent.ACTION_VIEW);
            Uri data = Uri.parse("mailto:?to=" + selectedFriend.getEmail());
            mailIntent.setData(data);
            startActivity(Intent.createChooser(mailIntent, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }

    }

    private void onGetCurretLocation() {
        if (!PermissionsManager.isGrantedPermission(Manifest.permission.ACCESS_COARSE_LOCATION, this)) {
            PermissionsManager.askPermission(Manifest.permission.ACCESS_COARSE_LOCATION, RequestCodes.REQUEST_ACCESS_COARSE_LOCATION_PERMISSION, this);
        } else {
            Location newlocation = LocationManager.getCurrentLocation(this);
            location = newlocation;
            this.tvLat.setText(location.getLatitude() + "");
            this.tvLong.setText(location.getLongitude() + "");
        }
    }

    @SuppressLint("MissingPermission")
    private void onClickCall() {
        if (!PermissionsManager.isGrantedPermission(Manifest.permission.CALL_PHONE, this)) {
            PermissionsManager.askPermission(Manifest.permission.CALL_PHONE, RequestCodes.REQUEST_PHONE_CALL_PERMISSION, this);
        } else {
            Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "+45" + selectedFriend.getPhone()));
            if (callIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(callIntent);
            }
        }

    }

    private void enableEditText(boolean enabled) {
        etName.setEnabled(enabled);
        etPhone.setEnabled(enabled);
        etEmail.setEnabled(enabled);
        etWebUrl.setEnabled(enabled);
        etAddress.setEnabled(enabled);
        dpBirthday.setEnabled(enabled);
        btnGetLocation.setEnabled(enabled);
        btnGetLocation.setBackgroundResource(enabled ? R.drawable.squardbutton : R.drawable.squardbuttondead);
    }

    public void cropImage(Uri mImageCaptureUri) {
        Log.d(Tag.DETAILE_TAG, "crop imaged started");
        Intent intent = new Intent("com.android.camera.action.CROP");
//        intent.setClassName("com.android.camera", "com.android.camera.CropImage");
        intent.setType("image/*");

        List<ResolveInfo> list = getPackageManager().queryIntentActivities(intent, 0);

        int size = list.size();

        if (size == 0) {
            Toast.makeText(this, "Can not find image crop app", Toast.LENGTH_SHORT).show();

            return;
        } else {
            intent.setData(mImageCaptureUri);

            intent.putExtra("outputX", 200);
            intent.putExtra("outputY", 200);
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("scale", true);
            intent.putExtra("return-data", true);

            Intent i = new Intent(intent);
            ResolveInfo res = list.get(1);
            i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            startActivityForResult(i, RequestCodes.REQUEST_PIC_CROP);

        }
    }

}
