package gh.com.id_verification.id_verification.controllers.exceptions;

import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.SQLGrammarException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.net.ConnectException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
public class ApiExceptionHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());




    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ResponseBody
    protected ApiErrorVO handleDataIntegrityViolation(DataIntegrityViolationException ex, WebRequest request) {
        Throwable cause = ex.getRootCause();
        if (cause instanceof SQLIntegrityConstraintViolationException) {
            SQLIntegrityConstraintViolationException consEx = (SQLIntegrityConstraintViolationException) cause;
            String message = "";
            String constraint = "";
            HttpStatus httpStatus = null;
            if (consEx.getMessage().contains("UNIQUE")) {
                message = "Cannot enter the same record twice";
                constraint = "DUPLICATED_RECORD";
                httpStatus = HttpStatus.CONFLICT;
            } else if (consEx.getMessage().contains("foreign key constraint")) {
                message = "Record still have reference from other table";
                constraint = "USED_RECORD";
                httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            }
            //return new ApiErrorVO(httpStatus.value(), message, consEx.getMessage(), constraint);
            return new ApiErrorVO(httpStatus.value(),  consEx.getMessage());
        } else if (ex.getCause() instanceof ConstraintViolationException) {
            ConstraintViolationException eror = (ConstraintViolationException)ex.getCause();
            return new ApiErrorVO(1, "Record already exists.");
        }
        return new ApiErrorVO(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Exception");
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiErrorVO handleValidationError(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        FieldError fieldError = bindingResult.getFieldError();
        String defaultMessage = fieldError.getDefaultMessage();
        LOGGER.error("MethodArgumentNotValidException : {}",defaultMessage);
        return new ApiErrorVO(1, defaultMessage);
    }

    @ExceptionHandler(SQLException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiErrorVO handleSQLError(SQLException ex) {
        LOGGER.error("SQLException : {}",ex.getMessage());
        switch (ex.getSQLState()){
            case "23000":
                return new ApiErrorVO(ex.getErrorCode(), "Record already exists for specified request");
            default:
                return new ApiErrorVO(ex.getErrorCode(), ex.getMessage());
        }
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ApiErrorVO handleInternalServerError(Exception ex) {
        LOGGER.error("Exception : {}",ex.getMessage());
        if(ex.getCause() instanceof SQLGrammarException){
            return new ApiErrorVO(HttpStatus.CONFLICT.value(), "Invalid request");
        }
        return new ApiErrorVO(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getCause().toString());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(code = HttpStatus.CONFLICT)
    @ResponseBody
    public ApiErrorVO handleConstraintViolationException(Exception ex) {
        LOGGER.error("Exception : {}",ex.getMessage());
        return new ApiErrorVO(HttpStatus.CONFLICT.value(), ex.getMessage());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ResponseBody
    public ApiErrorVO handleNotFoundError(Exception ex) {
        LOGGER.error("NotFoundException : {}",ex.getMessage());
        return new ApiErrorVO(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    @ExceptionHandler(value = {CannotCreateTransactionException.class})
    public ResponseEntity<?> cannotCreateTransactionException(CannotCreateTransactionException exception, WebRequest request) {
        if (exception.contains(ConnectException.class)) {
            LOGGER.error("DB ConnectException :  {}",exception.getMessage());
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }






}
