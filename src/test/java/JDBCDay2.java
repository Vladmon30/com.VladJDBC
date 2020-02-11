import org.junit.*;
import utility.DBUtility;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCDay2 {

    String name = "Polina";
    String lastname = "Malina";

    @Before
    public void setUp() {

// when we working with more than one DataBase in our framework, we need specify driver type before connecting to DB
// to make sure we are using correct DB driver for JDBC
// make sure your urlis valid. It means you have JDBC attributes, not only host name
        String url = "jdbc:postgresql://vladrdspostgresql.cttqcjkqsneq.us-east-2.rds.amazonaws.com:5432/hr";
        String user = "vladmon";
        String password = "Kawasaki35";
        DBUtility.createConnection(url, user, password);
    }

    @Ignore
    @Test
// In the table employees and column email update email where email_id =100 (email = "sking" to "vlad@cybertekschool.com")
    public void test1() {
// update client with client id 100 and change his emai
        String query = "UPDATE employees SET email = 'vlad@cybertekschool.com' WHERE employee_id=100;";
        DBUtility.executeQuery(query);
    }
//==============================================================================================
//    @Test
//    //create a user with all values in employees table
//    public void test2(){
//   // --INSERT INTO employees VALUES(222,'Denys', 'Monchanov','Denymon10@gmail.com', '224.508.5027', '2020-02-10 19:30:00', 'IT_PROG', 10000.00, 0.01,100,110);
//        String query = "INSERT INTO employees VALUES('120','Denys', 'Monchanov','Denymon10@gmail.com', '224.508.5027', '2020-02-10 19:30:00', 'IT_PROG', '10000.00', '0.01','100','110')";

    //    }
//============================================================================================
    @Test
    public void test2() {
// find employee that we added in pgAdmin4 based on first_name and last_name
        String query = "SELECT * FROM employees WHERE first_name = 'Denys' AND last_name = 'Monchanov';";
        System.out.println(DBUtility.getQueryResultMap(query));
    }


//**********************************************: TASK :************************************************************
/*
    Task: create 2 test-->
    first one will create a user in employees table
    second test will verify that user is exist
 */
        @Ignore   // run it ones because its already exist it will show ERROR!!! So wwe ignore it for now
        @Test
        public void createAUser() {
//first one will create a user in employees table:  create a user in employees table maryna
// this is our query to add new employee to the employee table with the parameters "name = Polina last_name = Maliina"
        String query = "INSERT INTO employees (first_name, last_name, email, phone_number, hire_date, job_id, salary, commission_pct, manager_id, department_id)\n" +
                        "VALUES ('"+name+"', '"+lastname+"', 'polina@gmail.com', '224.508.5027', '2020-02-10 19:30:00', 'IT_PROG', 10000.00, 0.01, 100, 110);";
// to execute our caommand
        DBUtility.executeQuery(query);
    }


// second test will verify that user is exist
        @Test
        public void verifyUserExist(){
// to verify that user with name and lastname exist
            Assert.assertTrue(DBUtility.verifyEmployeeExist(name, lastname));

        }

//************************************************* : TASK END : *************************************************************

    @After
    public void tearDown() {
        DBUtility.disconect();
    }


}


