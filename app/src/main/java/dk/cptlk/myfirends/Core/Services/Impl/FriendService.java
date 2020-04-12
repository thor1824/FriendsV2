package dk.cptlk.myfirends.Core.Services.Impl;

import java.util.List;

import dk.cptlk.myfirends.Core.DataAdapter.IRepository;
import dk.cptlk.myfirends.Core.Entities.Friend;
import dk.cptlk.myfirends.Core.Services.IFriendService;

public class FriendService implements IFriendService {
    private IRepository<Friend> repo;

    public FriendService(IRepository<Friend> repo) {
        this.repo = repo;
    }

    @Override
    public Friend create(Friend e) {
        return repo.create(e);
    }

    @Override
    public Friend read(long id) {
        return repo.read(id);
    }

    @Override
    public void delete(long id) {
        repo.delete(id);
    }

    @Override
    public Friend update(Friend e) {
        return repo.update(e);
    }

    @Override
    public List<Friend> readAll() {
        return repo.readAll();
    }

    @Override
    public String[] getNames() {
        List<Friend> temp = repo.readAll();
        String[] res = new String[temp.size()];
        for (int i = 0; i < res.length; i++) {
            res[i] = temp.get(i).getName();
        }
        return res;
    }
}
