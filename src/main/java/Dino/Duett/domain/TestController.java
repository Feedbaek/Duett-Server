package Dino.Duett.domain;

import Dino.Duett.config.login.jwt.JwtTokenProvider;
import Dino.Duett.config.login.jwt.JwtTokenType;
import Dino.Duett.domain.member.entity.Member;
import Dino.Duett.domain.member.entity.Role;
import Dino.Duett.domain.member.enums.MemberState;
import Dino.Duett.domain.member.enums.RoleName;
import Dino.Duett.domain.member.repository.MemberRepository;
import Dino.Duett.domain.mood.entity.Mood;
import Dino.Duett.domain.music.entity.Music;
import Dino.Duett.domain.profile.entity.Location;
import Dino.Duett.domain.profile.entity.Profile;
import Dino.Duett.domain.profile.enums.GenderType;
import Dino.Duett.domain.profile.enums.MbtiType;
import Dino.Duett.domain.tag.dto.request.TagRequest;
import Dino.Duett.domain.tag.enums.TagState;
import Dino.Duett.domain.tag.service.ProfileTagService;
import Dino.Duett.global.dto.TokenDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
@Transactional
public class TestController { // todo: 테스트 이후 삭제 예정
    private final MemberRepository memberRepository;
    private final JwtTokenProvider tokenProvider;
    private final ProfileTagService profileTagService;

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

        Mood mood1 = Mood.of("title","artist",null);
        Mood mood2 = Mood.of("title","artist",null);

        List<TagRequest> tagRequest1 =List.of(
                new TagRequest("팝",TagState.FEATURED),
                new TagRequest("발라드",TagState.STANDARD),
                new TagRequest("힙합",TagState.STANDARD)
        );

        List<TagRequest> tagRequest2 =List.of(
                new TagRequest("영화",TagState.FEATURED),
                new TagRequest("콘서트",TagState.STANDARD),
                new TagRequest("캠핑",TagState.STANDARD)
        );




        List <Music> musics1 = new ArrayList<>();
        List <Music> musics2 = new ArrayList<>();


        for(int i=0; i< 3; i++){
            musics1.add(Music.of("title","artist","https://www.youtube.com/watch?v=LnhE5-ONvOc"));
            musics2.add(Music.of("title","artist","https://www.youtube.com/watch?v=LnhE5-ONvOc"));
        }

        Profile profile1 = Profile.builder()
                .name("김철수")
                .gender(GenderType.MAN)
                .mbti(MbtiType.ENTJ)
                .birthDate("2000.01.01")
                .location(Location.of( 37.5758772, 126.9768121))
                .profileImage(null)
                .oneLineIntroduction("안녕하세요")
                .selfIntroduction("안녕하세요. 반갑습니다! 안녕하세요. 반갑습니다! 안녕하세요. 반갑습니다! 안녕하세요. 반갑습니다! 안녕하세요. 반갑습니다!")
                .likeableMusicTaste("좋아하는 음악은 없습니다.좋아하는 음악은 없습니다.좋아하는 음악은 없습니다.좋아하는 음악은 없습니다.좋아하는 음악은 없습니다.")
                .mood(mood1)
                .musics(musics1)
                .profileTags(new ArrayList<>())
                .build();

        Profile profile2 = Profile.builder()
                .name("김영희")
                .gender(GenderType.WOMAN)
                .mbti(MbtiType.ENFJ)
                .birthDate("1999.01.01")
                .location(Location.of(37.5758772, 126.9768121))
                .profileImage(null)
                .oneLineIntroduction("안녕하세요")
                .selfIntroduction("안녕하세요. 반갑습니다! 안녕하세요. 반갑습니다! 안녕하세요. 반갑습니다! 안녕하세요. 반갑습니다! 안녕하세요. 반갑습니다!")
                .likeableMusicTaste("좋아하는 음악은 없습니다.좋아하는 음악은 없습니다.좋아하는 음악은 없습니다.좋아하는 음악은 없습니다.좋아하는 음악은 없습니다.")
                .mood(mood2)
                .musics(musics2)
                .profileTags(new ArrayList<>())
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


        member1 = memberRepository.save(member1);
        member2 = memberRepository.save(member2);

        profileTagService.changeProfileTags(member1.getId(),tagRequest1,tagRequest2);
        profileTagService.changeProfileTags(member2.getId(),tagRequest1,tagRequest2);

        return member1;
    }
}
