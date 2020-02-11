package utility;

import org.junit.BeforeClass;
import org.testng.annotations.AfterClass;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBUtility {
    //to connect to DB
    private static Connection connection;
    //to execute query
    private static Statement statement;
    // to get actual data from DB
    private static ResultSet resultSet;

//-------------------------------------------------------------------------------------------
    public static void createConnection(String url, String user, String password) {
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//-------------------------------------------------------------------------------------------

    public static void disconect() {
        try {
            if (connection != null) {
                connection.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//-------------------------------------------------------------------------------------------


    public static void executeQuery(String query) {
        try {
//to create statement and allow scroll backand forward(TYPE_SCROLL_INSENSITIVE)
/// then from statement we create reselt set that will receive data based on query
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
//running query(command)
            resultSet = statement.executeQuery(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//-------------------------------------------------------------------------------------------

    public static int getRowCount() {
        int amountOfRows = 0;
        try {
//move to the last row
            resultSet.last();
// then this step return amount of rows
            amountOfRows = resultSet.getRow();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return amountOfRows;
    }

//-------------------------------------------------------------------------------------------

    public static List<String> getColumnNames(String query) {
        // create a list wich willhold columns name
        List<String> columns = new ArrayList<>();

        try {
//to send a command to DB
            executeQuery(query);

            ResultSetMetaData rsmd = resultSet.getMetaData();
//getting amount of columns
            int columnCount = rsmd.getColumnCount();
//to loop through column
            for(int i=1; i<=columnCount;i++){
//we are adding columns one by one based on the column index, starting from 1
            columns.add(rsmd.getColumnName(i));
        }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return columns;
    }

//-------------------------------------------------------------------------------------------

    public static List<String> executeQueryAndGetColumnValue(String query, String columnName) {
        executeQuery(query);

        List<String>values = new ArrayList<>();

        try {
//get meta data
            ResultSetMetaData rsmd = resultSet.getMetaData();
// we want to go through all rows one by one and get value of certain column
            while(resultSet.next()){
            values.add(resultSet.getString(columnName));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return values;
    }
//-------------------------------------------------------------------------------------------

public static boolean verifyEmployeeExist(String first_name, String last_name){
        boolean exist = false;
//Count(*)  --> returns count of records, if records doesnt exist it return 0
        String query = "SELECT COUNT (*) as count \n" +
                        "FROM employees\n" +
                        "WHERE first_name = '%s' and last_name = '%s';";
        query= String.format(query,first_name,last_name);
        executeQuery(query);
        try {
            resultSet.next();
            exist = resultSet.getInt(1) > 0;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return exist;
}

//-------------------------------------------------------------------------------------------
    /**
     *
     * @param query
     * @return returns query result in a list of maps where the list represents
     *         collection of rows and a map represents represent a single row with
     *         key being the column name
     */
    public static List<Map<String, Object>> getQueryResultMap(String query) {
        executeQuery(query);
        List<Map<String, Object>> rowList = new ArrayList<>();
        ResultSetMetaData rsmd;
        try {
            rsmd = resultSet.getMetaData();
            while (resultSet.next()) {
                Map<String, Object> colNameValueMap = new HashMap<>();
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    colNameValueMap.put(rsmd.getColumnName(i), resultSet.getObject(i));
                }
                rowList.add(colNameValueMap);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowList;
    }
}

//-------------------------------------------------------------------------------------------