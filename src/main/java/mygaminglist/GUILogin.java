package mygaminglist;

import database.*;

import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

import javax.swing.border.EmptyBorder;
import java.awt.CardLayout;
import java.awt.Font;
import javax.swing.border.MatteBorder;

public class GUILogin extends JFrame {
	private JPasswordField passwordField;
	private JTextField usernameField;
	private JTextField newUsernameField;
	private JTextField newPasswordField;
	private JTextField confirmPasswordField;
	
	private JPanel mainPane;
	
	//prompts
	private String usernamePrompt = "Username";
	private String passwordPrompt = "Password";
	private String confirmPasswordPrompt = "Confirm Password";
	private boolean isPrivate = true;

	public GUILogin() {
		UsersImpl users = new UsersImpl();
		
		//setup main panel
		mainPane = new JPanel();
		mainPane.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(65, 90, 108)));
		mainPane.setBackground(new Color(27, 40, 56));
		FlowLayout flowLayout = (FlowLayout) mainPane.getLayout();
		flowLayout.setHgap(0);
		flowLayout.setVgap(100);
		
		//setup border panel
		JPanel borderPane = new JPanel();
		borderPane.setBackground(new Color(27, 40, 56));
		borderPane.setLayout(new CardLayout(0, 0));
		mainPane.add(borderPane);
		
		borderPane.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				borderPane.requestFocus();
			}
		});
		
		//setup login panel
		JPanel loginPane = new JPanel();
		loginPane.setBackground(new Color(27, 40, 56));
		borderPane.add(loginPane, "login");
		loginPane.setLayout(new BorderLayout(0, 0));
		
		JPanel loginHeaderPane = new JPanel();
		loginHeaderPane.setBackground(new Color(23, 26, 33));
		loginPane.add(loginHeaderPane, BorderLayout.NORTH);
		FlowLayout fl_loginHeaderPanel = new FlowLayout(FlowLayout.CENTER, 5, 5);
		loginHeaderPane.setLayout(fl_loginHeaderPanel);
		
		JLabel loginPromptLabel = new JLabel("LOGIN");
		loginPromptLabel.setFont(new Font("Verdana", Font.PLAIN, 50));
		loginPromptLabel.setForeground(Color.WHITE);
		loginPromptLabel.setHorizontalAlignment(SwingConstants.CENTER);
		loginHeaderPane.add(loginPromptLabel);
		
		JPanel loginButtonPane = new JPanel();
		loginButtonPane.setBackground(new Color(23, 26, 33));
		loginPane.add(loginButtonPane, BorderLayout.SOUTH);
		
		JButton loginButton = new JButton("  Login  ");
		loginButton.setBackground(new Color(23, 26, 33));
		loginButton.setOpaque(true);
		loginButton.setForeground(Color.WHITE);
		loginButton.setFont(new Font("Verdana", Font.PLAIN, 32));
			loginButton.requestFocus();
		loginButtonPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		loginButtonPane.add(loginButton);
		
		//login
		loginButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				String username = usernameField.getText();
				
				@SuppressWarnings("deprecation")
				String password = passwordField.getText();

				User user = new User(username, password,isPrivate);

				try {
					loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
					users.login(user);
					GUIMain.usernameLoggedIn = username;
					System.out.println("successful login: " + username);
					login(borderPane);
					loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				} catch (IllegalArgumentException exp) {
					System.out.println("login failed");
					usernameField.setText(exp.getMessage());
				}
			}
		});
		
		JButton signUpButton = new JButton("Sign Up");
		signUpButton.setBackground(new Color(23, 26, 33));
		signUpButton.setOpaque(true);
		signUpButton.setForeground(Color.WHITE);
		signUpButton.setFont(new Font("Verdana", Font.PLAIN, 32));
		signUpButton.setFocusable(false);
		loginButtonPane.add(signUpButton);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setBackground(new Color(23, 26, 33));
		cancelButton.setOpaque(true);
		cancelButton.setForeground(Color.WHITE);
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reset();
				((CardLayout) mainPane.getParent().getLayout()).show(mainPane.getParent(), "main");
			}
		});
		cancelButton.setFont(new Font("Verdana", Font.PLAIN, 32));
		loginButtonPane.add(cancelButton);
		
		//switch to signup page
		signUpButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				((CardLayout) borderPane.getLayout()).show(borderPane, "signup");
			}
		});
		
		JPanel loginMainPane = new JPanel();
		loginMainPane.setBackground(new Color(23, 26, 33));
		loginMainPane.setBorder(new EmptyBorder(30, 50, 40, 50));
		loginPane.add(loginMainPane, BorderLayout.CENTER);
		loginMainPane.setLayout(new BorderLayout(50, 0));
		
		usernameField = new JTextField();
		usernameField.setBackground(new Color(27, 40, 56));
		usernameField.setForeground(Color.WHITE);
		usernameField.setFont(new Font("MS Song", Font.PLAIN, 32));
		loginMainPane.add(usernameField, BorderLayout.NORTH);
		usernameField.setColumns(10);
		usernameField.setText(usernamePrompt);
		
		//usernameField prompt
		usernameField.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				if (usernameField.getText().equals(usernamePrompt)) {
					usernameField.setText("");
				}
			}
			public void focusLost(FocusEvent e) {
				if (usernameField.getText().equals("")) {
					usernameField.setText(usernamePrompt);
				}
			}
		});
		
		passwordField = new JPasswordField();
		passwordField.setBackground(new Color(27, 40, 56));
		passwordField.setForeground(Color.WHITE);
		passwordField.setFont(new Font("MS Song", Font.PLAIN, 32));
		loginMainPane.add(passwordField, BorderLayout.SOUTH);
		passwordField.setText(passwordPrompt);
		passwordField.setEchoChar((char)0);
		
		//passwordField prompt
		passwordField.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				if (Arrays.equals(passwordField.getPassword(), passwordPrompt.toCharArray())){
					passwordField.setText("");
					passwordField.setEchoChar('*');
				}
			}
			public void focusLost(FocusEvent e) {
				if (passwordField.getPassword().length == 0){							//yuck
					passwordField.setText(passwordPrompt);
					passwordField.setEchoChar((char)0);
				}
			}
		});
		
		//setup new user panel
		JPanel newUserPane = new JPanel();
		newUserPane.setBackground(new Color(23, 26, 33));
		borderPane.add(newUserPane, "signup");
		newUserPane.setLayout(new BorderLayout(0, 0));

		JPanel newUserHeaderPane = new JPanel(new GridLayout(2, 1));
		newUserHeaderPane.setBackground(new Color(23, 26, 33));
		newUserPane.add(newUserHeaderPane, BorderLayout.NORTH);

		JPanel signUpLabelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		signUpLabelPanel.setBackground(new Color(23, 26, 33));
		newUserHeaderPane.add(signUpLabelPanel, BorderLayout.NORTH);

		JLabel signUpPromptLabel = new JLabel("SIGN UP");
		signUpPromptLabel.setForeground(Color.WHITE);
		signUpPromptLabel.setFont(new Font("Verdana", Font.PLAIN, 50));
		signUpLabelPanel.add(signUpPromptLabel);

		JPanel checkBoxPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		checkBoxPanel.setBackground(new Color(23, 26, 33));
		newUserHeaderPane.add(checkBoxPanel, BorderLayout.CENTER);
		JCheckBox isPrivateCheckBox = new JCheckBox("Would You like your account to be private? ");

		isPrivateCheckBox.setBackground(new Color(23, 26, 33));
		isPrivateCheckBox.setForeground(Color.WHITE);
		isPrivateCheckBox.setFont(new Font("MS Song", Font.PLAIN, 20));
		checkBoxPanel.add(isPrivateCheckBox,BorderLayout.CENTER);


		JPanel newUserButtonPane = new JPanel();
		newUserButtonPane.setBackground(new Color(23, 26, 33));
		newUserPane.add(newUserButtonPane, BorderLayout.SOUTH);
		newUserButtonPane.setLayout(new GridLayout(0, 2, 100, 0));



		JButton createAccountButton = new JButton("Create Account");
		createAccountButton.setBackground(new Color(23, 26, 33));
		createAccountButton.setOpaque(true);
		createAccountButton.setForeground(Color.WHITE);
		createAccountButton.setFont(new Font("Verdana", Font.PLAIN, 32));
		createAccountButton.setFocusable(false);
		newUserButtonPane.add(createAccountButton, BorderLayout.CENTER);
		
		//create account
		createAccountButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				String newUsername = newUsernameField.getText();
				String newPassword = newPasswordField.getText();
				if (newUsername.equals("")) {
					newUsernameField.setText("Username cannot be empty");
				} else if (!newPassword.equals(confirmPasswordField.getText())) {
					newUsernameField.setText("Password doesn't match");
				} else if (newPassword.length() < 4 || newPassword.length() > 16) {
					newUsernameField.setText("Password length must be between 4-16");
				} else {
					if (!isPrivateCheckBox.isSelected()){
						isPrivate = false;
					}
					User newUser = new User(newUsername, newPassword, isPrivate);

					try {
						createAccountButton.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
						users.createAccount(newUser);
						System.out.println("Created new account for " + newUsername);
						GUIMain.usernameLoggedIn = newUsername;
						login(borderPane);
						createAccountButton.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					} catch (IllegalArgumentException exp) {
						newUsernameField.setText(exp.getMessage().split(",")[0]);
					}
				}
			}
		});
		
		JButton signUpBackButton = new JButton("Back");
		signUpBackButton.setBackground(new Color(23, 26, 33));
		signUpBackButton.setOpaque(true);
		signUpBackButton.setForeground(Color.WHITE);
		signUpBackButton.setFont(new Font("Verdana", Font.PLAIN, 32));
		signUpBackButton.setFocusable(false);
		newUserButtonPane.add(signUpBackButton);
		
		//switch to login page
		signUpBackButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				((CardLayout) borderPane.getLayout()).show(borderPane, "login");
			}
		});
		
		JPanel newUserMainPane = new JPanel();
		newUserMainPane.setBackground(new Color(23, 26, 33));
		newUserMainPane.setBorder(new EmptyBorder(30, 50, 40, 50));
		newUserPane.add(newUserMainPane, BorderLayout.CENTER);
		newUserMainPane.setLayout(new BorderLayout(50, 25));



		newUsernameField = new JTextField();
		newUsernameField.setBackground(new Color(27, 40, 56));
		newUsernameField.setForeground(Color.WHITE);
		newUsernameField.setFont(new Font("MS Song", Font.PLAIN, 32));
		newUserMainPane.add(newUsernameField, BorderLayout.NORTH);
		newUsernameField.setColumns(10);
		newUsernameField.setText(usernamePrompt);
		
		//newUsernameField prompt
		newUsernameField.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				if (newUsernameField.getText().equals(usernamePrompt)) {
					newUsernameField.setText("");
				}
			}
			public void focusLost(FocusEvent e) {
				if (newUsernameField.getText().equals("")) {
					newUsernameField.setText(usernamePrompt);
				}
			}
		});
		
		newPasswordField = new JTextField();
		newPasswordField.setBackground(new Color(27, 40, 56));
		newPasswordField.setForeground(Color.WHITE);
		newPasswordField.setFont(new Font("MS Song", Font.PLAIN, 32));
		newPasswordField.setColumns(10);
		newUserMainPane.add(newPasswordField, BorderLayout.CENTER);
		newPasswordField.setText(passwordPrompt);
		
		//newPasswordField prompt
		newPasswordField.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				if (newPasswordField.getText().equals(passwordPrompt)) {
					newPasswordField.setText("");
				}
			}
			public void focusLost(FocusEvent e) {
				if (newPasswordField.getText().equals("")) {
					newPasswordField.setText(passwordPrompt);
				}
			}
		});
		
		confirmPasswordField = new JTextField();
		confirmPasswordField.setBackground(new Color(27, 40, 56));
		confirmPasswordField.setForeground(Color.WHITE);
		confirmPasswordField.setFont(new Font("MS Song", Font.PLAIN, 32));
		confirmPasswordField.setColumns(10);
		newUserMainPane.add(confirmPasswordField, BorderLayout.SOUTH);
		confirmPasswordField.setText(confirmPasswordPrompt);
		
		//checkNewPasswordField prompt
		confirmPasswordField.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				if (confirmPasswordField.getText().equals(confirmPasswordPrompt)) {
					confirmPasswordField.setText("");
				}
			}
			public void focusLost(FocusEvent e) {
				if (confirmPasswordField.getText().equals("")) {
					confirmPasswordField.setText(confirmPasswordPrompt);
				}
			}
		});
	}
	
	public JPanel getMainPane() {
		return mainPane;
	}
	
	private void login(JPanel borderPane) {
		GUIMain.loginButton.setText("Log out");
		((CardLayout) borderPane.getLayout()).show(borderPane, "login");
		((CardLayout) mainPane.getParent().getLayout()).show(mainPane.getParent(), "main");
		reset();
	}
	
	private void reset() {
		usernameField.setText(usernamePrompt);
		passwordField.setText(passwordPrompt);
		passwordField.setEchoChar('\0');
		newUsernameField.setText(usernamePrompt);
		newPasswordField.setText(passwordPrompt);
		confirmPasswordField.setText(confirmPasswordPrompt);
		
	}

}
