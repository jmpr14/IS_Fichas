import java.io.*;
import java.net.*;

public class NotificadorServer implements Runnable{
    volatile boolean running;
    ServerSocket socket;

    public NotificadorServer(ServerSocket s) {
        socket = s;
        running = false;
    }

    public void stopThread() {
        running = false;
    }

    public void run() {
        running = true;
        while (running) {
            try {
                Socket conn = socket.accept();
                DataInputStream in = new DataInputStream(conn.getInputStream());
                String r = in.readUTF();
                System.out.println("\nNOTIFICAÇÃO: Pedido " + r + " realizado!\n");
                in.close();
                conn.close();
            } catch (Exception e) {}
        }
    }

}
