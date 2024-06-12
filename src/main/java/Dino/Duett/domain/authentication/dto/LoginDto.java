package Dino.Duett.domain.authentication.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDto {
    private String phoneNumber;
    private String verificationCode;
}
