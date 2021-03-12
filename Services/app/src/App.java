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

            System.out.println("\n> INDIQUE O NÚMERO DA OPERAÇÃO PRETENDIDA:");
            System.out.println("0 - Sair.");
            System.out.println("1 - Novo Pedido.");
            System.out.println("2 - Ver Pedidos.");
            System.out.println("3 - Ver Relatório.");
            System.out.println("4 - Cancelar Pedido.");

            try {
                op = Integer.parseInt(reader.readLine());

                switch (op) {
                    case 0:
                        System.out.println("\nA SAIR!");
                        break;
                    case 1:
                        System.out.println("-- Insira o número de Utente do paciente.");
                        String numUtente = reader.readLine();
                        int id_doente;
                        if (!bd.checkDoente(numUtente)) {
                            System.out.println("---- NOVO REGISTO DE DOENTE ----");
                            System.out.println("-- Insira o nome do paciente.");
                            String nome = reader.readLine();
                            System.out.println("-- Insira a morada do paciente.");
                            String morada = reader.readLine();
                            System.out.println("-- Insira o telefone do paciente.");
                            String telefone = reader.readLine();

                            id_doente = bd.insertDoente(nome, telefone, numUtente, morada);
                        } else id_doente = bd.getIdDoente(numUtente);
                        System.out.println("-- Insira a sigla do tipo de Exame.");
                        String siglaExame = reader.readLine();
                        System.out.println("-- Insira uma descrição acerca do exame. (opcional)");
                        String descricao = reader.readLine();

                        int id_exame = bd.insertExame(siglaExame);
                        bd.insertPedido(id_exame, id_doente, descricao);

                        break;
                    case 2:
                        bd.showPedidos();
                        break;
                    case 3:
                        System.out.println("-- Insira o número do Pedido do qual pretende consultar o Relatório.");
                        String id_Pedido = reader.readLine();
                        bd.showRelatorio(id_Pedido);
                    case 4:
                        System.out.println("-- Insira o número do Pedido a cancelar.");
                        String num_Pedido = reader.readLine();
                        if (bd.cancelarPedido(num_Pedido)) System.out.println("Pedido Cancelado com sucesso!");
                        else System.out.println("Operação inválida!");
                        break;
                    default:
                        System.out.println("\nInsira uma operação válida!");
                        break;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }


    }
}
