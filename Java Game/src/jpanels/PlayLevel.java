package jpanels;

import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import game.BallWorld;
import sound.Sound;

public class PlayLevel
  extends JPanel
{
  public int level;
  public int scoreLevel;
  private JTextField levelTxt;
  private JTextField Balls;
  public String[] levels = new String[13];
  public String[] levelInfo = new String[13];
  private JButton play;
  
  private String username;
  
  public PlayLevel(int level, int score, String name)
  {
    Sound.BALL.stop();
    Sound.BALLCOLLISION.stop();
    Sound.FAIL.stop();
    Sound.WON.stop();
    Sound.LEVEL.play();
    Sound.CLICK.stop();
    Sound.INTRO.stop();
    Sound.CONGRATS.stop();
    this.scoreLevel = score;
    
    username = name;
    
    setCursor(Cursor.getPredefinedCursor(0));
    this.level = level;
    init();
    levelText();
    
    displayStart();
  }
  
  public void init()
  {
    setBackground(Color.GRAY);
    setMinimumSize(new Dimension(720, 493));
    setPreferredSize(new Dimension(720, 493));
    setLayout(null);
    
    this.levelTxt = new JTextField();
    this.levelTxt.setCursor(Cursor.getPredefinedCursor(0));
    this.levelTxt.setFont(new Font("Arial Unicode MS", 1, 33));
    this.levelTxt.setForeground(new Color(255, 255, 255));
    this.levelTxt.setEditable(false);
    this.levelTxt.setHorizontalAlignment(0);
    this.levelTxt.setColumns(10);
    this.levelTxt.setBackground(Color.DARK_GRAY);
    this.levelTxt.setBounds(-11, 155, 751, 82);
    add(this.levelTxt);
    
    this.Balls = new JTextField();
    this.Balls.setCursor(Cursor.getPredefinedCursor(0));
    this.Balls.setFont(new Font("Arial Unicode MS", 2, 25));
    this.Balls.setHorizontalAlignment(0);
    this.Balls.setForeground(Color.WHITE);
    this.Balls.setEditable(false);
    this.Balls.setColumns(10);
    this.Balls.setBackground(Color.LIGHT_GRAY);
    this.Balls.setBounds(-1, 237, 741, 55);
    add(this.Balls);
    
    this.play = new JButton(" ");
    this.play.setToolTipText("Click to play");
    this.play.setBorder(null);
    this.play.setCursor(Cursor.getPredefinedCursor(12));
    this.play.setIcon(new ImageIcon(PlayLevel.class.getResource("/gui/play.png")));
    this.play.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent arg0)
      {
        PlayLevel.this.createLevel();
      }
    });
    this.play.setBounds(286, 331, 163, 50);
    add(this.play);
    
    addMouseListener(new MouseAdapter()
    {
      public void mousePressed(MouseEvent e) {}
    });
  }
  
  private void levelText()
  {
    for (int i = 0; i < 13; i++) {
      if (i == 0) {
        this.levels[i] = "";
      } else if (i == 12) {
        this.levels[i] = "Final Level";
      } else {
        this.levels[i] = ("Level " + i);
      }
    }
    this.levelInfo[0] = "";
    this.levelInfo[1] = "get 1 out of 5 balls.";
    this.levelInfo[2] = "get 2 out of 10 balls.";
    this.levelInfo[3] = "get 4 out of 15 balls.";
    this.levelInfo[4] = "get 6 out of 20 balls.";
    this.levelInfo[5] = "get 10 out of 25 balls.";
    this.levelInfo[6] = "get 15 out of 30 balls.";
    this.levelInfo[7] = "get 18 out of 35 balls.";
    this.levelInfo[8] = "get 22 out of 40 balls.";
    this.levelInfo[9] = "get 30 out of 45 balls.";
    this.levelInfo[10] = "get 37 out of 50 balls.";
    this.levelInfo[11] = "get 48 out of 55 balls.";
    this.levelInfo[12] = "get 54 out of 60 balls.";
  }
  
  public void createLevel()
  {
    for (int i = 1; i < 13; i++) {
      if (i != 13) {
        if (this.level == i)
        {
          removeAll();
          setLayout(new BorderLayout());
          add(new BallWorld(735, 465, i * 5, this.level, this.scoreLevel, username), "Center");
          updateUI();
          break;
        }
      }
    }
  }
  
  private void displayStart()
  {
    for (int i = 1; i < 13; i++) {
      if (this.level == i)
      {
        this.levelTxt.setText(this.levels[i]);
        this.Balls.setText(this.levelInfo[i]);
        break;
      }
    }
  }
  
  public int getLevel()
  {
    return this.level;
  }
  
  public void setLevel(int level)
  {
    this.level = level;
  }
}