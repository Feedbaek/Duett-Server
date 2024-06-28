package Dino.Duett.global.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@Getter
public class CustomException extends RuntimeException {

    private static final String EXCEPTION_INFO_BRACKET = "{ %s | %s }";
    private static final String CODE_MESSAGE = " Code: %d, Message: %s ";
    private static final String PROPERTY_VALUE = "%s=%s";
    private static final String VALUE_DELIMITER = "; ";
    private static final String RESPONSE_MESSAGE = "%d %s";

    private final int code;
    private final String message;
    private final Map<String, String> property;

    protected CustomException(final ErrorCode errorCode) {
        this(errorCode, Collections.emptyMap());
    }

    protected CustomException(
        final ErrorCode errorCode,
        final Map<String, String> property
    ) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.property = property;
    }

    public static CustomException of(
        final ErrorCode errorCode,
        final Map<String, String> property
    ) {
        return new CustomException(errorCode, property);
    }

    public static CustomException from(final ErrorCode errorCode) {
        return new CustomException(errorCode);
    }


    public String getErrorInfoLog() {
        final String codeMessage = String.format(CODE_MESSAGE, code, message);
        final String errorPropertyValue = getPropertyToString();

        return String.format(EXCEPTION_INFO_BRACKET, codeMessage, errorPropertyValue);
    }

    public String getPropertyToString() {
        return property.entrySet()
            .stream()
            .map(entry -> String.format(PROPERTY_VALUE, entry.getKey(), entry.getValue()))
            .collect(Collectors.joining(VALUE_DELIMITER));
    }

    public String getErrorResponse() {
        return String.format(RESPONSE_MESSAGE, code, message);
    }
}
