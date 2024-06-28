package Dino.Duett.domain.signup.dto;


import Dino.Duett.domain.profile.enums.GenderType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class SignUpReq {
    @NotBlank
    private String phoneNumber;
    @NotBlank
    private String verificationCode;
    @NotBlank
    private String name;
    @NotBlank
    private String kakaoId;
    @NotNull
    @Schema(example = "MAN")
    private GenderType gender;
    @NotBlank
    private String birthDate;
    @NotNull
    private double[] location;
    @NotNull
    private MultipartFile profileImage;
    @NotBlank
    private String oneLineIntroduction;
}
