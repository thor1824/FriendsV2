package dk.cptlk.myfirends.Core.ClientAdapter;

import java.util.List;

import dk.cptlk.myfirends.Core.Entities.Friend;
import dk.cptlk.myfirends.Core.Entities.Location;

public interface IModel {
    Friend friendCreate(Friend e);

    Friend friendRead(long id);

    List<Friend> friendReadAll();

    Friend friendUpdate(Friend e);

    void friendDelete(long id);

    String[] getFriendNames();

    List<Friend> getFriendSortedName();

    List<Friend> getFriendSortedDistance(Location userLocation);
}
