-- CREATE DATABASE AddressBook;
-- USE AddressBook;

-- CREATE TABLE Contact(
--     id INT NOT NULL PRIMARY KEY IDENTITY(1,1),
--     name VARCHAR(100) NOT NULL,
--     nick_name VARCHAR(100) ,
--     address VARCHAR(128) ,
--     home_phone VARCHAR(10) ,
--     work_phone VARCHAR(10) ,
--     cell_phone VARCHAR(10) ,
--     email VARCHAR(100) ,
--     birthdate DATE,
--     web_site VARCHAR(100) ,
--     profession VARCHAR(100) ,
-- )


-- INSERT INTO Contact
-- (name, nick_name, address, home_phone, work_phone, cell_phone, email, birthdate, web_site, profession)
-- VALUES
-- ('Ahmed Mahmoud', 'Ahmed', 'Cairo', '0101111111', '0102222222', '0103333333', 'ahmed@gmail.com', '1980-05-10', 'www.ahmedmahmoud.com', 'Software Engineer'),
-- ('Sara Ali', 'Sara', 'Giza', '0104444444', '0105555555', '0106666666', 'sara@gmail.com', '1990-07-15', 'www.saraali.com', 'Teacher'),
-- ('John Doe', 'John', 'Alexandria', '0107777777', '0108888888', '0109999999', 'john@gmail.com', '1975-03-20', 'www.johndoe.com', 'Engineer'),
-- ('Laila Hassan', 'Laila', 'Mansoura', '0110000000', '0111111111', '0112222222', 'laila@gmail.com', '1985-11-30', 'www.lailahassan.com', 'Doctor'),
-- ('Omar Farouk', 'Omar', 'Tanta', '0113333333', '0114444444', '0115555555', 'omar@gmail.com', '1992-09-12', 'www.omarfarouk.com', 'Designer'),
-- ('Mona Adel', 'Mona', 'Port Said', '0116666666', '0117777777', '0118888888', 'mona@gmail.com', '1988-04-22', 'www.monaadel.com', 'HR Specialist'),
-- ('Khalid Mohamed', 'Khalid', 'Suez', '0120000000', '0121111111', '0122222222', 'khalid@gmail.com', '1979-01-08', 'www.khalidmohamed.com', 'Accountant'),
-- ('Rahma Rafik', 'Rahma', 'Ismailia', '0123333333', '0124444444', '0125555555', 'rahma@gmail.com', '1995-06-18', 'www.rahmarafik.com', 'Marketing Manager'),
-- ('Youssef Nabil', 'Youssef', 'Aswan', '0126666666', '0127777777', '0128888888', 'youssef@gmail.com', '1991-10-25', 'www.youssefnabil.com', 'Web Developer'),
-- ('Nour Sherif', 'Nour', 'Marsa Matrouh', '0129999999', '0130000000', '0131111111', 'nour@gmail.com', '1987-12-05', 'www.noursharif.com', 'Pharmacist');

------------------------------------------------------------------------------------------

-- -- Create a SQL Server login
-- CREATE LOGIN anonymous WITH PASSWORD = 'Anonymous@832003';
-- GO

-- -- Use the AddressBook database
-- USE AddressBook;
-- GO

-- -- Create a user in the AddressBook database
-- CREATE USER anonymous FOR LOGIN anonymous;
-- GO

-- -- Grant the user db_owner role for full permissions on the AddressBook database
-- ALTER ROLE db_owner ADD MEMBER anonymous;
-- GO

------------------------------------------------------------------------------------------

-- -- Stored Procedures
-- CREATE PROCEDURE dbo.GetContactById --! inputs
--     @Id INT
-- AS
-- BEGIN
--     SET NOCOUNT ON; -- this is used to prevent the procedure from returning a result set (number of rows affected)

--     SELECT *
--     FROM Contact
--     WHERE id = @Id;
-- END
-- GO

-- EXEC dbo.GetContactById @Id = 1;
-- GO
-- --------------------------------------
-- CREATE PROCEDURE dbo.GetContactNameEmail --! outputs
--     @Id INT,
--     @Name VARCHAR(100) OUTPUT,
--     @Email VARCHAR(100) OUTPUT
-- AS
-- BEGIN
--     SET NOCOUNT ON;

--     SELECT
--         @Name = name,
--         @Email = email
--     FROM Contact
--     WHERE id = @Id;
-- END
-- GO

-- DECLARE @Name VARCHAR(100), @Email VARCHAR(100);
-- EXEC dbo.GetContactNameEmail
--     @Id = 1,
--     @Name = @Name OUTPUT,
--     @Email = @Email OUTPUT;

-- SELECT @Name AS Name, @Email AS Email;
-- GO
-- --------------------------------------
-- CREATE PROCEDURE dbo.SearchContacts --! inputs+outputs
--     @SearchText VARCHAR(100),
--     @TotalCount INT OUTPUT
-- AS
-- BEGIN
--     SET NOCOUNT ON;

--     SELECT *
--     FROM Contact
--     WHERE name LIKE '%' + @SearchText + '%'
--        OR email LIKE '%' + @SearchText + '%';

--     SELECT @TotalCount = COUNT(*)
--     FROM Contact
--     WHERE name LIKE '%' + @SearchText + '%'
--        OR email LIKE '%' + @SearchText + '%';
-- END
-- GO

-- DECLARE @TotalCount INT;
-- EXEC dbo.SearchContacts
--     @SearchText = 'Ali',
--     @TotalCount = @TotalCount OUTPUT;

-- SELECT @TotalCount AS TotalCount;
-- GO
-- --------------------------------------
-- CREATE PROCEDURE dbo.GetAllContacts --! return table
-- AS
-- BEGIN
--     SET NOCOUNT ON;

--     SELECT *
--     FROM Contact;
-- END
-- GO

-- EXEC dbo.GetAllContacts;
-- GO
-- --------------------------------------
-- CREATE PROCEDURE dbo.GetContactsByName --! input+output+table
--     @SearchText VARCHAR(100),
--     @TotalCount INT OUTPUT
-- AS
-- BEGIN
--     SET NOCOUNT ON;

--     SELECT *
--     FROM Contact
--     WHERE name LIKE '%' + @SearchText + '%';

--     SELECT @TotalCount = COUNT(*)
--     FROM Contact
--     WHERE name LIKE '%' + @SearchText + '%';
-- END
-- GO

-- DECLARE @TotalCount INT;
-- EXEC dbo.GetContactsByName
--     @SearchText = 'Ali',
--     @TotalCount = @TotalCount OUTPUT;

-- SELECT @TotalCount AS TotalCount;
-- GO

--------------------------------------------------------------------------

-- Adding a new column with BLOB data type
ALTER TABLE Contact
ADD resume VARBINARY(MAX);

-- Adding a new column with CLOB data type
ALTER TABLE Contact
ADD notes NVARCHAR(MAX);

SELECT * FROM Contact