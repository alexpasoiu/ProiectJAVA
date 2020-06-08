package View;

import Model.Info;

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
    }
}
