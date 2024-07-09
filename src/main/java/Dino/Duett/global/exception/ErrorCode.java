package Dino.Duett.global.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum ErrorCode {
    // Basic HTTP Status Code
    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Unauthorized"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not Found"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),

    // 1000: Authentication
    INVALID_VERIFICATION_CODE(1000, "인증 코드가 유효하지 않습니다"),
    VERIFICATION_CODE_NOT_FOUND(1001, "인증 코드를 찾을 수 없습니다"),
    LOGIN_FAILED(1002, "로그인 실패"),
    INVALID_PHONE_NUMBER(1003, "휴대폰 번호가 유효하지 않습니다"),
    EXPIRED_JWT_TOKEN(1100, "JWT 토큰의 유효기간이 만료되었습니다"),
    INVALID_JWT_TOKEN(1101, "JWT 토큰이 유효하지 않습니다"),


    // 2000: Member
    DUPLICATE_PHONE_NUMBER(2000, "전화번호가 중복됩니다"),
    DUPLICATE_KAKAO_ID(2001, "카카오 아이디가 중복됩니다"),
    ROLE_NOT_FOUND(2002, "역할을 찾을 수 없습니다"),
    MEMBER_NOT_FOUND(2003, "사용자를 찾을 수 없습니다"),
    INVALID_TOKEN(2004, "토큰이 유효하지 않습니다"),
    MEMBER_COIN_NOT_ENOUGH(2005, "코인이 부족합니다"),

    // 3000: Gmail
    INVALID_CONTENT_TYPE(3000, "올바르지 않은 메일 형식"),
    EMAIL_VALIDATION_FAILED(3001, "이메일 유효성 검사 실패"),
    MAIL_NOT_FOUND(3002, "메일을 찾을 수 없습니다"),

    // 4000: Image
    IMAGE_NOT_FOUND(4000, "이미지를 찾을 수 없습니다"),
    IMAGE_SAVE_FAILED(4001, "이미지 저장 실패"),
    IMAGE_CONVERT_FAILED(4002, "이미지 변환 실패"),
    MULTIPART_FILE_CONVERT_FAILED(4003, "MultipartFile 변환 실패"),
    IMAGE_DELETE_FAILED(4004, "이미지 삭제 실패"),

    // 5000: Profile
    PROFILE_NOT_FOUND(5000, "프로필을 찾을 수 없습니다."),
    PROFILE_FORBIDDEN(5001, "프로필 접근 권한이 없습니다."),
    PROFILE_INCOMPLETE(5002, "프로필의 모든 필드를 채워야합니다."),
    PROFILE_USERNAME_EXIST(5003, "이미 존재하는 유저 아이디입니다."),
    PROFILE_SELF_LIKE(5004, "자신의 프로필은 좋아요할 수 없습니다."),

    // 6000: Music
    MUSIC_NOT_FOUND(6000, "음악을 찾을 수 없습니다"),
    MUSIC_MAX_LIMIT(6001, "음악 최대 개수 초과"),

    // 7000: Mood
    MOOD_NOT_FOUND(7000, "무드를 찾을 수 없습니다."),
    MOOD_FORBIDDEN(7001, "무드 접근 권한이 없습니다."),

    // 8000: Tag
    TAG_NOT_FOUND(8000, "태그를 찾을 수 없습니다."),
    PROFILE_TAG_MAX_LIMIT(8001, "프로필 태그 최대 개수 초과"),

    // 9000: Youtube
    YOUTUBE_API_REQUEST_FAILED(9000, "Youtube API 요청 실패"),
    YOUTUBE_API_REQUEST_LIMIT_EXCEEDED(9001, "Youtube API 키 사용량 초과");

    private final int code;
    private final String message;
}
