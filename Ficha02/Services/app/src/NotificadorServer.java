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
        while (running) {
            try {
                Socket conn = socket.accept();
                System.out.println("Ligação Aceite!");
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                String r;
                while (socket.isBound()) {
                    r = in.readLine();

                    while ((r.isEmpty() != true)) {
                        System.out.println("\nNOTIFICAÇÃO: Pedido " + r + " realizado!\n");
                        r = in.readLine();
                    }
                }
            } catch (Exception e) {
            }
        }
    }

}
