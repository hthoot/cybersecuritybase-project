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
import sec.project.domain.Message;
import sec.project.domain.UserInformation;

public class MessageRepository {

    private Connection connection;
    
    public MessageRepository() throws Exception {
        String databaseAddress = "jdbc:h2:file:./database";
        connection = DriverManager.getConnection(databaseAddress, "sa", "");
    }
    
    public void insertInitialData() {
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "CREATE TABLE Message (\n" +
                    "    id integer PRIMARY KEY AUTO_INCREMENT,\n" +
                    "    sender integer,\n" +
                    "    contents varchar(4096)" +
                    ");");            
            ps.executeUpdate();
            
            this.save(new Message(1, "Onx tääl ketää!?!?"));
            this.save(new Message(2, "what the hell is this place"));
            this.save(new Message(3, "Went to park tday, saw some birds and sun was shining. Then I had a latte. #blessed"));
            this.save(new Message(1, "oikeesti wtf"));
            this.save(new Message(2, "shut up y'all"));
        } catch (Throwable t) {
            System.err.println(t.getMessage());
        }
        
    }
    
    public void save(Message message) throws Exception {
        PreparedStatement ps = connection.prepareStatement("INSERT INTO Message (sender, contents) VALUES (" 
                    + "'" + message.getSenderId() +"', "
                    + "'" + message.getContents() + "'"                    
                + ")");
        ps.executeUpdate();        
    }

    public List<Message> findAll() throws Exception {
            PreparedStatement ps = connection.prepareStatement("SELECT id, sender, contents FROM Message");
            ResultSet rs = ps.executeQuery();
            
            ArrayList<Message> results = new ArrayList<Message>();
            while(rs.next()) {
                int id = rs.getInt(1);
                
                Message m = new Message(rs.getInt(1), rs.getString(2));
                m.setId(id);
                
                results.add(m);
            }
            
            return results;
    }

        public Message findOne(int mid) throws Exception {
            PreparedStatement ps = connection.prepareStatement("SELECT id, sender, contents FROM Message WHERE id = " + mid);
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()) {
                int id = rs.getInt(1);
                
                Message m = new Message(rs.getInt(2), rs.getString(3));
                m.setId(id);
                
                return m;
            } else {
                throw new Exception("Unknown mid " + mid);
            }
    }

}
