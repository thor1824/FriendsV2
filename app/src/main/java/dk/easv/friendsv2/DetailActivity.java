package dk.easv.friendsv2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import dk.easv.friendsv2.Model.BEFriend;

public class DetailActivity extends AppCompatActivity {

    String TAG = MainActivity.TAG;

    EditText etName;
    EditText etPhone;
    CheckBox cbFavorite;

    int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Log.d(TAG, "Detail Activity started");

        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        cbFavorite = findViewById(R.id.cbFavorite);

        Bundle extras = getIntent().getExtras();
        position = (int)extras.get("position");
        setGUI(extras);
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

    private void setGUI(Bundle data)
    {
        etName.setText(data.get("name").toString());
        etPhone.setText(data.get("phone").toString());
        cbFavorite.setChecked((boolean)data.get("favorite"));
    }

    private void onClickCancel()
    {
        setResult(RESULT_CANCELED);
        finish();
    }

    private void onClickOK()
    {
        Intent data = new Intent();
        data.putExtra("position", position);
        data.putExtra("newname", etName.getText().toString());
        setResult(RESULT_OK, data);
        finish();


    }
}
