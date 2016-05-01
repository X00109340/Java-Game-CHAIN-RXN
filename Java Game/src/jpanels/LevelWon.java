package jpanels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import game.Main;
import jFrames.HighScoresJframe;
import sound.Sound;

public class LevelWon
  extends JPanel
{
  private JTextField textField;
  private JTextField textField_1;
  private DecimalFormat formatter = new DecimalFormat("#,###");
  private Connection conn;
  private Statement stmt;
  private PreparedStatement pstm;
  private ResultSet rset;
  
  private String username;
  
  private int currentScore;
  
  LoginPanel lp = new LoginPanel();

  
  public LevelWon(final int level, final int scoreIN, String name)
  {
	  
	  conn = Main.getConnection();
	  
	  username = name;
	  currentScore = scoreIN;
    setToolTipText("Click here to play next level");
    setBackground(Color.LIGHT_GRAY);
    setPreferredSize(new Dimension(730, 493));
    setMinimumSize(new Dimension(730, 493));
    setCursor(Cursor.getPredefinedCursor(12));
    setLayout(null);
    
    this.textField = new JTextField(); 
    this.textField.setCursor(Cursor.getPredefinedCursor(0));
    this.textField.setBackground(Color.DARK_GRAY);
    this.textField.setBounds(-11, 0, 753, 170);
    add(this.textField);
    this.textField.setColumns(10);
    
    this.textField_1 = new JTextField();
    this.textField_1.setCursor(Cursor.getPredefinedCursor(0));
    this.textField_1.setColumns(10);
    this.textField_1.setBackground(Color.DARK_GRAY);
    this.textField_1.setBounds(-11, 339, 753, 170);
    add(this.textField_1);
    
    JLabel lblNewLabel = new JLabel("");
    lblNewLabel.setIcon(new ImageIcon(LevelWon.class.getResource("/gui/Pass.png")));
    lblNewLabel.setBounds(89, 181, 154, 147);
    add(lblNewLabel);
    
    JLabel lblMission = new JLabel("Mission Accomplished");
    lblMission.setForeground(Color.WHITE);
    lblMission.setFont(new Font("Arial Black", 2, 29));
    lblMission.setBounds(267, 208, 371, 40);
    add(lblMission);
    
    JLabel lblScore = new JLabel("Total Score: " + this.formatter.format(currentScore));
    lblScore.setForeground(Color.WHITE);
    lblScore.setFont(new Font("Arial Unicode MS", 0, 28));
    lblScore.setBounds(267, 271, 371, 40);
    add(lblScore);
    
    addMouseListener(new MouseAdapter()
    {
      public void mousePressed(MouseEvent e)
      {
        JPanel panel = LevelWon.this.getPanel();
        panel.removeAll();
        panel.setLayout(new BorderLayout());
        if (level + 1 > 12) {
          //panel.add(new HighScorePanel(score, 0, level), "Center");
        	Sound.WON.stop();
        	JPanel panel1 = LevelWon.this.getPanel();
			 
	        panel1.removeAll();
	        panel1.setLayout(new BorderLayout());
	        panel1.add(new MainScreen(name), "Center");
	        panel1.updateUI();
	        
          compareScores();
          //*******************************************************************
          HighScoresJframe hscore = new HighScoresJframe(getPreviousScore(), name);
          hscore.setVisible(true);
          
        } else {
          panel.add(new PlayLevel(level + 1, currentScore, name), "Center");
        }
        panel.updateUI();
      }
    });
  }
  
  private JPanel getPanel()
  {
    return this;
  }
  
  private int getPreviousScore()
  {
	  int scoreIN =0;
      try
      {
          String getAll = "SELECT Gamer_Score FROM Gamer where Gamer_Name =" + "'" + username + "'";

          stmt = conn.createStatement();
          rset = stmt.executeQuery(getAll);
      }
      catch (SQLException e)
      {
          System.out.println(e);
      }

      try {
          while(rset.next())
          {
              scoreIN = Integer.parseInt(rset.getString(1));

          }
      } catch (SQLException e) {
          e.printStackTrace();
      }
      return scoreIN;
  }
  
  private void compareScores()
  {
	  int preScore = getPreviousScore();
	  if (getPreviousScore() < currentScore) 
	  {
		  try{
	            String sql = "UPDATE Gamer SET Gamer_Score = " + "'" + currentScore + "'" +
	                    "where Gamer_Name=" + "'" + username + "'";
	            stmt = conn.createStatement();
	            stmt.executeUpdate(sql);
	            
	            JOptionPane.showMessageDialog(null, "Previous Highscore: " + preScore + "\nCurrent Score: " + currentScore + "\n**Highscore Updated**");
	            
	            System.out.println("******LevelWon-CompareScore()......  SCORE UPDATED" + currentScore + username);

	        } catch (Exception e) {
	            System.out.println("Problem " + e);
	        }
	  }	 
	  else
	  {
          JOptionPane.showMessageDialog(null, "Previous Highscore: " + preScore + "\nCurrent Score: " + currentScore);

	  }
  }
  
}