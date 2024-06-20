package Dino.Duett.domain.search.exception;

import Dino.Duett.global.exception.CustomException;
import Dino.Duett.global.exception.ErrorCode;

import java.util.Map;

public class YoutubeException extends CustomException {
    protected YoutubeException(ErrorCode errorCode) {
        super(errorCode);
    }
    protected YoutubeException(ErrorCode errorCode, Map<String, String> property) {
        super(errorCode, property);
    }
    public static class YoutubeApiRequestFailed extends YoutubeException {
        public YoutubeApiRequestFailed() {
            super(ErrorCode.YOUTUBE_API_REQUEST_FAILED);
        }
        public YoutubeApiRequestFailed(Map<String, String> property) {
            super(ErrorCode.YOUTUBE_API_REQUEST_FAILED, property);
        }
    }
    public static class YoutubeApiRequestLimitExceeded extends YoutubeException {
        public YoutubeApiRequestLimitExceeded() {
            super(ErrorCode.YOUTUBE_API_REQUEST_LIMIT_EXCEEDED);
        }
        public YoutubeApiRequestLimitExceeded(Map<String, String> property) {
            super(ErrorCode.YOUTUBE_API_REQUEST_LIMIT_EXCEEDED, property);
        }
    }
}
