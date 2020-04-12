package dk.cptlk.myfirends.Client.Internal.Utility.Factory;


import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dk.cptlk.myfirends.Client.Internal.Utility.Adapter.MyArrayAdapter;
import dk.cptlk.myfirends.Client.Internal.Utility.Adapter.SpecialisedListViewAdapter;
import dk.cptlk.myfirends.Core.Entities.Friend;
import dk.cptlk.myfirends.R;

public class AdapterFactory {

    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_IMG = "img";

    private AdapterFactory() {
    }

    public static SimpleAdapter BuildSpecialisedListViewAdapter(Context ctx, List<Friend> friends) {

        List<HashMap<String, String>> arrayList = new ArrayList<>();

        for (int i = 0; i < friends.size(); i++) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put(KEY_NAME, friends.get(i).getName());
            hashMap.put(KEY_EMAIL, friends.get(i).getEmail());
            hashMap.put(KEY_PHONE, friends.get(i).getPhone());
            //hashMap.put(KEY_IMG, (i).getBpm() + "");

            arrayList.add(hashMap);
        }

        String[] from = {KEY_NAME, KEY_EMAIL, KEY_PHONE};
        int[] to = {R.id.tvFrName, R.id.tvFrEmail, R.id.tvFrPhone};

        return new SpecialisedListViewAdapter(ctx, arrayList, R.layout.listview_cell1, from, to);
    }

    public static ArrayAdapter<Friend> BuildMyArrayAdapter(Context ctx, List<Friend> friends) {
        return new MyArrayAdapter(ctx, R.layout.listview_cell1, friends);
    }
}
