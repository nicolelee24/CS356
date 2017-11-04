import java.util.ArrayList;
import java.util.List;

/*
 * 	User object class, has an ID, followers, followings, and newsfeed
 *	User implements IComponent, is a leaf in Composite Pattern
 * 	User implements IObserver, is an observer in Observer Pattern 
 */
public class User implements IComponent, IObserver {
	
	private String userID;
	private List<User> followers;
	private List<User> followings;
	private List<String> newsfeed;
	
	public User(String userID) {
		this.userID = userID;
		followers = new ArrayList<User>();
		followings = new ArrayList<User>();
		newsfeed = new ArrayList<String>();
	}

	@Override
	public void updateNewsfeed(String tweet) {
		newsfeed.add(tweet);
	}

	@Override
	public String getID() {
		return userID;
	}
	
	public void addFollower(User user) {
		followers.add(user);
	}
	
	public void addFollowing(User user) {
		followings.add(user);
	}

	public List<User> getFollowers() {
		return followers;
	}

	public List<User> getFollowings() {
		return followings;
	}

	public List<String> getNewsfeed() {
		return newsfeed;
	}
	
	public User getUser() {
		return User.this;
	}

}
