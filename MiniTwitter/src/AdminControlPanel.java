import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

/*
 * 	Admin Control Panel to create users and groups,
 * 	spawn the UserView of selected user, and 
 * 	get statistics on total users, groups, tweets, and positive percentage.
 * 	Uses the Singleton Pattern, only one instance is allowed.
 */
@SuppressWarnings("serial")
public class AdminControlPanel extends JFrame {

	private static AdminControlPanel instance;
	private JPanel contentPane;
	private JTextField txtUserId;
	private JTextField txtGroupId;
	private JFrame jframe;
	private Group root = new Group("$ROOT");
	private GroupVisitor visitor = new GroupVisitor();
	private 	JTextArea displayTextArea = new JTextArea();
	private JScrollPane scrollpane = new JScrollPane(displayTextArea);

	public void run() {
		try {
			AdminControlPanel frame = new AdminControlPanel();
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private AdminControlPanel() {
		initialize();
	}
	
	// Singleton Pattern
	public static  AdminControlPanel getInstance() {
		if (instance == null) {
			instance = new AdminControlPanel();
		}
		return instance;
	}
	
	private void initialize() {
		setTitle("Admin Control Panel");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 550, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTree tree = new JTree();
		tree.setBounds(6, 9, 210, 320);
		tree.setBorder(UIManager.getBorder("Tree.editorBorder"));
		tree.setModel(new DefaultTreeModel(new DefaultMutableTreeNode("$ROOT")));
		contentPane.add(tree);
		
		txtUserId = new JTextField();
		txtUserId.setBounds(228, 6, 200, 26);
		txtUserId.setText("User ID");
		contentPane.add(txtUserId);
		txtUserId.setColumns(10);
		
		JButton btnAddUser = new JButton("Add User");
		btnAddUser.setBounds(427, 6, 117, 29);
		btnAddUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
				DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
				
				if(txtUserId.getText().trim().equals("")) {
					JOptionPane.showMessageDialog(jframe, "Invalid User ID, enter a User ID.");
				} else if (root.getUser(txtUserId.getText().trim()) != null) {
					JOptionPane.showMessageDialog(jframe, "Invalid User ID, the user already exists!");					
				} else if (selectedNode != null && selectedNode.getUserObject().toString().startsWith("$") ) {
					User user = new User(txtUserId.getText());
					if(selectedNode.getUserObject().toString().equals("$ROOT")) {
						root.addUser(user);
					} else {
						root.addUser(user);
						root.getGroup(selectedNode.getUserObject().toString()).addUser(user);
					}
					
					DefaultMutableTreeNode newUser = new DefaultMutableTreeNode(txtUserId.getText());
					model.insertNodeInto(newUser, selectedNode, selectedNode.getChildCount());
					model.reload();
					txtUserId.setText("");
				} else {
					JOptionPane.showMessageDialog(jframe, "Invalid group selection, select a group to add the user under.");
				}
			}
		});
		contentPane.add(btnAddUser);
		
		txtGroupId = new JTextField();
		txtGroupId.setBounds(228, 44, 200, 26);
		txtGroupId.setText("Group ID");
		contentPane.add(txtGroupId);
		txtGroupId.setColumns(10);
		
		JButton btnAddGroup = new JButton("Add Group");
		btnAddGroup.setBounds(427, 44, 117, 29);
		btnAddGroup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
				DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
				
				if(txtGroupId.getText().trim().equals("")) {
					JOptionPane.showMessageDialog(jframe, "Invalid Group ID, enter a Group ID.");
				} else if (root.getGroup("$" + txtGroupId.getText().trim().toUpperCase()) != null) {
					JOptionPane.showMessageDialog(jframe, "Invalid Group ID, the group already exists!");					
				} else if (selectedNode != null && selectedNode.getUserObject().toString().startsWith("$")) {
					String groupname = "$" + txtGroupId.getText().toUpperCase();
					Group group = new Group(groupname);
					if(selectedNode.getUserObject().toString().equals("$ROOT")) {
						root.addGroup(group);
					} else {
						root.addGroup(group);
						root.getGroup(selectedNode.getUserObject().toString()).addGroup(group);
					}
					
					DefaultMutableTreeNode newGroup = new DefaultMutableTreeNode(groupname);
					model.insertNodeInto(newGroup, selectedNode, selectedNode.getChildCount());
					model.reload();
					txtGroupId.setText("");
				} else {
					JOptionPane.showMessageDialog(jframe, "Select a Group to add the Group under.");
				}
			}
		});
		contentPane.add(btnAddGroup);
		
		scrollpane.setBounds(228, 127, 316, 164);
		contentPane.add(scrollpane);
		
		displayTextArea.setEditable(false);
		displayTextArea.setLineWrap(true);
		scrollpane.setViewportView(displayTextArea);
		
		JButton btnShowTotalTweets = new JButton("Show Total Tweets");
		btnShowTotalTweets.setBounds(228, 343, 153, 29);
		btnShowTotalTweets.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayTextArea.append("Total Tweets: " + visitor.visitTweets(root) + "\n");
			}
		});
		contentPane.add(btnShowTotalTweets);
		
		JButton btnShowPositive = new JButton("Show Positive %");
		btnShowPositive.setBounds(391, 343, 153, 29);
		btnShowPositive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayTextArea.append("Positive Percentage: " + visitor.visitPositivePercentage(root) + "% \n");
			}
		});
		contentPane.add(btnShowPositive);
		
		JButton btnShowTotalUsers = new JButton("Show Total Users");
		btnShowTotalUsers.setBounds(228, 302, 153, 29);
		btnShowTotalUsers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayTextArea.append("Total Users: " + visitor.visitUsers(root) + "\n");
			}
		});
		contentPane.add(btnShowTotalUsers);
		
		JButton btnShowTotalGroups = new JButton("Show Total Groups");
		btnShowTotalGroups.setBounds(391, 302, 153, 29);
		btnShowTotalGroups.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayTextArea.append("Total Groups: " + visitor.visitGroups(root) + "\n");
			}
		});
		contentPane.add(btnShowTotalGroups);
		
		JButton btnOpenUserView = new JButton("Open User View");
		btnOpenUserView.setBounds(6, 341, 210, 29);
		btnOpenUserView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
				if(selectedNode != null && !selectedNode.getUserObject().toString().startsWith("$")) {
					UserView userview = new UserView(root.getUser(selectedNode.getUserObject().toString()), root);
					userview.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(jframe, "Invalid selection, select a user to open User View for.");
				}
			}
		});
		contentPane.add(btnOpenUserView);
		
		JButton btnVerifyId = new JButton("Verify ID");
		btnVerifyId.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayTextArea.append("ID Verification: " + visitor.visitIdVerification(root) + "\n");
			}
		});
		btnVerifyId.setBounds(228, 82, 153, 29);
		contentPane.add(btnVerifyId);
		
		JButton btnLastUpdated = new JButton("Last Updated User");
		btnLastUpdated.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayTextArea.append("Last Updated User is: " + visitor.visitLastUpdatedUser(root) + "\n");
			}
		});
		btnLastUpdated.setBounds(391, 82, 153, 29);
		contentPane.add(btnLastUpdated);
		
	}
}
