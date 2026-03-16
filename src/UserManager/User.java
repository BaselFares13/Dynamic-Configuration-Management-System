package UserManager;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    private UUID id;
    private String username;
    private Role role;

    public User() { }
    
    public User(String username, Role role) {
        this.id = UUID.randomUUID();
        this.username = username;
        this.role = role;
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
    	this.role = role;
    }
    
    public boolean isAdmin() {
        return role == Role.ADMIN;
    }

    public boolean isService() {
        return role == Role.SERVICE;
    }
    
    @Override
    public String toString() {
        return "(" + this.id.toString() + ", " + this.username + ")";
    }
}
