package edu.upvictoria.fpoo;

import java.io.*;
import java.util.ArrayList;

public class CommandsActions {
    static String path = "/home/ramos/Desktop/iti-271215-poo-practica-1-ramosinvchristian/dataBaseManager/src/main/$PATH$";
    public static BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));

    public static void createTable(String nombreTabla, String[] campos) {
        String filePath = path + "/" + nombreTabla + ".csv";
        try (FileWriter writer = new FileWriter(filePath)) {
            for (String campo : campos) {
                writer.write(campo + ",");
            }
            writer.write("\n");
        } catch (IOException e) {
            e.getMessage();
        }
    }
    public static void showTables() {
        System.out.println("===============LIST OF TABLES===============");
        File directorio = new File(path);
        File[] archivos = directorio.listFiles();
        if (archivos != null) {
            for (File archivo : archivos) {
                System.out.println(archivo.getName());
            }
        } else {
            System.out.println("No hay tablas que mostrar.");
        }
    }
    public static void dropTable(String nombreTabla) {
        String filePath = path + "/" + nombreTabla + ".csv";
        File file = new File(filePath);
        try {
            if (!file.exists()) {
                throw new FileDoesntExist("El archivo " + nombreTabla + " no existe");
            }
        } catch (FileDoesntExist e) {
            throw new RuntimeException(e);
        } finally {
            System.out.println("fin");
        }
    }
    public static void insertInto(String tableName, ArrayList<String> valores) throws FileDoesntExist {
        String filePath = path + "/" + tableName + ".csv";
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileDoesntExist("El archivo " + tableName + " no existe");
        }
        try (FileWriter writer = new FileWriter(filePath, true)) {
                        for (String valorCampo : valores) {
                writer.write(valorCampo + ",");
            }
            writer.write("\n");
            System.out.println("LOS DATOS SE HAN INSERTADO CORRECTAMENTE");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}