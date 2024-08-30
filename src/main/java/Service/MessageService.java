package Service;

import Model.Message;
import DAO.MessageDAO;

import java.util.List;

public class MessageService {
    private MessageDAO messageDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }

    public Message getMessageById(int message_id){
        return messageDAO.getAllMessagesById(message_id);
    }

    public Message createMessage(Message message){
        if(messageDAO.getAllMessagesById(message.getMessage_id()) == null && !message.getMessage_text().isBlank()) {
            Message persistenMessage = messageDAO.createMessage(message);
            return persistenMessage;
        } else       
        return null;
    }

    public Message deleteMessageById(int message_id){
        messageDAO.deleteMessageById(message_id);
        return messageDAO.getAllMessagesById(message_id);
    }
    
    public Message updateMessageById(int message, String newMessage){
        if(!newMessage.isEmpty()){
            Message updatedMessage = messageDAO.updateMessageById(message, newMessage);
            return updatedMessage;
        } else
        return null;       
    }

    public List<Message> getAllMessagesByAccount(int account_id){
        return messageDAO.getAllMessagesByAccount(account_id);
    }
    }

