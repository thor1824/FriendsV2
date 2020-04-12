package dk.cptlk.myfirends.Data.Internal.DB.Contracts;

import android.provider.BaseColumns;

public final class FriendContract {

    private FriendContract() {
    }

    /* Inner class that defines the table contents */
    public static class FriendEntry implements BaseColumns {
        public static final String TABLE_NAME = "friend";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_ADDRESS = "address";
        public static final String COLUMN_LOCATION_LAT = "location_lat";
        public static final String COLUMN_LOCATION_LON = "location_lon";
        public static final String COLUMN_PHONE = "phone_no";
        public static final String COLUMN_WEB_URL = "web_url";
        public static final String COLUMN_IMG_URL = "img_blob";
        public static final String COLUMN_EMAIL_ADDRESS = "email";
        public static final String COLUMN_BIRTHDAY = "birthDay";
    }
}
