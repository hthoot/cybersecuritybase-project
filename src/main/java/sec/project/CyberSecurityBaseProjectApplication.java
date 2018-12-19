package sec.project;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import org.h2.tools.RunScript;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import sec.project.repository.MessageRepository;
import sec.project.repository.UserRepository;

@SpringBootApplication
public class CyberSecurityBaseProjectApplication {

    public static void main(String[] args) throws Throwable {
        
        UserRepository ur = new UserRepository();
        MessageRepository mr = new MessageRepository();
        
        ur.insertInitialData();
        mr.insertInitialData();
        
        SpringApplication.run(CyberSecurityBaseProjectApplication.class);
    }   
    
}
