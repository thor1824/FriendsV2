package dk.easv.friendsv2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import dk.easv.friendsv2.Helper.ExstraKeys;
import dk.easv.friendsv2.Helper.RequestCodes;
import dk.easv.friendsv2.Model.Friend;

public class DetailActivity extends AppCompatActivity {

    private String TAG = MainActivity.TAG;

    private EditText etName, etPhone, etUrl, etEmail;
    private CheckBox cbFavorite;
    private ImageView ivProfile;
    private Button btnOK;

    private Friend selectedFriend;
    private int m_pos;
    private boolean isEditEnabled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Log.d(TAG, "Detail Activity started");
        Log.d(TAG, getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA) + "");
        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etUrl = findViewById(R.id.etUrl);
        etEmail = findViewById(R.id.etEmail);
        ivProfile = findViewById(R.id.ivProfile);
        cbFavorite = findViewById(R.id.cbFavorite);
        btnOK = findViewById(R.id.btnOk);

        setGUI();
        setButtonEvents();
        m_pos = getIntent().getExtras().getInt(ExstraKeys.FRIEND_POSITION);


    }

    private void setButtonEvents() {
        findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickCancel();
            }
        });

        findViewById(R.id.btnOk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickOK();
            }
        });

        findViewById(R.id.btnCamera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickCamera();
            }
        });
        findViewById(R.id.btnBrowser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickBrowser();
            }
        });
        findViewById(R.id.btnCall).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickCall();
            }
        });
        findViewById(R.id.btnSms).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickMessage();
            }
        });
        findViewById(R.id.btnEmail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickEmail();
            }
        });
    }

    private void setGUI() {
        selectedFriend = (Friend) getIntent().getSerializableExtra(ExstraKeys.FRIEND);
        etName.setText(selectedFriend.getName());
        etPhone.setText(selectedFriend.getPhone());
        etEmail.setText(selectedFriend.getEmail());
        etUrl.setText(selectedFriend.getUrl());
        cbFavorite.setChecked(selectedFriend.isFavorite());
        btnOK.setText("Edit");

        enableEditText(false);

        String uri = "@drawable/profile_avatar_placeholder";  // where myresource (without the extension) is the file

        int imageResource = getResources().getIdentifier(uri, null, getPackageName());

        Drawable res = getResources().getDrawable(imageResource);
        ivProfile.setImageDrawable(res);
    }

    private void onClickCancel() {
        setResult(RESULT_CANCELED);
        finish();
    }

    private void onClickOK() {

        if (isEditEnabled) {
            enableEditText(false);
            Intent result = new Intent();

            selectedFriend = new Friend(etName.getText().toString(), etPhone.getText().toString(), cbFavorite.isChecked());
            result.putExtra(ExstraKeys.FRIEND_UPDATED, selectedFriend);
            result.putExtra(ExstraKeys.FRIEND_POSITION, m_pos);
            setResult(RESULT_OK, result);

            btnOK.setText("Edit");
            enableEditText(false);
            isEditEnabled = false;
            Toast.makeText(this,
                    "Saved", Toast.LENGTH_SHORT).show();
            //finish();
        } else {
            btnOK.setText("Save");
            enableEditText(true);
            isEditEnabled = true;
        }

    }

    private void enableEditText(boolean enabled) {
        etName.setEnabled(enabled);
        etPhone.setEnabled(enabled);
        etEmail.setEnabled(enabled);
        etUrl.setEnabled(enabled);
        cbFavorite.setEnabled(enabled);
    }

    private void onClickCamera() {
        if (!isGrantedPermission(Manifest.permission.CAMERA)) {
            askPermission(Manifest.permission.CAMERA, RequestCodes.REQUEST_CAMERA_PERMISSION);
        } else {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, RequestCodes.REQUEST_IMAGE_CAPTURE);
            }
        }

    }

    private void onClickBrowser() {
        if (!isGrantedPermission(Manifest.permission.INTERNET)) {
            askPermission(Manifest.permission.INTERNET, RequestCodes.REQUEST_INTERNET_PERMISSION);
        } else {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(selectedFriend.getUrl()));
            if (browserIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(browserIntent);
            }
        }

    }

    private void onClickCall() {
        if (!isGrantedPermission(Manifest.permission.CALL_PHONE)) {
            askPermission(Manifest.permission.CALL_PHONE, RequestCodes.REQUEST_PHONE_CALL_PERMISSION);
        } else {
            Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "+45" + selectedFriend.getPhone()));
            if (callIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(callIntent);
            }
        }

    }

    private void onClickMessage() {
        if (!isGrantedPermission(Manifest.permission.SEND_SMS)) {
            askPermission(Manifest.permission.SEND_SMS, RequestCodes.REQUEST_SEND_SMS_PERMISSION);
        } else {
            Intent sendIntent = new Intent(Intent.ACTION_VIEW);
            sendIntent.putExtra("sms_body", "default content");
            sendIntent.setType("vnd.android-dir/mms-sms");

            if (sendIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(sendIntent);
            }
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RequestCodes.REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ivProfile.setImageBitmap(imageBitmap);
        }
    }

    private void askPermission(String Permission, int RequestCode) {
        ActivityCompat.requestPermissions(this,
                new String[]{Permission},
                RequestCode);

    }

    private boolean isGrantedPermission(String permission) {
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            int cameraPermission = this.checkSelfPermission(permission);
            return cameraPermission == PackageManager.PERMISSION_GRANTED;
        } else {
            return true;
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
                // If request is cancelled, the result arrays are empty.
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
        }
    }

}
