package edu.upvictoria.fpoo;

/**
 1. Set a $PATH$ directory from which files will be read and/or created. This is done with the USE $PATH$ command.
 2. Display the list of Tables (CSV files in $PATH$) using the SHOW TABLES command.
 3. CREATE TABLE deberá crear un nuevo archivo, respetando la sintaxis de SQL para la creación de los campos. Por ejemplo:

  CREATE TABLE Alumnos (
    id INT NOT NULL PRIMARY KEY,
     nombre VARCHAR(20) NOT NULL,
     app VARCHAR(20) NOT NULL,
     apm VARCHAR(20) NOT NULL,
     edad INT NULL
 );

 Resultará en `$PATH$/Alumnos.csv` con las columnas:
 `id,nombre,app,apm,edad`.


 */


/**
 * Hello world!
 *
 */

public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!!!" );

    }
}

