package sourcecode;
/**
 * Created by maybra01 on 7/28/2016.
 */

import java.sql.*;

public class DataManager {


    //All connection related variables
    private String jdbcDriver = "jdbc:oracle:thin:@";
    private String dbURL = "tst1124";
    private String userID="hr9";
    private String userPass="hr9";
    private Connection dbConnection = null;
    private Statement sqlstatement = null;
    private PreparedStatement preparedSQLStatement = null;

    public DataManager() {
    }

    public void makeConnection(){

        System.setProperty("oracle.net.tns_admin", "C:\\oracle\\product\\11.2.0\\client_1\\network\\admin");
        /*This line points this project to look in a particular directory for an Oracle file called tnsnames.ora which
        contains the admin login details for the database stored at CSG
         */

        try{
            System.out.println("Attempting connection to database at: " + dbURL );
            dbConnection = DriverManager.getConnection( jdbcDriver + dbURL, userID, userPass);
            /*
                DriverManager class manages jdbc drivers form different vendors
                The jdbc:oracle:thin:tst1124 part of the getConnection method uses the oracle
                thin driver (a platform independent driver written purely in Java which allows direct access to an Oracle database)
            */
            System.out.println("Connection established...");
        }
        catch(Exception ex) {
            System.out.println("Failed to connect to database " + ex.getMessage());
        }
    }

    public ResultSet createTable(){

        ResultSet rs = null;
        DatabaseMetaData metadata = null;

        try {

            sqlstatement = dbConnection.createStatement();

            //System.out.println("Dropping table...");
            //String sql = "DROP TABLE TESTTABLE";
           // sqlstatement.executeQuery(sql);

            System.out.println("Creating table...");
            String sql = "CREATE TABLE TESTTABLE("
            + "PERSON_NAME VARCHAR2(25), "
            + "SURNAME VARCHAR2(25) "
            + ")";

            sqlstatement.executeQuery(sql);
            metadata = dbConnection.getMetaData();
            rs = metadata.getTables(null, null, "TESTTABLE",null);

            System.out.println("Table created successfully!");

        }catch(Exception ex){
            System.out.println("Create table failed: " + ex.getMessage());
        }

        return rs;
    }

    public int insertRecord(){
        int rowsInserted = 0;
        ResultSet rs = null;
        try {
            System.out.println("Inserting Record into table...");
            sqlstatement = dbConnection.createStatement();
            String sql = "INSERT INTO TESTTABLE("
                    + "PERSON_NAME , "
                    + "SURNAME) "
                    + "VALUES ('Brandon', 'May')";

            sqlstatement.executeQuery(sql);

            sql = "INSERT INTO TESTTABLE("
                    + "PERSON_NAME , "
                    + "SURNAME) "
                    + "VALUES ('Luke', 'Kramer')";

            sqlstatement.executeQuery(sql);

            sql = "SELECT count(*) as total "
                    + "FROM TESTTABLE ";

            rs =  sqlstatement.executeQuery(sql);
            rs.next();
            rowsInserted= rs.getInt("total");

            if(rowsInserted>1)
                System.out.println(rowsInserted + " Records inserted successfully!");
                else
            System.out.println(rowsInserted + " Record inserted successfully!");

        }catch(Exception ex){
            System.out.println("Record insertion failed: " + ex.getMessage());
        }

        return rowsInserted;
    }

    public int updateRecord(){
        int rowsUpdated = 0;
        ResultSet rs = null;
        try {

            System.out.println("Updating Record in table... (Changing Brandon's Surname to June");

            preparedSQLStatement = dbConnection.prepareStatement("UPDATE TESTTABLE SET SURNAME = ? WHERE PERSON_NAME = ?");
            preparedSQLStatement.setString(1,"June");
            preparedSQLStatement.setString(2,"Brandon");

            rowsUpdated = preparedSQLStatement.executeUpdate();

            System.out.println(rowsUpdated + " Record updated successfully!");


        }catch(Exception ex){
            System.out.println("Record update failed: " + ex.getMessage());
        }

        return rowsUpdated;
    }

    public int deleteAllRecords(){
        int rowsLeft = 0;
        ResultSet rs = null;
        try {
            sqlstatement = dbConnection.createStatement();


            String sql = "SELECT count(*) as total "
                    + "FROM TESTTABLE ";
            rs =  sqlstatement.executeQuery(sql);
            rs.next();
            rowsLeft= rs.getInt("total");


            System.out.println("Total records in table before delete: " + rowsLeft);

            sql = "DELETE FROM TESTTABLE";
            sqlstatement.executeQuery(sql);

            sql = "SELECT count(*) as total "
                    + "FROM TESTTABLE ";
            rs =  sqlstatement.executeQuery(sql);
            rs.next();
            rowsLeft= rs.getInt("total");

            System.out.println("Total records in table after delete: " + rowsLeft);

            System.out.println("All records deleted successfully!");

        }catch(Exception ex){
            System.out.println("Record Delete failed: " + ex.getMessage());
        }

        return rowsLeft;


    }

    public ResultSet selectFromDatabase(){

        ResultSet rs = null;
        try {

            sqlstatement = dbConnection.createStatement();
            String sql = "SELECT * FROM TESTTABLE";

            rs = sqlstatement.executeQuery(sql);
            System.out.println("\n");
            System.out.println("Records");
            System.out.println("--------------------------------------------");
            while(rs.next()){
                String name = rs.getString(1);
                String surname = rs.getString(2);
                System.out.println("First name = " + name);
                System.out.println("Last name = " + surname);

            }
            System.out.println("--------------------------------------------");
        }catch(Exception ex){
            System.out.println("CRUD Test failed: " + ex.getMessage());
        }

        return rs;
    }

    public ResultSet dropTable(){
        ResultSet rs = null;
        DatabaseMetaData metadata = null;

        try {

            sqlstatement = dbConnection.createStatement();

            System.out.println("Dropping table...");
            String sql = "DROP TABLE TESTTABLE";
            sqlstatement.executeQuery(sql);


            metadata = dbConnection.getMetaData();
            rs = metadata.getTables(null, null, "TESTTABLE",null);

            System.out.println("Table dropped successfully!");

        }catch(Exception ex){
            System.out.println("Drop table failed: " + ex.getMessage());
        }

        return rs;



    }

}
