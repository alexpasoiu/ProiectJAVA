package View;

import Model.Info;

import java.io.Serializable;

public class Message implements Serializable {
    public String ID;
    public String action;
    public Info contact;

    public Message (Info contact, String action, String ID){
        this.contact = contact;
        this.action = action;
        this.ID = ID;
    }

}
