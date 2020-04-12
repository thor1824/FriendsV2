package dk.cptlk.myfirends.Data.Internal.Repositories;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dk.cptlk.myfirends.Core.DataAdapter.IRepository;
import dk.cptlk.myfirends.Core.Entities.Friend;
import dk.cptlk.myfirends.Core.Entities.Location;
import dk.cptlk.myfirends.Data.Internal.DB.Contracts.FriendContract;
import dk.cptlk.myfirends.Data.Internal.DB.Helper.FriendDbHelper;

public class FriendRepoPersistent implements IRepository<Friend> {


    private final SQLiteOpenHelper dbHelper;

    public FriendRepoPersistent(SQLiteOpenHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    @Override
    public Friend create(Friend e) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FriendContract.FriendEntry.COLUMN_NAME, e.getName());
        values.put(FriendContract.FriendEntry.COLUMN_ADDRESS, e.getAddress());
        values.put(FriendContract.FriendEntry.COLUMN_BIRTHDAY, e.getBirthDay().getTime());
        values.put(FriendContract.FriendEntry.COLUMN_EMAIL_ADDRESS, e.getEmail());
        values.put(FriendContract.FriendEntry.COLUMN_IMG_URL, e.getImage());
        values.put(FriendContract.FriendEntry.COLUMN_LOCATION_LAT, e.getLocation().getLatitude());
        values.put(FriendContract.FriendEntry.COLUMN_LOCATION_LON, e.getLocation().getLongitude());
        values.put(FriendContract.FriendEntry.COLUMN_PHONE, e.getPhone());
        values.put(FriendContract.FriendEntry.COLUMN_WEB_URL, e.getWebsite());


        long newRowId = db.insert(FriendContract.FriendEntry.TABLE_NAME, null, values);

        db.close();
        e.setId(newRowId);
        return e;
    }

    @Override
    public Friend read(long id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selection = FriendContract.FriendEntry._ID + " = ?";
        String[] selectionArgs = {"" + id};

        Cursor cursor = db.query(FriendContract.FriendEntry.TABLE_NAME, null, selection, selectionArgs, null, null, null);

        Friend friend = null;
        while (cursor.moveToNext()) {
            long Id = cursor.getLong(cursor.getColumnIndexOrThrow(FriendContract.FriendEntry._ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(FriendContract.FriendEntry.COLUMN_NAME));
            String address = cursor.getString(cursor.getColumnIndexOrThrow(FriendContract.FriendEntry.COLUMN_ADDRESS));
            Date birthday = new Date(cursor.getLong(cursor.getColumnIndexOrThrow(FriendContract.FriendEntry.COLUMN_BIRTHDAY)));
            String email = cursor.getString(cursor.getColumnIndexOrThrow(FriendContract.FriendEntry.COLUMN_EMAIL_ADDRESS));
            byte[] imgBlob = cursor.getBlob(cursor.getColumnIndexOrThrow(FriendContract.FriendEntry.COLUMN_IMG_URL));
            double longitude = cursor.getDouble(cursor.getColumnIndexOrThrow(FriendContract.FriendEntry.COLUMN_LOCATION_LON));
            double latitude = cursor.getDouble(cursor.getColumnIndexOrThrow(FriendContract.FriendEntry.COLUMN_LOCATION_LAT));
            String phone = cursor.getString(cursor.getColumnIndexOrThrow(FriendContract.FriendEntry.COLUMN_PHONE));
            String webUrl = cursor.getString(cursor.getColumnIndexOrThrow(FriendContract.FriendEntry.COLUMN_WEB_URL));
            friend = new Friend(Id, name, address, phone, email, webUrl, imgBlob, birthday, new Location(longitude, latitude));
        }
        db.close();
        return friend;
    }

    @Override
    public List<Friend> readAll() {

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(FriendContract.FriendEntry.TABLE_NAME, null, null, null, null, null, null);

        List<Friend> friends = new ArrayList<>();
        while (cursor.moveToNext()) {
            long Id = cursor.getLong(cursor.getColumnIndexOrThrow(FriendContract.FriendEntry._ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(FriendContract.FriendEntry.COLUMN_NAME));
            String address = cursor.getString(cursor.getColumnIndexOrThrow(FriendContract.FriendEntry.COLUMN_ADDRESS));
            Date birthday = new Date(cursor.getLong(cursor.getColumnIndexOrThrow(FriendContract.FriendEntry.COLUMN_BIRTHDAY)));
            String email = cursor.getString(cursor.getColumnIndexOrThrow(FriendContract.FriendEntry.COLUMN_EMAIL_ADDRESS));
            byte[] imgBlob = cursor.getBlob(cursor.getColumnIndexOrThrow(FriendContract.FriendEntry.COLUMN_IMG_URL));
            double longitude = cursor.getDouble(cursor.getColumnIndexOrThrow(FriendContract.FriendEntry.COLUMN_LOCATION_LON));
            double latitude = cursor.getDouble(cursor.getColumnIndexOrThrow(FriendContract.FriendEntry.COLUMN_LOCATION_LAT));
            String phone = cursor.getString(cursor.getColumnIndexOrThrow(FriendContract.FriendEntry.COLUMN_PHONE));
            String webUrl = cursor.getString(cursor.getColumnIndexOrThrow(FriendContract.FriendEntry.COLUMN_WEB_URL));
            Friend friend = new Friend(Id, name, address, phone, email, webUrl, imgBlob, birthday, new Location(longitude, latitude));
            friends.add(friend);
        }
        db.close();
        return friends;
    }

    @Override
    public Friend update(Friend e) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(FriendContract.FriendEntry.COLUMN_NAME, e.getName());
        values.put(FriendContract.FriendEntry.COLUMN_ADDRESS, e.getAddress());
        values.put(FriendContract.FriendEntry.COLUMN_BIRTHDAY, e.getBirthDay().getTime());
        values.put(FriendContract.FriendEntry.COLUMN_EMAIL_ADDRESS, e.getEmail());
        values.put(FriendContract.FriendEntry.COLUMN_IMG_URL, e.getImage());
        values.put(FriendContract.FriendEntry.COLUMN_LOCATION_LON, e.getLocation().getLongitude());
        values.put(FriendContract.FriendEntry.COLUMN_LOCATION_LAT, e.getLocation().getLatitude());
        values.put(FriendContract.FriendEntry.COLUMN_PHONE, e.getPhone());
        values.put(FriendContract.FriendEntry.COLUMN_WEB_URL, e.getWebsite());
        String selection = FriendContract.FriendEntry._ID + " like ?";
        String[] selectionArgs = {"" + e.getId()};

        int count = db.update(FriendContract.FriendEntry.TABLE_NAME, values, selection, selectionArgs);
        return count >= 1 ? e : null;
    }

    @Override
    public void delete(long id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = FriendContract.FriendEntry._ID + " LIKE ?";
        String[] selectionArgs = {"" + id};
        int deletedRows = db.delete(FriendContract.FriendEntry.TABLE_NAME, selection, selectionArgs);
        db.close();
    }

    private void Reset() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL(FriendDbHelper.SQL_DELETE_ENTRIES);
        db.execSQL(FriendDbHelper.SQL_CREATE_ENTRIES);
        db.close();
    }
}
