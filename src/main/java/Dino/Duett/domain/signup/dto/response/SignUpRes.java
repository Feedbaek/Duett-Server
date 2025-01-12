package Dino.Duett.domain.signup.dto.response;

import Dino.Duett.domain.member.dto.MemberDto;
import Dino.Duett.global.dto.TokenDto;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class SignUpRes {
    @NotNull
    private MemberDto member;
    @NotNull
    private TokenDto token;
}
