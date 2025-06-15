package Service;

import Model.Message;

import DAO.MessageDao;
import DAO.AccountDao;
import Model.Account;

import java.util.List;

public class MessageService {
    private MessageDao messageDao;
    private AccountDao accountDao;

    public MessageService() {
        this.messageDao = new MessageDao();
        this.accountDao = new AccountDao();
    }

    public Message createMessage(Message message) {
        // Validate message_text
        if (message.getMessage_text() == null || message.getMessage_text().isBlank() ||
            message.getMessage_text().length() > 255) {
            return null;
        }

     
        


        Account user = accountDao.getAccountById(message.getPosted_by());
        if (user == null) {
        return null;
        }
       

        

        return messageDao.insertMessage(message);
    }

    public List<Message> getAllMessages() {
        return messageDao.getAllMessages();
    }

    public Message getMessageById(int id) {
        return messageDao.getMessageById(id);
    }

    public Message deleteMessageById(int id) {
        return messageDao.deleteMessageById(id);
    }



    public Message updateMessage(int messageId, String newText) {
        if (newText == null || newText.isBlank() || newText.length() > 255) {
            return null;
        }

        Message existing = messageDao.getMessageById(messageId);
        if(existing ==null){
            return null;
        }

        return messageDao.updateMessageText(messageId, newText);
    }



    public List<Message> getMessagesByAccountId(int accountId) {
        return messageDao.getMessagesByAccountId(accountId);
    }
}