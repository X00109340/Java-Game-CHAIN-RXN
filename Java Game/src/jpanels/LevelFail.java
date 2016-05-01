package jpanels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LevelFail
  extends JPanel
{
  private JTextField textField;
  private JTextField textField_1;
  private JLabel lblMoreBalls;
  
  public LevelFail(final int level, final int score, String text, String name)
  {
    setToolTipText("Click here to retry the level again");
    setBackground(Color.LIGHT_GRAY);
    setPreferredSize(new Dimension(730, 493));
    setMinimumSize(new Dimension(730, 493));
    setCursor(Cursor.getPredefinedCursor(12));
    setLayout(null);
    
    this.textField = new JTextField();
    this.textField.setCursor(Cursor.getPredefinedCursor(0));
    this.textField.setBackground(Color.DARK_GRAY);
    this.textField.setBounds(-11, 0, 751, 171);
    add(this.textField);
    this.textField.setColumns(10);
    
    this.textField_1 = new JTextField();
    this.textField_1.setCursor(Cursor.getPredefinedCursor(0));
    this.textField_1.setColumns(10);
    this.textField_1.setBackground(Color.DARK_GRAY);
    this.textField_1.setBounds(-11, 345, 751, 159);
    add(this.textField_1);
    
    JLabel lblNewLabel = new JLabel("");
    lblNewLabel.setIcon(new ImageIcon(LevelFail.class.getResource("/gui/fail.png")));
    lblNewLabel.setBounds(107, 201, 128, 119);
    add(lblNewLabel);
    
    this.lblMoreBalls = new JLabel(text);
    this.lblMoreBalls.setFont(new Font("Arial Unicode MS", 0, 32));
    this.lblMoreBalls.setBounds(282, 236, 346, 54);
    add(this.lblMoreBalls);
    
    addMouseListener(new MouseAdapter()
    {
      public void mousePressed(MouseEvent e)
      {
        JPanel panel = LevelFail.this.getPanel();
        panel.removeAll();
        panel.setLayout(new BorderLayout());
        panel.add(new PlayLevel(level, score, name), "Center");
        panel.updateUI();
      }
    });
  }
  
  private JPanel getPanel()
  {
    return this;
  }
}
