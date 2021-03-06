import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DataBase {

    private final String pass;

    public DataBase(String pass) {
        this.pass = pass;
    }

    public boolean checkWorklist(Integer idPedido) {
        boolean a = true;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.
                    getConnection("jdbc:mysql://localhost:3306/desk_medic?user=root&password="
                            + this.pass + "&useTimezone=true&serverTimezone=UTC");

            Statement stmt = conn.createStatement();

            String sql = "select * from Worklist where Pedido_id_pedido=" + idPedido + ";";
            ResultSet rss = stmt.executeQuery(sql);

            if (!rss.next()) a = false;
            conn.close();

            return a;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return a;
    }

    public void showPedidos() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.
                    getConnection("jdbc:mysql://localhost:3306/desk_medic?user=root&password="
                            + this.pass + "&useTimezone=true&serverTimezone=UTC");

            Statement stmt = conn.createStatement();
            ResultSet rs;
            int columnsNumber;

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("-- 1 - ver todos.");
            System.out.println("-- 2 - consultar pedidos para hoje.");
            System.out.println("-- 3 - consultar pedidos de uma data específica.");

            int input = Integer.parseInt(reader.readLine());
            if(input==3){
                System.out.println("-- Insira a data que pretende consultar, no formato yyyy-mm-dd.");
                String dia = reader.readLine();

                rs = stmt.executeQuery("SELECT id_pedido, num_ep, estado, Exame_id_exame, Doente_id_doente, data, hora, descricao FROM Pedido, Exame WHERE Pedido.Exame_id_exame = Exame.id_exame AND Exame.data = \"" + dia + "\";");
                ResultSetMetaData rsmd = rs.getMetaData();
                columnsNumber = rsmd.getColumnCount();
            }
            else if(input==2){
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
                String today = LocalDate.now().format(formatter);
                System.out.println(today);

                rs = stmt.executeQuery("SELECT id_pedido, num_ep, estado, Exame_id_exame, Doente_id_doente, data, hora, descricao FROM Pedido, Exame WHERE Pedido.Exame_id_exame = Exame.id_exame AND Exame.data = \"" + today + "\";");
                ResultSetMetaData rsmd = rs.getMetaData();
                columnsNumber = rsmd.getColumnCount();
            }
            else{
                System.out.println();
                rs = stmt.executeQuery("SELECT id_pedido, num_ep, estado, Exame_id_exame, Doente_id_doente, data, hora, descricao FROM Pedido, Exame WHERE Pedido.Exame_id_exame = Exame.id_exame;");
                ResultSetMetaData rsmd = rs.getMetaData();
                columnsNumber = rsmd.getColumnCount();
            }

            System.out.println("-----------------------------------------------------------------------------------------------");
            System.out.println("| PEDIDO |EPISÓDIO|   ESTADO   |  EXAME  |     PACIENTE     |     DATA     |     HORA    | DESCRIÇÃO");

            while (rs.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i == 1) System.out.print("     ");
                    else System.out.print("      ");
                    String columnValue = rs.getString(i);
                    switch (i) {
                        case 3:
                            if (Integer.parseInt(columnValue) == 0) System.out.print("Em espera");
                            else if (Integer.parseInt(columnValue) == 1) System.out.print("Realizado");
                            else System.out.print("Cancelado");
                            break;
                        case 4:
                            int id_Exame = Integer.parseInt(columnValue);
                            Statement media1 = conn.createStatement();
                            ResultSet rss1 = media1.executeQuery("select sigla from Exame where id_exame=" + id_Exame + ";");
                            rss1.next();
                            System.out.print(rss1.getString(1));
                            break;
                        case 5:
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
            System.out.println("-----------------------------------------------------------------------------------------------\n");
            conn.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void showRelatorio(String id_Pedido) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.
                    getConnection("jdbc:mysql://localhost:3306/desk_medic?user=root&password="
                            + this.pass + "&useTimezone=true&serverTimezone=UTC");

            Statement stmt = conn.createStatement();
            boolean exists = true;
            String sql = "select * from Pedido where id_pedido=" + id_Pedido + ";";
            ResultSet rss = stmt.executeQuery(sql);

            if (!rss.next()) exists = false;

            if (exists) {
                String query = "select relatorio from Pedido,Exame where id_pedido=" + id_Pedido + " AND Exame_id_exame = id_exame;";
                ResultSet rs = stmt.executeQuery(query);
                rs.next();
                System.out.println("\nRelatório do Pedido número " + id_Pedido + ":");
                System.out.println(rs.getString(1) + "\n");
            } else System.out.println("Operação Inválida!");

            conn.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean realizarPedido(String id_Pedido, String num_ep) {
        boolean a = true;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.
                    getConnection("jdbc:mysql://localhost:3306/desk_medic?user=root&password="
                            + this.pass + "&useTimezone=true&serverTimezone=UTC");

            Statement select = conn.createStatement();

            String sql = "select estado from Pedido WHERE id_pedido = " + id_Pedido + ";";
            ResultSet rss = select.executeQuery(sql);
            rss.next();

            if (rss.getInt(1) == 0) {
                Statement estado = conn.createStatement();
                estado.execute("UPDATE Pedido SET estado = 1 WHERE id_pedido = " + id_Pedido + ";");
                Statement episodio = conn.createStatement();
                episodio.execute("UPDATE Pedido SET num_ep = " + num_ep + " WHERE id_pedido = " + id_Pedido + ";");
            } else a = false;

            conn.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return a;
    }


    public String getIdDoenteIdPedido(String id_pedido) {
        String a = "";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.
                    getConnection("jdbc:mysql://localhost:3306/desk_medic?user=root&password="
                            + this.pass + "&useTimezone=true&serverTimezone=UTC");

            Statement select = conn.createStatement();


            String sql = "select Doente_id_doente from Pedido WHERE id_pedido = " + id_pedido + ";";
            //System.out.println(sql);

            ResultSet rss = select.executeQuery(sql);

            rss.next();
            a = rss.getString(1);
            //System.out.println("a: " +a);
            conn.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return a;
    }


    public String getNumUtenteIdDoente(String id_doente) {
        String a = "";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.
                    getConnection("jdbc:mysql://localhost:3306/desk_medic?user=root&password="
                            + this.pass + "&useTimezone=true&serverTimezone=UTC");

            Statement select = conn.createStatement();

            String sql = "select num_utente from Doente where id_doente=" + id_doente + ";";
            ResultSet rss = select.executeQuery(sql);
            rss.next();
            a = rss.getString(1);
            conn.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return a;
    }


    public String getNomeIdDoente(String num_utente) {
        String a = "";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.
                    getConnection("jdbc:mysql://localhost:3306/desk_medic?user=root&password="
                            + this.pass + "&useTimezone=true&serverTimezone=UTC");

            Statement select = conn.createStatement();

            String sql = "select nome from Doente where num_utente=" + num_utente + ";";
            ResultSet rss = select.executeQuery(sql);
            rss.next();
            a = rss.getString(1);
            conn.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return a;
    }

    public String getMoradaIdDoente(String num_utente) {
        String a = "";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.
                    getConnection("jdbc:mysql://localhost:3306/desk_medic?user=root&password="
                            + this.pass + "&useTimezone=true&serverTimezone=UTC");

            Statement select = conn.createStatement();

            String sql = "select morada from Doente where num_utente=" + num_utente + ";";
            ResultSet rss = select.executeQuery(sql);
            rss.next();
            a = rss.getString(1);
            conn.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return a;
    }

    public String getTelefoneIdDoente(String num_utente) {
        String a = "";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.
                    getConnection("jdbc:mysql://localhost:3306/desk_medic?user=root&password="
                            + this.pass + "&useTimezone=true&serverTimezone=UTC");

            Statement select = conn.createStatement();

            String sql = "select telefone from Doente where num_utente=" + num_utente + ";";
            ResultSet rss = select.executeQuery(sql);
            rss.next();
            a = rss.getString(1);
            conn.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return a;
    }


    public String getDescricaoIdPedido(String id_pedido) {
        String a = "";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.
                    getConnection("jdbc:mysql://localhost:3306/desk_medic?user=root&password="
                            + this.pass + "&useTimezone=true&serverTimezone=UTC");

            Statement select = conn.createStatement();

            String sql = "select descricao from pedido where id_pedido=" + id_pedido + ";";
            ResultSet rss = select.executeQuery(sql);
            rss.next();
            a = rss.getString(1);
            conn.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return a;
    }


    public String getIDExameIDPedido(String id_pedido) {
        String a = "";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.
                    getConnection("jdbc:mysql://localhost:3306/desk_medic?user=root&password="
                            + this.pass + "&useTimezone=true&serverTimezone=UTC");

            Statement select = conn.createStatement();

            String sql = "select Exame_id_exame from pedido where id_pedido=" + id_pedido + ";";
            ResultSet rss = select.executeQuery(sql);
            rss.next();
            a = rss.getString(1);
            conn.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return a;
    }


    public String getSiglaExameIDExame(String id_exame) {
        String a = "";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.
                    getConnection("jdbc:mysql://localhost:3306/desk_medic?user=root&password="
                            + this.pass + "&useTimezone=true&serverTimezone=UTC");

            Statement select = conn.createStatement();

            String sql = "select sigla from exame where id_exame=" + id_exame + ";";
            ResultSet rss = select.executeQuery(sql);
            rss.next();
            a = rss.getString(1);
            conn.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return a;
    }


    public void insertRelatorio(String id_pedido, String relatorio) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.
                    getConnection("jdbc:mysql://localhost:3306/desk_medic?user=root&password="
                            + this.pass + "&useTimezone=true&serverTimezone=UTC");

            Statement select = conn.createStatement();

            String sql = "select Exame_id_exame from Pedido WHERE id_pedido = " + id_pedido + ";";
            ResultSet rss = select.executeQuery(sql);
            rss.next();
            int id_exame = rss.getInt(1);

            Statement stmt = conn.createStatement();
            stmt.execute("UPDATE Exame SET relatorio = \"" + relatorio + "\" WHERE id_exame = " + id_exame + ";");

            conn.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertWorklist(int id_pedido, int alteracao) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.
                    getConnection("jdbc:mysql://localhost:3306/desk_medic?user=root&password="
                            + this.pass + "&useTimezone=true&serverTimezone=UTC");

            if (checkWorklist(id_pedido)) {
                Statement stmt = conn.createStatement();
                stmt.execute("update Worklist set alteracao = " + alteracao + " where Pedido_id_pedido = " + id_pedido + " ;");
                conn.close();
            } else {
                Statement stmt = conn.createStatement();
                stmt.execute("insert into Worklist values(0,\"" + id_pedido + "\", NOW(), \"" + alteracao + "\");");
                conn.close();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
