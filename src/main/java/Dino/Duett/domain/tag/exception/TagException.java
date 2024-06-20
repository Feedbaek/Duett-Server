package Dino.Duett.domain.tag.exception;

import Dino.Duett.global.exception.CustomException;
import Dino.Duett.global.exception.ErrorCode;

import java.util.Map;

public class TagException extends CustomException {
    protected TagException(ErrorCode errorCode) {
        super(errorCode);
    }
    protected TagException(ErrorCode errorCode, Map<String, String> property) {
        super(errorCode, property);
    }

    public static class TagNotFoundException extends TagException {
        public TagNotFoundException() {
            super(ErrorCode.TAG_NOT_FOUND);
        }
        public TagNotFoundException(Map<String, String> property) {
            super(ErrorCode.TAG_NOT_FOUND, property);
        }
    }
    public static class TagMaxLimitException extends TagException {
        public TagMaxLimitException() {
            super(ErrorCode.TAG_MAX_LIMIT);
        }
        public TagMaxLimitException(Map<String, String> property) {
            super(ErrorCode.TAG_MAX_LIMIT, property);
        }
    }

}
