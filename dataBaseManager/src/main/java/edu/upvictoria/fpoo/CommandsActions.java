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
            System.out.println("$PATH$: ¿ESTÁ SEGURO QUE DESEA ELIMINAR LA TABLA?");
            System.out.println("$PATH$: INGRESE [0] PARA CONFIRMAR");
            String op = bf.readLine();
            if (op.equals("0")) {
                if (file.delete()) {
                    System.out.println("$PATH$: LA TABLA " + nombreTabla + " SE HA ELIMINADO CORRECTAMENTE");
                } else {
                    System.out.println("ERROR");
                }
            }
        } catch (FileDoesntExist e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
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
    public static void deleteFrom(String nombreTabla, String condicion) throws FileDoesntExist {
        String filePath = path + "/" + nombreTabla + ".csv";
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileDoesntExist("El archivo " + nombreTabla + " no existe");
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String headerLine = reader.readLine();
            String[] headers = headerLine.split(",");
            ArrayList<Integer> columnasSeleccionadas = new ArrayList<>();
            for (int i = 0; i < headers.length; i++) {
                columnasSeleccionadas.add(i);
            }
            String line;
            StringBuilder resultad0 = new StringBuilder();
            resultad0.append(headerLine).append("\n");
            boolean realizado = false;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                if (validarCondicion(values, headers, condicion)) {
                    realizado = true;
                    continue;
                }
                resultad0.append(line).append("\n");
            }
            try (FileWriter writer = new FileWriter(filePath)) {
                writer.write(resultad0.toString());
            }
            if (!realizado) {
                System.out.println("NO EXISTE UN REGISTRO QUE CUMPLA CON LA CONDICIÓN");
            } else {
                System.out.println("DATOS ELIMINADOS CORRECTAMENTE - ["+nombreTabla+"]");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static boolean validarCondicion(String[] valores, String[] headers, String condition) {
        String[] condiciones = condition.split("\\s+(?i)OR\\s+");
        for (String condicion : condiciones) {
            String[] partesCondicion = condicion.split("=");
            if (partesCondicion.length != 2) {
                System.out.println("Error en la condición: " + condicion);
                return false;
            }
            String campo = partesCondicion[0].trim();
            String valor = partesCondicion[1].trim().replace("'", "");
            int indiceHeader = -1;
            for (int i = 0; i < headers.length; i++) {
                if (headers[i].trim().equals(campo)) {
                    indiceHeader = i;
                    break;
                }
            }
            if (indiceHeader == -1) {
                System.out.println("Campo " + campo + " no encontrado.");
                return false;
            }
            if (valores[indiceHeader].trim().equals(valor)) {
                return true;
            }
        }
        return false;
    }
}