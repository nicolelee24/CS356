import java.util.ArrayList;
import java.util.List;

/*
 * 	Group object class to group User objects together, has a groupID
 * 	Group implements IComponent, is a Composite in Composite pattern
 */
public class Group implements IComponent {

	private String groupID;
	private List<IComponent> components = new ArrayList<IComponent>();
	private long creationTime;
	
	public Group(String groupID) {
		this.groupID = groupID;
		this.creationTime = System.currentTimeMillis();
	}
	
	public Group(String groupID, Group group) {
		this.groupID = groupID;
		components.add(group);
		this.creationTime = System.currentTimeMillis();
	}
	
	public void addGroup(Group groupID) {
		components.add(groupID);
	}
	
	public void addUser(User userID) {
		components.add(userID);
	}
	
	public Group getGroup(String groupID) {
		IComponent group = null;
		for(IComponent g: components) {
			if(g.getID().equals(groupID)) {
				group = g;
				break;
			}
		}
		
		return (Group) group;
	}
	
	public User getUser(String userID) {
		IComponent user = null;
		for(IComponent u: components) {
			if(u.getID().equals(userID)) {
				user = u;
				break;
			}
		}
		
		return (User) user;
	}
	
	public List<IComponent> getComponents() {
		return components;
	}
	
	@Override
	public String getID() {
		return groupID;
	}

	@Override
	public long getCreationTime() {
		return creationTime;
	}

}
