package Dino.Duett.domain.music.exception;

import Dino.Duett.global.exception.CustomException;
import Dino.Duett.global.exception.ErrorCode;

import java.util.Map;

public class MusicException extends CustomException {
    protected MusicException(ErrorCode errorCode) {
        super(errorCode);
    }
    protected MusicException(ErrorCode errorCode, Map<String, String> property) {
        super(errorCode, property);
    }
    public static class MusicNotFoundException extends MusicException {
        public MusicNotFoundException() {
            super(ErrorCode.MUSIC_NOT_FOUND);
        }
        public MusicNotFoundException(Map<String, String> property) {
            super(ErrorCode.MUSIC_NOT_FOUND, property);
        }
    }
}
