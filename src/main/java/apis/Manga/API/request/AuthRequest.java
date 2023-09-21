package apis.Manga.API.request;


import lombok.Data;

public class AuthRequest {
    private String email;
    private String password;
    private String name;


    public String getUsername() {
        return name;
    }

    public void setUsername(String username) {
        this.name = username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }


}
