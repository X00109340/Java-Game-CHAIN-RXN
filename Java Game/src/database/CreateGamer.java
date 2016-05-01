package database;

import oracle.jdbc.pool.OracleDataSource;
import java.math.BigDecimal;
import java.sql.*;

/**
 * Created by Shajun on 13/04/2016.
 */
public class CreateGamer {
    private Connection conn = null;
    private Statement stmt = null;
    private PreparedStatement pstmt = null;
    private ResultSet rset;

    public CreateGamer(Connection conn)
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

            //ods.setURL("jdbc:oracle:thin:@//10.10.2.7:1521/global1");
            //ods.setUser("x00115098");
            //ods.setPassword("db17Dec88");

            conn = ods.getConnection();
            //System.out.println("Connected to " + ods.getUser() + "'s database");
        }
        catch (Exception e)
        {
            System.out.println("CreateGamer -- Unable to load driver " + e);
            System.exit(1);
        }
        return conn;
    }

    public void dropGamerTable()
    {
        System.out.println("\nChecking for existing Gamer table");

        try
        {
            stmt = conn.createStatement();
            try
            {

                stmt.execute("DROP TABLE Gamer");
                System.out.println("\nGamer Table dropped");
	            System.out.println("*****************************************************************");

            }

            catch (SQLException ex)
            {
                // No output
            }
        }
        catch (SQLException ex)
        {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    public void createGamerTable()
    {
        try
        {

            String createTable = "CREATE TABLE Gamer " +
                    "(Gamer_Name VARCHAR2(20) PRIMARY KEY," +
                    " Gamer_Password VARCHAR2(20), " +
                    " Gamer_Score NUMBER)";

            stmt = conn.createStatement();
            stmt.executeUpdate(createTable);
            System.out.println("\nGamer Table created");
            System.out.println("*****************************************************************");



            String insertStatement = "INSERT INTO GAMER(Gamer_Name, Gamer_Password, Gamer_Score) " +
                    "VALUES(?,?,0)";
            pstmt = conn.prepareStatement(insertStatement);

            pstmt.setString(1, "Shajun");
            pstmt.setString(2, "12345");


            pstmt.execute();



        }

        catch (SQLException xe)
        {
            System.out.println("Error: " + xe.getMessage());
            System.exit(1);
        }
    }

    public void showDB()
    {
        try
        {
            //String showAll = "SELECT * FROM GAMER ORDER BY BILL_ID ASC";
            String showAll = "SELECT * FROM GAMER";
            stmt = conn.createStatement();
            rset = stmt.executeQuery(showAll);
            System.out.println("\n-- Gamer TABLE --");
            while(rset.next())
            {
                System.out.println("HIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII");


                System.out.println(
                        rset.getString(1) + "\t" +
                                rset.getString(2)+ "\t" +
                                rset.getInt(3));
            }
            System.out.println("*****************************************************************");

        }
        catch (SQLException e)
        {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void closeDB()
    {
        try
        {
            stmt.close();
            rset.close();
            conn.close();
            System.out.println("\nConnection closed");
        }
        catch (SQLException e)
        {
            System.out.println("Could not close connection ");
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {


    }
}



