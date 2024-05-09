package edu.upvictoria.fpoo;
import java.io.*;

public class CommandsActions {

    static String path = "/home/ramos/Desktop/iti-271215-poo-practica-1-ramosinvchristian/dataBaseManager/src/main/$PATH$";

    public static BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));

    public static String obtenerRutaArchivo(String nombreTabla) {
        return path + "/" + nombreTabla + ".csv";
    }

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
            for (File archiv0 : archivos) {
                System.out.println(archiv0.getName());
            }
        } else {
            System.out.println("No hay tablas que mostrar.");
        }
    }
}