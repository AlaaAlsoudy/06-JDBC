import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try(var connection = SQLServerConnection.connect()){
            System.out.println("Connected to the SQL Server database successfully!");
        } catch (SQLException | DBException e){
            System.err.println(e.getMessage());
        }
    }
}