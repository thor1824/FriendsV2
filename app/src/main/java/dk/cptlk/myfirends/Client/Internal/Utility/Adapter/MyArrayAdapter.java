package dk.cptlk.myfirends.Client.Internal.Utility.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import dk.cptlk.myfirends.Client.Internal.Utility.Converter.BitmapConverter;
import dk.cptlk.myfirends.Core.Entities.Friend;
import dk.cptlk.myfirends.R;

public class MyArrayAdapter extends ArrayAdapter<Friend> {

    private static class ViewHolder {
        TextView tvName;
        TextView tvEmail;
        TextView tvPhone;
        ImageView ivProfile;
    }

    //Initialize adapter
    public MyArrayAdapter(Context context, int resource, List<Friend> items) {
        super(context, resource, items);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Friend f = getItem(position);


        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());

            convertView = inflater.inflate(R.layout.listview_cell1, parent, false);

            viewHolder.tvName = convertView.findViewById(R.id.tvFrName);
            viewHolder.tvEmail = convertView.findViewById(R.id.tvFrEmail);
            viewHolder.tvPhone = convertView.findViewById(R.id.tvFrPhone);
            viewHolder.ivProfile = convertView.findViewById(R.id.ivFrProfile);

            convertView.setTag(viewHolder);
        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvName.setText(f.getName() + " " + f.getId());
        viewHolder.tvEmail.setText(f.getEmail());
        viewHolder.tvPhone.setText(f.getPhone());
        if (f.getImage().length > 0) {
            viewHolder.ivProfile.setImageBitmap(BitmapConverter.getImage(f.getImage()));
        }
        return convertView;
    }
}
