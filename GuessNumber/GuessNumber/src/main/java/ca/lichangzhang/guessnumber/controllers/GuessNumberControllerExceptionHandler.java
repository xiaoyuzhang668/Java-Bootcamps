package ca.lichangzhang.guessnumber.controllers;

import ca.lichangzhang.guessnumber.service.GameFinishedException;
import ca.lichangzhang.guessnumber.service.GameIdNotFoundException;
import ca.lichangzhang.guessnumber.service.NoRoundRecordException;
import ca.lichangzhang.guessnumber.service.SQLException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Email: xiaoyuzhang668@gmail.com Date: 2022
 *
 * @author catzh
 */
@ControllerAdvice
@RestController
public class GuessNumberControllerExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String CONSTRAINT_MESSAGE = "Please try again!!";

    @ExceptionHandler(SQLException.class)
    public final ResponseEntity<Error> handleSqlException(SQLException ex,
            WebRequest request) {

        Error err = new Error();
        err.setMessage(CONSTRAINT_MESSAGE + "  SQL ERROR!!");
        return new ResponseEntity<>(err, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(GameFinishedException.class)
    public final ResponseEntity<Error> handleGameFinishedException(GameFinishedException ex,
            WebRequest request) {

        Error err = new Error();
        err.setMessage(CONSTRAINT_MESSAGE + " | " + ex.getMessage());
        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(GameIdNotFoundException.class)
    public final ResponseEntity<Error> handleGameIdNotFoundException(GameIdNotFoundException ex,
            WebRequest request) {

        Error err = new Error();
        err.setMessage(CONSTRAINT_MESSAGE + " | " + ex.getMessage());
        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoRoundRecordException.class)
    public final ResponseEntity<Error> handleNoRoundRecordException(NoRoundRecordException ex,
            WebRequest request) {

        Error err = new Error();
        err.setMessage(CONSTRAINT_MESSAGE + " | " + ex.getMessage());
        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }

}
