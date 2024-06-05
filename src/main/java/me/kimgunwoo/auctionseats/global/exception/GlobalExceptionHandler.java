package me.kimgunwoo.auctionseats.global.exception;

import me.kimgunwoo.auctionseats.global.dto.EmptyObject;
import me.kimgunwoo.auctionseats.global.response.ApiResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /*
     * ApiException 에 대한 handler
     * */
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse<EmptyObject>> apiExceptionHandler(ApiException exception) {
        return ResponseEntity.status(exception.getHttpStatus())
                .body(ApiResponse.of(exception.getCode(), exception.getMessage()));
    }

    /*
     * RuntimeException 에 대한 handler
     * */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<EmptyObject>> runtimeExceptionHandler(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.of(ErrorCode.INTERNAL_SERVER_ERROR.getCode(),
                        ErrorCode.INTERNAL_SERVER_ERROR.getMessage()));
    }


    /*
     * IllegalArgumentException 에 대한 handler
     * */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<EmptyObject>> illegalArgumentExceptionHandler(
            IllegalArgumentException exception
    ) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.of(ErrorCode.INTERNAL_BAD_REQUEST.getCode(), exception.getMessage()));
    }

    /*
     * MethodArgumentNotValidException 에 대한 handler
     * */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        BindingResult bindingResult = ex.getBindingResult();
        Map<String, String> errors = new HashMap<>();
        bindingResult.getAllErrors().forEach(error ->
                errors.put(((FieldError)error).getField(), error.getDefaultMessage()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.of(
                        ErrorCode.INTERNAL_BAD_REQUEST.getCode(),
                        ErrorCode.INTERNAL_BAD_REQUEST.getMessage(),
                        errors
                ));
    }
}
