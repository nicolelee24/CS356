// Subject interface for Observer Pattern
public interface ISubject {

	public void follow(User user);
	public void notifyFollowers(String tweet);
	
}
