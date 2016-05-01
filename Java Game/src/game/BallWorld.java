package game;

import game.Ball;
import game.ContainerBox;
import java.applet.AudioClip;
import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import jpanels.LevelFail;
import jpanels.LevelWon;
import sound.Sound;
import game.Main;

public class BallWorld
  extends JPanel
{
  private int count = 0;
  private int timer = 0;
  private int collisionCounter = 0;
  private static int MAX_BALLS = 0;
  private ArrayList<Ball> CollisionBalls = new ArrayList();
  private int[] toWin = { 0, 1, 2, 4, 6, 10, 15, 18, 22, 30, 37, 48, 54 };
  private ArrayList<Integer> timers = new ArrayList();
  private int score = 0;
  private DecimalFormat formatter = new DecimalFormat("#,###");
  private static final int UPDATE_RATE = 60;
  private Point point;
  private Point point1;
  private Point collisionPoint;
  private Ball ball5;
  private Ball ball6;
  private ContainerBox box;
  private Ball[] balls;
  private Color ballColor;
  private DrawCanvas canvas;
  private int canvasWidth;
  private int canvasHeight;
  private Graphics2D g2d;
  private int level;
  private JTextField infoText;
  private JTextField scoreField;
  private JTextField Levscore;
  private int levelScore;
  private int disScore;
  
  private String username;
  
  public BallWorld(int width, int height, int size, int level, int levelScore, String name)
  {
    Sound.LEVEL.stop();
    
    username = name;
    this.levelScore = levelScore;
    setPreferredSize(new Dimension(730, 493));
    setSize(new Dimension(730, 493));
    setCursor(Cursor.getPredefinedCursor(12));
    setBackground(Color.BLACK);
    MAX_BALLS = size;
    this.level = level;
    this.balls = new Ball[MAX_BALLS];
    this.canvasWidth = width;
    this.canvasHeight = height;
    
    Random rand = new Random();
    int radius = 8;
    int min = 2;
    int max = 3;
    
    int speed = 2;
    for (int i = 0; i < MAX_BALLS; i++)
    {
      this.ballColor = new Color(Color.HSBtoRGB((float)Math.random(), (float)Math.random(), 0.5F + (float)Math.random() / 2.0F)).brighter();
      int x = new Random().nextInt(this.canvasWidth - radius * 2 - 20) + radius + 10;
      int y = new Random().nextInt(this.canvasHeight - radius * 2 - 20) + radius + 10;
      while ((this.ballColor == Color.WHITE) || (this.ballColor == Color.gray)) {
        this.ballColor = Color.YELLOW.darker();
      }
      if (i == 0) {
        this.balls[i] = new Ball(x, y, radius, speed, new Random().nextInt(360), this.ballColor);
      } else {
        this.balls[i] = new Ball(x, y, radius, new Random().nextInt(max - min + 1) + min, new Random().nextInt(360), this.ballColor);
      }
    }
    this.box = new ContainerBox(0, 0, this.canvasWidth, this.canvasHeight, Color.BLACK.brighter(), Color.WHITE.brighter());
    
    this.canvas = new DrawCanvas();
    setLayout(new BorderLayout());
    
    Box theBox = Box.createHorizontalBox();
    theBox.setCursor(Cursor.getPredefinedCursor(0));
    
    initScoreDisplay();
    
    theBox.add(this.infoText);
    theBox.add(this.Levscore);
    add(this.canvas, "Center");
    add(theBox, "North");
    add(this.scoreField, "South");
    
    addMouseListener(new MouseAdapter()
    {
      public void mousePressed(MouseEvent e)
      {
        BallWorld.this.point = new Point(e.getX(), e.getY());
        if (BallWorld.this.count == 0)
        {
          Sound.CLICK.play();
          BallWorld.this.ball5 = new Ball(e.getX(), e.getY(), 35.0F, 0.0F, 0.0F, Color.WHITE, 0);
          BallWorld.this.CollisionBalls.add(BallWorld.this.ball5);
          BallWorld.this.timers.add(Integer.valueOf(0));
          BallWorld.this.timer = 0;
        }
        BallWorld.this.count = 1;
        BallWorld.this.setCursor(Cursor.getPredefinedCursor(0));
      }
    });
    addMouseMotionListener(new MouseMotionAdapter()
    {
      public void mouseMoved(MouseEvent e)
      {
        BallWorld.this.point1 = new Point(e.getX(), e.getY());
        BallWorld.this.ball6 = new Ball(e.getX(), e.getY(), 35.0F, 0.0F, 0.0F, Color.WHITE.brighter());
        if (BallWorld.this.count == 0) {
            BallWorld.this.setToolTipText(null);
        } else {
          BallWorld.this.setToolTipText(null);
        }
      }
    });
    gameStart();
  }
  
  private void initScoreDisplay()
  {
    this.infoText = new JTextField("");
    this.infoText.setToolTipText("No. of balls to explode in order to pass this level.");
    this.infoText.setFont(new Font("Courier New", 1, 14));
    this.infoText.setVerifyInputWhenFocusTarget(false);
    this.infoText.setDisabledTextColor(Color.WHITE);
    this.infoText.setEnabled(false);
    this.infoText.setEditable(false);
    this.infoText.setCursor(Cursor.getPredefinedCursor(0));
    this.infoText.setBorder(new EmptyBorder(0, 0, 0, 0));
    this.infoText.setForeground(Color.WHITE);
    this.infoText.setBackground(this.box.getColorFilled());
    
    this.Levscore = new JTextField("Level Score: " + this.formatter.format(this.levelScore));
    this.Levscore.setToolTipText("Prev. Level Score");
    this.Levscore.setHorizontalAlignment(11);
    this.Levscore.setFont(new Font("Courier New", 1, 14));
    this.Levscore.setVerifyInputWhenFocusTarget(false);
    this.Levscore.setDisabledTextColor(Color.WHITE);
    this.Levscore.setEnabled(false);
    this.Levscore.setEditable(false);
    this.Levscore.setCursor(Cursor.getPredefinedCursor(0));
    this.Levscore.setBorder(new EmptyBorder(0, 0, 0, 0));
    this.Levscore.setForeground(Color.WHITE);
    this.Levscore.setBackground(this.box.getColorFilled());
    
    this.scoreField = new JTextField("Score: 0");
    this.scoreField.setToolTipText("Current Level Score");
    this.scoreField.setHorizontalAlignment(11);
    this.scoreField.setFont(new Font("Courier New", 1, 14));
    this.scoreField.setVerifyInputWhenFocusTarget(false);
    this.scoreField.setDisabledTextColor(Color.WHITE);
    this.scoreField.setEnabled(false);
    this.scoreField.setEditable(false);
    this.scoreField.setCursor(Cursor.getPredefinedCursor(0));
    this.scoreField.setBorder(new EmptyBorder(0, 0, 0, 0));
    this.scoreField.setForeground(Color.WHITE);
    this.scoreField.setBackground(this.box.getColorFilled());
  }
  
  private JPanel getPanel()
  {
    return this;
  }
  
  public void gameStart()
  {
    Thread gameThread = new Thread()
    {
      public void run()
      {
        for (;;)
        {
          BallWorld.this.gameUpdate();
          if (BallWorld.this.point != null) {
            BallWorld.this.detectCollisions();
          }
          BallWorld.this.checkWin(this);
          
          BallWorld.this.repaint();
          try
          {
            Thread.sleep(16L);
          }
          catch (InterruptedException localInterruptedException) {}
        }
      }
    };
    gameThread.start();
  }
  
  public void gameUpdate()
  {
    for (int i = 0; i < this.balls.length; i++) {
      this.balls[i].bounceBall(this.box);
    }
  }
  
  public void detectCollisions()
  {
    for (int i = 0; i < this.balls.length; i++) {
      for (int j = 0; j < this.CollisionBalls.size(); j++) {
        if ((this.balls[i].getX() + this.balls[i].getRadius() + ((Ball)this.CollisionBalls.get(j)).getRadius() > ((Ball)this.CollisionBalls.get(j)).getX()) && 
          (this.balls[i].getX() < ((Ball)this.CollisionBalls.get(j)).getX() + this.balls[i].getRadius() + ((Ball)this.CollisionBalls.get(j)).getRadius()) && 
          (this.balls[i].getY() + this.balls[i].getRadius() + ((Ball)this.CollisionBalls.get(j)).getRadius() > ((Ball)this.CollisionBalls.get(j)).getY()) && 
          (this.balls[i].getY() < ((Ball)this.CollisionBalls.get(j)).getY() + this.balls[i].getRadius() + ((Ball)this.CollisionBalls.get(j)).getRadius()) && 
          (this.balls[i].getColor() != this.box.getColorFilled()) && (((Ball)this.CollisionBalls.get(j)).getColor() != this.box.getColorFilled()))
        {
          double distance = Math.sqrt((this.balls[i].getX() - ((Ball)this.CollisionBalls.get(j)).getX()) * (this.balls[i].getX() - ((Ball)this.CollisionBalls.get(j)).getX()) + 
            (this.balls[i].getY() - ((Ball)this.CollisionBalls.get(j)).getY()) * (this.balls[i].getY() - ((Ball)this.CollisionBalls.get(j)).getY()));
          if (distance < this.balls[i].getRadius() + ((Ball)this.CollisionBalls.get(j)).getRadius())
          {
            float collisionPointX = (this.balls[i].getX() * ((Ball)this.CollisionBalls.get(j)).getRadius() + ((Ball)this.CollisionBalls.get(j)).getX() * this.balls[i].getRadius()) / (
              this.balls[i].getRadius() + ((Ball)this.CollisionBalls.get(j)).getRadius());
            
            float collisionPointY = (this.balls[i].getY() * ((Ball)this.CollisionBalls.get(j)).getRadius() + ((Ball)this.CollisionBalls.get(j)).getY() * this.balls[i].getRadius()) / (
              this.balls[i].getRadius() + ((Ball)this.CollisionBalls.get(j)).getRadius());
            
            Color prev = this.balls[i].getColor();
            this.balls[i].setColor(this.box.getColorFilled());
            this.collisionPoint = new Point((int)collisionPointX, (int)collisionPointY);
            for (int z = 0; z < MAX_BALLS; z++) {
              if (this.collisionCounter == z)
              {
                this.CollisionBalls.add(new Ball(collisionPointX, collisionPointY, 35, 0, 0, prev, ((Ball)this.CollisionBalls.get(j)).getCollBall() + 1));
                this.timers.add(Integer.valueOf(0));
                break;
              }
            }
            this.collisionCounter += 1;
            if (((Ball)this.CollisionBalls.get(j)).getCollBall() == 0) {
              this.score += 100;
            } else {
              this.score += 100 * (int)Math.pow((this.CollisionBalls.get(j + 1)).getCollBall(), 3);
            }
            Sound.BALLCOLLISION.play();
          }
        }
      }
    }
  }
  
  private void checkWin(Thread game)
  {
    for (int s = 1; s < this.toWin.length; s++)
    {
      if ((this.collisionCounter >= this.toWin[this.level]) && (((Integer)this.timers.get(this.timers.size() - 1)).intValue() == 199))
      {
        Sound.WON.play();
        try
        {
          Thread.sleep(16L);
        }
        catch (InterruptedException localInterruptedException) {}
        JPanel panel = getPanel();
        panel.removeAll();
        
       
        Main.score = this.levelScore + this.score;
        Main.level = this.level;
        panel.add(new LevelWon(this.level, this.levelScore + this.score, username), "Center");
        panel.updateUI();
        
        game.stop();
      }
      if ((this.collisionCounter == 0) && (this.timer == 199))
      {
        Sound.FAIL.play();
        try
        {
          Thread.sleep(33L);
        }
        catch (InterruptedException localInterruptedException1) {}
        JPanel panel = getPanel();
        panel.removeAll();
        if (this.toWin[this.level] - this.collisionCounter > 1) {
          panel.add(new LevelFail(this.level, this.levelScore, this.toWin[this.level] - this.collisionCounter + " more balls", username), "Center");
        } else {
          panel.add(new LevelFail(this.level, this.levelScore, this.toWin[this.level] - this.collisionCounter + " more ball",username), "Center");
        }
        panel.updateUI();
        
        game.stop();
      }
      if ((this.collisionCounter < this.toWin[this.level]) && (this.collisionCounter > 0) && (((Integer)this.timers.get(this.timers.size() - 1)).intValue() == 199))
      {
        Sound.FAIL.play();
        try
        {
          Thread.sleep(33L);
        }
        catch (InterruptedException localInterruptedException2) {}
        JPanel panel = getPanel();
        panel.removeAll();
        if (this.toWin[this.level] - this.collisionCounter > 1) {
          panel.add(new LevelFail(this.level, this.levelScore, this.toWin[this.level] - this.collisionCounter + " more balls", username), "Center");
        } else {
          panel.add(new LevelFail(this.level, this.levelScore, this.toWin[this.level] - this.collisionCounter + " more ball", username), "Center");
        }
        panel.updateUI();
        
        game.stop();
      }
    }
  }
  
  class DrawCanvas
    extends JPanel
  {
    DrawCanvas() {}
    
    public void paintComponent(Graphics g)
    {
      super.paintComponent(g);
     
      BallWorld.this.g2d = ((Graphics2D)g);
      
      setRenderingHints();
      
      BallWorld.this.box.draw(BallWorld.this.g2d);
      
      drawBalls();
      
      drawInfoText();
      
      drawMouseMovedBall();
      
      drawMouseClickBall();
      
      drawCollisionBalls();
    }
    
    private void setRenderingHints()
    {
      BallWorld.this.g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
      BallWorld.this.g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      BallWorld.this.g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    }
    
    private void drawMouseClickBall()
    {
      if (BallWorld.this.point != null)
      {
        BallWorld.this.g2d.setComposite(AlphaComposite.getInstance(3, 0.6F));
        BallWorld.this.point1 = null;
        if (BallWorld.this.timer == 199) {
          ((Ball)BallWorld.this.CollisionBalls.get(0)).setColor(BallWorld.this.box.getColorFilled());
        }
        if (BallWorld.this.timer > 173) {
          ((Ball)BallWorld.this.CollisionBalls.get(0)).shrink(BallWorld.this.g2d);
        }
        if (BallWorld.this.timer == 0)
        {
          ((Ball)BallWorld.this.CollisionBalls.get(0)).expand(BallWorld.this.g2d);
          BallWorld.this.timer += 1;
        }
        else
        {
          ((Ball)BallWorld.this.CollisionBalls.get(0)).draw(BallWorld.this.g2d);
          BallWorld.this.timer += 1;
        }
      }
    }
    
    private void drawCollisionBalls()
    {
      for (int i = 1; i < BallWorld.this.CollisionBalls.size(); i++) {
        if ((((Ball)BallWorld.this.CollisionBalls.get(i)).getColor() != BallWorld.this.box.getColorFilled()) && (((Ball)BallWorld.this.CollisionBalls.get(i)).isAlive()))
        {
          BallWorld.this.g2d.setComposite(AlphaComposite.getInstance(3, 0.8F));
          if (((Integer)BallWorld.this.timers.get(i)).intValue() == 0)
          {
            ((Ball)BallWorld.this.CollisionBalls.get(i)).expand(BallWorld.this.g2d);
            BallWorld.this.timers.set(i, Integer.valueOf(((Integer)BallWorld.this.timers.get(i)).intValue() + 1));
          }
          else
          {
            ((Ball)BallWorld.this.CollisionBalls.get(i)).draw(BallWorld.this.g2d);
            if ((((Integer)BallWorld.this.timers.get(i)).intValue() > 20) && (((Integer)BallWorld.this.timers.get(i)).intValue() < 100))
            {
              BallWorld.this.g2d.setColor(Color.WHITE.brighter());
              BallWorld.this.g2d.setFont(new Font("Times New Roman", 1, 14));
              BallWorld.this.g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
              BallWorld.this.g2d.drawString("+" + Integer.toString(100 * (int)Math.pow(((Ball)BallWorld.this.CollisionBalls.get(i)).getCollBall(), 3.0D)), ((Ball)BallWorld.this.CollisionBalls.get(i)).getX() + 5.0F, ((Ball)BallWorld.this.CollisionBalls.get(i)).getY());
            }
            BallWorld.this.timers.set(i, Integer.valueOf(((Integer)BallWorld.this.timers.get(i)).intValue() + 1));
          }
          if (((Integer)BallWorld.this.timers.get(i)).intValue() > 173) {
            ((Ball)BallWorld.this.CollisionBalls.get(i)).shrink(BallWorld.this.g2d);
          }
          BallWorld.this.scoreField.setText("Score: " + BallWorld.this.formatter.format(BallWorld.this.score));
          if (((Integer)BallWorld.this.timers.get(i)).intValue() == 199) {
            ((Ball)BallWorld.this.CollisionBalls.get(i)).setColor(BallWorld.this.box.getColorFilled());
          }
        }
      }
    }
    
    private void drawMouseMovedBall()
    {
      if ((BallWorld.this.point1 != null) && (BallWorld.this.point == null))
      {
        BallWorld.this.g2d.setComposite(AlphaComposite.getInstance(3, 0.4F));
        BallWorld.this.ball6.draw(BallWorld.this.g2d);
      }
    }
    
    private void drawInfoText()
    {
      if (BallWorld.this.level > 1) {
        BallWorld.this.infoText.setText(" Explode " + BallWorld.this.toWin[BallWorld.this.level] + " ball");
      } else if (BallWorld.this.level == 1) {
        BallWorld.this.infoText.setText(" Explode 1 ball");
      }
      if (BallWorld.this.collisionCounter > 0) {
        if (BallWorld.this.toWin[BallWorld.this.level] - BallWorld.this.collisionCounter > 0)
        {
          BallWorld.this.infoText.setText(BallWorld.this.toWin[BallWorld.this.level] - BallWorld.this.collisionCounter + " more ball");
        }
        else
        {
          BallWorld.this.infoText.setText("");
          Color prev = BallWorld.this.box.getColorFilled();
          BallWorld.this.box.setColorFilled(Color.DARK_GRAY.brighter());
          changeBallColor(prev);
        }
      }
    }
    
    private void drawBalls()
    {
      for (int i = 0; i < BallWorld.this.balls.length; i++) {
        if (BallWorld.this.balls[i].getColor() != BallWorld.this.box.getColorFilled()) {
          BallWorld.this.balls[i].draw(BallWorld.this.g2d);
        }
      }
    }
    
    private void changeBallColor(Color prev)
    {
      Ball[] arrayOfBall;
      int j = (arrayOfBall = BallWorld.this.balls).length;
      for (int i = 0; i < j; i++)
      {
        Ball s = arrayOfBall[i];
        if (s.getColor() == prev) {
          s.setColor(BallWorld.this.box.getColorFilled());
        }
      }
      for (Ball s : BallWorld.this.CollisionBalls) {
        if (s.getColor() == prev) {
          s.setColor(BallWorld.this.box.getColorFilled());
        }
      }
      BallWorld.this.infoText.setBackground(BallWorld.this.box.getColorFilled());
      BallWorld.this.Levscore.setBackground(BallWorld.this.box.getColorFilled());
      BallWorld.this.scoreField.setBackground(BallWorld.this.box.getColorFilled());
    }
    
    public Dimension getPreferredSize()
    {
      return new Dimension(BallWorld.this.canvasWidth, BallWorld.this.canvasHeight);
    }
  }
}
