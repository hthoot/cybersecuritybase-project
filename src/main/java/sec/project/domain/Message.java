/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sec.project.domain;

public class Message {

    private int id;
    private int senderId;
    private String contents;
    private String senderName;
    
    public Message(int senderId, String contents) {
        this.senderId = senderId;
        this.contents = contents;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }
    
    public void setContents(String contents) {
        this.contents = contents;
    }
    
    public int getId() {
        return this.id;
    }
    
    public int getSenderId() {
        return this.senderId;
    }
    
    public String getContents() {
        return this.contents;
    }
    
    public String getSenderName() {
        return this.senderName;
    }
    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }
    
    
}
