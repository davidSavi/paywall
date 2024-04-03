package org.paywall.user.model.exception;

import lombok.AllArgsConstructor;

public class InvalidUserNameException extends IllegalArgumentException {
  public InvalidUserNameException(String s) {
    super(s);
  }

}
