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
	private String idVerified;
	private String lastUpdatedUserID;
	
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

	@Override
	public String visitIdVerification(Group g) {
		// unique User IDs and Group IDs are enforced on creation in admin control panel
		// this method will test if there is a 'space' in their IDs
		idVerified = "TRUE!";
		for (IComponent x: g.getComponents()) {
			if (x instanceof User) {
				User user = (User)x;
				if (user.getID().contains(" ")) {
					return idVerified = "FALSE!";
				}
			} else if (x instanceof Group) {
				Group group = (Group)x;
				if (group.getID().contains(" ")) {
					return idVerified = "FALSE!";
				}
			}
		}
		return idVerified;
	}

	@Override
	public String visitLastUpdatedUser(Group g) {
		lastUpdatedUserID = "";
		long largestTime = 0;
		for (IComponent u: g.getComponents()) {
			if (u instanceof User) {
				User user = (User)u;
				if (user.getLastUpdatedTime() > largestTime) {
					lastUpdatedUserID = user.getID();
					largestTime = user.getLastUpdatedTime();
				}

			}
		}
		return lastUpdatedUserID;
	}

}
