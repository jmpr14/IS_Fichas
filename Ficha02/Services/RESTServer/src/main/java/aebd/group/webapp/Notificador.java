package aebd.group.webapp;

import java.io.DataOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class Notificador {
    private Socket socket;
    private PrintWriter out;

    public Notificador( Socket conn,PrintWriter o){

        socket=conn;
        out=o;
    }

    public Socket getSocket(){
        return socket;
    }

    public PrintWriter getPrintwriter(){
        return out;
    }

    public void notificador(String message) {
        try {
            DataOutputStream dout = new DataOutputStream(socket.getOutputStream());
            dout.writeUTF(message);
            dout.flush();
            dout.close();
            socket.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}