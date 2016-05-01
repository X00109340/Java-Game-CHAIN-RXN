package jFrames;

import java.awt.BorderLayout;
import net.proteanit.sql.*;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import game.Main;
import jpanels.LoginPanel;
import jpanels.MainScreen;

import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.BevelBorder;
import java.awt.Cursor;
import javax.swing.JTextField;

public class HighScoresJframe extends JFrame {

	
	private static HighScoresJframe obj = null;
	private JPanel contentPane;
	private JTable table;
	
	private String name;
	
	Connection conn = null;
	ResultSet rset = null;
	PreparedStatement pstmt = null;
	private JTextField textField;


	

	/**
	 * Create the frame.
	 */
	public HighScoresJframe(int scoreIN, String nameIN) 
	{
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 501, 436);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(135, 206, 250));
		contentPane.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(255, 200, 0), new Color(255, 0, 0), new Color(255, 200, 0), Color.RED));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
		conn = Main.getConnection();
		name = nameIN;
		
		

		
		table = new JTable();
		table.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
		table.setEnabled(false);
		table.setBounds(10, 35, 473, 202);
		contentPane.add(table);
		
		updateTable();
		
		JButton allHighScore = new JButton("Display All");
		allHighScore.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		allHighScore.setFont(new Font("Courier New", Font.BOLD, 13));
		allHighScore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateTable();
			}
		});
		allHighScore.setBounds(10, 352, 133, 37);
		contentPane.add(allHighScore);
		
		JButton myHighScore = new JButton("My Highscore");
		myHighScore.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		myHighScore.setFont(new Font("Courier New", Font.BOLD, 13));
		myHighScore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getMyScore();
			}
		});
		myHighScore.setBounds(180, 352, 133, 37);
		contentPane.add(myHighScore);
		
		JButton exitButton = new JButton("Exit");
		exitButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		exitButton.setFont(new Font("Courier New", Font.BOLD, 13));
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		exitButton.setBounds(344, 352, 121, 37);
		contentPane.add(exitButton);
		
		JLabel lblGamerName = new JLabel("Gamer Name");
		lblGamerName.setFont(new Font("Courier New", Font.BOLD, 13));
		lblGamerName.setBounds(67, 10, 93, 14);
		contentPane.add(lblGamerName);
		
		JLabel lblGamerScore = new JLabel("Gamer Score");
		lblGamerScore.setFont(new Font("Courier New", Font.BOLD, 13));
		lblGamerScore.setBounds(319, 10, 93, 14);
		contentPane.add(lblGamerScore);
		
		JLabel lblSearchGamer = new JLabel("Search Gamer");
		lblSearchGamer.setFont(new Font("Courier New", Font.BOLD, 13));
		lblSearchGamer.setBounds(22, 264, 121, 14);
		contentPane.add(lblSearchGamer);
		
		JLabel lblGamerName_1 = new JLabel("Gamer Name:");
		lblGamerName_1.setFont(new Font("Courier New", Font.BOLD, 13));
		lblGamerName_1.setBounds(22, 299, 98, 14);
		contentPane.add(lblGamerName_1);
		
		textField = new JTextField();
		textField.setFont(new Font("Courier New", Font.BOLD, 13));
		textField.setBounds(180, 289, 133, 36);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton searchButton = new JButton("Search");
		searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		searchButton.setFont(new Font("Courier New", Font.BOLD, 13));
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				searchGamerName(textField.getText().trim());
			}
		});
		searchButton.setBounds(344, 288, 121, 37);
		contentPane.add(searchButton);
	}
	
	private void updateTable()
	{
		try
		{
		String sql = "SELECT Gamer_name, Gamer_score FROM GAMER ORDER BY GAMER_SCORE DESC";
		pstmt = conn.prepareStatement(sql);
		rset = pstmt.executeQuery(sql);
		table.setModel(DbUtils.resultSetToTableModel(rset));
		
		
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	private void getMyScore()
	{
		try
		{
		String sql = "SELECT Gamer_Name, Gamer_Score FROM GAMER WHERE Gamer_Name =" + "'" + name + "'";
		pstmt = conn.prepareStatement(sql);
		//pstmt = conn.prepareStatement(sql);
		rset = pstmt.executeQuery(sql);
		table.setModel(DbUtils.resultSetToTableModel(rset));
		
		
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	private void searchGamerName(String nameIN)
	{
		try
		{
		String sql = "SELECT Gamer_Name, Gamer_Score FROM GAMER WHERE Gamer_Name =" + "'" + nameIN + "'";
		pstmt = conn.prepareStatement(sql);
		rset = pstmt.executeQuery(sql);
		
		table.setModel(DbUtils.resultSetToTableModel(rset));
    	textField.setText("");

        if(!pstmt.executeQuery(sql).next())
        {
        	JOptionPane.showMessageDialog(null, "Gamer Name Not Found", "Error", JOptionPane.ERROR_MESSAGE);
        	textField.setText("");
        }

        
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	
}













