package Dino.Duett.domain.message.exception;

import Dino.Duett.global.exception.CustomException;
import Dino.Duett.global.exception.ErrorCode;

import java.util.Map;

public class MessageException extends CustomException {
    public MessageException(ErrorCode errorCode) {
        super(errorCode);
    }
    public MessageException(ErrorCode errorCode, Map<String, String> property) {
        super(errorCode, property);
    }

    public static class MessageNotFoundException extends MessageException {
        public MessageNotFoundException() {
            super(ErrorCode.MESSAGE_NOT_FOUND);
        }
        public MessageNotFoundException(Map<String, String> property) {
            super(ErrorCode.MESSAGE_NOT_FOUND, property);
        }
    }

    public static class MessageTypeInvalidException extends MessageException {
        public MessageTypeInvalidException() {
            super(ErrorCode.MESSAGE_TYPE_INVALID);
        }
        public MessageTypeInvalidException(Map<String, String> property) {
            super(ErrorCode.MESSAGE_TYPE_INVALID, property);
        }
    }

    public static class MessageLengthExceedException extends MessageException {
        public MessageLengthExceedException() {
            super(ErrorCode.MESSAGE_LENGTH_EXCEED);
        }
        public MessageLengthExceedException(Map<String, String> property) {
            super(ErrorCode.MESSAGE_LENGTH_EXCEED, property);
        }
    }
}
