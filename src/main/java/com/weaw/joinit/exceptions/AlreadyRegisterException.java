package com.weaw.joinit.exceptions;

public class AlreadyRegisterException extends RuntimeException {
  public AlreadyRegisterException(){
    super("The user already registered");
  }
}
