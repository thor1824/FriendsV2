package dk.cptlk.myfirends.Data.Internal.DB.Helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import dk.cptlk.myfirends.Data.Internal.DB.Contracts.FriendContract;

public class FriendDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Friend.db";

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FriendContract.FriendEntry.TABLE_NAME + " (" +
                    FriendContract.FriendEntry._ID + " INTEGER PRIMARY KEY," +
                    FriendContract.FriendEntry.COLUMN_NAME + " TEXT," +
                    FriendContract.FriendEntry.COLUMN_ADDRESS + " TEXT," +
                    FriendContract.FriendEntry.COLUMN_LOCATION_LAT + " DECIMAL," +
                    FriendContract.FriendEntry.COLUMN_LOCATION_LON + " DECIMAL," +
                    FriendContract.FriendEntry.COLUMN_PHONE + " TEXT," +
                    FriendContract.FriendEntry.COLUMN_WEB_URL + " TEXT," +
                    FriendContract.FriendEntry.COLUMN_IMG_URL + " BLOB," +
                    FriendContract.FriendEntry.COLUMN_EMAIL_ADDRESS + " TEXT," +
                    FriendContract.FriendEntry.COLUMN_BIRTHDAY + " BIGINT" +
                    ")";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FriendContract.FriendEntry.TABLE_NAME;

    public FriendDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

}
