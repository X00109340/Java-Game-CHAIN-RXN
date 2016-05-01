package jpanels;

import game.Ball;
import game.BallWorld;
import game.ContainerBox;
import game.Main;
import gui.RandomColour;
import jFrames.HighScoresJframe;

import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import sound.Sound;

public class MainScreen
  extends JPanel
{
  private static int MAX_BALLS = 6;
  private Ball[] balls;
  private ContainerBox box;
  private DrawCanvas canvas;
  private int canvasWidth;
  private int canvasHeight;
  private Graphics2D g2d;
  private Thread gameThread = new Thread();
  private JButton startButton;
  private JLabel username;
  private String name;
  
  public MainScreen(String nameIN)
  {
	  
	setToolTipText("Left Mouse Click to Start Game. \nRight Mouse Click to view High Score Table.");
    setCursor(Cursor.getPredefinedCursor(12));
    Sound.INTRO.loop();
    name = nameIN;
    
	  
    setPreferredSize(new Dimension(730, 493));
    setSize(new Dimension(730, 493));
    int radius = 10;
    this.balls = new Ball[MAX_BALLS];
    this.canvasWidth = 730;
    this.canvasHeight = 493;
    
    this.balls[0] = new Ball(new Random().nextInt(this.canvasWidth - radius * 2 - 20) + radius + 10, new Random().nextInt(this.canvasHeight - radius * 2 - 20) + radius + 10, radius, 3.0F, new Random().nextInt(360), Color.RED);
    this.balls[1] = new Ball(new Random().nextInt(this.canvasWidth - radius * 2 - 20) + radius + 10, new Random().nextInt(this.canvasHeight - radius * 2 - 20) + radius + 10, radius, 3.0F, new Random().nextInt(360), Color.BLUE);
    this.balls[2] = new Ball(new Random().nextInt(this.canvasWidth - radius * 2 - 20) + radius + 10, new Random().nextInt(this.canvasHeight - radius * 2 - 20) + radius + 10, radius, 3.0F, new Random().nextInt(360), Color.GREEN);
    this.balls[3] = new Ball(new Random().nextInt(this.canvasWidth - radius * 2 - 20) + radius + 10, new Random().nextInt(this.canvasHeight - radius * 2 - 20) + radius + 10, radius, 3.0F, new Random().nextInt(360), Color.YELLOW);
    this.balls[4] = new Ball(new Random().nextInt(this.canvasWidth - radius * 2 - 20) + radius + 10, new Random().nextInt(this.canvasHeight - radius * 2 - 20) + radius + 10, radius, 3.0F, new Random().nextInt(360), Color.PINK);
    this.balls[5] = new Ball(new Random().nextInt(this.canvasWidth - radius * 2 - 20) + radius + 10, new Random().nextInt(this.canvasHeight - radius * 2 - 20) + radius + 10, radius, 3.0F, new Random().nextInt(360), Color.CYAN);
    
    this.box = new ContainerBox(0, 0, this.canvasWidth, this.canvasHeight, Color.BLACK, Color.WHITE);
    
    this.canvas = new DrawCanvas();
    setLayout(new BorderLayout());
    add(this.canvas, "Center");
    
    addComponentListener(new ComponentAdapter()
    {
      public void componentResized(ComponentEvent e)
      {
        Component c = (Component)e.getSource();
        Dimension dim = c.getSize();
        MainScreen.this.canvasWidth = dim.width;
        MainScreen.this.canvasHeight = dim.height;
        
        MainScreen.this.box.set(0, 0, MainScreen.this.canvasWidth, MainScreen.this.canvasHeight);
      }
    });
    addMouseListener(new MouseAdapter()
    {
      public void mousePressed(MouseEvent e)
      {
        if (e.getButton() == 1)
        {
          JPanel panel = MainScreen.this.getPanel();
          panel.removeAll();
          panel.setLayout(new BorderLayout());
          panel.add(new PlayLevel(1, 0, name), "Center");
          panel.updateUI();
          MainScreen.this.gameThread.stop();
        }
        else if (e.getButton() == 3)
        {
        	
          
      
          HighScoresJframe hscore = new HighScoresJframe(0, name);
          hscore.setVisible(true);
            
        	
        }
      }
    });
    gameStart();

  }
  
  private JPanel getPanel()
  {
    return this;
  }
  
  public void gameStart()
  {
    this.gameThread = new Thread()
    {
      public void run()
      {
        for (;;)
        {
          MainScreen.this.gameUpdate();
          try
          {
            Thread.sleep(16L);
          }
          catch (InterruptedException localInterruptedException) {}
          MainScreen.this.repaint();
        }
      }
    };
    this.gameThread.start();
  }
  
  
  public void gameUpdate()
  {
    for (int i = 0; i < this.balls.length; i++)
    {
      this.balls[i].bounceBall(this.box);
      
    }
  }
  
  class DrawCanvas
    extends JPanel
  {
    DrawCanvas() {}
    
    public void paintComponent(Graphics g)
    {
      super.paintComponent(g);
      MainScreen.this.g2d = ((Graphics2D)g);
      MainScreen.this.g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
      MainScreen.this.g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      MainScreen.this.g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
      
      MainScreen.this.box.draw(MainScreen.this.g2d);
      for (int i = 0; i < MainScreen.this.balls.length; i++) {
        if (MainScreen.this.balls[i].getColor() != Color.BLACK) {
          MainScreen.this.balls[i].draw(MainScreen.this.g2d);
        }
      }
      MainScreen.this.g2d.setColor(Color.WHITE);
      MainScreen.this.g2d.setFont(new Font("Courier New", 0, 12));
      
      drawTitle();
    }
    
    private void drawTitle()
    {
      MainScreen.this.g2d.setFont(Main.customFont);
      RandomColour c = new RandomColour();
      int size = 52;
      
      MainScreen.this.g2d.setColor(new Color(c.getR(), c.getG(), c.getB()));
      MainScreen.this.g2d.drawString("CHAIN", 7 * size, 2 * size);
      
      MainScreen.this.g2d.drawString("REACTION", 7 * size, 2 * size + 100);
      
      MainScreen.this.g2d.setColor(Color.WHITE);
      MainScreen.this.g2d.setFont(new Font("Arial Unicode MS ", 1, 20));
      MainScreen.this.g2d.drawString("Start a chain reaction to", 7 * size, 6 * size);
      MainScreen.this.g2d.drawString("explode as many atoms as possible!", 7 * size, 6 * size + 20);
      
      MainScreen.this.g2d.setColor(Color.RED);
      MainScreen.this.g2d.setFont(new Font("Arial Unicode MS ", 1, 25));
      MainScreen.this.g2d.drawString("Click to Start", 7 * size, 8 * size + 30);
      

    }
    
   
    
    public Dimension getPreferredSize()
    {
      return new Dimension(MainScreen.this.canvasWidth, MainScreen.this.canvasHeight);
    }
  }
  

  
  public String getName()
  {
	  return name;
	  
  }
}

