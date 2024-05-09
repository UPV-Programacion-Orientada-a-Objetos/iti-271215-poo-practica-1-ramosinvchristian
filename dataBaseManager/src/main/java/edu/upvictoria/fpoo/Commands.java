package edu.upvictoria.fpoo;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Commands {

    static String path = "/home/ramos/Desktop/iti-271215-poo-practica-1-ramosinvchristian/dataBaseManager/src/main/$PATH$";
    static BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));

    public static void menu(BufferedReader lector) throws IOException {

        System.out.println("Ingrese un comando");
        System.out.print("$PATH$:");
        String comando = obtenerComando(lector);

          if (comando.startsWith("CREATE TABLE")) {
            crearTabla(comando);
            if (!comando.startsWith("CREATE TABLE")) {
            }
        } else if (comando.startsWith("SHOW TABLES")) {
            if (!comando.startsWith("SHOW TABLES")) {
            }
            CommandsActions.showTables();

            String condition = null;
            if (comando.contains("WHERE")) {
                condition = comando.substring(comando.indexOf("WHERE") + 5).trim();
            }
        } else if (comando.startsWith("exit")) {
            System.exit(0);
        }
    }
    public static String obtenerComando(BufferedReader lector) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = lector.readLine()) != null) {
            sb.append(line.trim());
            if (line.trim().endsWith(";")) {
                break;
            }
        }
        return sb.substring(0, sb.length() - 1); // -(;)
    }
    public static void crearTabla(String comando) {
        String[] palabras = comando.split(" ");
        String tableName = palabras[2];
        System.out.println("Nombre de la tabla: " + tableName);

        String dentro = comando.substring(comando.indexOf("(") + 1, comando.lastIndexOf(")")).trim();
        String[] campos = dentro.split(",");

        File archivo = new File(path + "/" + tableName + ".csv");
        if (!archivo.exists()) {
            CommandsActions.createTable(tableName, campos);
        } else {
            System.out.println("El archivo o tabla " + tableName + ", ya existe.");
        }
    }
}
