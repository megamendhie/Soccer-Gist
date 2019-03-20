package datafiles;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Message {
    private String messageUser;
    private String messageText;
    private String messageUserId;
    private long messageTime;
    private long  messageLikesCount;
    private Map<String, Boolean> messageLikes = new HashMap<>();

    public Message(String messageUser, String messageText, String messageUserId, long messageLikesCount, Map<String, Boolean> messageLikes) {
        this.messageUser = messageUser;
        this.messageText = messageText;
        messageTime = new Date().getTime();
        this.messageUserId = messageUserId;
        this.messageLikesCount = messageLikesCount;
        this.messageLikes= messageLikes;
    }

    public String getMessageUser() {
        return messageUser;
    }

    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageUserId() {
        return messageUserId;
    }

    public void setMessageUserId(String messageUserId) {
        this.messageUserId = messageUserId;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }

    public long getMessageLikesCount() {
        return messageLikesCount;
    }

    public void setMessageLikesCount(long messageLikesCount) {
        this.messageLikesCount = messageLikesCount;
    }

    public Map<String, Boolean> getMessageLikes() {
        return messageLikes;
    }

    public void setMessageLikes(Map<String, Boolean> messageLikes) {
        this.messageLikes = messageLikes;
    }
}
