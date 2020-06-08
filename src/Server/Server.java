package Server;

import Model.Carriers;
import Model.Info;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server {
    private Thread thread;
    private Socket socket;
    private ServerSocket serverSocket;
    private int port;
    private HashMap<String, Info> contactMap;
    private Carriers carrierEnum;

    private HashMap<Socket, ClientWrapper> clientList;
    // private Communication database;

    public Server(int port){
        clientList = new HashMap<>();
        carrierEnum = new Carriers("Digi", "vodafone", "Orange");
        contactMap = new HashMap<>();
        this.port = port;//atribuire
        //MainDB mainDB = new MainDB();
        //database = mainDB.initDB();
        //loadContacts();

        try {
            serverSocket = new ServerSocket(this.port);//instantiere
        } catch (IOException e) {
            e.printStackTrace();//printeaza eroare
        }
        startServer();
    }
    public void startServer(){//asculta dupa conexiuni noi, cand gaseste ii atribuie un theread nou
        System.out.println("running server");
        while(true) try{
            socket = serverSocket.accept();//accepta conexiunea
            ClientWrapper clientWrapper = new ClientWrapper(socket);
            clientList.put(socket, clientWrapper);
            System.out.println(" acquiered new client " + socket.getLocalPort());
            thread = new Thread(){
                @Override
                public void run(){
                    clientWrapper.readObject(clientWrapper);//este infinit. PASEZ VARIABILA CLIENTWRAPPER
                    // Daca iese din metoda, inchide thread ul
                    this.interrupt();
                }
            };
            thread.start();//executa functia de run
            //sendInitialData(clientWrapper);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    class ClientWrapper {
        private Socket socket;
        private ObjectOutputStream objectOutputStream;
        private ObjectInputStream objectInputStream;

        public ClientWrapper(Socket socket) {
            this.socket = socket;
            try {
                objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                objectOutputStream.flush(); //curata streamul conform documentatiei
                objectInputStream = new ObjectInputStream(socket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public ObjectInputStream getObjectInputStream() {//returneaza instanta de objInput din instanta clientwrapper
            return objectInputStream;
        }

        public ObjectOutputStream getObjectOutputStream() {
            return objectOutputStream;
        }

        public Socket getSocket() {
            return socket;
        }

        public void readObject(ClientWrapper clientWrapper) {
            Socket socket = clientWrapper.getSocket();//returneaza instanta de getsocket DIN CLIENTWRAPPER
            while (!socket.isClosed()) {
                try {
                    Object receivedObject = clientWrapper.getObjectInputStream().readObject();//receivedObject returneaza OBIECTUL primit de la client
                    //respond(clientWrapper,(Message) receivedObject);
                } catch (Exception e) {
                    e.printStackTrace();
                    try {
                        socket.close();/// inchide socketul si iasa din while
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }
}
