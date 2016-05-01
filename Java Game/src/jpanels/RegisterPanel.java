package jpanels;


import jpanels.LevelFail;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import game.BallWorld;
import game.Main;
import sound.Sound;

import database.GamerOperations;

public class RegisterPanel extends JPanel {

    private final JLabel LOGO = new JLabel(new ImageIcon(getClass().getResource("/images/register.png")));

    Connection conn = Main.getConnection();

	private JTextField loginTxt;
	private JLabel usernameTxt;
	private JLabel passwordTxt;
	
    private KeyListener usernameKeyListener;
    private KeyListener passwordKeyListener;

	private JTextField usernameField;
	private JPasswordField passwordField;
	
	private JButton registerButton;
	private JButton backButton;
	
	private static String SPECIAL_CHARS_REGEX_PATTERN = "[\\\\!\"#$%&()*+,./:;<=>?@\\[\\]^-`�_{|}~]+";
	public RegisterPanel()
	  {
	    //Sound.INTRO.play();

	    
	    setCursor(Cursor.getPredefinedCursor(0));
	    
	    display();
	    //addGamer("Mary","1111");

	  }
	
	public void display()
	{
		//setBackground(Color.GRAY);
	    setMinimumSize(new Dimension(735, 465));
	    setPreferredSize(new Dimension(735, 465));
	    setLayout(null);
	    	    
	    this.loginTxt = new JTextField("Please Choose Your Username And Password");
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
	    
	    this.LOGO.setBounds(80,30,600,200);
	    add(this.LOGO);
	    
	    
	    usernameTxt = new JLabel("Username:");
        this.usernameTxt.setBounds(200, 250, 100, 20);
	    add(this.usernameTxt);
	    
	    usernameField = new JTextField();
	    this.usernameField.setBounds(300,250,200,20);
	    usernameField.setToolTipText("Enter your username, minimum 5 characters up to 20 characters in length, letter and numbers only.");
	    add(this.usernameField);
	    

        passwordTxt = new JLabel("Password:");

        this.passwordTxt.setBounds(200, 300, 100, 20);
	    add(this.passwordTxt);
	    
	    passwordField = new JPasswordField();
	    this.passwordField.setBounds(300,300,200,20);
	    passwordField.setToolTipText("Enter your password, minimum 5 characters up to 20 characters in length, letter and numbers only.");
	    add(this.passwordField);
	    
	    backButton = new JButton("Back");
	    backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	    backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                Sound.BUTTONCLICK.play();

				JPanel panel = RegisterPanel.this.getPanel();
		        panel.removeAll();
		        panel.setLayout(new BorderLayout());
		        panel.add(new LoginPanel(), "Center");
		        panel.updateUI();	
				
				
				
			}
		});
	    
	    this.backButton.setBounds(200, 400, 100, 50);
	    add(this.backButton);
	    
	    registerButton = new JButton("Register");
	    registerButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	    registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                
                Sound.BUTTONCLICK.play();

				checkUserNameAvailability();

				
				
			}
		});
	    
	    //this.registerButton.setBounds(-11, 0, 751, 30);
	    this.registerButton.setBounds(450, 400, 100, 50);
	    add(this.registerButton);
	    
	   
	    
	    
	    
	    
	}
	
	 private JPanel getPanel()
	  {
	    return this;
	  }
	 
	 private String getPassword()
	 {
		 String strPassword = new String(passwordField.getPassword());
		 return strPassword;
		 
	 }
	 
	 private void addGamer(String nameIN, String passwordIN)
	 {
		 GamerOperations go = new GamerOperations(conn);
		 
		 go.addGamer(nameIN, passwordIN);
		 
	 }
	 
	 private void checkUserNameAvailability()
	 {
		 if(usernameField.getText().length() >= 5)
		 {
		 try {
				Connection conn = Main.getConnection();
				
				String user = usernameField.getText().trim();
				String pass = getPassword().trim();
				
				Statement stmt = conn.createStatement();
				String showAll = "SELECT * FROM GAMER WHERE Gamer_Name =" + "'" + user + "'";
	            stmt = conn.createStatement();
	            ResultSet rset = stmt.executeQuery(showAll);
	            
	            int count = 0;
	            if(rset.next())
	            {


	                System.out.println(
	                        rset.getString(1) + "\t" +
	                                rset.getString(2)+ "\t" +
	                                rset.getString(3));
	                count = count + 1;
	            }
	            
	            if(count != 0)
	            {
		            JOptionPane.showMessageDialog(null, "Username already taken\n\nPlease use a diffrent username");;
	            }
	            else if (count == 0)
	            {
	                registerUser();
				}
	            	            
	            
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
		 else
		 {
			 JOptionPane.showMessageDialog(null, "Username must be a minimum of 5 characters and less than 20 characters");
		 }
	 }
	 
	 private void registerUser()
	 {
		 Pattern pUsername = Pattern.compile(SPECIAL_CHARS_REGEX_PATTERN);
         Matcher mUsername = pUsername.matcher(usernameField.getText());

         Pattern pPassword = Pattern.compile(SPECIAL_CHARS_REGEX_PATTERN);
         Matcher mPassword = pPassword.matcher(getPassword());

         boolean characterFound = mUsername.find();
         boolean passwordFound = mPassword.find();
         
         if (usernameField.getText().length() <= 4 || passwordField.getPassword().length <= 4 || characterFound || passwordFound
         			|| usernameField.getText().length() > 20 || passwordField.getPassword().length > 20) 
         {
             JOptionPane.showMessageDialog(null, "CHECK USERNAME AND PASSWORD \n* No special characters allowed (eg) &@/> \n* Username and Password must be a minimum of 5 characters and less than 20 characters", "Error", JOptionPane.WARNING_MESSAGE);
         } 
         else 
         {

             addGamer(usernameField.getText().trim(), getPassword().trim());

             JOptionPane.showMessageDialog(null, "Congratulations -- You have successfully registered \n* Username: " + usernameField.getText() + "\n\nPlease Login again", "Success", JOptionPane.INFORMATION_MESSAGE);

             JPanel panel = RegisterPanel.this.getPanel();
             panel.removeAll();
             panel.setLayout(new BorderLayout());
             panel.add(new LoginPanel(), "Center");
             panel.updateUI();
         }
	 }
	  
}
