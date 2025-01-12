package Dino.Duett.domain.profile.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Schema(description = "위치 좌표 요청")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LocationRequest {
    @NotNull
    @Schema(description = "위도와 경도 좌표의 리스트", example = "[37.7749, -122.4194]")
    private List<Double> location;
}