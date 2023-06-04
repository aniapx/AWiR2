package edu.zut.awir2.Exceptions;

public class StorageFileNotFoundException extends Exception {
    public StorageFileNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}