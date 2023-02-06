package Service;

import java.util.*;
import DAO.MessageDAO;
import Model.Message;


public class MessageService {

    public MessageDAO messageDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
        }

    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
        }
        
        /**
         * @param message
         * @return
         */
        public Message createMessage(Message message){
            
            if(message.getMessage_text() == null){
                return null;
            }else if(message.getMessage_text().length() == 0 || message.getMessage_text().length() > 255){
                return null;
            } 
            Message newMessage = messageDAO.createMessage(message);
            if(newMessage != null){
                return newMessage;
            }
            return null;
        }
        
        //to retrive the messages
        public List<Message> getAllMessages(){
            return messageDAO.getAllMessages();
        }
        
        public Message getMessageById(int message_id){
            return messageDAO.getMessageById(message_id);
       }

        public Message updateMessage(int message_id, String message_text){
            Message message = messageDAO.getMessageById(message_id);
            if(message == null){
                return null;
            }
            if(message_text == null || message_text.isEmpty() || message_text.length() > 255){
                return null;
            }
            messageDAO.updateMessage(message_id, message_text);
            return messageDAO.getMessageById(message_id); //new Message(message_id, message.getPosted_by(), message_text, message.getTime_posted_epoch()); 
        }
        //public void deleteMessage(int message_id){
            //messageDAO.deleteMessageById(message_id);
        public Message deleteMessage(int message_id){   
            Message message = messageDAO.getMessageById(message_id);
            messageDAO.deleteMessageById(message_id);
            return message;

        }

        public List<Message> getMessagesByAccount(int account_id) {
            return messageDAO.getMessagesForAuthor(account_id);
        }
        
    }
    

