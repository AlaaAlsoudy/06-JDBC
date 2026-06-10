//************************************************ DataSource Objects, Transaction & Batch Update ************************************************//
//~ DataSource:
// Get connection on data sources at the system (database, files, ...)
// Driver vendors provide implementations of this interface
// Advantages:
    // 1. provide connection pooling -> open more than 1 connection on your db
    // 2. provide distributed transaction calling -> while dealing with more than 1 db, or databases on different servers
// 3 Different ways DataSource object can be implemented:
    // 1. Basic DataSource: 
        // -> all drivers implement this interface
        // -> make connection more easily
        // -> doesn't support distributed transaction and connection pooling
    // 2. Pooled DataSource:
        // -> supports pooling
        // -> can make more than 1 connection at the same time on the same db
        // -> if any problem occurs with any connection, DataSource with DBMS can recycle it
    // 3. Advanced DataSource:
        // -> supports pooling and distributed transaction (dealing with multiple servers)
// Deploying a DataSource object: (3 steps)
    // 1. Create a DataSource object
    // 2. Setting the properties of the DataSource object
    // 3. Register the DataSource object
//& EX: 
//1. create property file (fileName.properties) 
    // the file stores config data, db info, settings, ...
    // signature: key=value 
    // can be written with any text editor
// 2. read the file and link it to the DataSource object
    // define Properties object
    // define FileInputStream object ---> refers to the property file
    // define DataSource object ---> for the DB we use (like: SQLServerDataSource)
    // read the file and load it
    // set the properties from this file to the DataSource object (all information of the DB)
    // finally, register the DataSource object and check the connection
//--------------------------------------------------------------------------------------------
//~ Transactions:
// when we want to execute more than 1 query as a unit (all of them or none of them)
// by default once we set connection -> auto commit is true (so we want to change it and make the commit manually)
// steps to make a transaction:
    // 1. set auto commit to false -> (connection.setAutoCommit(false))
    // 2. write the queries we want to execute
    // 3. commit -> (connection.commit())
// Setting and rolling back to SavePoints: (returning auto commit to true and rollback to the specific point you set)
    // 1. set auto commit to true -> (connection.setAutoCommit(true))
    // 2. declare a SavePoint -> (SavePoint SavePointName = connection.setSavePoint(SavePointName))
    // 3. write the queries we want to execute
    // 4. rollback -> (connection.rollback(SavePointName))
// Transaction vs savepoint:
    // -> Transaction: like a single point or unit going to db, the whole transaction will be executed on db or not
    // -> Savepoint: like a checkpoint/flag at a specific point, if something goes wrong, we can rollback to this point
//--------------------------------------------------------------------------------------------
//~ Batch Update:
// Batch update is a way to execute more than 1 query as a unit (all of them or none of them)
// Difference between Batch Update and Transaction:
    // Queries in batch are related to each other (the same query for multiple times or rows)
    // Queries in transaction can be related to each other or not
// Steps to make a batch update:
    // 1. disable auto commit -> (connection.setAutoCommit(false))
    // 1. create a statement object (with any type of statement)
    // 2. addBatch() -> add the query to the batch (same statement will be added with different parameters)
    // 3. executeBatch() -> start the execution of all the queries in the batch
    // 4. commit the changes -> (connection.commit())

//&-----------------------------------------------------Example:-----------------------------------------------------

import java.sql.*;
import java.util.Scanner;

import classes.PropRead;
public class B_DataSource_Transactions_BatchUpdate {
    public static void main(String[] args) {
        Connection connection;
        try {
            connection = PropRead.getSQLServerDataSource().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Contact");
            boolean ok = false;
            Scanner scanner = new Scanner(System.in);

            System.out.println("Data before any changes: ....");
            while(resultSet.next()){
                System.out.println(resultSet.getInt("id") + "\t" +
                resultSet.getString("name"));
            }
            // ex with transactions 
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement("UPDATE Contact SET name=? WHERE id=?");

            ps.setString(1, "Ahmed Saleh");
            ps.setInt(2, 1);
            ps.executeUpdate();
            ps.setString(1, "Sara Waleed");
            ps.setInt(2, 2);
            ps.executeUpdate();

            // commit or rollback
            System.out.println("Make sure you want to update data:");
            ok = askUserIfOkToSave(scanner);
            if (ok)
                connection.commit(); //! commit if both succeed
            else
                connection.rollback(); //! rollback if error

            // to see the changes
            resultSet = statement.executeQuery("SELECT * FROM Contact");
            System.out.println("Data after updating: ....");
            while(resultSet.next()){
                System.out.println(resultSet.getInt("id") + "\t" +
                resultSet.getString("name"));
            }
            //---------------------------------------------------------------------
            // ex with batch update
            connection.setAutoCommit(false);
            String SQL = """
                INSERT INTO Contact (name, nick_name, address, home_phone, work_phone, cell_phone, email, birthdate, web_site, profession) VALUES
                ('Ali Ahmed', 'Ali', 'Giza', '0123333333', '0124444444', '0125555555', 'ali@gmail.com', '1995-05-05', 'www.aliahmed.com', 'Engineer')
            """;
            statement.addBatch(SQL);
            SQL = """
                INSERT INTO Contact (name, nick_name, address, home_phone, work_phone, cell_phone, email, birthdate, web_site, profession) VALUES
                ('Khalid Mohamed', 'Khalid', 'Cairo', '0126666666', '0127777777', '0128888888', 'khalid@gmail.com', '1993-03-03', 'www.khalidmohamed.com', 'Engineer')
            """;
            statement.addBatch(SQL);
            SQL = """
                INSERT INTO Contact (name, nick_name, address, home_phone, work_phone, cell_phone, email, birthdate, web_site, profession) VALUES
                ('Rahma Rafik', 'Rahma', 'Alex', '0129999999', '0128888888', '0127777777', 'rahma@gmail.com', '1992-02-02', 'www.rahmamohamed.com', 'Engineer')
            """;
            statement.addBatch(SQL);
            int [] count = statement.executeBatch();

            // commit or rollback
            System.out.println("Make sure you want to insert data:");
            ok = askUserIfOkToSave(scanner);
            if (ok){
                connection.commit();
                System.out.println("Number of rows inserted: " + count.length);
            } else
                connection.rollback();
            
            // to check the data after insert
            resultSet = statement.executeQuery("SELECT * FROM Contact");
            System.out.println("Data after inserting: ....");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                System.out.println(id + " " + name + " " + email);
            }
            resultSet.close();
            statement.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean askUserIfOkToSave(Scanner sc) {
        System.out.println("Are you sure you want to save the changes? (y/n)");
        String input = sc.nextLine();
        return input.equalsIgnoreCase("y");
    }
}
