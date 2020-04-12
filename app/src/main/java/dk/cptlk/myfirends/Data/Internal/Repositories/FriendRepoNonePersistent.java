package dk.cptlk.myfirends.Data.Internal.Repositories;

import java.util.ArrayList;
import java.util.List;

import dk.cptlk.myfirends.Core.DataAdapter.IRepository;
import dk.cptlk.myfirends.Core.Entities.Friend;

public class FriendRepoNonePersistent implements IRepository<Friend> {

    private static ArrayList<Friend> friends;

    /*public FriendRepoNonePersistent() {
        friends = new ArrayList<Friend>();
        create(new Friend("Alex", "123456", "", "", website, image));
        create(new Friend("Anders", "234567", address, false, email, website, image));
        create(new Friend("Andreas", "126256", address, true, email, website, image));
        create(new Friend("Asvør", "234567", address, false, email, website, image));
        create(new Friend("Casper", "123456", address, true, email, website, image));
        create(new Friend("Christian", "994567", address, false, email, website, image));
        create(new Friend("Daniel", "127426", address, false, email, website, image));
        create(new Friend("David", "204587", address, true, email, website, image));
        create(new Friend("Grzegorz", "123456", address, false, email, website, image));
        create(new Friend("Henrik", "234567", address, true, email, website, image));
        create(new Friend("Huseen", "123456", address, false, email, website, image));
        create(new Friend("Jakub", "234567", address, false, email, website, image));
        create(new Friend("Jan", "123456", address, false, email, website, image));
        create(new Friend("Jørgen", "234567", address, true, email, website, image));
        create(new Friend("Kasper", "123456", address, false, email, website, image));
        create(new Friend("Kristian", "234567", address, false, email, website, image));
        create(new Friend("Mads", "123456", address, true, email, website, image));
        create(new Friend("Mark", "234567", address, true, email, website, image));
        create(new Friend("Marek", "123456", address, false, email, website, image));
        create(new Friend("Martin", "234567", address, true, email, website, image));
        create(new Friend("Mate", "123456", address, false, email, website, image));
        create(new Friend("Mathias", "234567", address, true, email, website, image));
        create(new Friend("Nedas", "234567", address, false, email, website, image));
        create(new Friend("Nijas", "123456", address, false, email, website, image));
        create(new Friend("Niklas", "234567", address, true, email, website, image));
        create(new Friend("Philip", "123456", address, false, email, website, image));
        create(new Friend("Simon", "234567", address, false, email, website, image));
        create(new Friend("Szymon", "234567", address, true, email, website, image));
        create(new Friend("Theis", "123456", address, false, email, website, image));
        create(new Friend("Thorbjørn", "234567", address, true, email, website, image));
    }
*/
    @Override
    public Friend create(Friend e) {
        //e = new Friend(getNextAvailableID(), e.getName(), e.getPhone(), e.isFavorite(), email, website, image);
        System.out.println(e.getId());
        friends.add(e);
        return e;
    }

    @Override
    public Friend read(long id) {
        for (int i = 0; i < friends.size(); i++) {
            if (friends.get(i).getId() == id) {
                return friends.get(i);
            }
        }
        return null;
    }

    @Override
    public List<Friend> readAll() {
        return friends;
    }

    @Override
    public Friend update(Friend e) {
        Integer indexToBeUpdated = getIndex(e.getId());

        if (indexToBeUpdated != -1) {
            friends.set(indexToBeUpdated, e);
        }
        return null;
    }

    @Override
    public void delete(long id) {
        Integer indexToBeRemoved = getIndex(id);

        if (indexToBeRemoved != -1) {
            friends.remove(indexToBeRemoved);
        }
    }

    private long getNextAvailableID() {
        try {
            return friends.get(friends.size() - 1).getId() + 1;
        } catch (IndexOutOfBoundsException e) {
            return 1;
        }
    }

    private Integer getIndex(long id) {
        Integer index = new Integer(-1);

        for (int i = 0; i < friends.size(); i++) {
            if (friends.get(i).getId() == id) {
                index = new Integer(i);
            }
        }
        return index;
    }


}
