package classes;
import java.sql.Date;

public class ContactPerson {
    private int id;
    private String name;
    private String nickName;
    private String address;
    private String homePhone;
    private String workPhone;
    private String cellPhone;
    private String email;
    private Date birthdate;
    private String webSite;
    private String profession;

    public ContactPerson(int id, String name, String nickName, String address, String homePhone, String workPhone, String cellPhone, String email, Date birthdate, String webSite, String profession) {
        this.id = id;
        this.name = name;
        this.nickName = nickName;
        this.address = address;
        this.homePhone = homePhone;
        this.workPhone = workPhone;
        this.cellPhone = cellPhone;
        this.email = email;
        this.birthdate = birthdate;
        this.webSite = webSite;
        this.profession = profession;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getNickName() { return nickName; }
    public String getAddress() { return address; }
    public String getHomePhone() { return homePhone; }
    public String getWorkPhone() { return workPhone; }
    public String getCellPhone() { return cellPhone; }
    public String getEmail() { return email; }
    public Date getBirthdate() { return birthdate; }
    public String getWebSite() { return webSite; }
    public String getProfession() { return profession; }

    public void setAddress(String address) { this.address = address; }
    public void setBirthDate(Date birthdate) { this.birthdate = birthdate; }
    public void setEmail(String email) { this.email = email; }
}
