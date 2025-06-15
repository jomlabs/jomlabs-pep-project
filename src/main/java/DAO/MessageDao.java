package DAO;

import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class MessageDao {

    public Message  insertMessage(Message message){
        Connection  connection = ConnectionUtil.getConnection();

        String sql ="INSERT INTO Message (posted_by, message_text,time_posted_epoch) VALUES (?, ?, ?)";

        try{
            PreparedStatement ps = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, message.getPosted_by());
            ps.setString(2, message.getMessage_text());
            ps.setLong(3, message.getTime_posted_epoch());
            ps.executeUpdate();

            ResultSet rs =ps.getGeneratedKeys();
            if(rs.next()){

                int messageId = rs.getInt(1);
                return new Message(messageId, message.getPosted_by(),message.getMessage_text(), message.getTime_posted_epoch());
            }
    
        }catch(SQLException e){
            e.printStackTrace();
        }
    
        return null;
    }


    public List<Message> getAllMessages(){

        Connection connection  = ConnectionUtil.getConnection();
        String sql  = "SELECT * FROM Message";

        List<Message> messages = new ArrayList<>();

        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                messages.add(new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")
                ));

            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        return messages;
    }


    public Message getMessageById(int messageId) {
    Connection connection = ConnectionUtil.getConnection();
    String sql = "SELECT * FROM Message WHERE message_id = ?";

    try {
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, messageId);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return new Message(
                rs.getInt("message_id"),
                rs.getInt("posted_by"),
                rs.getString("message_text"),
                rs.getLong("time_posted_epoch")
            );
        }
    } catch (SQLException e) {
        e.printStackTrace(); // critical
    }

    return null;
}



    public Message deleteMessageById(int messageId){
        Message message =getMessageById(messageId);

        if(message != null){
            Connection connection = ConnectionUtil.getConnection();
            String sql ="DELETE FROM Message WHERE message_id =?";

            try {
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1, messageId);
                ps.executeUpdate(); 
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return message;
    }

    public Message updateMessageText(int messageId, String newText) {
    Connection connection = ConnectionUtil.getConnection();
    String sql = "UPDATE Message SET message_text = ? WHERE message_id = ?";

    try {
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, newText);
        ps.setInt(2, messageId);
        int updated = ps.executeUpdate();

        if (updated > 0) {
            return getMessageById(messageId); 
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return null;
}


    public List<Message>  getMessagesByAccountId(int accountId){
        Connection connection = ConnectionUtil.getConnection();
        String sql ="SELECT * FROM Message WHERE  posted_by = ?";
        List<Message> messages = new ArrayList<>();

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                messages.add(new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")
                ));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return messages;
    }
}
