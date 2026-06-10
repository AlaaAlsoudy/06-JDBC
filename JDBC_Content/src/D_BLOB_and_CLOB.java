//************************************************ BLOB and CLOB ************************************************//
//~ BLOB: (Binary Large Object)
// it's a data type that stores binary data, such as images, audio, video, etc.
// it's used to store large amounts of data that cannot be stored in a text-based format (like a string or a number).
// it's stored as a singe entity in the DB.
// we can write and read data from it.
//~ CLOB: (Character Large Object)
// it's a data type that stores text data, such as strings, characters, words, plain text, XML, etc.
// it's used to store large amounts of text data that cannot be stored in a text-based format (like a string or a number).
// it's stored as a singe entity in the DB.
// we can write and read data from it too.

//&-----------------------------------------------------Example:-----------------------------------------------------

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.Reader;
import java.sql.*;

import classes.PropRead;

public class D_BLOB_and_CLOB {
    public static void main(String[] args) {
        Connection connection;
        try {
            connection = PropRead.getSQLServerDataSource().getConnection();
            String SQL = "UPDATE Contact SET resume=? WHERE email='ahmed@gmail.com'";
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            Statement statement = connection.createStatement();
            ResultSet resultSet =null;
            File file = null;
            
            // //!! writing the BLOB
            file = new File("src/files/resume.pdf");
            FileInputStream fileInputStream = new FileInputStream(file);
            preparedStatement.setBinaryStream(1, fileInputStream);
            preparedStatement.executeUpdate();

            // //!! reading the BLOB
            SQL = "SELECT resume FROM Contact WHERE email='ahmed@gmail.com'";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL);
            file = new File("src/files/resume_from_db.pdf");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            if (resultSet.next()){
                InputStream inputStream = resultSet.getBinaryStream("resume"); // read blob
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    fileOutputStream.write(buffer, 0, bytesRead); // write to the local file
                }
            }
            System.out.println("-----------------------------------------------");
            //!! writing the CLOB
            SQL = "UPDATE Contact SET notes=? WHERE email='ahmed@gmail.com'";
            preparedStatement = connection.prepareStatement(SQL);
            file = new File("src/files/notes.txt");
            FileReader fileReader = new FileReader(file);
            preparedStatement.setCharacterStream(1, fileReader);
            preparedStatement.executeUpdate();
            System.out.println("-----------------------------------------------");
            // //!! reading the CLOB
            SQL = "SELECT notes FROM Contact WHERE email='ahmed@gmail.com'";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL);
            file = new File("src/files/notes_from_db.txt");
            FileWriter fileWriter = new FileWriter(file);
            if (resultSet.next()){
                Reader reader = resultSet.getCharacterStream("notes");
                char[] buffer = new char[1024];
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    fileWriter.write(buffer, 0, n);
                }
                fileWriter.flush();
            }

            fileReader.close();
            fileWriter.close();
            fileInputStream.close();
            fileOutputStream.close();
            preparedStatement.close();
            statement.close(); 
            resultSet.close();
            connection.close();
            

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}