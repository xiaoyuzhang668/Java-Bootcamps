package ca.lichangzhang.guessnumber.service;

/**
 * Email: xiaoyuzhang668@gmail.com Date: 2022
 *
 * @author catzh
 */
public class GameIdNotFoundException extends Exception {

    public GameIdNotFoundException(String message) {
        super(message);
    }

    public GameIdNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
