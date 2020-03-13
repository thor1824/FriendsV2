package dk.easv.friendsv2.Model;


import java.io.Serializable;

public class Friend implements Serializable {

    private String name;
    private String phone;
    private String url;
    private String imgUrl;
    private String email;
    private Boolean isFavorite;

    public Friend(String name, String phone) {
        this(name, phone, false);
    }

    public Friend(String name, String phone, Boolean isFavorite) {
        this.name = name;
        this.phone = phone;
        this.url = "https://placeholder.com/";
        this.imgUrl = "https://via.placeholder.com/300/09f/fff.png";
        this.email = name + "@email.dk";
        this.isFavorite = isFavorite;
    }

    public String getPhone() {
        return phone;
    }

    public String getUrl() {
        return url;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getEmail() {
        return email;
    }

    public Boolean getFavorite() {
        return isFavorite;
    }

    public String getName() {
        return name;
    }

    public Boolean isFavorite() {
        return isFavorite;
    }


}
