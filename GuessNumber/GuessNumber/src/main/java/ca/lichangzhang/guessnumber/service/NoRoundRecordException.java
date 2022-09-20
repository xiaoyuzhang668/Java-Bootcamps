package ca.lichangzhang.guessnumber.service;

/**　Email: xiaoyuzhang668@gmail.com
 *   Date: 2022
 *
 *  @author catzh
 */
public class NoRoundRecordException extends Exception {
  public NoRoundRecordException(String message) {
        super(message);
    }

    public NoRoundRecordException(String message, Throwable cause) {
        super(message, cause);
    }
}
