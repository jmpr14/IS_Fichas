package aebd.group.webapp;

import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class RegistaPedido {

    public static void guardaPedido(Pedido pedido){

        String pass = "";

        try {
            FileReader arq = new FileReader("configs.txt");
            BufferedReader lerArq = new BufferedReader(arq);
            pass = lerArq.readLine();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            //step1 load the driver class
            Class.forName("com.mysql.cj.jdbc.Driver");

            //step2 create  the connection object
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/desk_services?user=root&password="
                            + pass + "&useTimezone=true&serverTimezone=UTC");

            //step3 create the statement object
            Statement stmt = con.createStatement();

            //step4 execute query

            String selectDoente = "select * from doente where num_utente = '" + pedido.getNumUtente() + "';";
            ResultSet resultDoente = stmt.executeQuery(selectDoente);
            if(!resultDoente.next()){
                String queryDoente = "insert into doente values (0,\"" + pedido.getNumUtente() + "\",\"" + pedido.getNome() + "\",\"" + pedido.getTelefone() + "\",\"" + pedido.getMorada() + "\");";
                int result = stmt.executeUpdate(queryDoente);
            }

            int id_doente = 0;
            String selectDoente1 = "select * from doente where num_utente = '" + pedido.getNumUtente() + "';";
            ResultSet resultDoente1 = stmt.executeQuery(selectDoente1);
            if(resultDoente1.next())
                id_doente = resultDoente1.getInt(1);


            String queryExame = "update exame set relatorio = + \"" + pedido.getRelatorio() + "\" where id_exame = " + pedido.getId_exame() + ";";
            int result1 = stmt.executeUpdate(queryExame);


            int estado = 1;
            String id_pedido = pedido.getNumPedido();

            String queryPedido = "update pedido set estado = 1 where id_pedido = " + id_pedido + ";";

            int result2 = stmt.executeUpdate(queryPedido);


            String queryPedido1 = "update pedido set Exame_id_exame = " + pedido.getId_exame() + " where id_pedido = " + id_pedido + ";";

            int result4 = stmt.executeUpdate(queryPedido1);


            String numep = pedido.getNum_Episodio();
            String queryPedido2 = "update pedido set num_ep = " + numep + " where id_pedido = " + id_pedido + ";";

            int result5 = stmt.executeUpdate(queryPedido2);

            String sql = "select notificacao from pedido where id_pedido = " + id_pedido + ";" ;
            ResultSet rss = stmt.executeQuery(sql);
            rss.next();

            if (rss.getInt(1) == 1) {
                Socket socket = new Socket("127.0.0.1", 65000);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                Notificador not = new Notificador(socket, out);
                System.out.println("Iniciar notificação!");
                not.notificador(id_pedido);
            }

            //step5 close the connection object
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
