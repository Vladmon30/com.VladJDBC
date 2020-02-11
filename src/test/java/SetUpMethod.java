import org.junit.*;

import java.sql.*;

public class SetUpMethod {

    String url = "jdbc:postgresql://room-reservation-qa.cxvqfpt4mc2y.us-east-1.rds.amazonaws.com:5432/hr";
    String username = "hr";
    String password = "hr";
    Connection connection;
    Statement statement;
    ResultSet resultSet;
//-----------------------------------------------------------------------
@Before
public void setUp() throws SQLException, ClassNotFoundException {

// when we working with more than one DataBase in our framework, we need specify driver type before connecting to DB
// to make sure we are using correct DB driver for JDBC
    Class.forName("org.postgresql.Driver");
    connection = DriverManager.getConnection(url, username, password);
    statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
//-----------------------------------------------------------------------
}

//----------------------------------------------------------------------------------
//TEST PART
    @Test
    public void jdbcTest1() throws SQLException
    {

// now we gonna run a query, so we need create result set
        resultSet = statement.executeQuery("SELECT * FROM employees;");
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
    }


    @Test
    public void jdbcTest2() throws SQLException
    {
        resultSet = statement.executeQuery("SELECT * FROM locations;");

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
    }


//---------------------------------------------------------------------------
    @After
    public void tearDown() throws SQLException
    {
        connection.close();
    }
//------------------------------------------------------------------------------

}
