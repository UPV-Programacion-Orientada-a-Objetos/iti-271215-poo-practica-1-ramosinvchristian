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
            System.out.println("NO TABLE TO SHOW");
        }
    }

    public static void dropTable(String nombreTabla) {
        String filePath = path + "/" + nombreTabla + ".csv";
        File file = new File(filePath);
        try {
            if (!file.exists()) {
                throw new FileDoesntExist("SORRY, THE FILE " + nombreTabla + " DOES NOT EXIST");
            }
            System.out.println("$PATH$: ¿DO YOU WANT TO DELETE THE TABLE?");
            System.out.println("$PATH$: 0 = CONFIRM");
            String op = bf.readLine();
            if (op.equals("0")) {
                if (file.delete()) {
                    System.out.println("$PATH$: THE TABLE " + nombreTabla + " SE HA ELIMINADO CORRECTAMENTE");
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
            throw new FileDoesntExist("SORRY, THE FILE " + tableName + " DOES NOT EXIST");
        }
        try (FileWriter writer = new FileWriter(filePath, true)) {
            for (String valorCampo : valores) {
                writer.write(valorCampo + ",");
            }
            writer.write("\n");
            System.out.println("THE DATA HAS BEEN INSERTED CORRECTLY");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteFrom(String nombreTabla, String condicion) throws FileDoesntExist {
        String filePath = path + "/" + nombreTabla + ".csv";
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileDoesntExist("SORRY, THE FILE " + nombreTabla + " DOES NOT EXIST");
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
                System.out.println("THERE IS NO RECORD THAT MEETS THE RESPECTIVE CONDITION");
            } else {
                System.out.println("DATA CORRECTLY DELETED " + nombreTabla + " ");
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
                System.out.println("ERROR IN THE CONDITION " + condicion);
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
                System.out.println("SORRY, THE SECTOR (FIELD) " + campo + " DOES NOT EXIST");
                return false;
            }
            if (valores[indiceHeader].trim().equals(valor)) {
                return true;
            }
        }
        return false;
    }

    public static void updateTable(String nombreTabla, String clausula, String condicion) throws FileDoesntExist {
        String filePath = path + "/" + nombreTabla + ".csv";
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileDoesntExist("SORRY, THE FILE " + nombreTabla + " DOOES NOT EXIST");
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String headerLine = reader.readLine();
            String[] headers = headerLine.split(",");
            String[] sets = clausula.split(",");
            String[] asignarColumnas = new String[sets.length];
            String[] asignarValores = new String[sets.length];
            for (int i = 0; i < sets.length; i++) {
                String[] asignarPar = sets[i].split("=");
                asignarColumnas[i] = asignarPar[0].trim();
                asignarValores[i] = asignarPar[1].trim().replace("'", "");
            }
            StringBuilder result = new StringBuilder();
            result.append(headerLine).append("\n");
            boolean actualizado = false;
            String line;
            while ((line = reader.readLine()) != null) {
                String[] valores = line.split(",");
                if (validarCondicion(valores, headers, condicion)) {
                    for (int i = 0; i < headers.length; i++) {
                        for (int j = 0; j < asignarColumnas.length; j++) {
                            if (headers[i].trim().equals(asignarColumnas[j])) {
                                valores[i] = asignarValores[j];
                                actualizado = true;
                                break;
                            }
                        }
                    }
                }
                result.append(String.join(",", valores)).append("\n");
            }
            try (FileWriter writer = new FileWriter(filePath)) {
                writer.write(result.toString());
            }
            if (actualizado) {
                System.out.println("UPDATED REGISTRATION IN " + nombreTabla + " ");
            } else {
                System.out.println("NO RECORDS WERE FOUND THAT MADE THE CONDITION FULFILLED");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void selectFrom(String nombreTabla, String[] columns, String condicion) throws FileDoesntExist {
        String filePath = path + "/" + nombreTabla + ".csv";
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileDoesntExist("SORRY, THE FILE " + nombreTabla + " DOES NOT EXIST");
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String headerLine = reader.readLine();
            String[] headers = headerLine.split(",");
            ArrayList<Integer> columnas = new ArrayList<>();
            if (columns[0].equals("*")) {
                for (int i = 0; i < headers.length; i++) {
                    columnas.add(i);
                }
            } else {
                for (String column : columns) {
                    boolean encontrado = false;
                    for (int i = 0; i < headers.length; i++) {
                        if (headers[i].trim().equals(column.trim())) {
                            columnas.add(i);
                            encontrado = true;
                            break;
                        }
                    }
                    if (!encontrado) {
                        System.out.println("SORRY, THE COLUMN " + column + " DOES NOT EXIST");
                        return;
                    }
                }
            }
            String line;
            while ((line = reader.readLine()) != null) {
                String[] valores = line.split(",");
                if (condicion == null || validarCondicion(valores, headers, condicion)) {
                    for (int index : columnas) {
                        System.out.print(headers[index] + ": " + valores[index] + " ");
                    }
                    System.out.println();
                }
            }
        } catch (FileDoesntExist e) {
            System.out.println("SORRY, THE FILE " + filePath + " DOES NOT EXIST");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}