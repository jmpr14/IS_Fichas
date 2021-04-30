import java.io.*;
import java.net.*;

public class Notificador implements Runnable{
    private volatile boolean running;

    public Notificador() {
        running = false;
    }

    public void stopThread() {
        running = false;
    }

    public void run() {
        running = true;
        ServerSocket ss = null;
        try {
            ss = new ServerSocket(6666);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (running) {
            Socket s = null;
            try {
                s = ss.accept();//establishes connection
                DataInputStream dis = new DataInputStream(s.getInputStream());
                String str = dis.readUTF();
                System.out.println("\nNOTIFICAÇÃO: Pedido " + str + " realizado!\n");
            } catch (Exception e) {
                try {
                    s.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                e.printStackTrace();
            }
        }
    }

}
