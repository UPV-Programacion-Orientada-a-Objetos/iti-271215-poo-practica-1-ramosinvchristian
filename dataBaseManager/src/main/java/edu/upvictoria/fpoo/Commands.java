package edu.upvictoria.fpoo;

import java.io.BufferedReader;
import java.io.IOException;

public class Commands {

    static String path = "/home/ramos/Desktop/iti-271215-poo-practica-1-ramosinvchristian/dataBaseManager/src/main/$PATH$";

    public static void menu(BufferedReader reader) throws IOException {

        System.out.println("Enter a command");
        System.out.print("$PATH$:");
        String command = getCommands(reader);

        if (command.startsWith("SHOW TABLES")) {
         if (!command.startsWith("SHOW TABLES")) {
            }
            CommandsActions.showTables();
        } else if (command.startsWith("Exit")) {
            System.exit(0);
        }
    }
    public static String getCommands(BufferedReader reader) throws IOException {
        StringBuilder sb = new StringBuilder();
        return sb.substring(0, sb.length() - 1); // -(;)
    }
}