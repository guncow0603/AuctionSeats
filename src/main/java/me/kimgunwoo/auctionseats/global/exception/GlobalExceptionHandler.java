package me.kimgunwoo.auctionseats.global.exception;

import me.kimgunwoo.auctionseats.global.dto.EmptyObject;
import me.kimgunwoo.auctionseats.global.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@RestControllerAdvice
public class GlobalExceptionHandler {

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
}
