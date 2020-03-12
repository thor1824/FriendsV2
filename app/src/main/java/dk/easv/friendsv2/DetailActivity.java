package dk.easv.friendsv2;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import java.net.MalformedURLException;
import java.net.URL;

import dk.easv.friendsv2.Helper.ExstraKeys;
import dk.easv.friendsv2.Model.Friend;

public class DetailActivity extends AppCompatActivity {

    String TAG = MainActivity.TAG;

    EditText etName;
    EditText etPhone;
    EditText etUrl;
    EditText etEmail;
    CheckBox cbFavorite;
    ImageView ivProfile;


    Friend selectedFriend;
    int m_pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Log.d(TAG, "Detail Activity started");

        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etUrl = findViewById(R.id.etUrl);
        etEmail = findViewById(R.id.etEmail);
        ivProfile = findViewById(R.id.ivProfile);
        cbFavorite = findViewById(R.id.cbFavorite);

        setGUI();

        m_pos = getIntent().getExtras().getInt(ExstraKeys.FRIEND_POSITION);
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

    }

    private void setGUI() {
        selectedFriend = (Friend) getIntent().getSerializableExtra(ExstraKeys.FRIEND);

        etName.setText(selectedFriend.getName());
        etPhone.setText(selectedFriend.getPhone());
        etEmail.setText(selectedFriend.getEmail());
        etUrl.setText(selectedFriend.getUrl());
        //ivProfile.setImageURI(new Uri(""));
        cbFavorite.setChecked(selectedFriend.isFavorite());

    }

    private void onClickCancel() {
        setResult(RESULT_CANCELED);
        finish();
    }

    private void onClickOK() {
        Intent result = new Intent();

        selectedFriend = new Friend(etName.getText().toString(), etPhone.getText().toString(), cbFavorite.isChecked());
        result.putExtra(ExstraKeys.FRIEND_UPDATED, selectedFriend);
        result.putExtra(ExstraKeys.FRIEND_POSITION, m_pos);
        setResult(RESULT_OK, result);
        finish();
    }
}
