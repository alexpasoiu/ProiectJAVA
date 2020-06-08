package View;

import Model.Info;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

public class Client {
    private Socket socket;
    private Thread thread;
    private int port;
    private boolean foundHost = false;
    private ObjectOutputStream objectOutputStream = null;
    private ObjectInputStream objectInputStream = null;
    private HashMap<String, Info> contacts;
    private Interfata ui;

    public Client(int port, HashMap<String,Info> contacts, Interfata ui){
        this.contacts = contacts;
        this.ui = ui;
        this.port = port;
        connectAsClient(this.port);
    }
    private void connectAsClient(Integer port) {
        while (!this.foundHost) {// cat timp nu a gasit server, incearca sa se conecteze pana gaseste
            try {
                socket = new Socket("127.0.0.1", port);
                objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                objectOutputStream.flush(); //curata stream ul conform documentatiei
                objectInputStream = new ObjectInputStream(socket.getInputStream());
                foundHost = true;
                System.out.println("CONNECTED TO SERVER");
            } catch (Exception e) {
                System.out.println("Couldnt connect to server...");
                foundHost = false;
            }
        }
        thread = new Thread(){
            @Override
            public void run() {
                receiveObject();
                this.interrupt();
            }
        };
        thread.start();
    }
    public void sendObject(Object object) {
        if (foundHost) {
            try {
                this.objectOutputStream.writeObject(object);
            } catch (IOException io) {
                io.printStackTrace();
            }
        }
    }
    public void receiveObject() {
        while (foundHost) {
            try {
                Object obj = objectInputStream.readObject();//variabila "obj" returneaza obiectul de pe inputstream care a fost trimis de server
                handle((Message) obj);
            } catch (Exception e){
                foundHost=false;
                e.printStackTrace();
            }
        }
    }
    public void handle(Message message){
        if(message.action.equals(Action.ADD)){
            contacts.put(message.ID, message.contact);
            Info contact = message.contact;
            ui.updateComboBox();
        }
        if(message.action.equals(Action.FAIL)){
            JOptionPane.showMessageDialog(null,"Contactul este deja existent");
        }
        if(message.action.equals(Action.SUCCESS)){
            JOptionPane.showMessageDialog(null,"Contactul a fost adaugat in baza de date");
        }
        if(message.action.equals((Action.REMOVE))){
            contacts.remove(message.ID);
            ui.updateComboBox();
            JOptionPane.showMessageDialog(null,"Contactul a fost sters din baza de date");
        }
        if(message.action.equals(Action.MODIFY)){
            contacts.replace(message.ID,message.contact);
            ui.updateComboBox();
            JOptionPane.showMessageDialog(null,"Contactul a fost modificat");
        }
    }
}
