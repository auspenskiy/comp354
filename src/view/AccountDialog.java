package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import model.User;
import controller.DataManager;
import controller.UserDB;
import controller.ViewManager;

/**
 * This class is responsible for displaying a dialog when creating a new account
 * 
 * @author Andrey Uspenskiy
 * @modifiedBy Anne-Marie Dube, Francois Stelluti, Zachary Bergeron, Ningge Hu
 */

@SuppressWarnings("serial")
public class AccountDialog extends JDialog {
	private JTextField userName, email, firstName, lastName;
	private JPasswordField userPassword, repeatPassword;
	private JFileChooser userPicChooser;
	private JButton openFileButton;
	private JLabel fileChooserLabel, imageLabel;
	private JPanel imagePanel;
	private ImageIcon mImageIcon;
	private String sname;
	private User user;
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	public AccountDialog() {
		super(ApplicationWindow.instance(), "Create Account", true);
		this.setSize(500, 500);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.initCreateAccountDialog();
		this.setVisible(true);

	}
	
	public AccountDialog(User user) {
		super(ApplicationWindow.instance(), "Edit Account", true);
		this.user = user;
		this.setSize(500, 500);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.initCreateAccountDialog();
		this.setVisible(true);
	}
	
	/**
	 * Initializes the Create Account Dialog
	 */
	private void initCreateAccountDialog() {
		JPanel firstNameContainer = new JPanel();
		firstNameContainer.setBackground(Color.white);
		firstNameContainer.setPreferredSize(new Dimension(220, 60));
		firstName = new JTextField();

		firstName.setPreferredSize(new Dimension(200, 25));
		firstNameContainer.setBorder(BorderFactory
				.createTitledBorder("First Name"));
		firstNameContainer.add(firstName);

		JPanel lastNameContainer = new JPanel();
		lastNameContainer.setBackground(Color.white);
		lastNameContainer.setPreferredSize(new Dimension(220, 60));
		lastName = new JTextField();

		lastName.setPreferredSize(new Dimension(200, 25));
		lastNameContainer.setBorder(BorderFactory
				.createTitledBorder("Last Name"));
		lastNameContainer.add(lastName);

		openFileButton = new JButton("Open a File...",
				ViewManager.createImageIcon("images/Open16.gif"));

		JPanel fileChooserContainer = new JPanel();
		fileChooserContainer.setBackground(Color.white);
		fileChooserContainer.setPreferredSize(new Dimension(430, 60));
		userPicChooser = new JFileChooser();

		fileChooserLabel = new JLabel("Choose image file :");
		fileChooserContainer.add(fileChooserLabel);
		fileChooserContainer.add(openFileButton);
		imagePanel = new JPanel();
		openFileButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int choice = userPicChooser.showOpenDialog(null);
				if (choice == JFileChooser.APPROVE_OPTION) {
					File file = userPicChooser.getSelectedFile();
					sname = file.getAbsolutePath();
					mImageIcon = new ImageIcon(sname);
					imageLabel = new JLabel("", new ImageIcon(mImageIcon
							.getImage().getScaledInstance(130, 130, SOMEBITS)),
							JLabel.CENTER);
					imagePanel.removeAll();
					imagePanel.add(imageLabel, BorderLayout.CENTER);
					imagePanel.revalidate();
					imagePanel.repaint();
				}
			}
		});

		// User Name
		JPanel userNameContainer = new JPanel();
		userNameContainer.setBackground(Color.white);
		userNameContainer.setPreferredSize(new Dimension(220, 60));
		userName = new JTextField();
		userName.setPreferredSize(new Dimension(200, 25));
		userNameContainer.setBorder(BorderFactory
				.createTitledBorder("User Name"));
		userNameContainer.add(userName);

		// Email
		JPanel emailContainer = new JPanel();
		emailContainer.setBackground(Color.white);
		emailContainer.setPreferredSize(new Dimension(220, 60));
		email = new JTextField();
		email.setPreferredSize(new Dimension(200, 25));
		emailContainer.setBorder(BorderFactory.createTitledBorder("Email"));
		emailContainer.add(email);

		// Password
		JPanel userPasswordContainer = new JPanel();
		userPasswordContainer.setBackground(Color.white);
		userPasswordContainer.setPreferredSize(new Dimension(220, 60));
		userPassword = new JPasswordField();
		userPassword.setPreferredSize(new Dimension(200, 25));
		userPasswordContainer.setBorder(BorderFactory
				.createTitledBorder("Password"));
		userPasswordContainer.add(userPassword);

		// Repeat Password
		JPanel repeatPasswordContainer = new JPanel();
		repeatPasswordContainer.setBackground(Color.white);
		repeatPasswordContainer.setPreferredSize(new Dimension(220, 60));
		repeatPassword = new JPasswordField();
		repeatPassword.setPreferredSize(new Dimension(200, 25));
		repeatPasswordContainer.setBorder(BorderFactory
			.createTitledBorder("Repeat password"));
		repeatPasswordContainer.add(repeatPassword);

		// If editing a user, populate the text fields
		if (this.user != null) {
		    firstName.setText(user.getFirstName());
		    lastName.setText(user.getLastName());
		    userName.setText(user.getUserName());
		    email.setText(user.getEmail());
		}

		JPanel control = new JPanel();
		JButton okButton;
		if (this.user == null) {
		    okButton = new JButton("Create account");
		} else {
		    okButton = new JButton("Edit account");
		}
		okButton.addActionListener(new ActionListener() {

		    public void actionPerformed(ActionEvent arg0) {
			if (isInputValid()) {

			    if (user == null) {
				user = new User(userName.getText().trim(),
					getAccountPassword(userPassword.getPassword()),
					email.getText(), firstName.getText(), lastName
					.getText());
			    } else {
				user.setUserName(userName.getText().trim());
				user.setPassword(getAccountPassword(userPassword.getPassword()));
				user.setEmail(email.getText());
				user.setFirstName(firstName.getText());
				user.setLastName(lastName.getText());
			    }
			    if (sname != null) {
				user.setUserPicture(sname);
			    }

			    // This if-statement checks to see if the DB is
			    // empty
			    // If it is empty, create the first user as an Admin
			    // If not empty, create a regular user
			    if (DataManager.userTableIsEmpty()) {
				user.setAdmin(true);
			    }
			    user.persist();
			    ViewManager.logout(); // Switch to the
			    setVisible(false);
			}
		    }
		});
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});
		control.add(okButton);
		control.add(cancelButton);

		JPanel content = new JPanel();
		content.setBackground(Color.white);
		content.add(firstNameContainer);
		content.add(lastNameContainer);
		content.add(userNameContainer);
		content.add(emailContainer);
		content.add(userPasswordContainer);
		content.add(repeatPasswordContainer);
		content.add(fileChooserContainer);
		content.add(imagePanel);

		this.getContentPane().add(content, BorderLayout.CENTER);
		this.getContentPane().add(control, BorderLayout.SOUTH);
	}

	/**
	 * Method to get the password based on an array of characters
	 * 
	 * @param char[] pass
	 * @return String of the password itself
	 */
	private String getAccountPassword(char[] pass) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < pass.length; i++) {
			builder.append(pass[i]);
		}
		return builder.toString();
	}

	/**
	 * Method checks to see if all inputs are valid
	 * 
	 * @return boolean
	 */
	private boolean isInputValid() {
		List<User> users = UserDB.getAll();
		Pattern p = Pattern.compile("[\\w\\.]+@[\\w\\.]+\\.\\w{2,3}\\b");
		Matcher m = p.matcher(email.getText());
		boolean b = m.matches();

		if (b == false) {
			JOptionPane.showMessageDialog(null,
					"Invalid Email, Please Enter A Valid Email", "Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		// Checks that all fields have input
		if (email.getText().hashCode() == 0
				|| userName.getText().hashCode() == 0
				|| userPassword.getPassword().length == 0
				|| firstName.getText().hashCode() == 0
				|| lastName.getText().hashCode() == 0) {
			JOptionPane.showMessageDialog(null, "Please fill out all fields",
					"Cannot Create User", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		// Makes sure that passwords match
		if (!areCharsEqual(userPassword.getPassword(),
				repeatPassword.getPassword())) {
			JOptionPane.showMessageDialog(null,
					"Passwords do not match. Please try again!");
			userPassword.setText("");
			repeatPassword.setText("");
			return false;
		}

		for (User user : users) {
			// Checks to see if the supplied e-mail already exist
			if (user.getEmail().equals(email.getText().trim()) &&
				(this.user != null && this.user.getId() != user.getId())) {
				JOptionPane.showMessageDialog(null, "A user with e-mail: "
						+ email.getText().trim() + " already exists");
				email.setText("");
				return false;
			}

			// Checks to see if the supplied username already exist
			if (user.getUserName().equals(userName.getText().trim()) &&
				(this.user != null && this.user.getId() != user.getId())) {
				JOptionPane.showMessageDialog(null, "A user with username: "
						+ userName.getText().trim() + " already exists");
				userName.setText("");
				return false;
			}

		}

		return true;
	}

	/**
	 * Method checks to see if both supplied passwords are the same
	 * 
	 * @param Two
	 *            character arrays, fist and second
	 * @return boolean
	 */
	private boolean areCharsEqual(char[] first, char[] second) {
		if (first.length != second.length) {
			return false;
		}
		for (int i = 0; i < second.length; i++) {
			if (first[i] != second[i]) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Method resets the dialog form to blank fields
	 */
	private void resetForm() {
		firstName.setText("");
		lastName.setText("");
		email.setText("");
		userName.setText("");
		userPassword.setText("");
		repeatPassword.setText("");
	}

	public User getUser() {
	    return user;
	}

}