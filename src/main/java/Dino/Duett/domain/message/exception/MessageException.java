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
            super(ErrorCode.MAIL_NOT_FOUND);
        }
        public MessageNotFoundException(Map<String, String> property) {
            super(ErrorCode.MAIL_NOT_FOUND, property);
        }
    }
}
