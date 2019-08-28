package io.tpd.superheroes.controller;

import io.tpd.superheroes.controller.errors.SuperHeroAppError;
import io.tpd.superheroes.exceptions.NonExistingHeroException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * Maps exceptions to HTTP responses
 *
 * @author moises.macero
 */
@RestControllerAdvice
public class SuperHeroExceptionHandler {

    @Value("${superheroes.sendreport.uri}")
    private String sendReportUri;

    @ExceptionHandler(NonExistingHeroException.class)
    public ResponseEntity<SuperHeroAppError> handleNonExistingHero(HttpServletRequest request,
                                                                   NonExistingHeroException ex) {
        final SuperHeroAppError error = new SuperHeroAppError(
                ex.getErrorCode(),
                "This superhero is hiding in the cave",
                "superhero-exceptions",
                "You can't find this superhero right now. Try later.",
                "Saving someone",
                sendReportUri
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

}
