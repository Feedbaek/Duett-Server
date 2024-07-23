package Dino.Duett.domain.signup.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WithdrawalReq {
    @NotBlank(message = "전화번호는 공백일 수 없습니다.")
    private String phoneNumber;
    @NotBlank(message = "탈퇴 이유는 공백일 수 없습니다.")
    private String reason;
}
