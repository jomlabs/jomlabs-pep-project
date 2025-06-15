package Controller;

import java.util.List;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    private AccountService accountService = new AccountService();
    private MessageService messageService = new MessageService();


    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);

        app.post("/register", this::handleRegister);
        app.post("login", this::handleLogin);
        app.post("/messages", this::handleCreateMessage);
        app.get("/messages",this::handleGetAllMessages);
        app.get("/messages/{message_id}",this::handleGetMessageById);
        app.delete("/messages/{message_id}", this::handleDeleteMessageById);
        app.patch("/messages/{message_id}", this::handleUpdateMessageById);
        app.get("/accounts/{account_id}/messages", this::handleGetMessagesByAccountId);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private void handleRegister(Context ctx){
        Account account = ctx.bodyAsClass(Account.class);
        Account result  = accountService.reqister(account);

        if(result != null){
            ctx.json(result);
        }else{
            ctx.status(400);
        }
    }


    private void handleLogin(Context ctx){
        Account account = ctx.bodyAsClass(Account.class);
        Account result = accountService.login(account);
        if(result != null){
            ctx.json(result);
        }else{
            ctx.status(401);
        }
    }

    private void handleCreateMessage(Context ctx){
        Message message = ctx.bodyAsClass(Message.class);
        Message result = messageService.createMessage(message);
        
        if(result != null){
            ctx.json(result);
        }else{
            ctx.status(400);
        }
    }

    private void handleGetAllMessages(Context ctx){
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }

   private void handleGetMessageById(Context ctx) {
    try {
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.getMessageById(messageId);

        if (message != null) {
            ctx.json(message);
        } else {
            ctx.result(""); 
        }
    } catch (Exception e) {
        e.printStackTrace();
        ctx.status(500).result("Internal server error");
    }
}


    private void handleDeleteMessageById(Context ctx) {
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message deleted = messageService.deleteMessageById(messageId);

        if (deleted != null) {
            ctx.json(deleted);
        } else {
            ctx.json(""); 
        }
    }


    private void handleUpdateMessageById(Context ctx){
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message request  = ctx.bodyAsClass(Message.class);
        Message updated = messageService.updateMessage(messageId, request.getMessage_text());

        if(updated != null){
            ctx.json(updated);
        }else{
            ctx.status(400);
        }
    }


    private void handleGetMessagesByAccountId(Context ctx){
        int accountId = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> messages = messageService.getMessagesByAccountId(accountId);
        ctx.json(messages);
    }




}