import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;

public class MetadataTest {

    String url = "jdbc:postgresql://room-reservation-qa.cxvqfpt4mc2y.us-east-1.rds.amazonaws.com:5432/hr";
    String username = "hr";
    String password = "hr";
    Connection connection;
    Statement statement;
    ResultSet resultSet;

    @Before
    public void setUp() throws SQLException
    {
        connection = DriverManager.getConnection(url, username, password);
        statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
    }


@Test
public void jdbcMetaDataTest() throws SQLException {
//get metadata about DB
        DatabaseMetaData dbMetaData = connection.getMetaData();
        System.out.println("Username: " + dbMetaData.getUserName());
        System.out.println("URL: " + dbMetaData.getDatabaseProductName());

        String expecteddbType = "PostgreSQL";
        String actualdbType = dbMetaData.getDatabaseProductName();

        Assert.assertEquals(expecteddbType, actualdbType);

        System.out.println(actualdbType + " : version " + dbMetaData.getDatabaseProductVersion());
}

@Test
public void jdbcResSetMetaData() throws SQLException {
//get metadata about resultset --> metadata about our query

    ResultSet resultSet = statement.executeQuery("SELECT * FROM employees;");
    ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

// in employees table we have 11 columns
// get amount of the columns
    System.out.println(resultSetMetaData.getColumnCount());   //11

// give us the column name based on the index 2 column
    System.out.println(resultSetMetaData.getColumnName(2));   // first_name

}

@Test
// print all column names for this query : SELECT * FROM employees;
public void printAlQueriesName() throws SQLException {
        ResultSet resultSet =statement.executeQuery("SELECT * FROM employees;");
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

        for(int i=1; i<=resultSetMetaData.getColumnCount(); i++){
            System.out.println("Column name: " + resultSetMetaData.getColumnName(i));
    }
}
// to output data types and columns name of employees table
@Test
public void jdbcTest() throws SQLException {
//ResultSet --> returns data based on Query
            ResultSet resultSet = statement.executeQuery("SELECT * FROM employees;");
//ReseltSetMetaData --> returns data about data ( info about ResSet)
            ResultSetMetaData rsmd = resultSet.getMetaData();
//We need to know amount of our columns
            int numOfColumn = rsmd.getColumnCount();
// iterate it step by step to get column name and datatype
                for(int i=1; i<=numOfColumn; i++){
                    System.out.println("column type with index: " + i + rsmd.getColumnTypeName(i)+ ": " +rsmd.getColumnName(i));
    }

}

    @After
    public void tearDown() throws SQLException{ connection.close(); }

}
