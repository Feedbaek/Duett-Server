package Dino.Duett.domain.mood.exception;

import Dino.Duett.global.exception.CustomException;
import Dino.Duett.global.exception.ErrorCode;

import java.util.Map;

public class MoodException extends CustomException {
    protected MoodException(ErrorCode errorCode) {
        super(errorCode);
    }
    protected MoodException(ErrorCode errorCode, Map<String, String> property) {
        super(errorCode, property);
    }
    public static class MoodNotFoundException extends MoodException {
        public MoodNotFoundException() {
            super(ErrorCode.MOOD_NOT_FOUND);
        }
        public MoodNotFoundException(Map<String, String> property) {
            super(ErrorCode.MOOD_NOT_FOUND, property);
        }
    }
    public static class MoodForbiddenException extends MoodException {
        public MoodForbiddenException() {
            super(ErrorCode.MOOD_FORBIDDEN);
        }
        public MoodForbiddenException(Map<String, String> property) {
            super(ErrorCode.MOOD_FORBIDDEN, property);
        }
    }
}
