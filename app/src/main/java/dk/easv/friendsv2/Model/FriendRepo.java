package dk.easv.friendsv2.Model;

import java.util.ArrayList;

public class FriendRepo {

	ArrayList<Friend> m_friends;
	
	public FriendRepo()
	{
		m_friends = new ArrayList<Friend>();
		m_friends.add(new Friend("Alex", "123456", true));
		m_friends.add(new Friend("Anders", "234567"));
		m_friends.add(new Friend("Andreas", "126256", true));
		m_friends.add(new Friend("Asvør", "234567"));
		m_friends.add(new Friend("Casper", "123456", true));
		m_friends.add(new Friend("Christian", "994567"));
		m_friends.add(new Friend("Daniel", "127426"));
		m_friends.add(new Friend("David", "204587", true));
		m_friends.add(new Friend("Grzegorz", "123456"));
		m_friends.add(new Friend("Henrik", "234567", true));
		m_friends.add(new Friend("Huseen", "123456"));
		m_friends.add(new Friend("Jakub", "234567"));
		m_friends.add(new Friend("Jan", "123456"));
		m_friends.add(new Friend("Jørgen", "234567", true));
		m_friends.add(new Friend("Kasper", "123456"));
		m_friends.add(new Friend("Kristian", "234567"));
		m_friends.add(new Friend("Mads", "123456", true));
		m_friends.add(new Friend("Mark", "234567", true));
		m_friends.add(new Friend("Marek", "123456"));
		m_friends.add(new Friend("Martin", "234567", true));
		m_friends.add(new Friend("Mate", "123456"));
		m_friends.add(new Friend("Mathias", "234567", true));
		m_friends.add(new Friend("Nedas", "234567"));
		m_friends.add(new Friend("Nijas", "123456"));
		m_friends.add(new Friend("Niklas", "234567", true));
		m_friends.add(new Friend("Philip", "123456"));
		m_friends.add(new Friend("Simon", "234567"));
		m_friends.add(new Friend("Szymon", "234567", true));
		m_friends.add(new Friend("Theis", "123456"));
		m_friends.add(new Friend("Thorbjørn", "234567"));
	}
	
	public ArrayList<Friend> getAll()
	{ return m_friends; }
	
	public String[] getNames()
	{
		String[] res = new String[m_friends.size()];
		for (int i = 0; i < res.length; i++) {
			res[i] = m_friends.get(i).getName();
		}
		return res;
	}

	public void update(Friend f, int position) {
		m_friends.set(position, f);
	}

}
