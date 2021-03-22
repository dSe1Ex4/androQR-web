package com.example.androidQr.exception;

public class FileIsEmptyException extends RuntimeException {

  public FileIsEmptyException(String fileName) {
    super("File " + fileName+ " is empty");
  }
}
