import org.junit.Test;

import java.sql.*;

public class JDBCDay1
{
/*
    host = room-reservation-qa.cxvqfpt4mc2y.us-east-1.rds.amazonaws.com
	database = hr
	user = hr
    password = hr
    port =5432 (default for postgree)

 String url = "jdbc:postgresql://host:port/database";
    String username = "hr";
    String password = "hr";

 */             //"jdbc:postgresql://host:port/database"
    String url = "jdbc:postgresql://room-reservation-qa.cxvqfpt4mc2y.us-east-1.rds.amazonaws.com:5432/hr";
    String username = "hr";
    String password = "hr";

    @Test
    public void jdbcTest1() throws SQLException
    {

// connecting to the db from intellij with java code and JDBC API
        Connection connection = DriverManager.getConnection(url, username, password);
// create statement
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
// now we gonna run a query, so we need create result set
        ResultSet resultSet = statement.executeQuery("SELECT * FROM employees;");
// we need to skip first record, beccause it start from 0
        resultSet.next();
// we getting the first record based on the column name
        String value = resultSet.getString("first_name");
//just to output result into terminal
        System.out.println(value);
//to close stream of a data

        while(resultSet.next())
        {
            System.out.println(resultSet.getString("first_name"));
        }
        connection.close();
}


@Test
public void jdbcTest2() throws SQLException
{
    Connection connection = DriverManager.getConnection(url, username, password);
    Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
    ResultSet resultSet = statement.executeQuery("SELECT city FROM locations;");

    resultSet.next();
    String value = resultSet.getString("city");
    System.out.println(value);
// next() --> returns boolean, we can get value based on the column name
    while (resultSet.next())
    {
        System.out.println(resultSet.getRow() + ": "+ resultSet.getString("city"));
    }
//go to first row
    resultSet.first();
    System.out.println("------------");
    System.out.println(resultSet.getRow());


    connection.close();
}
}


