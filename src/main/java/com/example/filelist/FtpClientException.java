package com.example.filelist;

/**
 * @author innokenty
 */
class FtpClientException extends Exception {

    public FtpClientException(String message) {
        super(message);
    }

    public FtpClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
