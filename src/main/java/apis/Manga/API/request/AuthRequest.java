package apis.Manga.API.request;


import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;
    private String username;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }


}
