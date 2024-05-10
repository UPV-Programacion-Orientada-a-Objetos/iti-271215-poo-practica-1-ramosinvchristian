package edu.upvictoria.fpoo;

/**
 1. [terminado] Set a $PATH$ directory from which files will be read and/or created. This is done with the USE $PATH$ command.
 2. [terminado] Display the list of Tables (CSV files in $PATH$) using the SHOW TABLES command.
 3. [terminado] CREATE TABLE must create a new file, respecting the SQL syntax for creating the fields. For example:
  CREATE TABLE Alumnos (
    id INT NOT NULL PRIMARY KEY,
     nombre VARCHAR(20) NOT NULL,
     app VARCHAR(20) NOT NULL,
     apm VARCHAR(20) NOT NULL,
     edad INT NULL
 );
 Resultará en `$PATH$/Alumnos.csv` con las columnas:
 `id,nombre,app,apm,edad`.

 4. [termiando] DROP TABLE should remove the file from the folder. **Ask if you really want to delete the file**.
 5. [terminado] INSERT should insert a new line in the file/table. respecting the SQL syntax. For example:
 INSERT INTO table_name (column1, column2,column3, ...) VALUES (value1, value2, value3, ...);`.

 6. [terminado] DELETE should delete a row or a set of row the section WHERE respecting the SQL syntaxis (`DELETE FROM table_name WHERE condition;`)
 > **NOTA:**
 > Respecting the complejes search using the commands `AND` Y `OR`. For example:
 `DELETE FROM Alumnos WHERE (app='González' AND apm <> 'Hernández') OR id=25;`; Sólo borraría a los alumnos con apellidos *González Hernández* o aquél con *id=25*.

 7. UPDATE will update the columns of the row or a set od row. Respecting the SQL syntaxis. For example:
 `UPDATE table_name SET column1 = value1, column2 = value2, ... WHERE condition;`.
 **NOTA: la sección de `WHERE condition` respeta el mismo formato del comando `DELETE`.

 8. SELECT will get set of dates from the table/file respecting the SQL syntaxis. For example:
 `SELECT [*] | select_expr [, select_expr] ... [as alias] [FROM  table_reference [WHERE where_condition]`.

 ## Entregables.

 A continuación se describe el conjunto de entregables:

 1. Library in archive `JAR` auto-executable
 2. Source code, it will let in the repository
 3. In the repository should programmer all the unitaries tests necesary por proof the function og the system
 4. Should will realize a report ethat contain the explication of function of sistem, also the explication to detail de las pruebas unitarias realizadas.
 **THE REPORT GIVE UP IN PDF FORMAT TO CLASSROOM*.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Hello world!
 *
 */

public class App {
    public static void main( String[] args ) throws IOException {
        System.out.println( "Hello World!!!" );

        BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));
        String op;
        String use;
        boolean continuar = true;
        System.out.println("exit = salir.");
        do {
            System.out.print("BASE DE DATOS:");
            use = String.valueOf(lector.readLine());
            if (!use.startsWith("USE $PATH$")) {
                System.out.println("INVALIDO");
            }
        } while ((!use.startsWith("USE $PATH$")));
        do {
            try {
                Commands.menu(lector);
                op = lector.readLine();
                if (op.equalsIgnoreCase("exit")){
                    continuar = false;
                }
            } catch (Exception e) {
                System.out.println("Ocurrió un error: " + e.getMessage());
            }
        } while (continuar);
    }
}