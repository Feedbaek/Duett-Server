package Dino.Duett.domain.profile.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Schema(description = "내 정보 등록 및 수정 요청", type = "multipartForm")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class ProfileInfoRequest {
    @Schema(description = "프로필 이미지", example = "image.jpg", nullable = true)
    private MultipartFile profileImage;
    @Schema(description = "닉네임", example = "crush", minLength = 3, maxLength = 15, nullable = true)
    @Size(min = 3, max = 15)
    private String name;
    @Schema(description = "프로필 한줄소개", example = "안녕하세요!", minLength = 5, maxLength = 50, nullable = true)
    @Size(min = 5, max = 50)
    private String oneLineIntroduction;
    @Schema(description = "프로필 이미지 삭제 여부", example = "true", nullable = true)
    private Boolean isDeleteImage;
}
