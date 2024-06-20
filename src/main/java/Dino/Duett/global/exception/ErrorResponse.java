package Dino.Duett.global.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ErrorResponse {

    private int code;
    private String message;
    private Map<String, String> property;

    public static ErrorResponse from(final CustomException customException) {
        return new ErrorResponse(customException.getCode(), customException.getMessage(), customException.getProperty());
    }
}
