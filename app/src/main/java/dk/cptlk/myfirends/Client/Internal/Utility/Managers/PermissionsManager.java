package dk.cptlk.myfirends.Client.Internal.Utility.Managers;

import android.app.Activity;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;

public class PermissionsManager {

    public static boolean isGrantedPermission(String permission, Activity act) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            int permissionResult = act.checkSelfPermission(permission);
            return permissionResult == PackageManager.PERMISSION_GRANTED;
        } else {
            return true;
        }
    }

    public static void askPermission(String Permission, int RequestCode, Activity act) {
        ActivityCompat.requestPermissions(act,
                new String[]{Permission},
                RequestCode);

    }

}
