package sec.project.repository;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.h2.tools.RunScript;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sec.project.domain.UserInformation;

public class UserRepository {

    private Connection connection;
    
    public UserRepository() throws Exception {
        String databaseAddress = "jdbc:h2:file:./database";
        connection = DriverManager.getConnection(databaseAddress, "sa", "");
    }
    
    public void insertInitialData() {
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "CREATE TABLE User (\n" +
                    "    id integer PRIMARY KEY AUTO_INCREMENT,\n" +
                    "    name varchar(200),\n" +
                    "    password varchar(200),\n" +
                    "    ssn varchar(200)\n" +
                    ");");            
            ps.executeUpdate();
            
            this.saveIfDoesntExist(new UserInformation("seppo56", "password", "121256-123X"));
            this.saveIfDoesntExist(new UserInformation("keijo", "password", "211155-321Y"));
            this.saveIfDoesntExist(new UserInformation("pirkko123", "password", "010162-121A"));                                
        } catch (Throwable t) {
            System.err.println(t.getMessage());
        }
       
    }
    
    public void save(UserInformation userInformation) throws Exception {
        PreparedStatement ps = connection.prepareStatement("INSERT INTO User (name, password, ssn) VALUES (" 
                    + "'" + userInformation.getName() +"', "
                    + "'" + userInformation.getPassword()+"', "
                    + "'" + userInformation.getSocialSecurityNumber() + "'"
                + ")");
        ps.executeUpdate();        
    }

    public void saveIfDoesntExist(UserInformation userInformation) throws Exception {
        PreparedStatement ps = connection.prepareStatement("SELECT id FROM User WHERE name = '" + userInformation.getName() + "'");
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) {
            PreparedStatement ps0 = connection.prepareStatement("INSERT INTO User (name, password, ssn) VALUES (" 
                    + "'" + userInformation.getName() +"', "
                    + "'" + userInformation.getPassword()+"', "
                    + "'" + userInformation.getSocialSecurityNumber() + "'"
                    + ")");
            ps0.executeUpdate();        
        }
    }

    public List<UserInformation> findAll() throws Exception {
            PreparedStatement ps = connection.prepareStatement("SELECT id, name, password, ssn FROM User");
            ResultSet rs = ps.executeQuery();
            
            ArrayList<UserInformation> results = new ArrayList<UserInformation>();
            while(rs.next()) {
                int id = rs.getInt(1);
                
                UserInformation ui = new UserInformation(rs.getString(2), rs.getString(3), rs.getString(4));
                ui.setId((long)id);
                
                results.add(ui);
            }
            
            return results;
    }

        public UserInformation findOne(long uid) throws Exception {
            PreparedStatement ps = connection.prepareStatement("SELECT id, name, password, ssn FROM User WHERE id = " + uid);
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()) {
                int id = rs.getInt(1);
                
                UserInformation ui = new UserInformation(rs.getString(2), rs.getString(3), rs.getString(4));
                ui.setId((long)id);
                
                return ui;
            } else {
                throw new Exception("Unknown uid " + uid);
            }
    }

}
