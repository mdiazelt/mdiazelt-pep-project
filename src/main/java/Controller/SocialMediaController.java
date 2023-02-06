package Controller;


import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
    /*I added this */
    MessageService messageService;
    AccountService accountService;

    public SocialMediaController(){
        this.messageService = new MessageService();
        this.accountService = new AccountService();
    }
    public Javalin startAPI() {
        Javalin app = Javalin.create();

        app.post("/register", this::registerNewUser);
        app.post("/login", this::userLogin);
        app.post("/messages", this::postNewMessage);
        app.get("/messages", this::getAllMessages);
        app.get("/messages/{message_id}", this::getMessagesById);
        app.delete("/messages/{message_id}", this::deleteMessageById);
        app.patch("/messages/{message_id}",  this::updateMessage);
        app.get("accounts/{account_id}/messages", this::getMessagesForAccount);

        return app;   
    }

    //1
    private void registerNewUser(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account newUser = mapper.readValue(ctx.body(), Account.class);
        Account addedUser = accountService.createAccount(newUser);
        if(addedUser != null){
            ctx.json(mapper.writeValueAsString(addedUser));
        }else{
            ctx.status(400);
        }
    }
    //2
    private void userLogin(Context ctx) throws 
    JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account verifyAccount = mapper.readValue(ctx.body(), Account.class);
        Account checkedAccount = accountService.login(verifyAccount);
        if(checkedAccount != null){
            ctx.json(mapper.writeValueAsString(checkedAccount));
        }else{
            ctx.status(401);
        }

    }
    //3
    private void postNewMessage(Context ctx) throws 
    JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message createdMessage = messageService.createMessage(message);
        if(createdMessage!=null){
            ctx.json(mapper.writeValueAsString(createdMessage));
        }else{
            ctx.status(400);
        }
    }

    //4
    private void getAllMessages(Context ctx) throws 
    JsonProcessingException { 
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }

    //5
    private void getMessagesById(Context ctx) throws 
    JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        int message_id = Integer.parseInt((ctx.pathParam("message_id")));
        Message messagesById = messageService.getMessageById(message_id);
        if(messagesById != null){
            ctx.json(mapper.writeValueAsString(messagesById));
        }else{
            ctx.status(200);
        }
    }

    //6
    private void deleteMessageById(Context ctx) throws 
    JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        //Message message = mapper.readValue(ctx.body(), Message.class);
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.deleteMessage(message_id);
        if(message != null){
            ctx.json(message);
        }else{
        ctx.status(200);
    }
    }

    //7
    private void updateMessage(Context ctx) throws 
    JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message updatedMessage =messageService.updateMessage(message_id, message.getMessage_text());
        System.out.println(updatedMessage);
        if(updatedMessage != null){
            ctx.json(mapper.writeValueAsString(updatedMessage));  
        }else{
            ctx.status(400); 
        }  
    }

    //8
    private void getMessagesForAccount(Context ctx) throws 
    JsonProcessingException{
        int account_id = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> messages = messageService.getMessagesByAccount(account_id);
        ctx.json(messages);
        }
    }



    /*
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     *//*
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
    }*/

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     *//*
    private void exampleHandler(Context context) {
        context.json("sample text");
    }*/


