package edu.upvictoria.fpoo;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Commands {

    static String path = "/home/ramos/Desktop/iti-271215-poo-practica-1-ramosinvchristian/dataBaseManager/src/main/$PATH$";

    public static void menu(BufferedReader lector) throws IOException {

        System.out.println("ENTER A COMANDO");
        System.out.print("$PATH$:");
        String comando = obtenerComando(lector);

        if (comando.startsWith("DROP TABLE")) {
            if (!comando.startsWith("DROP TABLE")) {
                throw new ErrorSintaxis("SINTAX ERROR");
            }
            String tableName = comando.substring(11).trim();
            String nombre = tableName.substring(0, tableName.length());
            CommandsActions.dropTable(nombre);
        } else if (comando.startsWith("CREATE TABLE")) {
            crearTabla(comando);
            if (!comando.startsWith("CREATE TABLE")) {
                throw new ErrorSintaxis("SINTAX ERROR");
            }
        } else if (comando.startsWith("SHOW TABLES")) {
            if (!comando.startsWith("SHOW TABLES")) {
                throw new ErrorSintaxis("SINTAX ERROR");
            }
            CommandsActions.showTables();
        } else if (comando.startsWith("INSERT")) {
            if (!comando.startsWith("INSERT INTO")) {
                throw new ErrorSintaxis("SINTAX ERROR");
            }
            insertDatos(comando);
        } else if (comando.startsWith("DELETE FROM")) {
            String[] partes = comando.split(" ");
            if (partes.length < 3 || !partes[1].equalsIgnoreCase("FROM")) {
                throw new ErrorSintaxis("SINTAX ERROR");
            }
            String tableName = partes[2].trim();
            String condition = null;
            if (comando.contains("WHERE")) {
                condition = comando.substring(comando.indexOf("WHERE") + 5).trim();
            }
            CommandsActions.deleteFrom(tableName, condition);
        } else if (comando.startsWith("UPDATE")) {
            if (!comando.startsWith("UPDATE ")) {
                throw new ErrorSintaxis("SINTAX ERROR");
            }
            updateDatos(comando);
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
        System.out.println("TABLE: " + tableName);
        String dentro = comando.substring(comando.indexOf("(") + 1, comando.lastIndexOf(")")).trim();
        String[] campos = dentro.split(",");
        File archivo = new File(path + "/" + tableName + ".csv");
        if (!archivo.exists()) {
            CommandsActions.createTable(tableName, campos);
        } else {
            System.out.println("THE FILE OR TABLE " + tableName + " IT'S ALREADY EXIST");
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
    public static void updateDatos(String comando) throws FileDoesntExist {
        try {
            String tableName = comando.substring(7, comando.indexOf("SET")).trim();
            String setClause = comando.substring(comando.indexOf("SET") + 3, comando.indexOf("WHERE")).trim();
            String condition = comando.substring(comando.indexOf("WHERE") + 5).trim();
            CommandsActions.updateTable(tableName, setClause, condition);
        } catch (FileDoesntExist e) {
            System.out.println(e.getMessage());
        }
    }
    public static void selectDatos(String comando) throws FileDoesntExist {
        try {
            String[] partes = comando.split("FROM");
            if (partes.length == 2) {
                String selectClausula = partes[0].substring(7).trim();
                String[] subPartes = partes[1].trim().split("WHERE");
                String tabla = subPartes[0].trim();
                String condition = (subPartes.length == 2) ? subPartes[1].trim() : null;
                String[] columnas = selectClausula.equals("*") ? new String[]{"*"} : selectClausula.split(",");
                CommandsActions.selectFrom(tabla, columnas, condition);
            } else if (partes.length == 1) {
                String selectClausula = partes[0].substring(7).trim();
                String tabla = selectClausula.trim();
                String[] columnas = selectClausula.equals("*") ? new String[]{"*"} : selectClausula.split(",");
                CommandsActions.selectFrom(tabla, columnas, null);
            } else {
                throw new ErrorSintaxis("SINTAX ERROR");
            }
        } catch (FileDoesntExist e) {
            System.out.println(e.getMessage());
        }
    }
}