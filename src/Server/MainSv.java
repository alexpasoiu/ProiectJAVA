package Server;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MainSv {
    //initialize socket and input stream
    private Socket socket   = null;
    private ServerSocket server   = null;
    private DataInputStream in       =  null;
    // constructor with port

    public static void main(String[] args) {
        Server server =new Server(5000);
    }
}
