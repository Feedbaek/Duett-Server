package Dino.Duett.domain.member.exception;

import Dino.Duett.global.exception.CustomException;
import Dino.Duett.global.exception.ErrorCode;

import java.util.Map;

public class MemberException extends CustomException {
    protected MemberException(ErrorCode errorCode) {
        super(errorCode);
    }
    protected MemberException(ErrorCode errorCode, Map<String, String> property) {
        super(errorCode, property);
    }

    // PhoneNumber 중복 예외
    public static class DuplicatePhoneNumberException extends MemberException {
        public DuplicatePhoneNumberException() {
            super(ErrorCode.DUPLICATE_PHONE_NUMBER);
        }
        public DuplicatePhoneNumberException(Map<String, String> property) {
            super(ErrorCode.DUPLICATE_PHONE_NUMBER, property);
        }
    }

    // KakaoId 중복 예외
    public static class DuplicateKakaoIdException extends MemberException {
        public DuplicateKakaoIdException() {
            super(ErrorCode.DUPLICATE_KAKAO_ID);
        }
        public DuplicateKakaoIdException(Map<String, String> property) {
            super(ErrorCode.DUPLICATE_KAKAO_ID, property);
        }
    }

    // Role 찾을 수 없는 예외
    public static class RoleNotFoundException extends MemberException {
        public RoleNotFoundException() {
            super(ErrorCode.ROLE_NOT_FOUND);
        }
        public RoleNotFoundException(Map<String, String> property) {
            super(ErrorCode.ROLE_NOT_FOUND, property);
        }
    }

    // Member 찾을 수 없는 예외
    public static class MemberNotFoundException extends MemberException {
        public MemberNotFoundException() {
            super(ErrorCode.MEMBER_NOT_FOUND);
        }
        public MemberNotFoundException(Map<String, String> property) {
            super(ErrorCode.MEMBER_NOT_FOUND, property);
        }
    }

    // 토큰이 유효하지 않은 예외
    public static class InvalidTokenException extends MemberException {
        public InvalidTokenException() {
            super(ErrorCode.INVALID_TOKEN);
        }
        public InvalidTokenException(Map<String, String> property) {
            super(ErrorCode.INVALID_TOKEN, property);
        }
    }
    // Member의 코인이 부족한 예외
    public static class MemberCoinNotEnoughException extends MemberException {
        public MemberCoinNotEnoughException() {
            super(ErrorCode.MEMBER_COIN_NOT_ENOUGH);
        }
        public MemberCoinNotEnoughException(Map<String, String> property) {
            super(ErrorCode.MEMBER_COIN_NOT_ENOUGH, property);
        }
    }
}
