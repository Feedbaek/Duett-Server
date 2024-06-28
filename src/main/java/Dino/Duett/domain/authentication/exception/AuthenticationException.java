package Dino.Duett.domain.authentication.exception;

import Dino.Duett.global.exception.CustomException;
import Dino.Duett.global.exception.ErrorCode;

import java.util.Map;

public class AuthenticationException extends CustomException {
    protected AuthenticationException(ErrorCode errorCode) {
        super(errorCode);
    }
    protected AuthenticationException(ErrorCode errorCode, Map<String, String> property) {
        super(errorCode, property);
    }

    public static class InvalidVerificationCodeException extends AuthenticationException {
        public InvalidVerificationCodeException() {
            super(ErrorCode.INVALID_VERIFICATION_CODE);
        }
        public InvalidVerificationCodeException(Map<String, String> property) {
            super(ErrorCode.INVALID_VERIFICATION_CODE, property);
        }
    }

    public static class VerificationCodeNotFoundException extends AuthenticationException {
        public VerificationCodeNotFoundException() {
            super(ErrorCode.VERIFICATION_CODE_NOT_FOUND);
        }
        public VerificationCodeNotFoundException(Map<String, String> property) {
            super(ErrorCode.VERIFICATION_CODE_NOT_FOUND, property);
        }
    }

    public static class InvalidPhoneNumberException extends AuthenticationException {
        public InvalidPhoneNumberException() {
            super(ErrorCode.INVALID_PHONE_NUMBER);
        }
        public InvalidPhoneNumberException(Map<String, String> property) {
            super(ErrorCode.INVALID_PHONE_NUMBER, property);
        }
    }

}
