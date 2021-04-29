import org.json.JSONObject;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;


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

        System.out.println("\n--------------------------------------------------------------");
        System.out.println("------------------------ SERVICES APP ------------------------");
        System.out.println("--------------------------------------------------------------");

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
                        String nome, morada, telefone;
                        int id_doente;
                        if (!bd.checkDoente(numUtente)) {
                            System.out.println("---- NOVO REGISTO DE DOENTE ----");
                            System.out.println("-- Insira o nome do paciente.");
                            nome = reader.readLine();
                            System.out.println("-- Insira a morada do paciente.");
                            morada = reader.readLine();
                            System.out.println("-- Insira o telefone do paciente.");
                            telefone = reader.readLine();

                            id_doente = bd.insertDoente(nome, telefone, numUtente, morada);

                        } else {
                            id_doente = bd.getIdDoente(numUtente);
                            nome = bd.getNomeIdDoente(numUtente);
                            morada = bd.getMoradaIdDoente(numUtente);
                            telefone = bd.getTelefoneIdDoente(numUtente);
                        }
                        System.out.println("-- Insira a sigla do tipo de Exame.");
                        String siglaExame = reader.readLine();

                        System.out.println("-- Insira o dia que pretende para marcar o exame, no formato yyyy-mm-dd.");
                        String dataExame = reader.readLine();
                        System.out.println("-- Insira a hora que pretende para marcar o exame, no formato hh:mm.");
                        String horaExame = reader.readLine();

                        System.out.println("-- Insira uma descrição acerca do exame. (opcional)");
                        String descricao = reader.readLine();

                        int id_exame = bd.insertExame(siglaExame, dataExame, horaExame);
                        int id_pedido = bd.insertPedido(id_exame, id_doente, descricao);
                        bd.insertWorklist(id_pedido,0);

                        JSONObject jo = new JSONObject();
                        // escrever para JSON
                        jo.put("id_doente", Integer.toString(id_doente));
                        jo.put("numUtente", numUtente);
                        jo.put("tipo", "NW");
                        jo.put("nome", nome);
                        jo.put("morada", morada);
                        jo.put("telefone", telefone);
                        jo.put("id_exame", Integer.toString(id_exame));
                        jo.put("siglaExame", siglaExame);
                        jo.put("dataExame", dataExame);
                        jo.put("horaExame", horaExame);
                        jo.put("id_pedido", Integer.toString(id_pedido));
                        jo.put("descricao", descricao);

                        try (FileWriter file = new FileWriter("./logs/"+id_pedido+".json")) {

                            file.write(jo.toString());
                            //System.out.println("Successfully Copied JSON Object to File...");
                            //System.out.println("\nJSON Object: " + jo);

                            file.flush();
                            file.close();
                        }

                        CreatePost.sendJson(jo);

                        break;
                    case 2:
                        bd.showPedidos();
                        break;
                    case 3:
                        System.out.println("-- Insira o número do Pedido do qual pretende consultar o Relatório.");
                        String id_Pedido = reader.readLine();
                        bd.showRelatorio(id_Pedido);
                        break;
                    case 4:
                        System.out.println("-- Insira o número do Pedido a cancelar.");
                        String num_Pedido = reader.readLine();
                        if (bd.cancelarPedido(num_Pedido)){
                            System.out.println("Pedido Cancelado com sucesso!");
                            bd.insertWorklist(Integer.parseInt(num_Pedido),2);

                            String iddoente = bd.getIdDoenteIdPedido(num_Pedido);
                            String numeroutente = bd.getNumUtenteIdDoente(iddoente);
                            String nomeutente = bd.getNomeIdDoente(numeroutente);
                            String telefoneutente = bd.getTelefoneIdDoente(numeroutente);
                            String moradautente = bd.getMoradaIdDoente(numeroutente);
                            String idexame =bd.getIDExameIDPedido(num_Pedido);
                            String siglaexame_ = bd.getSiglaExameIDExame(idexame);
                            String descricao_ = bd.getDescricaoIdPedido(num_Pedido);
                            String data_exame = bd.getDataIdExame(idexame);
                            String hora_exame = bd.getHoraIdExame(idexame);

                            // escrever para JSON
                            JSONObject cancel = new JSONObject();
                            // escrever para JSON
                            cancel.put("id_doente", iddoente);
                            cancel.put("numUtente", numeroutente);
                            cancel.put("tipo", "CA");
                            cancel.put("nome", nomeutente);
                            cancel.put("morada", moradautente);
                            cancel.put("telefone", telefoneutente);
                            cancel.put("id_exame", idexame);
                            cancel.put("siglaExame", siglaexame_);
                            cancel.put("dataExame", data_exame);
                            cancel.put("horaExame", hora_exame);
                            cancel.put("id_pedido", num_Pedido);
                            cancel.put("descricao", descricao_);

                            try (FileWriter file = new FileWriter("./cancel/"+num_Pedido+".json")) {

                                file.write(cancel.toString());
                                //System.out.println("Successfully Copied JSON Object to File...");
                                //System.out.println("\nJSON Object: " + cancel);

                                file.flush();
                                file.close();
                            }
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
