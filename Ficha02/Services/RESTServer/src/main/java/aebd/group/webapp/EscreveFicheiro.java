package aebd.group.webapp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;

public class EscreveFicheiro {

    public static void escrever(String json, String id_pedido){
        try {
            File dir = new File("..\\Received");
            if(!dir.exists())
                Files.createDirectory(dir.toPath());
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
