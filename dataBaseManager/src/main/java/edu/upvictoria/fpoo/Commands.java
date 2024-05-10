package edu.upvictoria.fpoo;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Commands {

    static String path = "/home/ramos/Desktop/iti-271215-poo-practica-1-ramosinvchristian/dataBaseManager/src/main/$PATH$";

    public static void menu(BufferedReader lector) throws IOException {

        System.out.println("Ingrese un comando");
        System.out.print("$PATH$:");
        String comando = obtenerComando(lector);

        if (comando.startsWith("DROP TABLE")) {
            if (!comando.startsWith("DROP TABLE")) {
                throw new ErrorSintaxis("ERROR DE SINTAXIS");
            }
            String tableName = comando.substring(11).trim();
            String nombre = tableName.substring(0, tableName.length());
            CommandsActions.dropTable(nombre);
        } else if (comando.startsWith("CREATE TABLE")) {
            crearTabla(comando);
            if (!comando.startsWith("CREATE TABLE")) {
                throw new ErrorSintaxis("ERROR DE SINTAXIS");
            }
        } else if (comando.startsWith("SHOW TABLES")) {
            if (!comando.startsWith("SHOW TABLES")) {
                throw new ErrorSintaxis("ERROR DE SINTAXIS");
            }
            CommandsActions.showTables();
        } else if (comando.startsWith("INSERT")) {
            if (!comando.startsWith("INSERT INTO")) {
                throw new ErrorSintaxis("ERROR DE SINTAXIS");
            }
            insertDatos(comando);
        } else if (comando.startsWith("DELETE FROM")) {
            String[] partes = comando.split(" ");
            if (partes.length < 3 || !partes[1].equalsIgnoreCase("FROM")) {
                throw new ErrorSintaxis("ERROR DE SINTAXIS");
            }
            String tableName = partes[2].trim();
            String condition = null;
            if (comando.contains("WHERE")) {
                condition = comando.substring(comando.indexOf("WHERE") + 5).trim();
            }
        CommandsActions.deleteFrom(tableName, condition);
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
        return sb.substring(0, sb.length() - 1);
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
    public static void insertDatos(String comando) {
        try {
            String nombreTabla = comando.substring(12, comando.indexOf("(")).trim();
            String columnas = comando.substring(comando.indexOf("(") + 1, comando.indexOf(")"));
            String valores = comando.substring(comando.lastIndexOf("(") + 1, comando.lastIndexOf(")"));
            String[] aColumnas = columnas.split(",");
            String[] aValores = valores.split(",");
            ArrayList<String> nombreColumnas = new ArrayList<>();
            ArrayList<String> valoresColumnas = new ArrayList<>();
            for (String columna : aColumnas) {
                nombreColumnas.add(columna.trim());
            }
            for (String dato : aValores) {
                valoresColumnas.add(dato.trim());
            }
            CommandsActions.insertInto(nombreTabla, valoresColumnas);
        } catch (FileDoesntExist e) {
            System.out.println(e.getMessage());
        }
    }
}
