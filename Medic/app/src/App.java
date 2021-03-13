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

        System.out.println("\n-------------------------------------------------------------");
        System.out.println("------------------------- MEDIC APP -------------------------");
        System.out.println("-------------------------------------------------------------");

        while (op != 0) {

            System.out.println("\n> INDIQUE O NÚMERO DA OPERAÇÃO PRETENDIDA:");
            System.out.println("0 - Sair.");
            System.out.println("1 - Ver Pedidos.");
            System.out.println("2 - Ver Relatório.");
            System.out.println("3 - Realizar exame.");

            try {
                op = Integer.parseInt(reader.readLine());

                switch (op) {
                    case 0:
                        System.out.println("\nA SAIR!");
                        break;
                    case 1:
                        bd.showPedidos();
                        break;
                    case 2:
                        System.out.println("-- Insira o número do Pedido de Exame do qual pretende consultar o Relatório.");
                        String id_Pedido = reader.readLine();
                        bd.showRelatorio(id_Pedido);
                        break;
                    case 3:
                        System.out.println("-- Insira o número do Pedido de Exame a realizar.");
                        String num_Pedido = reader.readLine();
                        System.out.println("---- Insira o número de Episódio da Consulta a iniciar.");
                        String num_ep = reader.readLine();
                        if (bd.realizarPedido(num_Pedido,num_ep)){
                            System.out.println("Exame em curso!");
                            System.out.println("---- Insira o Relatório do Exame realizado.");
                            String relatorio = reader.readLine();
                            bd.insertRelatorio(num_Pedido,relatorio);
                            bd.insertWorklist(Integer.parseInt(num_Pedido),1);
                            System.out.println("Exame Realizado com sucesso!");
                        }
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
