package aebd.group.webapp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class LerPasta {

    public static String lerFicheiro(){

        File f = new File("../app/logs");

        StringBuilder msg = new StringBuilder();

        File fich = new File("");
        if(f.exists() && f.list().length >0){
            fich = f.listFiles()[0];

            try {
                BufferedReader bf = new BufferedReader(new FileReader(fich));

                while(bf.ready()){
                    msg.append(bf.readLine());
                }

                bf.close();

                File dir = new File("..\\app");
                String path = dir.getCanonicalPath();

                System.out.println(fich.getName());
                System.out.println(msg.toString());

                /*
                fich.deleteOnExit();

                System.out.println(fich.getAbsolutePath());
                System.out.println(path);
                System.out.println(path + "\\logs_done\\" + fich.getName());

                String pathFrom = path + "\\logs\\" + fich.getName();
                String pathTo = path + "\\logs_done\\" + fich.getName();
                //Files.move(Path.of(pathFrom), Path.of(pathTo));
                */
                boolean ret = fich.renameTo(new File(path + "\\logs_done\\" + fich.getName()));
                System.out.println(ret);
/*
                File teste = new File("C:\\Users\\José Rodrigues\\Desktop\\3.json");
                System.out.println(teste.delete());*/
            }

            catch (Exception e){
                System.out.println(e.getMessage());
            }
        }

        return msg.toString();
    }

    public static void main(String[] args) {
        while (true){
            lerFicheiro();
        }
    }
}
