package classes;
import java.io.FileInputStream;
import java.util.Properties;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

public class PropRead {
    public static SQLServerDataSource getSQLServerDataSource() {
        Properties props = new Properties();
        FileInputStream fis = null;
        SQLServerDataSource sqlDS = new SQLServerDataSource();

        try{
            fis = new FileInputStream("src/db.properties");
            props.load(fis);
            sqlDS.setURL(props.getProperty("Database_URL"));
            sqlDS.setUser(props.getProperty("UserName"));
            sqlDS.setPassword(props.getProperty("Password"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sqlDS;
    }
}