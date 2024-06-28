package Dino.Duett.domain.mood.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Schema(description = "mood 수정 요청", type = "multipartForm")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class MoodRequest {
    @Schema(description = "음악 제목", example = "title")
    @NotEmpty
    String title;
    @Schema(description = "음악 아티스트 이름", example = "artist")
    @NotEmpty
    String artist;
    @Schema(description = "mood 이미지", example = "mood_image.png")
    @NotEmpty
    MultipartFile moodImage;
    @Schema(description = "mood 이미지 삭제 여부", example = "true")
    private Boolean isDeleteImage;
}
