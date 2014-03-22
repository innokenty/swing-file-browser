package com.example.filelist;

/**
 * @author innokenty
 */
class FileListException extends Exception {

    public FileListException(String message) {
        super(message);
    }

    public FileListException(String message, Throwable cause) {
        super(message, cause);
    }
}
