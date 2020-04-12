package dk.cptlk.myfirends.Client.Internal.Model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import dk.cptlk.myfirends.Core.ClientAdapter.IModel;
import dk.cptlk.myfirends.Core.Utility.Comparator.DistanceFromChosenLocationComparator;
import dk.cptlk.myfirends.Core.Entities.Friend;
import dk.cptlk.myfirends.Core.Entities.Location;
import dk.cptlk.myfirends.Core.Services.IFriendService;


public class Model implements IModel {
    private IFriendService friendService;

    public Model(IFriendService friendService) {
        this.friendService = friendService;
    }

    @Override
    public Friend friendCreate(Friend e) {
        return friendService.create(e);
    }

    @Override
    public Friend friendRead(long id) {
        return friendService.read(id);
    }

    @Override
    public List<Friend> friendReadAll() {
        return friendService.readAll();
    }

    @Override
    public Friend friendUpdate(Friend e) {
        return friendService.update(e);
    }

    @Override
    public void friendDelete(long id) {
        friendService.delete(id);
    }

    @Override
    public String[] getFriendNames() {
        return friendService.getNames();
    }

    @Override
    public List<Friend> getFriendSortedName() {
        List<Friend> list = friendReadAll();
        Collections.sort(list, new Comparator<Friend>() {
            @Override
            public int compare(Friend f1, Friend f2) {
                return f1.getName().compareTo(f2.getName());
            }
        });

        return list;
    }

    @Override
    public List<Friend> getFriendSortedDistance(Location userLocation) {
        List<Friend> list = friendReadAll();
        Collections.sort(list, new DistanceFromChosenLocationComparator(userLocation));
        return list;
    }
}


