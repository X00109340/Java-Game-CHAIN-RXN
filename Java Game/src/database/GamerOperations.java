package database;

import oracle.jdbc.pool.OracleDataSource;

import java.math.BigDecimal;
import java.sql.*;

/**
 * Created by Shajun on 13/04/2016.
 */
public class GamerOperations {
    private Connection conn;
    private Statement stmt;
    private PreparedStatement pstm;
    private ResultSet rset;

    public GamerOperations(Connection conn)
    {
        this.conn = conn;
    }

    public Connection openDB()
    {
        try
        {
            OracleDataSource ods = new OracleDataSource();

            // Shajun's connection settings - home
            //ods.setURL("jdbc:oracle:thin:@localhost:1521:XE");
            //ods.setUser("system");
            //ods.setPassword("system");

            // Shajun's connection settings - College
            //ods.setURL("jdbc:oracle:thin:@//10.10.2.7:1521/global1");
            //ods.setUser("x00109340");
            //ods.setPassword("db25Sep95");

            conn = ods.getConnection();
            //System.out.println("Connected to " + ods.getUser() + "'s database");
        }
        catch (Exception e)
        {
            System.out.println("Unable to load driver " + e);
            System.exit(1);
        }
        return conn;
    }

    public void closeDB() {
        try
        {
            conn.close();
            System.out.println("Connection Closed");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    // Add BILL
    public void addGamer(String gamerName, String gamerPassword)
    {
        try
        {
            String insertStatement = "INSERT INTO Gamer(Gamer_Name, Gamer_Password, Gamer_Score) " +
                    "VALUES(?,?,0)";


            pstm = conn.prepareStatement(insertStatement);

            pstm.setString(1, gamerName);
            pstm.setString(2, gamerPassword);

            pstm.executeUpdate();
        }
        catch(SQLException e1)
        {
            System.out.println(e1);
        }
    }

    public ResultSet getGamers()
    {
        try
        {
            String getAll = "SELECT * FROM Gamer";

            stmt = conn.createStatement();
            rset = stmt.executeQuery(getAll);
        }
        catch (SQLException e)
        {
            System.out.println(e);
        }
        return rset;
    }


    public void updateScore(String gamerNameIN, String scoreIN)
    {
        try{
            String sql = "UPDATE GAMER SET Gamer_Score = " + "'" + scoreIN + "'" +
                    "where Gamer_Name=" + "'" + gamerNameIN + "'";
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);

        } catch (Exception e) {
            System.out.println("Problem " + e);
        }
    }


   
}


