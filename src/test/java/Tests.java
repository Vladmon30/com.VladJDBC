import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;

public class Tests {

    String url = "jdbc:postgresql://room-reservation-qa.cxvqfpt4mc2y.us-east-1.rds.amazonaws.com:5432/hr";
    String username = "hr";
    String password = "hr";
    Connection connection;
    Statement statement;
    ResultSet resultSet;

    @Before
    public void setUp() throws SQLException {
        connection = DriverManager.getConnection(url, username, password);
        statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
    }

    @Test
    public void veruf107Employees() throws SQLException {

// Task verify that there are 107 employees:
        resultSet = statement.executeQuery("SELECT * FROM employees;");
// we need to go to the last row
        resultSet.last();

        int ActualNumOfRows = resultSet.getRow();
        int ExpectedNumOfRow = 107;
        Assert.assertEquals(ExpectedNumOfRow, ActualNumOfRows);
    }

    @After
    public void tearDown() throws SQLException { connection.close(); }
}
