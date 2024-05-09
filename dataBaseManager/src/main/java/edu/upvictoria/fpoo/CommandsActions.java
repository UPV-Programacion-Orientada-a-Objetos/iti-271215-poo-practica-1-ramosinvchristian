package edu.upvictoria.fpoo;
import java.io.*;

public class CommandsActions {

    static String path = "/home/ramos/Desktop/iti-271215-poo-practica-1-ramosinvchristian/dataBaseManager/src/main/$PATH$";
    public static BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));

    public static void showTables() {
        System.out.println("===============LIST OF TABLES===============");
        File directory = new File(path);
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file0 : files) {
                System.out.println(file0.getName());
            }
        } else {
            System.out.println("Does not exist tables to show.");
        }
    }
}