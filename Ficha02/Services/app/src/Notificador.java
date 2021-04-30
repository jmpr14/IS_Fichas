import java.io.*;
import java.net.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class Notificador extends Thread{
    private final AtomicBoolean running;

    public Notificador() {
        running = new AtomicBoolean(false);
    }

    public void stopThread() {
        running.set(false);
    }

    public void run() {
        running.set(true);
        try {
            ServerSocket ss = new ServerSocket(6666);
            while (running.get()) {
                Socket s = ss.accept();//establishes connection
                DataInputStream dis = new DataInputStream(s.getInputStream());
                String str = dis.readUTF();
                System.out.println("\nNOTIFICAÇÃO: Pedido " + str + " realizado!\n");
            }
            ss.close();
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            System.out.println(e.getMessage());
        }
    }

}
