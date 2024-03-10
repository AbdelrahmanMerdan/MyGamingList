package src;

import javax.swing.JPanel;
import java.awt.FlowLayout;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.BoxLayout;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import java.awt.CardLayout;
import javax.swing.JTabbedPane;
import java.awt.Component;
import java.awt.Font;

public class GUILogin extends JFrame {
	private JPasswordField passwordField;
	private JTextField usernameField;
	private JTextField newUsernameField;
	private JTextField newPasswordField;
	private JTextField confirmPasswordField;
	
	private JPanel mainPane;

	public GUILogin() {
		//prompts
		String usernamePrompt = "Username";
		String passwordPrompt = "Password";
		String confirmPasswordPrompt = "Confirm Password";
		
		//setup main panel
		mainPane = new JPanel();
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
		loginButtonPane.setLayout(new GridLayout(0, 2, 100, 0));
		
		JButton loginButton = new JButton("  Login  ");
		loginButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
			loginButton.requestFocus();
		loginButtonPane.add(loginButton);
		
		//login					(needs database implementation)
		loginButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if (!usernameField.getText().equals("")) {
					login();
					System.out.println("sucessful login");
				} else {
					newUsernameField.setText("invalid username/password");
				}
			}
		});
		
		JButton signUpButton = new JButton("Sign Up");
		signUpButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		signUpButton.setFocusable(false);
		loginButtonPane.add(signUpButton);
		
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
		
		//create account		(needs database implementation)
		createAccountButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if (!newUsernameField.getText().equals("") && newPasswordField.getText().equals(confirmPasswordField.getText())) {
					login();
					System.out.println("new account, we are indeed prospering");
				} else {
					newUsernameField.setText("invalid username/password");
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
		((CardLayout) mainPane.getParent().getLayout()).show(mainPane.getParent(), "main");
	}

}
