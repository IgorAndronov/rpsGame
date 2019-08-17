package com.interview.task.stats.web.eh;


import com.interview.task.stats.web.models.GeneralResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({RuntimeException.class})
    protected ResponseEntity<GeneralResponse> handleGeneralExceptions(RuntimeException ex){
        log.error("Error", ex);

        return new ResponseEntity<>(new GeneralResponse("ERROR", ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
