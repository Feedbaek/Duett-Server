package Dino.Duett.domain.authentication.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDto {
    @NotBlank
    @Schema(description = "휴대폰 번호", example = "01012345678")
    private String phoneNumber;
    @NotBlank
    @Schema(description = "인증 코드", example = "a123456")
    private String verificationCode;
}
