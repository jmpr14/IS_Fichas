import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

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

        Scanner scanner = new Scanner(System.in);
        int op = -1;

        while (op != 0) {

            System.out.println("> INDIQUE O NÚMERO DA OPÇÃO");
            System.out.println("0 - Sair");
            System.out.println("1 - Novo Pedido");
            System.out.println("2 - Ver Pedidos");
            System.out.println("3 - Ver Relatórios");

            op = scanner.nextInt();

            switch (op) {
                case 1:
                    System.out.println("-- Insira número de Utente do paciente");
                    String numUtente = scanner.next();
                    if (!bd.checkDoente(numUtente)) {
                        System.out.println("-- Insira nome do paciente");
                        String nome = scanner.nextLine();
                        System.out.println("-- Insira a morada do paciente");
                        String morada = scanner.nextLine();
                        System.out.println("-- Insira o telefone do paciente");
                        String telefone = scanner.nextLine();

                        bd.insertDoente(nome, telefone, numUtente, morada);
                    }
                    System.out.println("-- Insira a sigla do tipo de Exame");
                    String siglaExame = scanner.nextLine();
                    //System.out.println("-- Insira o número do episódio");
                    //Integer numEpisodio = scanner.nextInt();
                    System.out.println("-- Insira descrição acerca do exame (opcional)");
                    String descricao = scanner.nextLine();

                    bd.insertExame(descricao, siglaExame);

                    break;
                case 2:

                    break;
                case 3:

                    break;
                default:
                    break;
            }
        }


    }
}
