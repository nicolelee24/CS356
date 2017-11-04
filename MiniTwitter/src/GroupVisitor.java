import java.text.DecimalFormat;

/*	GroupVisitor implements IVisitor, is a Visitor in Visitor Pattern
 * 	Will visit the specified group and count total users, groups, tweets, & positive percentage
 */
public class GroupVisitor implements IVisitor {
	
	private int totalUsers;
	private int totalGroups;
	private int	totalTweets;
	private String positivePercentage;
	private String[] positiveWords = {"good", "awesome", "great", "excellent"};
	
	@Override
	public int visitUsers(Group g) {
		totalUsers = 0;
		for (IComponent user: g.getComponents()) {
			if(user instanceof User) {
				totalUsers++;
			}
		}
		return totalUsers;
	}

	@Override
	public int visitGroups(Group g) {
		totalGroups = 1;
		for (IComponent group: g.getComponents()) {
			if(group instanceof Group) {
				totalGroups++;
			}
		}
		return totalGroups;
	}

	@Override
	public int visitTweets(Group g) {
		totalTweets = 0;
		for (IComponent u: g.getComponents()) {
			if (u instanceof User) {
				User user = (User)u;
				totalTweets += user.getNewsfeed().size();
			}
		}
		return totalTweets;
	}

	@Override
	public String visitPositivePercentage(Group g) {
		int positiveWordCount = 0;
		for (IComponent u: g.getComponents()) {
			if (u instanceof User) {
				User user = (User)u;
				for (String tweet: user.getNewsfeed()) {
					tweet.toLowerCase();
					for (String word: positiveWords) {
						if(tweet.contains(word)) {
							positiveWordCount++;
							break;
						}
					}
				}
			}
		}
		
		if (visitTweets(g) == 0) {
			return "0";
		}
		
		DecimalFormat df1 = new DecimalFormat("#.#");
		positivePercentage = df1.format((double)positiveWordCount/visitTweets(g) * 100);
		return positivePercentage;
	}

}
