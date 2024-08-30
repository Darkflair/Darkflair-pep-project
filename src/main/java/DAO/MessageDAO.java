package DAO;

import Model.Message;
import java.sql.Connection;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class MessageDAO {
    
    public List<Message> getAllMessages(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Message";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }

    public Message getAllMessagesById(int message_id){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM Message WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,message_id);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                return message;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Message createMessage(Message message){
        Connection connection = ConnectionUtil.getConnection();
            try {
            String sql = "INSERT INTO Message (posted_by, message_text, time_posted_epoch) VALUES (?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());
            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if (pkeyResultSet.next()) {
                int generated_message_id = (int) pkeyResultSet.getInt(1);
                return new Message(generated_message_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void deleteMessageById(int message_id){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "DELETE * FROM Message WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, message_id);
            preparedStatement.executeQuery();
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public Message updateMessageById(int message_id, String newMessage){
        Connection connection = ConnectionUtil.getConnection();
        Message updatedMessage = null;
        try{
            String sql = "UPDATE Message SET message_text = ? WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, newMessage);
            preparedStatement.setInt(2, message_id);
            int affectedRows = preparedStatement.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException("Updating message failed, no rows affected.");
        }
    updatedMessage = getAllMessagesById(message_id);

} catch (SQLException e) {
    e.printStackTrace();
}

return updatedMessage;
}

public List<Message> getAllMessagesByAccount(int account_id){
Connection connection = ConnectionUtil.getConnection();
List<Message> messages = new ArrayList<>();
try{
    String sql = "SELECT * FROM Message WHERE posted_by = ?";
    PreparedStatement preparedStatement = connection.prepareStatement(sql);

    preparedStatement.setInt(1, account_id);

    ResultSet resultSet = preparedStatement.executeQuery();
    
    while (resultSet.next()) {
        Message message = new Message(
            resultSet.getInt("message_id"),
            resultSet.getInt("posted_by"),
            resultSet.getString("message_text"),
            resultSet.getLong("time_posted_epoch")
        );
        messages.add(message);
}
} catch(SQLException e){
    e.printStackTrace();
}
return messages;
}
}
