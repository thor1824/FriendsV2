package dk.easv.friendsv2;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import dk.easv.friendsv2.Helper.ExstraKeys;
import dk.easv.friendsv2.Model.Friend;
import dk.easv.friendsv2.Model.FriendRepo;

public class MainActivity extends ListActivity {

    public static String TAG = "Friend2";

    FriendRepo m_friends;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("FriendRepo v2");
        m_friends = new FriendRepo();

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
        Friend friend = m_friends.getAll().get(position);
        x.putExtra(ExstraKeys.FRIEND, friend);
        x.putExtra(ExstraKeys.FRIEND_POSITION, position);
        startActivityForResult(x, 10);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        Log.d(TAG, "onResult resultCode = " + resultCode);
        if (resultCode == RESULT_OK) {
            Friend upDatedFriend = (Friend)data.getExtras().getSerializable(ExstraKeys.FRIEND_UPDATED);
            int position = data.getExtras().getInt(ExstraKeys.FRIEND_POSITION);

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
