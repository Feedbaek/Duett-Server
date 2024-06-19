package Dino.Duett.domain;

import Dino.Duett.config.login.jwt.JwtTokenProvider;
import Dino.Duett.config.login.jwt.JwtTokenType;
import Dino.Duett.domain.member.entity.Member;
import Dino.Duett.domain.member.entity.Role;
import Dino.Duett.domain.member.enums.MemberState;
import Dino.Duett.domain.member.enums.RoleName;
import Dino.Duett.domain.member.repository.MemberRepository;
import Dino.Duett.domain.profile.entity.Profile;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {
    private final MemberRepository memberRepository;
    private final JwtTokenProvider tokenProvider;
    @PostMapping
    public ResponseEntity<?> test() {
        Role role = Role.builder()
                .id(1L)
                .name(RoleName.USER.name())
                .build();
        Member member = Member.builder()
                .phoneNumber("010-4420-6790")
                .kakaoId("kakaoId")
                .coin(0)
                .state(MemberState.ACTIVE)
                .role(role)
                .profile(Profile.builder().build())
                .build();

        memberRepository.save(member);
        return ResponseEntity.ok(tokenProvider.createToken(member.getId(), JwtTokenType.ACCESS_TOKEN));
    }

}
