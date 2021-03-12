import com.mysql.cj.exceptions.DataReadException;

import java.sql.*;

public class DataBase {

    private String pass;

    public DataBase(String pass) {
        this.pass = pass;
    }

    public boolean checkDoente(String numUtente) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.
                    getConnection("jdbc:mysql://localhost:3306/desk_services?user=root&password="
                            + this.pass + "&useTimezone=true&serverTimezone=UTC");

            Statement stmt = conn.createStatement();

            String sql = "select * from Doente where num_utente=" + numUtente + ";";
            ResultSet rss = stmt.executeQuery(sql);
            rss.next();

            boolean a = true;
            if (rss.next()==false) a = false;
            conn.close();

            return a;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    public void insertExame(String descricao, String sigla) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.
                    getConnection("jdbc:mysql://localhost:3306/desk_services?user=root&password="
                            + this.pass + "&useTimezone=true&serverTimezone=UTC");

            Statement stmt = conn.createStatement();

            boolean a = stmt.execute("insert into Exame values(0,\"" + descricao + "\", \"" + "" + "\", \"" + sigla + "\");");

            conn.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public int insertDoente(String nome, String telefone, String num_utente, String morada) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.
                    getConnection("jdbc:mysql://localhost:3306/desk_services?user=root&password="
                            + this.pass + "&useTimezone=true&serverTimezone=UTC");

            Statement stmt = conn.createStatement();

            boolean a = stmt.execute("insert into Doente (num_utente,nome,telefone,morada) values(\"" + num_utente + "\", \"" + nome + "\", \"" + telefone + "\", \"" + morada + "\");");

            Statement select = conn.createStatement();

            String sql = "select id_doente from Doente where num_utente=" + num_utente + ";";
            ResultSet rss = select.executeQuery(sql);
            rss.next();
            int a = rss.getInt(1);
            conn.close();
            return a;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertPedido(String descricao, String sigla) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.
                    getConnection("jdbc:mysql://localhost:3306/desk_services?user=root&password="
                            + this.pass + "&useTimezone=true&serverTimezone=UTC");

            Statement stmt = conn.createStatement();

            boolean a = stmt.execute("insert into Pedido values(0,\"" + descricao + "\", \"" + "" + "\", \"" + sigla + "\");");

            conn.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void get() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.
                    getConnection("jdbc:mysql://localhost:3306/desk_services?user=root&password="
                            + this.pass + "&useTimezone=true&serverTimezone=UTC");

            Statement stmt = conn.createStatement();
            Statement media = conn.createStatement();

            ResultSet rs = stmt.executeQuery("select * from tipo_exame");
            while (rs.next()) {
                int idPL = rs.getInt(1);
                System.out.println(idPL);
                String sql = "select descricao from tipo_exame where id_tipo_exame=" + idPL + ";";
                ResultSet rss = media.executeQuery(sql);
                rss.next();
                System.out.println(rss.getString(1));
            }

            conn.close();
        } catch ( Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
