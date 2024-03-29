package src;

import database.UsersImpl;

import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPasswordField;
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

	public GUILogin() {
		UsersImpl users = new UsersImpl();
		
		//setup main panel
		mainPane = new JPanel();
		mainPane.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(65, 90, 108)));
		mainPane.setBackground(new Color(65, 90, 108));
		FlowLayout flowLayout = (FlowLayout) mainPane.getLayout();
		flowLayout.setHgap(0);
		flowLayout.setVgap(100);
		
		//setup border panel
		JPanel borderPane = new JPanel();
		borderPane.setLayout(new CardLayout(0, 0));
		mainPane.add(borderPane);
		
		borderPane.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				borderPane.requestFocus();
			}
		});
		
		//setup login panel
		JPanel loginPane = new JPanel();
		borderPane.add(loginPane, "login");
		loginPane.setLayout(new BorderLayout(0, 0));
		
		JPanel loginHeaderPane = new JPanel();
		loginPane.add(loginHeaderPane, BorderLayout.NORTH);
		FlowLayout fl_loginHeaderPanel = new FlowLayout(FlowLayout.CENTER, 5, 5);
		loginHeaderPane.setLayout(fl_loginHeaderPanel);
		
		JLabel loginPromptLabel = new JLabel("LOGIN");
		loginPromptLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
		loginPromptLabel.setHorizontalAlignment(SwingConstants.CENTER);
		loginHeaderPane.add(loginPromptLabel);
		
		JPanel loginButtonPane = new JPanel();
		loginPane.add(loginButtonPane, BorderLayout.SOUTH);
		
		JButton loginButton = new JButton("  Login  ");
		loginButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
			loginButton.requestFocus();
		loginButtonPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		loginButtonPane.add(loginButton);
		
		//login
		loginButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				String username = usernameField.getText();
				
				@SuppressWarnings("deprecation")
				String password = passwordField.getText();
				User user = new User(username, password);

				try {
					users.login(user);
					GUIMain.usernameLoggedIn = username;
					System.out.println("successful login: " + username);
					login();
				} catch (IllegalArgumentException exp) {
					System.out.println("login failed");
					usernameField.setText(exp.getMessage());
				}
			}
		});
		
		JButton signUpButton = new JButton("Sign Up");
		signUpButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		signUpButton.setFocusable(false);
		loginButtonPane.add(signUpButton);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reset();
				((CardLayout) mainPane.getParent().getLayout()).show(mainPane.getParent(), "main");
			}
		});
		cancelButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		loginButtonPane.add(cancelButton);
		
		//switch to signup page
		signUpButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				((CardLayout) borderPane.getLayout()).show(borderPane, "signup");
			}
		});
		
		JPanel loginMainPane = new JPanel();
		loginMainPane.setBorder(new EmptyBorder(30, 50, 40, 50));
		loginPane.add(loginMainPane, BorderLayout.CENTER);
		loginMainPane.setLayout(new BorderLayout(50, 0));
		
		usernameField = new JTextField();
		usernameField.setFont(new Font("Tahoma", Font.PLAIN, 16));
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
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 16));
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
		borderPane.add(newUserPane, "signup");
		newUserPane.setLayout(new BorderLayout(0, 0));
		
		JPanel newUserHeaderPane = new JPanel();
		newUserPane.add(newUserHeaderPane, BorderLayout.NORTH);
		
		JLabel signUpPromptLabel = new JLabel("SIGN UP");
		signUpPromptLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
		newUserHeaderPane.add(signUpPromptLabel);
		
		JPanel newUserButtonPane = new JPanel();
		newUserPane.add(newUserButtonPane, BorderLayout.SOUTH);
		newUserButtonPane.setLayout(new GridLayout(0, 2, 100, 0));
		
		JButton createAccountButton = new JButton("Create Account");
		createAccountButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		createAccountButton.setFocusable(false);
		newUserButtonPane.add(createAccountButton);
		
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
					User newUser = new User(newUsername, newPassword);

					try {
						users.createAccount(newUser);
						System.out.println("Created new account for " + newUsername);
						GUIMain.usernameLoggedIn = newUsername;
						login();
					} catch (IllegalArgumentException exp) {
						newUsernameField.setText(exp.getMessage().split(",")[0]);
					}
				}
			}
		});
		
		JButton signUpBackButton = new JButton("Back");
		signUpBackButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		signUpBackButton.setFocusable(false);
		newUserButtonPane.add(signUpBackButton);
		
		//switch to login page
		signUpBackButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				((CardLayout) borderPane.getLayout()).show(borderPane, "login");
			}
		});
		
		JPanel newUserMainPane = new JPanel();
		newUserMainPane.setBorder(new EmptyBorder(30, 50, 40, 50));
		newUserPane.add(newUserMainPane, BorderLayout.CENTER);
		newUserMainPane.setLayout(new BorderLayout(50, 25));
		
		newUsernameField = new JTextField();
		newUsernameField.setFont(new Font("Tahoma", Font.PLAIN, 16));
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
		newPasswordField.setFont(new Font("Tahoma", Font.PLAIN, 16));
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
		confirmPasswordField.setFont(new Font("Tahoma", Font.PLAIN, 16));
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
	
	private void login() {
		GUIMain.loginButton.setText("Log out");
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
