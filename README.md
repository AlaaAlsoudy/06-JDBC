# Java JDBC - Database Connectivity

This repository contains my Java JDBC (Java Database Connectivity) practice files for working with SQL Server databases.
It includes examples and explanations of core JDBC concepts and database interaction patterns.

## Core JDBC Concepts

- **JDBC Fundamentals** - Connecting to SQL Server, Statement, PreparedStatement, CallableStatement
- **ResultSet & ResultSetMetaData** - Retrieving and analyzing query results
- **DatabaseMetaData** - Getting database information and metadata
- **PreparedStatement** - Executing parameterized queries safely
- **CallableStatement** - Working with stored procedures (IN, OUT, INOUT parameters)
- **Transaction Management** - commit(), rollback(), setAutoCommit()
- **Batch Updates** - Executing multiple SQL statements efficiently
- **RowSet Objects** - CachedRowSet, WebRowSet for disconnected data access
- **BLOB & CLOB** - Handling binary and character large objects (resume PDF, notes text)
- **DataSource** - Using SQLServerDataSource for connection management
- **DAO Pattern** - Data Access Object for clean database operations

## How to Run

To devolop and run it using (Visual Studio Code) Make sure you have:
- Installed JDK on your machine.
- Installed the required Java extensions in VS Code.

Recommended extensions:
- Extension Pack for Java
- Java Debugger
- Java Language Support

Then open the project in VS Code and run these commands in the terminal:

```bash
javac JDBC_Content/src/*.java JDBC_Content/src/classes/*.java
```

To run a specific file that contains the `main` method, use:

```bash
java -cp "bin;lib/*" file-name-here
```

Replace `file-name-here` with the name of the file/class you want to run.
