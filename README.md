# JDBC Practice Repository

This repository contains my Java JDBC practice files.

It includes examples and explanations of the following topics:

- JDBC connection setup
- ResultSet objects
- DataSource and transaction handling
- Batch updates
- RowSet objects
- BLOB and CLOB handling

# Java JDBC - Database Connectivity

This repository contains my Java JDBC (Java Database Connectivity) practice files for working with SQL Server databases.
It includes examples and explanations of core JDBC concepts and database interaction patterns, such as:

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

Install a JDK 11 or newer on your machine.

Open the repository in Visual Studio Code and install a Java extension such as the Java Extension Pack or Language Support for Java(TM) by Red Hat.

Use the VS Code Java features to compile the source files in `JDBC_Content/src`.

From the repository root, compile with:

- `javac JDBC_Content/src/*.java JDBC_Content/src/classes/*.java`

Then run an example with:

- `java -cp JDBC_Content/src A_JDBC_Fundamentals`
- `java -cp JDBC_Content/src B_DataSource_Transactions_BatchUpdate`
- `java -cp JDBC_Content/src C_RowSet_Objects`
- `java -cp JDBC_Content/src D_BLOB_and_CLOB`


How to Run
This project is developed and run using Visual Studio Code.

Before running the project, make sure you have:

Installed a JDK on your machine.
Installed the required Java extensions in VS Code.
Recommended VS Code extensions:

Extension Pack for Java
Java Debugger
Java Language Support
Compile the files
If your Java files are inside a specific folder under src, compile them by writing that folder path:
javac -cp "lib/*" -d bin src/folderName/*.java
If you have more than one folder under src, you can compile them by listing the folders in the same command:
javac -cp "lib/*" -d bin src/folder1/*.java src/folder2/*.java
From Windows CMD:
for /R src %f in (*.java) do javac -cp "lib/*" -d bin "%f"
Run the file
After compiling, run the class that contains the main method:

java -cp "bin;lib/*" ClassName
Replace ClassName with the name of the class that contains the main method.