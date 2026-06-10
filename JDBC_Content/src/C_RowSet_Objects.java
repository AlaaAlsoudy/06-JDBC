//************************************************ RowSet Objects ************************************************//
//~ What is a RowSet?
// A RowSet is one of the javaBeans components (make the integration with the whole app easier).
// it's much easier and flexible to use a RowSet than a ResultSet to deal with db
// It's scrollable and updatable (as a ResultSet) and these features are allowed even if the DBMS allow or not
// It has 3 events triggers notifications:
    // 1. cursorMoved event: 
        // generated when there's a cursor movement
        // occurs when the next() or previous() methods are called
    // 2. rowChanged event:
        // generated when a row is changed (added, updated, deleted) from the rowSet
    // 3. rowSetChanged event:
        // generated when the whole rowSet is changed or created
// we can use a listener to handle rowSet events and update the UI (using rowSetObject.addRowSetListener(ui))
//--------------------------------------------------------------------------------------------
//~ Kinds of RowSets:
// 1. Connected RowSet: 
    // a RowSet that is connected to a database all the time
    // JDBCRowSet -> Has ResultSet capabilities + JavaBean capabilities
// 2. Disconnected RowSet: 
    // connected only one time, bring the data and fill rowSet, then we don't need to connect again
    // CashedRowSet -> Has ResultSet capabilities + JavaBean capabilities + disconnected ResultSet capabilities
    // WebRowSet -> Can write itself as an XML document
              // -> CashedRowSet capabilities + XML capabilities
    // JoinRowSet -> WebRowSet capabilities + SQL join capabilities (apply sql join queries without need to connect to DB all the time)
    // FilteredRowSet -> WebRowSet capabilities + filter capabilities (on the RowSet we have)
//--------------------------------------------------------------------------------------------
//~ The JdbcRowSet Objects:
// 4 ways to create a JdbcRowSet:
    // 1. convert a ResultSet to a JdbcRowSet
        /*
        & ex:
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM table");
        JdbcRowSet jdbcRowSet = new JdbcRowSet(); //! it will take the same properties from the resultSet
        jdbcRowSet.populate(resultSet);
        */
    // 2. after connection to the DB we can create a JdbcRowSet
        /*
        & ex:
        JdbcRowSet jdbcRowSet = new JdbcRowSet(connection); //! by default it will ba a scrollable and updatable object
        jdbcRowSet.setCommand("SELECT * FROM table");
        jdbcRowSet.execute();
        */
    // 3. use rowSet default constructor and set properties on it (and here we implicitly set url, user and password properties)
        /*
        & ex:
        JdbcRowSet jdbcRowSet = new JdbcRowSet();
        jdbcRowSet.setUrl("jdbc:sqlserver://localhost:1433;databaseName=databaseName");
        jdbcRowSet.setUsername("user");
        jdbcRowSet.setPassword("password");
        jdbcRowSet.setCommand("SELECT * FROM table");
        jdbcRowSet.execute();
        */
    // 4. use RowSetFactory to create a JdbcRowSet and then we can set properties on it
        /*
        & ex:
        RowSetFactory factory = RowSetProvider.newFactory();
        JdbcRowSet jdbcRowSet = factory.createJdbcRowSet();
        jdbcRowSet.setUrl("jdbc:sqlserver://localhost:1433;databaseName=databaseName");
        jdbcRowSet.setUsername("user");
        jdbcRowSet.setPassword("password");
        jdbcRowSet.setCommand("SELECT * FROM table");
        jdbcRowSet.execute();
        */
       // it allows us to :
            // createJdbcRowSet() -> the connected mode
            // createCashedRowSet() -> the disconnected mode
            // createFilteredRowSet()
            // createJoinRowSet()
            // createWebRowSet()
// Operations on JdbcRowSet:
    // navigate through the rowSet
        /*
        & ex:
        jdbcRowSet.absolute(4); //! 4 is the index of the row we want to go to
        jdbcRowSet.first(); //! go to the first row
        jdbcRowSet.last(); //! go to the last row
        jdbcRowSet.next(); //! go to the next row
        jdbcRowSet.previous(); //! go to the previous row
        jdbcRowSet.afterLast(); //! go to the last row 
        */
    // update the rowSet
        /*
        & ex:
        jdbcRowSet.absolute(3); //!row we want to update
        jdbcRowSet.updateString("columnLabel", "newValue"); //! or updateInt(), updateDate(), ...
        jdbcRowSet.updateRow();
        */
    // insert to & delete from the rowSet
        /*
        & ex: (inserting a new row)
        jdbcRowSet.moveToInsertRow(); //! go to the end of the rowSet to insert a new row
        jdbcRowSet.updateString("columnLabel", "newValue");
        jdbcRowSet.updateInt("columnLabel", newValue);
        jdbcRowSet.insertRow();
        & ex: (deleting a row)
        jdbcRowSet.absolute(3); //!row we want to delete
        jdbcRowSet.deleteRow();
        */
       //^ we prefer to avoid deleting from the db, we can use flag to make a soft delete (hide data for you only)
//--------------------------------------------------------------------------------------------
//~ The CachedRowSet Objects:
// Setting up a CashedRowSet:
    // 1. create a CashedRowSet object (using RowSetFactory or default constructor)
        // RowSetFactory factory = RowSetProvider.newFactory();
        // CachedRowSet cachedRowSet = factory.createCachedRowSet();
    // 2. set properties (url, user and password) on it
    // 3. set the command to execute on it
//--------------------------------------------------------------------------------------------
//~ The WebRowSet Objects:
// it's readable as an XML document
// the same as a CashedRowSet but it can write itself as an XML document
// the benefit here is --> we can convert the rowSet to XML and write it to a file
/*
& ex:
WebRowSet webRowSet = new WebRowSet();
webRowSet.setUrl("jdbc:sqlserver://localhost:1433;databaseName=databaseName");
webRowSet.setUsername("user");
webRowSet.setPassword("password");
webRowSet.setCommand("SELECT * FROM table");
webRowSet.execute();
webRowSet.writeXml(System.out);

FileOutputStream fos = new FileOutputStream("webRowSet.xml"); //! empty file here
webRowSet.writeXml(fos); //! write data at rowSet to the file

FileInputStream fis = new FileInputStream("webRowSet.xml"); //! file with data
webRowSet.readXml(fis); //! read data from the file
*/

//&-----------------------------------------------------Example:-----------------------------------------------------

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.*;

import javax.sql.rowset.*;

import classes.PropRead;

public class C_RowSet_Objects {
    public static void main(String[] args) throws Exception {
        Connection connection;
        try{
            connection =PropRead.getSQLServerDataSource().getConnection();

            // using resultSet
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Contact");
            System.out.println("----------------Using ResultSet----------------");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                System.out.println(id + " " + name + " " + email);
            }

            // using rowSet (cashedRowSet)
            RowSetFactory factory = RowSetProvider.newFactory();
            CachedRowSet cachedRowSet = factory.createCachedRowSet();
            cachedRowSet.setUrl("jdbc:sqlserver://localhost:1433;databaseName=AddressBook;encrypt=true;trustServerCertificate=true");
            cachedRowSet.setUsername("Anonymous");
            cachedRowSet.setPassword("Anonymous@832003");
            cachedRowSet.setCommand("SELECT * FROM Contact");
            cachedRowSet.execute();

            System.out.println("----------------Using RowSet (CashedRowSet)----------------");
            while(cachedRowSet.next()){
                int id = cachedRowSet.getInt("id");
                String name = cachedRowSet.getString("name");
                String email = cachedRowSet.getString("email");
                System.out.println(id + " " + name + " " + email);
            }

            // using rowSet (webRowSet)
            WebRowSet webRowSet = factory.createWebRowSet();
            webRowSet.setUrl("jdbc:sqlserver://localhost:1433;databaseName=AddressBook;encrypt=true;trustServerCertificate=true");
            webRowSet.setUsername("Anonymous");
            webRowSet.setPassword("Anonymous@832003");
            webRowSet.setCommand("SELECT * FROM Contact");
            webRowSet.execute();

            FileOutputStream fos = new FileOutputStream("src/files/webRowSet.xml");
            webRowSet.writeXml(fos);
            fos.close();

            WebRowSet webRowSet2 = factory.createWebRowSet();
            FileInputStream fis = new FileInputStream("src/files/webRowSet.xml");
            webRowSet2.readXml(fis);
            fis.close();

            System.out.println("----------------Using RowSet (WebRowSet)----------------");
            webRowSet2.beforeFirst();
            while (webRowSet2.next()) {
                int id = webRowSet2.getInt("id");
                String name = webRowSet2.getString("name");
                String email = webRowSet2.getString("email");
                System.out.println(id + " " + name + " " + email);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}