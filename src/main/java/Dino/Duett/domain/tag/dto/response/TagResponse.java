package Dino.Duett.domain.tag.dto.response;

import Dino.Duett.domain.tag.entity.Tag;
import Dino.Duett.domain.tag.enums.TagState;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Schema(description = "태그 응답")
@Getter
@AllArgsConstructor
public class TagResponse {
    @Schema(description = "태그 이름", example = "팝")
    String name;
    @Schema(description = "태그 강조 순위. FEATURED: 강조 선택, STANDARD: 선택, NONE: 미선택", example = "STANDARD")
    TagState state;

    public static TagResponse of(String name,
                                 TagState state) {
        return new TagResponse(
                name,
                state);
    }
}
