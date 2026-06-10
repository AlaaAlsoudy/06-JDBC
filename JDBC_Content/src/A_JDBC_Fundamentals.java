//************************************************ JDBC Fundamentals ************************************************//
// JDBC is an API (Application Programming Interface) in Java that allows developers to connect and interact with databases.
// It provides a standard set of interfaces and classes for accessing and manipulating databases. 
// It works with many database types like: MySQL, PostgreSQL, Oracle, SQL Server, etc.
//^ JDBC Drivers vs. JDBC API:
// JDBC Drivers: 
    // These are specific implementations provided by database vendors that allow Java applications to connect to their databases. 
// JDBC API: 
    // This is the standard set of interfaces and classes defined in the java.sql package that provides a common way to interact with databases. 
    // The JDBC API is implemented by the JDBC drivers provided by database vendors.
//---------------------------------------------------------------------
//~ Benefits of JDBC:
//1. Connect to the DB
//2. Execute SQL queries (e.g., SELECT, INSERT, UPDATE, DELETE) to the specified database.
//3. Retrieve and process results from the database.
//---------------------------------------------------------------------
//~ The main components of JDBC include:
// 1. JDBC API: which provides a common set of interfaces and classes for database access with any DB type.
// 2. JDBC Driver Manager: manages the relation between your java app (with JDBC API) and the driver you use to connect to the database.
// 3. Connection: It represents a connection to a specific database and provides methods for executing SQL statements.
// 4. Statement: It is used to execute SQL queries and update statements against the database.
// 5. ResultSet: It represents the result set returned by a query.
// 6. SQLException: It is a checked exception that is thrown when an error occurs during database access.
//---------------------------------------------------------------------
//~  Steps to develop a JDBC application:
// 1. Load the JDBC driver (set of classes that allow you to connect to a specific database) for your database //^^ it's a jar file normally
// 2. Establish a connection to the database using the driver manager.
// 3. Create a statement object using the connection, statements like:
    // Statement statement = connection.createStatement(); 
        // -> this creates a statement object
        // -> when the SELECT statement is static statement.
    // PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE id = ?");  //! (?) is a placeholder for a parameter
        // -> this creates a prepared statement object, which allows you to execute parameterized queries.
        // -> when the SELECT statement is dynamic statement, and you want to execute it multiple times with different parameters.
        // -> we use (?) as a placeholder for a parameter, and then we set the value of the parameter using the setXXX(index of the ? placeholder, value) method. //! setInt(), setString(), setDate(), etc.
    // CallableStatement callableStatement = connection.prepareCall("{call stored_procedure_name()}"); 
        // -> this creates a callable statement object, which allows you to execute stored procedures in the database.
        // -> it can has different types of parameters:
            // IN parameters: parameters that are passed to the stored procedure when it is executed.
            // OUT parameters: parameters that are returned from the stored procedure when it is executed.
            // INOUT parameters: parameters that are both passed to and returned from the stored procedure.
        // -> it can also return a result set from the stored procedure.
// 4. Execute SQL queries or update statements using the statement object, execute methods like:
    // statement.executeUpdate(); -> this is used for executing SQL statements that modify the database (e.g., INSERT, UPDATE, DELETE).
        // return the number of rows affected by the execution of the SQL statement.
    // statement.executeQuery(); -> this is used for executing SQL statements that retrieve data from the database (e.g., SELECT).
        //^ while using prepared statement obj, we don't pass the SQL query as a parameter to the executeQuery() method, because the SQL query is already defined when we create the prepared statement object.
        // int rowCount = statement.executeUpdate("UPDATE users SET name = 'John Doe' WHERE id = 1");
    // statement.execute(statement); 
        // -> this is used for executing SQL statements that may return multiple results (e.g., SELECT, UPDATE, DELETE).
        // -> it returns a boolean value indicating whether the first result is a ResultSet object (true) or an update count (false).
        // ex: Boolean hasResultSet = statement.execute("SELECT * FROM users");
    // statement.executeBatch(); 
        // -> this is used for executing a batch of SQL statements.
        // -> it returns an array of update counts containing one element for each command in the batch, in the same order as the commands were added to the batch.
        // ex: int [] updateCounts = statement.executeBatch();
// 5. Process the result set returned by the query (if available).
// 6. Close the connection and statement objects when you're done.
//---------------------------------------------------------------------
//~ ResultSet Object that store the results from queries execution:
// ResultSet resultSet = statement.executeQuery(statement); 
// ->like virtual table, that stores the results from the query execution
// -> scrollable, updatable, holdable.
// -> provides connection methods to create statements with desired result set:
    // with Statement obj: resultSet = statement.executeQuery(int RSType, int RSConcurrency);
    // with PreparedStatement&CallableStatement obj: resultSet = preparedStatement.executeQuery(String SQL,int RSType, int RSConcurrency);
// -> ResultSet Types:
    // 1. TYPE_FORWARD_ONLY: allows you to move only forward through the result set (default).
    // 2. TYPE_SCROLL_INSENSITIVE: allows you to scroll both forward and backward through the result set, but it does not reflect changes made to the database after the result set was created.
    // 3. TYPE_SCROLL_SENSITIVE: allows you to scroll both forward and backward through the result set, and it reflects changes made to the database after the result set was created (not recommended).
// -> ResultSet Concurrency:
    // 1. CONCUR_READ_ONLY: allows you to read data from the result set but not modify it (default).
    // 2. CONCUR_UPDATABLE: allows you to read and modify data in the result set. //^ must be used with a scrollable result set (TYPE_SCROLL_INSENSITIVE or TYPE_SCROLL_SENSITIVE).
// -> ResultSet Categories:
    // 1. navigation methods: used to move the cursor around the result set 
        // (e.g., next(), previous(), first(), last(), absolute(int row), relative(int rows)).
        //^ not all of them are supported by all types of result sets, for example, TYPE_FORWARD_ONLY only supports next() method.
    // 2. get methods: used to retrieve data from the current row of the result set (e.g., getInt(), getString(), getDate(), etc.).
        // pass the column name or column index as a parameter.
    // 3. update methods: used to modify data in the result set (e.g., updateInt(), updateString(), updateDate(), etc.).
        //^ must be used with a scrollable and updatable result set (TYPE_SCROLL_INSENSITIVE or TYPE_SCROLL_SENSITIVE with CONCUR_UPDATABLE).
// -> ResultSet Metadata:
    // ResultSetMetaData resultSetMetaData = resultSet.getMetaData(); //! this returns a ResultSetMetaData object
    // provides information about the columns in the result set, such as their name, type, and length.
    // Has some methods like:
        // getColumnCount(); -> returns the number of columns in the result set.
        // getColumnName(int column); -> returns the name of the column at the specified index.
        // getColumnTypeName(int column); -> returns the name of the data type of the column at the specified index.
        // getPrecision(int column); -> returns the precision of the column at the specified index.
        // getScale(int column); -> returns the scale of the column at the specified index.
        // isAutoIncrement(int column); -> returns true if the column is an auto-increment column, false otherwise.
        // isNullable(int column); -> returns the nullability of the column at the specified index.
        // isCurrency(int column); -> returns true if the column is a currency column, false otherwise.
//---------------------------------------------------------------------
//~ JDBC Database Metadata:
// DatabaseMetaData databaseMetaData = connection.getMetaData(); //! this returns a DatabaseMetaData object
// provides information about the database, such as its name, version, and supported SQL features.
// Provides information about schemas, tables, columns, and indexes in the database.
// Has some methods like:
    // getDatabaseProductName(); -> returns the name of the database product (e.g., "Microsoft SQL Server").
    // getDatabaseProductVersion(); -> returns the version of the database product (e.g., "12.0.2000.8").
    // getDriverName(); -> returns the name of the JDBC driver (e.g., "com.microsoft.sqlserver.jdbc.SQLServerDriver").
    // getDriverVersion(); -> returns the version of the JDBC driver (e.g., "6.4.0.0").
    // getTables(String catalog, String schemaPattern, String tableNamePattern, String[] types);
        // -> returns a ResultSet object containing information about the tables in the database.
        // -> takes four parameters: catalog, schemaPattern, tableNamePattern, and types.
            // catalog: the name of the catalog to search in (optional).
            // schemaPattern: the name of the schema to search in (optional).
            // tableNamePattern: the name of the table to search for (optional).
            // types: an array of table types to search for (optional).
//&-----------------------------------------------------Example:-----------------------------------------------------

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

import classes.ContactDAO;
import classes.ContactPerson;
public class A_JDBC_Fundamentals {
    public static void main(String[] args) {
        String url = "jdbc:sqlserver://localhost:1433;databaseName=AddressBook;encrypt=true;trustServerCertificate=true;"; //!here "AddressBook" is the name of the database you want to connect to
        String username = "anonymous"; //! "anonymous" is the username for the database connection
        String password = "Anonymous@832003"; //! "Anonymous@832003" is the password for the database connection

        try {

            // 1. load the JDBC driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); //! loads the SQL JDBC driver class, which is necessary to establish a connection to the SQL database
            // 2. establish a connection
            Connection connection = DriverManager.getConnection(url, username, password); //! establishes a connection to the database using the specified URL for the database/server, username, and password
            //3. prepare / create a statement
            Statement statement = connection.createStatement(); //! creates a Statement object, which is used to execute SQL queries against the database
            //4. execute a query
            ResultSet resultSet ;
            
            System.out.println("----------------Using Statement and ResultSet----------------");
            resultSet = statement.executeQuery("SELECT * FROM Contact"); //! executes a query to retrieve all records from the "Contact" table
            ArrayList<ContactPerson> contacts = ContactDAO.getAllContacts(resultSet);
            ContactDAO.displayContacts(contacts);

            // trying prepared statement
            PreparedStatement preparedStatement = connection.prepareStatement("update Contact set name = ? where id = ?");
            preparedStatement.setString(1, "Ahmed Saleh");
            preparedStatement.setInt(2, 1);
            preparedStatement.executeUpdate();
            preparedStatement.setString (1, "Sara Waleed");
            preparedStatement.setInt(2, 2);
            preparedStatement.executeUpdate();

            System.out.println("----------------Using PreparedStatement----------------");
            resultSet = statement.executeQuery("SELECT * FROM Contact"); //! executes a query to retrieve all records from the "Contact" table
            contacts = ContactDAO.getAllContacts(resultSet);
            ContactDAO.displayContacts(contacts);

            // trying callable statement
            CallableStatement callableStatement = connection.prepareCall("{call GetContactById(?)}"); //! In parameters
            callableStatement.setInt(1, 1);

            resultSet = callableStatement.executeQuery();
            System.out.println("----------------Using CallableStatement (IN parameters)----------------");
            contacts = ContactDAO.getAllContacts(resultSet);
            ContactDAO.displayContacts(contacts);

            callableStatement=connection.prepareCall("{call GetContactNameEmail(?,?,?)}"); //! Out parameters
            callableStatement.setInt(1, 1);
            callableStatement.registerOutParameter(2, Types.VARCHAR);
            callableStatement.registerOutParameter(3, Types.VARCHAR);
            callableStatement.execute();
            String name = callableStatement.getString(2);
            String email = callableStatement.getString(3);
            System.out.println("----------------Using CallableStatement (OUT parameters)----------------");
            System.out.println(name + " " + email);

            callableStatement=connection.prepareCall("{call SearchContacts(?,?)}"); //! InOut parameters
            callableStatement.setString(1, "Ali");
            callableStatement.registerOutParameter(2, Types.INTEGER);
            callableStatement.execute();
            int totalCount = callableStatement.getInt(2);
            System.out.println("----------------Using CallableStatement (InOut parameters)----------------");
            System.out.println(totalCount);

            callableStatement = connection.prepareCall("{call GetAllContacts()}"); //! return result set
            resultSet = callableStatement.executeQuery();
            System.out.println("----------------Using CallableStatement (return result set)----------------");
            contacts = ContactDAO.getAllContacts(resultSet);
            ContactDAO.displayContacts(contacts);

            //display info about the result set
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData(); //! returns metadata about the result set, such as the number of columns, column names, and column types
            int columnCount = resultSetMetaData.getColumnCount(); //! returns the number of columns in the result set

            System.out.println("----------------Result Set Info----------------");
            for (int i = 1; i <= columnCount; i++) {
                System.out.println("Column Name: " + resultSetMetaData.getColumnName(i) + " (Column Type: " + resultSetMetaData.getColumnTypeName(i) + ")");
                System.out.println("Precision: " + resultSetMetaData.getPrecision(i));
                System.out.println("Scale: " + resultSetMetaData.getScale(i));
                System.out.println("Auto Increment: " + resultSetMetaData.isAutoIncrement(i));
                System.out.println("Nullable: " + resultSetMetaData.isNullable(i));
                System.out.println("Currency: " + resultSetMetaData.isCurrency(i));
                System.out.println("------------------------------------------------");
            }

            // display database info
            DatabaseMetaData metaData = connection.getMetaData();
            String catalog = null;
            String schemaPattern = null;
            String tableNamePattern = null;
            String ColumnNamePattern = null;
            String [] types = null;

            System.out.println("----------------Database Info----------------");
            System.out.println("Database Name: " + metaData.getDatabaseProductName());
            System.out.println("Database Version: " + metaData.getDatabaseProductVersion());
            System.out.println("Driver Name: " + metaData.getDriverName());
            System.out.println("Driver Version: " + metaData.getDriverVersion());

            resultSet = metaData.getTables(catalog, "dbo", tableNamePattern, types);
            System.out.println("Tables: ");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("TABLE_NAME"));
            }

            resultSet = metaData.getColumns(catalog, schemaPattern, "Contact", ColumnNamePattern);
            System.out.println("Columns: ");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("COLUMN_NAME"));
            }

            // search contacts by name
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter name to search for: ");
            name = scanner.nextLine();
            contacts = ContactDAO.searchContactPersons(name);
            ContactDAO.displayContacts(contacts);

            //5. closes objects to free up database resources
            scanner.close();
            resultSet.close();
            statement.close();
            preparedStatement.close();
            callableStatement.close();
            connection.close();

        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            // with SQLException Type
            System.out.println("SQL State: " + ((SQLException) e).getSQLState());
            System.out.println("Error Code: " + ((SQLException) e).getErrorCode());
        }
    }
}
