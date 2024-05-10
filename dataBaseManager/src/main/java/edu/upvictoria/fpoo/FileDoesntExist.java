package edu.upvictoria.fpoo;

import java.io.FileNotFoundException;
public class FileDoesntExist extends FileNotFoundException {
    public FileDoesntExist(String mensaje){
        super(mensaje);
    }
}