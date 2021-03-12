import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;


public class App {

    public static void main(String[] args) {

        String pass = "";
        try {
            FileReader arq = new FileReader("configs.txt");
            BufferedReader lerArq = new BufferedReader(arq);
            pass = lerArq.readLine();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        DataBase bd = new DataBase(pass);

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int op = -1;

        while (op != 0) {

            System.out.println("> INDIQUE O NÚMERO DA OPÇÃO");
            System.out.println("0 - Sair");
            System.out.println("1 - Novo Pedido");
            System.out.println("2 - Ver Pedidos");
            System.out.println("3 - Ver Relatórios");

            try {
                op = Integer.parseInt(reader.readLine());

                switch (op) {
                    case 1:
                        System.out.println("-- Insira número de Utente do paciente");
                        String numUtente = reader.readLine();
                        int id_doente;
                        if (!bd.checkDoente(numUtente)) {
                            System.out.println("---- NOVO REGISTO DE DOENTE");
                            System.out.println("-- Insira nome do paciente");
                            String nome = reader.readLine();
                            System.out.println("-- Insira a morada do paciente");
                            String morada = reader.readLine();
                            System.out.println("-- Insira o telefone do paciente");
                            String telefone = reader.readLine();

                            id_doente = bd.insertDoente(nome, telefone, numUtente, morada);
                        }
                        System.out.println("-- Insira a sigla do tipo de Exame");
                        String siglaExame = reader.readLine();
                        System.out.println("-- Insira descrição acerca do exame (opcional)");
                        String descricao = reader.readLine();

                        bd.insertExame(descricao, siglaExame);
                        java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
                        bd.insertPedido();

                        break;
                    case 2:

                        break;
                    case 3:

                        break;
                    default:
                        break;
                }
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }


    }
}
