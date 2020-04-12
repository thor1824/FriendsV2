package dk.cptlk.myfirends.Core.Entities;



import java.io.Serializable;
import java.util.Date;

public class Friend implements Serializable {

    private long id;
    private String name;
    private String address;
    private Location location;
    private String phone;
    private String website;
    private byte[] image;
    private String email;
    private Date birthDay;

    public Friend(String name, String address, String phone, String email, String website, byte[] image, Date birthDay, Location location) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.website = website;
        this.image = image;
        this.location = location;
        this.birthDay = birthDay;
    }
    public Friend(long id, String name, String address, String phone, String email, String website, byte[] image, Date birthDay, Location location) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.website = website;
        this.image = image;
        this.location = location;
        this.birthDay = birthDay;
    }


    public String getPhone() {
        return phone;
    }

    public String getWebsite() {
        return website;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public String getAddress() {
        return address;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
