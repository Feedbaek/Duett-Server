package Dino.Duett.domain;

import Dino.Duett.config.login.jwt.JwtTokenProvider;
import Dino.Duett.config.login.jwt.JwtTokenType;
import Dino.Duett.domain.member.entity.Member;
import Dino.Duett.domain.member.entity.Role;
import Dino.Duett.domain.member.enums.MemberState;
import Dino.Duett.domain.member.enums.RoleName;
import Dino.Duett.domain.member.repository.MemberRepository;
import Dino.Duett.domain.music.dto.request.MusicCreateRequest;
import Dino.Duett.domain.music.entity.Music;
import Dino.Duett.domain.music.service.MusicService;
import Dino.Duett.domain.profile.entity.Profile;
import Dino.Duett.domain.profile.entity.Location;
import Dino.Duett.domain.profile.enums.GenderType;
import Dino.Duett.domain.profile.repository.ProfileRepository;
import Dino.Duett.global.dto.TokenDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {
    private final MemberRepository memberRepository;
    private final ProfileRepository profileRepository;
    private final JwtTokenProvider tokenProvider;
    private final MusicService musicService;

    @PostMapping("/login")
    @Operation(description = "test 로그인", tags = {"테스트"})
    public ResponseEntity<?> testLogin() {

        Member member = createTestMember();

        String accessToken = tokenProvider.createToken(member.getId(), JwtTokenType.ACCESS_TOKEN);
        String refreshToken = tokenProvider.createToken(member.getId(), JwtTokenType.REFRESH_TOKEN);

        return ResponseEntity.ok(TokenDto.of(accessToken, refreshToken));
    }

    @PostMapping("/create")
    @Operation(description = "test 계정 여러개 만들기", tags = {"테스트"})
    public ResponseEntity<?> testMember() {

        Member member = createTestMembers();

        String accessToken = tokenProvider.createToken(member.getId(), JwtTokenType.ACCESS_TOKEN);
        String refreshToken = tokenProvider.createToken(member.getId(), JwtTokenType.REFRESH_TOKEN);

        return ResponseEntity.ok(TokenDto.of(accessToken, refreshToken));
    }

    private Member createTestMembers(){
        Member member = null;
        for(int i =0 ; i< 15 ; i++) {
            member = createTestMember();
        }
        return member;
    }

    private Member createTestMember(){
        Role role = Role.builder()
                .id(1L)
                .name(RoleName.USER.name())
                .build();

        Profile profile1 = Profile.builder()
                .name("김철수")
                .gender(GenderType.MAN)
                .birthDate("1999.01.01")
                .location(Location.of( 37.5758772, 126.9768121))
                .profileImage(null)
                .build();

        Profile profile2 = Profile.builder()
                .name("김영희")
                .gender(GenderType.WOMAN)
                .birthDate("1999.01.01")
                .location(Location.of(37.5758772, 126.9768121))
                .profileImage(null)
                .build();

        Member member1 = Member.builder()
                .phoneNumber(UUID.randomUUID().toString().substring(0, 11))
                .kakaoId(UUID.randomUUID().toString().substring(0, 30))
                .coin(0)
                .state(MemberState.ACTIVE)
                .role(role)
                .profile(profile1)
                .build();

        Member member2 = Member.builder()
                .phoneNumber(UUID.randomUUID().toString().substring(0, 11))
                .kakaoId(UUID.randomUUID().toString().substring(0, 30))
                .coin(0)
                .state(MemberState.ACTIVE)
                .role(role)
                .profile(profile2)
                .build();


        profileRepository.save(profile1);
        profileRepository.save(profile2);
        memberRepository.save(member1);
        memberRepository.save(member2);

        return member1;
    }
}
