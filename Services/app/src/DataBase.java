import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DataBase {

    public static void insertTipoExame(String pass, String sigla, String descricao){

        try
        {
//step1 load the driver class
            Class.forName("com.mysql.cj.jdbc.Driver");

//step2 create  the connection object
            //Connection con = DriverManager.getConnection(
            //      "jdbc:mysql://localhost:1521/orclpdb1.localdomain", "monitor", "monitor");

            Connection conn = DriverManager.
                    getConnection("jdbc:mysql://localhost:3306/desk_services?user=root&password="
                            + pass +"&useTimezone=true&serverTimezone=UTC");
//step3 create the statement object
            Statement stmt = conn.createStatement();

//step4 execute query
            boolean a = stmt.execute("insert into Tipo_Exame values(0,\"" + sigla + "\", \"" + descricao + "\");");

//step5 close the connection object
            conn.close();
        } catch(
                Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public static void get(String pass) {

        try
        {
//step1 load the driver class
            Class.forName("com.mysql.cj.jdbc.Driver");

//step2 create  the connection object
            //Connection con = DriverManager.getConnection(
              //      "jdbc:mysql://localhost:1521/orclpdb1.localdomain", "monitor", "monitor");

            Connection conn = DriverManager.
                    getConnection("jdbc:mysql://localhost:3306/desk_services?user=root&password="
                            + pass +"&useTimezone=true&serverTimezone=UTC");
//step3 create the statement object
            Statement stmt = conn.createStatement();
            Statement media = conn.createStatement();

//step4 execute query
            ResultSet rs = stmt.executeQuery("select * from tipo_exame");
            while (rs.next()) {
                int idPL = rs.getInt(1);
                System.out.println(idPL);
                String sql = "select descricao from tipo_exame where id_tipo_exame=" + idPL + ";";
                ResultSet rss = media.executeQuery(sql);
                rss.next();
                System.out.println(rss.getString(1));
            }

//step5 close the connection object
            conn.close();
        } catch(
                Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
