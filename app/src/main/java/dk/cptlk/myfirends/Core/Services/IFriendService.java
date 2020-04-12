package dk.cptlk.myfirends.Core.Services;

import java.util.List;

import dk.cptlk.myfirends.Core.Entities.Friend;

public interface IFriendService {
    Friend create(Friend e);

    Friend read(long id);

    void delete(long id);

    Friend update(Friend e);

    List<Friend> readAll();

    String[] getNames();
}
