package Dino.Duett.gmail.exception;

import Dino.Duett.global.exception.CustomException;
import Dino.Duett.global.exception.ErrorCode;

import java.util.Map;

public class GmailException extends CustomException {
    protected GmailException(ErrorCode errorCode) {
        super(errorCode);
    }
    protected GmailException(ErrorCode errorCode, Map<String, String> property) {
        super(errorCode, property);
    }

    public static class InvalidContentTypeException extends GmailException {
        public InvalidContentTypeException() {
            super(ErrorCode.INVALID_CONTENT_TYPE);
        }
        public InvalidContentTypeException(Map<String, String> property) {
            super(ErrorCode.INVALID_CONTENT_TYPE, property);
        }
    }

    public static class EmailValidationFailedException extends GmailException {
        public EmailValidationFailedException() {
            super(ErrorCode.EMAIL_VALIDATION_FAILED);
        }
        public EmailValidationFailedException(Map<String, String> property) {
            super(ErrorCode.EMAIL_VALIDATION_FAILED, property);
        }
    }

    public static class MessageNotFoundException extends GmailException {
        public MessageNotFoundException() {
            super(ErrorCode.MESSAGE_NOT_FOUND);
        }
        public MessageNotFoundException(Map<String, String> property) {
            super(ErrorCode.MESSAGE_NOT_FOUND, property);
        }
    }
}
