package Dino.Duett.global.handler;

import Dino.Duett.domain.member.exception.MemberException;
import Dino.Duett.domain.mood.exception.MoodException;
import Dino.Duett.domain.music.exception.MusicException;
import Dino.Duett.domain.profile.exception.ProfileException;
import Dino.Duett.domain.search.exception.YoutubeException;
import Dino.Duett.domain.tag.exception.TagException;
import Dino.Duett.global.exception.CustomException;
import Dino.Duett.global.exception.ErrorCode;
import Dino.Duett.global.exception.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorResponse> handleInternalServer(final Exception e) {
//        final CustomException customException = CustomException.from(
//            ErrorCode.INTERNAL_SERVER_ERROR);
//
//        log.error(e.toString());
//
//        return ResponseEntity.internalServerError().body(ErrorResponse.from(customException));
//    }

    // 기존 유효성 검사 예외 핸들러
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        final ErrorResponse response = ErrorResponse.from(CustomException.of(ErrorCode.BAD_REQUEST, errors));
        return ResponseEntity.badRequest().body(response);

    }
    // 새로운 타입 미스매치 예외 핸들러
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        Map<String, String> errors = new HashMap<>();
        String fieldName = ex.getName();
        String errorMessage = "Invalid value for field: " + fieldName;
        errors.put(fieldName, errorMessage);
        final ErrorResponse response = ErrorResponse.from(CustomException.of(ErrorCode.BAD_REQUEST, errors));
        return ResponseEntity.badRequest().body(response);
    }

    // 잘못된 형식의 데이터 전송
    @ExceptionHandler(HttpMessageConversionException.class)
    protected ResponseEntity<ErrorResponse> handleHttpMessageConversionException(final HttpMessageConversionException e) {
        log.error("HttpMessageConversionException", e);
        final ErrorResponse response = ErrorResponse.from(CustomException.from(ErrorCode.BAD_REQUEST));
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler({
            TagException.ProfileTagMaxLimitException.class,
            ProfileException.ProfileIncompleteException.class
        }
    )
    public ResponseEntity<ErrorResponse> handleGlobalBadRequestException(final CustomException e) {
        log.error(e.getErrorInfoLog());
        return ResponseEntity.badRequest().body(ErrorResponse.from(e));
    }
    @ExceptionHandler({
            MusicException.MusicNotFoundException.class,
            ProfileException.ProfileNotFoundException.class,
            TagException.TagNotFoundException.class,
            MoodException.MoodNotFoundException.class
    })
    public ResponseEntity<ErrorResponse> handleGlobalNotFoundException(final CustomException e) {
        log.error(e.getErrorInfoLog());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.from(e));
    }


    @ExceptionHandler({
            ProfileException.ProfileForbiddenException.class
    })
    public ResponseEntity<ErrorResponse> handleGlobalForbiddenException(final CustomException e) {
        log.error(e.getErrorInfoLog());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ErrorResponse.from(e));
    }

    @ExceptionHandler({
            MemberException.MemberCoinNotEnoughException.class,
    })
    public ResponseEntity<ErrorResponse> handleGlobalPaymentRequiredException(final CustomException e) {
        log.error(e.getErrorInfoLog());
        return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).body(ErrorResponse.from(e));
    }

    @ExceptionHandler({
            YoutubeException.class,
    })
    public ResponseEntity<ErrorResponse> handleGlobalInternalServerException(final CustomException e) {
        log.error(e.getErrorInfoLog());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse.from(e));
    }
}
