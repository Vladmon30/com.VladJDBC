import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;

public class DifferentMethods {
    /*
    next()--> move to the next row. If we put inside a loop we will scroll to the end of the list with records.
    getRow() --> to get current row index
    beforeFirst() --> moves us to the row 0, there is no information.(to get data from first row use next();)
    first() -->  move to the first row
    fast() -->  move to the last row
    previous() --> goes previous row
    absolute(rowNum) --> jump to specific row
     */
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
    public void jdbcAfterLast() throws SQLException {
        resultSet = statement.executeQuery("SELECT * FROM locations;");
        resultSet.next();
        String value = resultSet.getString("city");
        System.out.println(value);
// move out of the list of the records.
// if we try get the data we get an EXCEPTION
        resultSet.afterLast();
    }


    @Test
    public void jdbcLast() throws SQLException {
        resultSet = statement.executeQuery("SELECT * FROM locations;");
        resultSet.next();
        String value = resultSet.getString("city");
        System.out.println(value);

        while (resultSet.next())
        {
            System.out.println(resultSet.getRow() + ": "+ resultSet.getString("city"));
        }
//show last row
        resultSet.last();
        System.out.println("------------");
        System.out.println(resultSet.getString("city"));

    }

    @Test
    public void jdbcFirst() throws SQLException {
        resultSet = statement.executeQuery("SELECT * FROM locations;");
        resultSet.next();
        String value = resultSet.getString("city");
        System.out.println(value);

        while (resultSet.next())
        {
            System.out.println(resultSet.getRow() + ": "+ resultSet.getString("city"));
        }
//show first row
        resultSet.first();
        System.out.println("------------");
        System.out.println(resultSet.getString("city"));

    }

    @Test
    public void jdbcAbsolute() throws SQLException {
        resultSet = statement.executeQuery("SELECT * FROM locations;");
        resultSet.next();
        String value = resultSet.getString("city");
        while (resultSet.next())
        {
            System.out.println(resultSet.getRow() + ": "+ resultSet.getString("city"));
        }
//show name of row by index "Toronto"
        resultSet.absolute(9);
        System.out.println("--------");
        System.out.println(resultSet.getString("city"));

        String actualCity = resultSet.getString("city");
//verify that expected city equals actual city
        Assert.assertEquals("Toronto", actualCity);
    }





    @After
    public void tearDown() throws SQLException
    {
        connection.close();
 }
 }


