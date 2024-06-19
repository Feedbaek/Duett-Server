package Dino.Duett.domain.profile.exception;

import Dino.Duett.global.exception.CustomException;
import Dino.Duett.global.exception.ErrorCode;

public class ProfileException extends CustomException {
    protected ProfileException(ErrorCode errorCode) {
        super(errorCode);
    }

    public static class ProfileNotFoundException extends ProfileException {
        public ProfileNotFoundException() {
            super(ErrorCode.PROFILE_NOT_FOUND);
        }
    }

    public static class ProfileForbiddenException extends ProfileException {
        public ProfileForbiddenException() {
            super(ErrorCode.PROFILE_FORBIDDEN);
        }
    }
}
