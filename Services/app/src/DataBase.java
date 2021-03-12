import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DataBase {

    public static boolean getUser(String pass, int numPaciente) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.
                    getConnection("jdbc:mysql://localhost:3306/desk_services?user=root&password="
                            + pass + "&useTimezone=true&serverTimezone=UTC");

            Statement stmt = conn.createStatement();

            String sql = "select * from Doente where id_doente=" + numPaciente + ";";
            ResultSet rss = stmt.executeQuery(sql);
            rss.next();

            conn.close();
            boolean a = true;
            if (rss.getString(1)=="") a = false;

        } catch (
                Exception e) {
            System.out.println(e.getMessage());
        }

        return a;
    }

    public static void insertTipoExame(String pass, String sigla, String descricao) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.
                    getConnection("jdbc:mysql://localhost:3306/desk_services?user=root&password="
                            + pass + "&useTimezone=true&serverTimezone=UTC");

            Statement stmt = conn.createStatement();

            boolean a = stmt.execute("insert into Tipo_Exame values(0,\"" + sigla + "\", \"" + descricao + "\");");

            conn.close();
        } catch (
                Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void get(String pass) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.
                    getConnection("jdbc:mysql://localhost:3306/desk_services?user=root&password="
                            + pass + "&useTimezone=true&serverTimezone=UTC");

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
        } catch (
                Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
