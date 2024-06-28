package Dino.Duett.domain.image.exception;

import Dino.Duett.global.exception.CustomException;
import Dino.Duett.global.exception.ErrorCode;

import java.util.Map;

public class ImageException extends CustomException {
    protected ImageException(ErrorCode errorCode) {
        super(errorCode);
    }
    protected ImageException(ErrorCode errorCode, Map<String, String> property) {
        super(errorCode, property);
    }

    // Image 찾을 수 없는 예외
    public static class ImageNotFoundException extends ImageException {
        public ImageNotFoundException() {
            super(ErrorCode.IMAGE_NOT_FOUND);
        }
        public ImageNotFoundException(Map<String, String> property) {
            super(ErrorCode.IMAGE_NOT_FOUND, property);
        }
    }
    // Image 저장 실패 예외
    public static class ImageSaveFailedException extends ImageException {
        public ImageSaveFailedException() {
            super(ErrorCode.IMAGE_SAVE_FAILED);
        }
        public ImageSaveFailedException(Map<String, String> property) {
            super(ErrorCode.IMAGE_SAVE_FAILED, property);
        }
    }

    // Image 삭제 실패 예외
    public static class ImageDeleteFailedException extends ImageException {
        public ImageDeleteFailedException() {
            super(ErrorCode.IMAGE_DELETE_FAILED);
        }
        public ImageDeleteFailedException(Map<String, String> property) {
            super(ErrorCode.IMAGE_DELETE_FAILED, property);
        }
    }

    // Image 변환 실패
    public static class ImageConvertFailedException extends ImageException {
        public ImageConvertFailedException() {
            super(ErrorCode.IMAGE_CONVERT_FAILED);
        }
        public ImageConvertFailedException(Map<String, String> property) {
            super(ErrorCode.IMAGE_CONVERT_FAILED, property);
        }
    }

    // MultipartFile 변환 실패
    public static class MultipartFileConvertFailedException extends ImageException {
        public MultipartFileConvertFailedException() {
            super(ErrorCode.MULTIPART_FILE_CONVERT_FAILED);
        }
        public MultipartFileConvertFailedException(Map<String, String> property) {
            super(ErrorCode.MULTIPART_FILE_CONVERT_FAILED, property);
        }
    }
}
