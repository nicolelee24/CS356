import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

/*
 * UserView GUI implements ISubject,
 * user of the view is the subject (observable) to its followers.
 * The user can follow other users, post tweets, and view their newsfeed.
 */
@SuppressWarnings("serial")
public class UserView extends JFrame implements ISubject{

	private JPanel contentPane;
	private JTextField txtUserId;
	private User user;
	private Group root;
	private JFrame jframe = new JFrame();
	private	JTextArea txtrCurrentFollowing = new JTextArea();
	private 	JTextArea txtrNewsfeed = new JTextArea();
	private	JScrollPane cfscrollPane = new JScrollPane(txtrCurrentFollowing);
	private JScrollPane nfscrollPane = new JScrollPane(txtrNewsfeed);
	private JTextArea txtrTweetMessage = new JTextArea();
	private final JScrollPane tmscrollpane = new JScrollPane(txtrTweetMessage);
	private JTextField txtCreationTime;
	private final JTextField txtNewsfeedLastUpdated = new JTextField();
	
	@SuppressWarnings("deprecation")
	public UserView(User userr, Group group) {
		
		root = group;
		user = root.getUser(userr.getID());
		
		setTitle("User View for " + user.getID());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 500, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		cfscrollPane.setBounds(218, 40, 276, 85);
		contentPane.add(cfscrollPane);
		
		nfscrollPane.setBounds(6, 213, 488, 159);
		contentPane.add(nfscrollPane);
		
		txtUserId = new JTextField();
		txtUserId.setText("User ID");
		txtUserId.setBounds(6, 37, 200, 26);
		contentPane.add(txtUserId);
		txtUserId.setColumns(10);
		
		txtrCurrentFollowing.setEditable(false);
		txtrCurrentFollowing.setText("Current Following:\n");		
		for (User u: root.getUser(user.getID()).getFollowings()) {
			txtrCurrentFollowing.append("\n" + u.getID());
		}
		cfscrollPane.setViewportView(txtrCurrentFollowing);
		
		JButton btnFollowUser = new JButton("Follow User");
		btnFollowUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtUserId.getText().trim().equals("")) {
					JOptionPane.showMessageDialog(jframe, "Invalid User ID, enter a User ID.");
				} else if (txtUserId.getText().trim().equals(user.getID())) {
					JOptionPane.showMessageDialog(jframe, "Invalid User ID, cannot follow yourself.");
				} else if (root.getUser(user.getID()).getFollowings().contains(root.getUser(txtUserId.getText().trim()))) {
					JOptionPane.showMessageDialog(jframe, "Invalid User ID, you already follow that user.");
				} else if (root.getUser(txtUserId.getText().trim()) == null) {
					JOptionPane.showMessageDialog(jframe, "Invalid User ID, user doesn't exist!");					
				} else {
					follow(root.getUser(txtUserId.getText().trim()));
					txtUserId.setText("");
				}
			}
		});
		btnFollowUser.setBounds(6, 75, 200, 29);
		contentPane.add(btnFollowUser);
		
		txtrNewsfeed.setEditable(false);
		txtrNewsfeed.setLineWrap(true);
		
		ActionListener updateNF = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtrNewsfeed.setText("Newsfeed:\n");
				for (String tweet: root.getUser(user.getID()).getNewsfeed()) {
					txtrNewsfeed.append("\n" + tweet);
				}
				nfscrollPane.setViewportView(txtrNewsfeed);
			}
		};
		Timer timer = new Timer(500, updateNF);
		timer.setRepeats(true);
		timer.start();
		
		tmscrollpane.setBounds(6, 137, 359, 29);		
		contentPane.add(tmscrollpane);
		
		txtrTweetMessage.setLineWrap(true);
		txtrTweetMessage.setText("Tweet Message");
		tmscrollpane.setViewportView(txtrTweetMessage);

		JButton btnTweet = new JButton("Post Tweet");
		btnTweet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtrTweetMessage.getText().trim().equals("")) {
					JOptionPane.showMessageDialog(jframe, "Invalid message, enter text.");
				} else {
					String tweet = user.getID() + ": " + txtrTweetMessage.getText().toString();
					root.getUser(user.getID()).updateNewsfeed(tweet);
					notifyFollowers(tweet);
					txtrNewsfeed.append("\n" + tweet);
					txtrTweetMessage.setText("");
				}
			}
		});
		btnTweet.setBounds(377, 137, 117, 29);
		contentPane.add(btnTweet);
		
		Date date = new Date(user.getCreationTime());
		txtCreationTime = new JTextField();
		txtCreationTime.setEditable(false);
		txtCreationTime.setText("User Creation Time: " + date.toLocaleString());
		txtCreationTime.setBounds(6, 6, 488, 26);
		contentPane.add(txtCreationTime);
		txtCreationTime.setColumns(10);
		
		txtNewsfeedLastUpdated.setEditable(false);
		txtNewsfeedLastUpdated.setBounds(6, 175, 488, 26);
		txtNewsfeedLastUpdated.setColumns(10);
		
		ActionListener updateNFLU = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Date date1 = new Date(user.getLastUpdatedTime());
				txtNewsfeedLastUpdated.setText("Newsfeed Last Updated On: " + date1.toLocaleString());
			}
		};
		Timer timer1 = new Timer(500, updateNFLU);
		timer1.setRepeats(true);
		timer1.start();
		
		
		contentPane.add(txtNewsfeedLastUpdated);
		
	}

	@Override
	public void follow(User u) {
		root.getUser(user.getID()).addFollowing(root.getUser(u.getID()));
		txtrCurrentFollowing.append("\n" + u.getID());
		root.getUser(u.getID()).addFollower(root.getUser(user.getID()));
	}

	@Override
	public void notifyFollowers(String tweet) {
		for(User u: root.getUser(user.getID()).getFollowers()) {
			root.getUser(u.getID()).updateNewsfeed(tweet);
		}
	}
}
