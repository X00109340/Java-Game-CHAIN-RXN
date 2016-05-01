package jpanels;
import sound.Sound;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;


import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import game.Main;


public class LoginPanel extends JPanel {
	

	

    private final JLabel LOGO = new JLabel(new ImageIcon(getClass().getResource("/images/clickir.png")));


	private JTextField loginTxt;
	private JLabel usernameTxt;
	private JLabel passwordTxt;
	
    private KeyListener usernameKeyListener;
    private KeyListener passwordKeyListener;

	private JTextField usernameField;
	private JPasswordField passwordField;
	
	private JButton loginButton;
	private JButton registerButton;
	
	private String username;
	
	private static String SPECIAL_CHARS_REGEX_PATTERN = "[\\\\!\"#$%&()*+,./:;<=>?@\\[\\]^-`�_{|}~]+";

	
	public LoginPanel()
	  {
	    
	    
	    setCursor(Cursor.getPredefinedCursor(0));
	    
	    display();

	  }
	
	public void display()
	{
		//setBackground(Color.GRAY);
	    setMinimumSize(new Dimension(735, 465));
	    setPreferredSize(new Dimension(735, 465));
	    setLayout(null);
	    
	    	    
	    this.loginTxt = new JTextField("Please Login OR Register To Play The Game");
	    this.loginTxt.setCursor(Cursor.getPredefinedCursor(0));
	    this.loginTxt.setFont(new Font("Arial Unicode MS", 1, 12));
	    this.loginTxt.setForeground(new Color(255, 255, 255));
	    this.loginTxt.setEditable(false);
	    this.loginTxt.setHorizontalAlignment(0);
	    this.loginTxt.setColumns(10);
	    this.loginTxt.setBackground(Color.DARK_GRAY);
	    //this.loginTxt.setBounds(-11, 155, 751, 82);
	    this.loginTxt.setBounds(-11, 0, 751, 30);
	    add(this.loginTxt);
	    
	    this.LOGO.setBounds(180,30,400,200);
	    add(this.LOGO);

	    
	    //Username
	    
	    usernameTxt = new JLabel("Username:");
        this.usernameTxt.setBounds(200, 250, 100, 20);
	    add(this.usernameTxt);
	    
	    usernameField = new JTextField();
	    this.usernameField.setBounds(300,250,200,20);
	    usernameField.setToolTipText("Enter your username");
	    add(this.usernameField);
	    

        passwordTxt = new JLabel("Password:");
        this.passwordTxt.setBounds(200, 300, 100, 20);       
	    add(this.passwordTxt);
	    
	    passwordField = new JPasswordField();
	    this.passwordField.setBounds(300,300,200,20);
	    passwordField.setToolTipText("Enter your password");
	    passwordField.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				loginButton.doClick();
			}
		});
	    add(this.passwordField);
	    
	    
	    loginButton = new JButton("Login");
	    loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	    loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Pattern pUsername = Pattern.compile(SPECIAL_CHARS_REGEX_PATTERN);
                Matcher mUsername = pUsername.matcher(usernameField.getText());

                Pattern pPassword = Pattern.compile(SPECIAL_CHARS_REGEX_PATTERN);
                Matcher mPassword = pPassword.matcher(getPassword());

                boolean characterFound = mUsername.find();
                boolean passwordFound = mPassword.find();
                
                if (usernameField.getText().length() <= 4 || passwordField.getPassword().length <= 4 || characterFound || passwordFound
                			|| usernameField.getText().length() > 20 || passwordField.getPassword().length > 20) 
                {
                    Sound.BUTTONCLICK.play();
                    JOptionPane.showMessageDialog(null, "CHECK USERNAME AND PASSWORD", "WRONG CREDENTIALS", JOptionPane.WARNING_MESSAGE);
                } 
                else
                {
                Sound.BUTTONCLICK.play();
                LoginPanel.this.checkUser(usernameField.getText().trim(), LoginPanel.this.getPassword());
                }
				
				
			}
		});
	    
	    //this.loginButton.setBounds(200, 400, 100, 50);
	    this.loginButton.setBounds(450, 400, 100, 50);
	    add(this.loginButton);
	    
	    registerButton = new JButton("Register");
	    registerButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	    registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                Sound.BUTTONCLICK.play();

				JPanel panel = LoginPanel.this.getPanel();
		        panel.removeAll();
		        panel.setLayout(new BorderLayout());
		        panel.add(new RegisterPanel(), "Center");
		        panel.updateUI();						
				
			}
		});
	    
	    //this.loginButton.setBounds(-11, 0, 751, 30);
	    this.registerButton.setBounds(200, 400, 100, 50);
	    add(this.registerButton);
	    
	    
	    
	    
	    
	    
	}
	
	 private JPanel getPanel()
	  {
	    return this;
	  }
	 
	 public String getUser()
	 {
		 return usernameField.getText().trim();
	 }
	 
	 private String getPassword()
	 {
		 String strPassword = new String(passwordField.getPassword());
		 return strPassword;
		 
	 }
	 
	 private void checkUser(String nameIN, String passwordIN)
	 {
		 

		 
		 try {
				Connection conn = Main.getConnection();
				
				String user = usernameField.getText().trim();
				String pass = getPassword().trim();
				
				Statement stmt = conn.createStatement();
				String showAll = "SELECT * FROM GAMER WHERE Gamer_Name =" + "'" + user + "'" +
	                    "AND Gamer_Password=" + "'" + pass + "'";
	            stmt = conn.createStatement();
	            ResultSet rset = stmt.executeQuery(showAll);
	            System.out.println("\n-- Gamer TABLE --");
	            
	            int count = 0;
	            if(rset.next())
	            {


	                count = count + 1;
	            }
	            
	            if(count == 1)
	            {
		            //If there is a gamer with same username and password then display mainScreen panel
		            JPanel panel = LoginPanel.this.getPanel();
					 
			        panel.removeAll();
			        panel.setLayout(new BorderLayout());
			        panel.add(new MainScreen(nameIN), "Center");
			        panel.updateUI();
			        setUsername(nameIN);
	            }
	            else if (count == 0)
	            {
	                JOptionPane.showMessageDialog(null, "Wrong Username OR Password --- Try Again", "Error", JOptionPane.ERROR_MESSAGE);
				}
	            	            
	            
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		 
	 }
	 
	 private void setUsername(String usernameIN)
	 {
		 username = usernameIN;
	 }
	 
	 public String getUsername()
	 {
		 return username;
	 }
	 


	  
}
