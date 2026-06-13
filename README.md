# Java Database Connectivity (JDBC)

This repository contains my Java JDBC (Java Database Connectivity) practice files for working with relational databases.

It includes examples and explanations of core JDBC concepts and database interaction patterns.

---

## Core JDBC Concepts

- Connection
- Statement
- PreparedStatement
- CallableStatement
- ResultSet
- ResultSetMetaData
- DatabaseMetaData
- Transactions
- Batch Processing
- RowSet
- BLOB & CLOB
- DataSource
- DAO Pattern

---

## Database Setup

The database schema used in this repository is available in the `database` folder.

Before running any JDBC examples:

1. Create the required database.
2. Execute the provided SQL script.
3. Make sure the database server is running.
4. Verify that the connection URL, username, and password in the Java files match your database configuration.

---

## JDBC Driver Setup

### Step 1: Download the JDBC Driver

Before running any example, download the JDBC driver for your database.

### SQL Server

https://learn.microsoft.com/en-us/sql/connect/jdbc/download-microsoft-jdbc-driver-for-sql-server

### MySQL

https://dev.mysql.com/downloads/connector/j/

### PostgreSQL

https://jdbc.postgresql.org/download/

### Oracle

https://www.oracle.com/database/technologies/appdev/jdbc-downloads.html

### Step 2: Add the Driver to the Project

After downloading the driver:

1. Create a `lib` folder in the project root.
2. Place the JDBC `.jar` file inside the `lib` folder.
3. Add the driver to the project classpath.

Example project structure:

```text
project/
├── lib/
│   └── mssql-jdbc-13.4.0.jre11.jar
├── src/
└── README.md
```

### Step 3: Configure the Classpath

After that, add the JDBC driver to the classpath.

### Option 1: VS Code Settings

Add the JDBC driver to your project settings:

```json
{
    "java.project.referencedLibraries": [
        "lib/*.jar"
    ]
}
```

### Option 2: VS Code Command Palette

1. Open the Command Palette:

```text
Ctrl + Shift + P
```

2. Search for:

```text
Java: Configure Classpath
```

3. Select:

```text
Libraries -> Add Library
```

4. Choose the JDBC driver `.jar` file that you placed inside the `lib` folder.

---

## Common DataSource Classes

Different databases provide their own DataSource implementation:

| Database | DataSource Class |
|-----------|------------------|
| SQL Server | `com.microsoft.sqlserver.jdbc.SQLServerDataSource` |
| MySQL | `com.mysql.cj.jdbc.MysqlDataSource` |
| PostgreSQL | `org.postgresql.ds.PGSimpleDataSource` |
| Oracle | `oracle.jdbc.pool.OracleDataSource` |

---

## JDBC Connection URL Examples

### SQL Server

```java
String url =
"jdbc:sqlserver://localhost:1433;databaseName=BookStore;encrypt=true;trustServerCertificate=true;";
```

### MySQL

```java
String url =
"jdbc:mysql://localhost:3306/myDatabase";
```

### PostgreSQL

```java
String url =
"jdbc:postgresql://localhost:5432/myDatabase";
```

### Oracle

```java
String url =
"jdbc:oracle:thin:@localhost:1521:xe";
```

## How to Run

To develop and run the project using Visual Studio Code, make sure you have:
- Installed JDK on your machine.
- Installed the required Java extensions in VS Code.

Recommended extensions:
- Extension Pack for Java
- Java Debugger
- Java Language Support

### Compile

Then open the project in VS Code and run these commands in the terminal:
```bash
cd folder-name
```
Replace `folder-name` with the name of the folder you want to run

Then use:
```bash
javac -cp "lib/*" -d bin src/classes/*.java src/*.java
```

### Run

To run a specific file that contains the `main` method, use:

```bash
java -cp "bin;lib/*" file-name-here
```

Replace `file-name-here` with the name of the file/class you want to run.
