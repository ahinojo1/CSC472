package csc472.depaul.edu.messender;

public class Message {
    String msg;
    String source;
    String destination;

    public Message(String msg){
        this.msg = msg;
    }

    public Message(String msg, String source){
        this.msg = msg;
        this.source = source;
    }

    public Message(String msg, String source, String destination){
        this.msg = msg;
        this.source = source;
        this.destination = destination;
    }
}
