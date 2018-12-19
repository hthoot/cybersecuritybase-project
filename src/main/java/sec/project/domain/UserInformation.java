package sec.project.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class UserInformation extends AbstractPersistable<Long> {

    private String userName;
    private String requestedPassword;
    private String socialSecurityNumber;
    @Id
    private Long id;

    public UserInformation() {
        super();
    }

    public UserInformation(String userName, String requestedPassword, String ssn) {
        this();
        this.userName = userName;
        this.requestedPassword = requestedPassword;
        this.socialSecurityNumber = ssn;
    }

    public String getName() {
        return userName;
    }

    public void setName(String name) {
        this.userName = name;
    }

    public String getPassword() {
        return requestedPassword;
    }

    public void setPassword(String password) {
        this.requestedPassword = password;
    }
    
    public void setSocialSecurityNumber(String ssn) {
        this.socialSecurityNumber = ssn;
    }
    
    public String getSocialSecurityNumber() {
        return this.socialSecurityNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
