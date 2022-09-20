package ca.lichangzhang.guessnumber.service;

/**　Email: xiaoyuzhang668@gmail.com
 *   Date: 2022
 *
 *  @author catzh
 */
public class GameFinishedException extends Exception {
    public GameFinishedException(String message) {
        super(message);
    }

    public GameFinishedException(String message, Throwable cause) {
        super(message, cause);
    }
}
