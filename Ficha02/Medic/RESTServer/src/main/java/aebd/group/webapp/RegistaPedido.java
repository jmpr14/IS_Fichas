package aebd.group.webapp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
                    "jdbc:mysql://localhost:3306/desk_medic?user=root&password="
                            + pass + "&useTimezone=true&serverTimezone=UTC");

//step3 create the statement object
            Statement stmt = con.createStatement();

//step4 execute query

            String selectDoente = "select * from doente where num_utente = '" + pedido.getNumUtente() + "';";
            ResultSet resultDoente = stmt.executeQuery(selectDoente);
            if(!resultDoente.next()){
                String queryDoente = "insert into doente values (0,'" + pedido.getNumUtente() + "','" + pedido.getNome() + "','" + pedido.getTelefone() + "','" + pedido.getMorada() + "');";
                int result = stmt.executeUpdate(queryDoente);
            }

            String queryExame = "insert into exame values (0,'','" + pedido.getSiglaExame() + "," + pedido.getDataExame() + ",\"" + pedido.getHoraExame() + "\"');";
            int result1 = stmt.executeUpdate(queryExame);

            int estado = 2;
            if(pedido.getTipo().equals("NW")){
                estado = 0;

                String queryPedido = "insert into pedido values (" + pedido.getId_pedido() + "," + pedido.getDataExame() + ",0"
                        + "," + estado + "," + pedido.getId_exame() + "," + pedido.getId_doente() + ",'" + pedido.getDescricao() + "');";

                int result2 = stmt.executeUpdate(queryPedido);

                String selectPedido = "SELECT id_pedido FROM pedido ORDER BY id_pedido DESC LIMIT 1;";
                ResultSet resultPedido = stmt.executeQuery(selectPedido);

                String queryWorklist = "insert into worklist values (0," + pedido.getId_pedido() + ",\"" + pedido.getDataExame() + " " + pedido.getHoraExame() + "\"," + estado + ");";
                int result3 = stmt.executeUpdate(queryWorklist);
            }

            if (estado == 2) {

                String queryPedido1 = "update pedido set estado = 2 where id_pedido = " + pedido.getId_pedido() + ";";

                int result5 = stmt.executeUpdate(queryPedido1);

            }


//step5 close the connection object
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
