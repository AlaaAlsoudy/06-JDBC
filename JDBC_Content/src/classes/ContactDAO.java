package classes;
import java.sql.*;
import java.util.ArrayList;

public class ContactDAO {
    public static ArrayList<ContactPerson> getAllContacts(ResultSet resultSet) {
        ArrayList<ContactPerson> contacts = new ArrayList<>();
        try{
            while (resultSet.next()) {
                ContactPerson contact = new ContactPerson(
                    //! retrieves the value of the "column label" column from the current row in the result set
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("nick_name"),
                    resultSet.getString("address"),
                    resultSet.getString("home_phone"),
                    resultSet.getString("work_phone"),
                    resultSet.getString("cell_phone"),
                    resultSet.getString("email"),
                    resultSet.getDate("birthdate"),
                    resultSet.getString("web_site"),
                    resultSet.getString("profession")
                );
                contacts.add(contact);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contacts;
    }

    public static ArrayList<ContactPerson> searchContactPersons(String name) {
        ArrayList<ContactPerson> contacts = new ArrayList<>();
        name+="%";
        try {
            Connection connection = PropRead.getSQLServerDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Contact WHERE name LIKE ?");
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            contacts = getAllContacts(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contacts;
    }
    public static void displayContacts(ArrayList<ContactPerson> contacts) {
        for (ContactPerson contact : contacts) {
            System.out.print("ID: " + contact.getId()+" | ");
            System.out.print("Name: " + contact.getName()+" | ");
            System.out.print("Email: " + contact.getEmail()+"\n");
        }
    }
}
