package dk.cptlk.myfirends.Core.Utility.Comparator;

import java.util.Comparator;

import dk.cptlk.myfirends.Core.Entities.Friend;
import dk.cptlk.myfirends.Core.Entities.Location;

public class DistanceFromChosenLocationComparator implements Comparator<Friend> {

    public final Location userLocation;

    public DistanceFromChosenLocationComparator(Location userLocation){
        this.userLocation = userLocation;
    }

    @Override
    public int compare(Friend f1, Friend f2) {
        double distanceF1 = Math.sqrt(
                Math.pow(f1.getLocation().getLongitude()  - userLocation.getLatitude(), 2)+
                        Math.pow(f1.getLocation().getLatitude() - userLocation.getLatitude(), 2));
        double distanceF2 = Math.sqrt(
                Math.pow(f2.getLocation().getLongitude()  - userLocation.getLatitude(), 2)+
                        Math.pow(f2.getLocation().getLatitude() - userLocation.getLatitude(), 2));
        return distanceF1 > distanceF2 ? 1 : (distanceF1 == distanceF2 ? 0 : -1);

    }

    @Override
    public boolean equals(Object o) {
        return false;
    }
}