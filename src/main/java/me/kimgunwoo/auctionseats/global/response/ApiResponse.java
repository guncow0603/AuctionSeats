package me.kimgunwoo.auctionseats.global.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.kimgunwoo.auctionseats.global.dto.EmptyObject;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {

    private String code;
    private String message;
    private T data;

    public static <K> ApiResponse<K> of(String code, String message, K data) {
        return new ApiResponse<>(code, message, data);
    }

    public static  ApiResponse<EmptyObject> of(String code, String message) {
        EmptyObject emptyObject = EmptyObject.get();
        return new ApiResponse<>(code, message, emptyObject);
    }
}