package Dino.Duett.domain.tag.dto.request;

import Dino.Duett.domain.tag.enums.TagState;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "태그 등록 요청")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class TagRequest {
    @Schema(description = "태그 이름", example = "음악")
    String name;
    @Schema(description = "태그 강조 순위. FEATURED: 강조 선택, STANDARD: 선택, NONE: 미선택", example = "1")
    TagState state;
}
