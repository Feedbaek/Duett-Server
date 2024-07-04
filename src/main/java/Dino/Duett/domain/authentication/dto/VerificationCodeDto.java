package Dino.Duett.domain.authentication.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class VerificationCodeDto {
    @NotBlank
    private boolean exists;
    @NotBlank
    private String code;
}
