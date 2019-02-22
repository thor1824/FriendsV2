package dk.easv.friendsv2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;

public class DetailActivity extends AppCompatActivity {

    String TAG = MainActivity.TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Log.d(TAG, "Detail Activity started");

        EditText etName = findViewById(R.id.etName);
        EditText etPhone = findViewById(R.id.etPhone);
        CheckBox cbFavorite = findViewById(R.id.cbFavorite);

        Bundle extras = getIntent().getExtras();
        etName.setText(extras.get("name").toString());
        etPhone.setText(extras.get("phone").toString());
        cbFavorite.setChecked((boolean)extras.get("favorite"));

    }
}
