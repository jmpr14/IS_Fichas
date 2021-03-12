import java.sql.*;

public class DataBase {

    private final String pass;

    public DataBase(String pass) {
        this.pass = pass;
    }

    public boolean checkDoente(String numUtente) {
        boolean a = true;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.
                    getConnection("jdbc:mysql://localhost:3306/desk_services?user=root&password="
                            + this.pass + "&useTimezone=true&serverTimezone=UTC");

            Statement stmt = conn.createStatement();

            String sql = "select * from Doente where num_utente=" + numUtente + ";";
            ResultSet rss = stmt.executeQuery(sql);

            if (!rss.next()) a = false;
            conn.close();

            return a;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return a;
    }

    public int insertExame(String sigla) {
        int a = 0;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.
                    getConnection("jdbc:mysql://localhost:3306/desk_services?user=root&password="
                            + this.pass + "&useTimezone=true&serverTimezone=UTC");

            Statement stmt = conn.createStatement();

            stmt.execute("insert into Exame values(0,\"" + "" + "\", \"" + sigla + "\");");

            Statement select = conn.createStatement();

            String sql = "SELECT LAST_INSERT_ID();";
            ResultSet rss = select.executeQuery(sql);
            rss.next();
            a = rss.getInt(1);
            conn.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return a;
    }

    public int insertDoente(String nome, String telefone, String num_utente, String morada) {
        int a = 0;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.
                    getConnection("jdbc:mysql://localhost:3306/desk_services?user=root&password="
                            + this.pass + "&useTimezone=true&serverTimezone=UTC");

            Statement stmt = conn.createStatement();

            stmt.execute("insert into Doente (num_utente,nome,telefone,morada) values(\"" + num_utente + "\", \"" + nome + "\", \"" + telefone + "\", \"" + morada + "\");");

            Statement select = conn.createStatement();

            String sql = "select id_doente from Doente where num_utente=" + num_utente + ";";
            ResultSet rss = select.executeQuery(sql);
            rss.next();
            a = rss.getInt(1);
            conn.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return a;
    }

    public int getIdDoente(String num_utente) {
        int a = 0;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.
                    getConnection("jdbc:mysql://localhost:3306/desk_services?user=root&password="
                            + this.pass + "&useTimezone=true&serverTimezone=UTC");

            Statement select = conn.createStatement();

            String sql = "select id_doente from Doente where num_utente=" + num_utente + ";";
            ResultSet rss = select.executeQuery(sql);
            rss.next();
            a = rss.getInt(1);
            conn.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return a;
    }

    public void insertPedido(int id_exame, int id_doente, String descricao) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.
                    getConnection("jdbc:mysql://localhost:3306/desk_services?user=root&password="
                            + this.pass + "&useTimezone=true&serverTimezone=UTC");

            Statement stmt = conn.createStatement();

            stmt.execute("insert into Pedido values(0, NOW(), \"" + "0" + "\", \"" + "0" + "\", \"" + id_exame + "\", \"" + id_doente + "\", \"" + descricao + "\");");

            conn.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void showPedidos() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.
                    getConnection("jdbc:mysql://localhost:3306/desk_services?user=root&password="
                            + this.pass + "&useTimezone=true&serverTimezone=UTC");

            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery("select * from Pedido");

            ResultSetMetaData rsmd = rs.getMetaData();

            System.out.println("--------------------------------------------------------------------------");
            System.out.println("| PEDIDO |      DATETIME      |EPISÓDIO|   ESTADO   |  EXAME  |    PACIENTE   |  DESCRIÇÂO");
            int columnsNumber = rsmd.getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i == 1) System.out.print("    ");
                    else System.out.print("     ");
                    String columnValue = rs.getString(i);
                    switch (i) {
                        case 4:
                            if (Integer.parseInt(columnValue) == 0) System.out.print("Em espera");
                            else if (Integer.parseInt(columnValue) == 1) System.out.print("Realizado");
                            else System.out.print("Cancelado");
                            break;
                        case 5:
                            int id_Exame = Integer.parseInt(columnValue);
                            Statement media1 = conn.createStatement();
                            ResultSet rss1 = media1.executeQuery("select sigla from Exame where id_exame=" + id_Exame + ";");
                            rss1.next();
                            System.out.print(rss1.getString(1));
                            break;
                        case 6:
                            int id_Doente = Integer.parseInt(columnValue);
                            Statement media2 = conn.createStatement();
                            ResultSet rss2 = media2.executeQuery("select nome from Doente where id_doente=" + id_Doente + ";");
                            rss2.next();
                            System.out.print(rss2.getString(1));
                            break;
                        default:
                            System.out.print(columnValue);
                            break;
                    }

                }
                System.out.println();
            }
            System.out.println("--------------------------------------------------------------------------\n");
            conn.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void showRelatorio(String id_Pedido) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.
                    getConnection("jdbc:mysql://localhost:3306/desk_services?user=root&password="
                            + this.pass + "&useTimezone=true&serverTimezone=UTC");

            Statement stmt = conn.createStatement();
            boolean exists = true;
            String sql = "select * from Pedido where id_pedido=" + id_Pedido + ";";
            ResultSet rss = stmt.executeQuery(sql);

            if (!rss.next()) exists = false;

            if (exists) {
                String query = "select relatorio from Pedido,Exame where id_pedido=" + id_Pedido + " AND Exame_id_exame = id_exame;";
                ResultSet rs = stmt.executeQuery(query); rs.next();
                System.out.println("\nRelatório do Pedido número " + id_Pedido + ":");
                System.out.println(rs.getString(1) + "\n");
            } else System.out.println("Operação Inválida!");

            conn.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean cancelarPedido(String id_Pedido) {
        boolean a = true;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.
                    getConnection("jdbc:mysql://localhost:3306/desk_services?user=root&password="
                            + this.pass + "&useTimezone=true&serverTimezone=UTC");

            Statement select = conn.createStatement();

            String sql = "select estado from Pedido WHERE id_pedido = " + id_Pedido + ";";
            ResultSet rss = select.executeQuery(sql);
            rss.next();

            if (rss.getInt(1) == 0) {
                Statement stmt = conn.createStatement();
                stmt.execute("UPDATE Pedido SET estado = 2 WHERE id_pedido = " + id_Pedido + ";");
            } else a = false;

            conn.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return a;
    }


}
