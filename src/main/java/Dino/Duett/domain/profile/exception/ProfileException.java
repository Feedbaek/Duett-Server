package Dino.Duett.domain.profile.exception;

import Dino.Duett.global.exception.CustomException;
import Dino.Duett.global.exception.ErrorCode;

import java.util.Map;

public class ProfileException extends CustomException {
    protected ProfileException(ErrorCode errorCode) {
        super(errorCode);
    }
    protected ProfileException(ErrorCode errorCode, Map<String, String> property) {
        super(errorCode, property);
    }

    public static class ProfileNotFoundException extends ProfileException {
        public ProfileNotFoundException() {
            super(ErrorCode.PROFILE_NOT_FOUND);
        }
        public ProfileNotFoundException(Map<String, String> property) {
            super(ErrorCode.PROFILE_NOT_FOUND, property);
        }
    }

    public static class ProfileForbiddenException extends ProfileException {
        public ProfileForbiddenException() {
            super(ErrorCode.PROFILE_FORBIDDEN);
        }
        public ProfileForbiddenException(Map<String, String> property) {
            super(ErrorCode.PROFILE_FORBIDDEN, property);
        }
    }

    public static class ProfileUsernameExistException extends ProfileException {
        public ProfileUsernameExistException(ErrorCode errorCode) {
            super(errorCode);
        }
        public ProfileUsernameExistException() {
            super(ErrorCode.PROFILE_USERNAME_EXIST);
        }
    }


    public static class ProfileIncompleteException extends ProfileException {
        public ProfileIncompleteException() {
            super(ErrorCode.PROFILE_INCOMPLETE);
        }
        public ProfileIncompleteException(Map<String, String> property) {
            super(ErrorCode.PROFILE_INCOMPLETE, property);
        }
    }
}
