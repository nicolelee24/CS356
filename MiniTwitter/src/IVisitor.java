// Visitor interface for the Visitor Pattern
public interface IVisitor {

	public int visitUsers(Group g);
	public int visitGroups(Group g);
	public int visitTweets(Group g);
	public String visitPositivePercentage(Group g);
	
}
