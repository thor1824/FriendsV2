package dk.easv.friendsv2;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import java.io.Serializable;

import dk.easv.friendsv2.Model.BEFriend;

public class DetailActivity extends AppCompatActivity {

    String TAG = MainActivity.TAG;

    EditText etName;
    EditText etPhone;
    CheckBox cbFavorite;


    BEFriend m_friend;
    int m_pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Log.d(TAG, "Detail Activity started");

        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        cbFavorite = findViewById(R.id.cbFavorite);

        setGUI();

        m_pos = getIntent().getExtras().getInt("position");
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

    private void setGUI()
    {
        m_friend = (BEFriend) getIntent().getSerializableExtra("friend");

        etName.setText(m_friend.getName());
        etPhone.setText(m_friend.getPhone());
        cbFavorite.setChecked(m_friend.isFavorite());
    }

    private void onClickCancel() {
        setResult(RESULT_CANCELED);
        finish();
    }

    private void onClickOK() {
      Intent result = new Intent();

      m_friend = new BEFriend(etName.getText().toString(), etPhone.getText().toString(), cbFavorite.isChecked());
      result.putExtra("updatedfriend",m_friend );
      result.putExtra("position", m_pos);
      setResult(RESULT_OK, result);
      finish();
    }
}
