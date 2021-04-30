package aebd.group.webapp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class EscreveFicheiro {

    public static void escrever(String json, String id_pedido){
        try {
            File fich = new File("..\\Received\\" + id_pedido + ".json");
            BufferedWriter buffWrite = new BufferedWriter(new FileWriter(fich.getPath()));
            buffWrite.write(json);
            buffWrite.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
