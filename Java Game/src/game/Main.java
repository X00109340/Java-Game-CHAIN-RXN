package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import database.CreateGamer;
import jpanels.LoginPanel;
import jpanels.MainScreen;
import jpanels.PlayLevel;
import oracle.jdbc.pool.OracleDataSource;

public class Main
{
  public static int level;
  public static int score;
  public static Font customFont;
  
  
  //***************************************************************************************
  //For connecting to database
  final static int LOCATION = 0;   // 0 = college, 1 = home
  final static int PERSON = 0;     // 0 = Shajun
  static Connection conn = null;
  
  //***************************************************************************************
  
  public static void main(String[] args)
  {
    SwingUtilities.invokeLater(new Runnable()
    {
      public void run()
      {
    	  try {
   			UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
   		} catch (ClassNotFoundException | InstantiationException| IllegalAccessException | UnsupportedLookAndFeelException e) {
   			e.printStackTrace();
   		}
    	  
			Connection conn = getConnection();
    	    
    	    
            //dropAllTables(conn);


            CreateGamerTable(conn);

            //GamerOperations go = new GamerOperations(conn);
            
            //go.addGamer("John", "1234");

    		

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        final JFrame frame = new JFrame("- -CLICKIR- -");
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/images/chain.png")));
        
        frame.setFont(new Font("SketchFlow Print", 1, 17));
        
        //Setting frame location according to dimensions specified above
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
        frame.setBackground(Color.BLUE);
        frame.setResizable(true);
        frame.setMinimumSize(new Dimension(740, 525));
        frame.setDefaultCloseOperation(3);
        frame.setResizable(false);
        frame.setContentPane(new LoginPanel());
        

        
        JMenuBar menuBar = new JMenuBar();
        menuBar.setPreferredSize(new Dimension(0, 0));
        menuBar.setMinimumSize(new Dimension(0, 0));
        menuBar.setMaximumSize(new Dimension(0, 0));
        menuBar.setBorder(null);
        menuBar.setBorderPainted(false);
        menuBar.setFocusable(false);
        menuBar.setOpaque(false);
        menuBar.setRequestFocusEnabled(false);
        menuBar.setVerifyInputWhenFocusTarget(false);
        menuBar.setEnabled(true);
        menuBar.setForeground(SystemColor.menu);
        frame.setJMenuBar(menuBar);
        
        JMenu menu = new JMenu("");
        menuBar.add(menu);
        
        JMenuItem secret = new JMenuItem("");
        JMenuItem quit = new JMenuItem("");
        
        secret.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
            Main.score = 0;
            int level1 = Integer.parseInt(JOptionPane.showInputDialog("Please enter a level to check \nFrom level 1 - 12"));
            if (level1 <= 12) {
              frame.setContentPane(new PlayLevel(level1, 0, "Test"));
            } else {
            	
            }
            frame.revalidate();
            frame.repaint();
          }
        });
        
        quit.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
            if ((Main.level < 13) && (Main.score > 0))
            {
//              frame.setContentPane(new HighScorePanel(Main.score, 0, Main.level));
//              frame.revalidate();
//              frame.repaint();
               // frame.setContentPane(new MainScreen(null));

            }
          }
        });
        menu.add(secret);
        menu.add(quit);
        
        
        secret.setAccelerator(KeyStroke.getKeyStroke(83, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        quit.setAccelerator(KeyStroke.getKeyStroke(81, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        try
        {
          Main.customFont = Font.createFont(0, getClass().getResourceAsStream("/font/led.ttf")).deriveFont(95.0F);
          
          GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
          
          ge.registerFont(Font.createFont(0, getClass().getResourceAsStream("/font/led.ttf")));
        }
        catch (IOException e)
        {
          e.printStackTrace();
        }
        catch (FontFormatException e)
        {
          e.printStackTrace();
        }
      }
    });
  }
  
	public static Connection getConnection() {

	        String[] locationAry = {"jdbc:oracle:thin:@//10.10.2.7:1521/global1","jdbc:oracle:thin:@localhost:1521:XE"};
	        String[] connectionsHomeUsername =
	                {"system"};             // Shajun
	        String[] connectionsHomePassword =
	                {"db25Sep95"};             // Shajun


	        String[] connectionsCollegeUsername =
	                {"x00109340"};     // Shajun

	        String[] connectionsCollegePassword =
	                {"db25Sep95"};     // Shajun

	        try {
	            OracleDataSource ods = new OracleDataSource();

	            if(LOCATION == 1) {
	                ods.setURL(locationAry[LOCATION]);
	                ods.setUser(connectionsHomeUsername[PERSON]);
	                ods.setPassword(connectionsHomePassword[PERSON]);
	            }
	            else
	            {
	                ods.setURL(locationAry[LOCATION]);
	                ods.setUser(connectionsCollegeUsername[PERSON]);
	                ods.setPassword(connectionsCollegePassword[PERSON]);
	            }

	            conn = ods.getConnection();
	            //System.out.println("MAIN -- Connected to " + ods.getUser() + "'s database");

	        } catch (Exception e) {
	            System.out.println("Unable to load driver " + e);

	            System.exit(1);
	        }
	        return conn;
	    }
	 
		private static void CreateGamerTable(Connection conn)
	    {
	        CreateGamer cg = new CreateGamer(conn);
	        cg.dropGamerTable();
	        cg.createGamerTable();
	        cg.showDB();
	    }
	    
		private static void dropAllTables(Connection conn)
	    {
	        CreateGamer cg = new CreateGamer(conn);
	        cg.dropGamerTable();

	        
	    }
}
