import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        String configs;

        try {
            FileReader arq = new FileReader("configs.txt");
            BufferedReader lerArq = new BufferedReader(arq);

            configs = lerArq.readLine();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        int op = -1;

        while(op != 0){

            System.out.println("0 - Sair");
            System.out.println("1 - Novo Pedido");
            System.out.println("2 - Novo Exame");
            System.out.println("3 - Alterar Exame");

            op = scanner.nextInt();

            switch (op){
                case 1:

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
