package dk.easv.friendsv2;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import dk.easv.friendsv2.Model.BEFriend;
import dk.easv.friendsv2.Model.Friends;

public class MainActivity extends ListActivity {

    public static String TAG = "Friend2";

    Friends m_friends;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("Friends v2");
        m_friends = new Friends();

        String[] friends;

        friends = m_friends.getNames();

        ListAdapter adapter =
                new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1,
                        friends);

        setListAdapter(adapter);

    }


    @Override
    public void onListItemClick(ListView parent, View v, int position,
                                long id) {

        Intent x = new Intent(this, DetailActivity.class);
        Log.d(TAG, "Detail activity will be started");
        BEFriend friend = m_friends.getAll().get(position);
        x.putExtra("friend", friend);
        x.putExtra("position", position);
        startActivityForResult(x, 10);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        Log.d(TAG, "onResult resultCode = " + resultCode);
        if (resultCode == RESULT_OK) {
            BEFriend upDatedFriend = (BEFriend)data.getExtras().getSerializable("updatedfriend");
            int position = data.getExtras().getInt("position");

            Log.d(TAG, "onResult pos = " + position + " name = " + upDatedFriend.getName());
            m_friends.update(upDatedFriend,position);

            ListAdapter adapter =
                    new ArrayAdapter<String>(this,
                            android.R.layout.simple_list_item_1,
                            m_friends.getNames());

            setListAdapter(adapter);
        }
    }

}
