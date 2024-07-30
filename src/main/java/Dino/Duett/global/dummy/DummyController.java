package Dino.Duett.global.dummy;

import Dino.Duett.config.EnvBean;
import Dino.Duett.config.login.jwt.JwtTokenProvider;
import Dino.Duett.config.login.jwt.JwtTokenType;
import Dino.Duett.domain.image.entity.Image;
import Dino.Duett.domain.member.entity.Member;
import Dino.Duett.domain.member.entity.Role;
import Dino.Duett.domain.member.enums.MemberState;
import Dino.Duett.domain.member.enums.RoleName;
import Dino.Duett.domain.member.repository.MemberRepository;
import Dino.Duett.domain.mood.entity.Mood;
import Dino.Duett.domain.music.dto.request.MusicCreateRequest;
import Dino.Duett.domain.music.entity.Music;
import Dino.Duett.domain.profile.entity.Location;
import Dino.Duett.domain.profile.entity.Profile;
import Dino.Duett.domain.profile.enums.GenderType;
import Dino.Duett.domain.profile.enums.MbtiType;
import Dino.Duett.domain.profile.service.ProfileService;
import Dino.Duett.domain.tag.dto.request.TagRequest;
import Dino.Duett.domain.tag.enums.TagState;
import Dino.Duett.domain.tag.service.ProfileTagService;
import Dino.Duett.global.dto.TokenDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
@Transactional
public class DummyController { // todo: 테스트 이후 API 삭제 예정
    private final MemberRepository memberRepository;
    private final JwtTokenProvider tokenProvider;
    private final ProfileTagService profileTagService;
    private final ProfileService profileService;
    private final Random random = new Random();
    private final EnvBean envBean;
    private final StringRedisTemplate redisTemplate;

    @PostMapping("/signup-member")
    @Operation(description = "회원가입 후 초기 상태로 로그인(이미지 제외)", tags = {"테스트"})
    public ResponseEntity<?> testSignupMember() {
        Member member = createDummyMemberAfterSignup();

        String accessToken = tokenProvider.createToken(member.getId(), JwtTokenType.ACCESS_TOKEN);
        String refreshToken = tokenProvider.createToken(member.getId(), JwtTokenType.REFRESH_TOKEN);
        redisTemplate.opsForValue().set(refreshToken, member.getId().toString());
        return ResponseEntity.ok(TokenDto.of(accessToken, refreshToken));
    }

    @PostMapping("/complete-member")
    @Operation(description = "모든 정보 채워진 유저 로그인", tags = {"테스트"})
    public ResponseEntity<?> testCompleteMember() {

        Member member = createDummyMember();

        String accessToken = tokenProvider.createToken(member.getId(), JwtTokenType.ACCESS_TOKEN);
        String refreshToken = tokenProvider.createToken(member.getId(), JwtTokenType.REFRESH_TOKEN);

        return ResponseEntity.ok(TokenDto.of(accessToken, refreshToken));
    }

    @PostMapping("/create-users")
    @Operation(description = "test 계정 여러개 만들기", tags = {"테스트"})
    public ResponseEntity<?> testMember() {

        Member member = createTestMembers();

        String accessToken = tokenProvider.createToken(member.getId(), JwtTokenType.ACCESS_TOKEN);
        String refreshToken = tokenProvider.createToken(member.getId(), JwtTokenType.REFRESH_TOKEN);
        redisTemplate.opsForValue().set(refreshToken, member.getId().toString());
        return ResponseEntity.ok(TokenDto.of(accessToken, refreshToken));
    }

    private Member createTestMembers() {
        Member member = null;
        for (int i = 0; i < 30; i++) {
            member = createDummyMember();
        }
        return member;
    }

    private Member createDummyMemberAfterSignup() {
        Member member = Member.builder()
                .phoneNumber(UUID.randomUUID().toString().substring(0, 11))
                .kakaoId(UUID.randomUUID().toString().substring(0, 8))
                .coin(0)
                .state(MemberState.ACTIVE)
                .role(makeDummyRole())
                .profile(makeDummyProfileAfterSignup())
                .build();
        memberRepository.save(member);
        return member;
    }

    private Mood makeDummyMood() {

        List<String> titles = List.of("Small girl", "뉴진스", "투모로우바이투게더", "Livingstone", "Glen Hansard & Marketa Irglova", "Charlie Puth");
        List<String> artists = List.of("이영지", "Attention", "어느날 머리에서 뿔이 자랐다", "Architect", "Falling Slowly", "I Don't Think That I Like Her");

        int randomIndex = random.nextInt(titles.size());
        return Mood.of(titles.get(randomIndex), artists.get(randomIndex), makeDummyMoodImage());
    }

    private List<TagRequest> makeDummyMusicTagRequests() {

        List<String> tags =
                List.of("팝", "힙합", "댄스", "일렉트로닉", "라틴", "록",
                "R&B", "메탈", "블루스", "소울", "아프로",
                "애니메이션", "앰비언트", "얼터너티브", "연주곡", "인디",
                "재즈", "컨트리", "Kpop", "클래식", "Jpop",
                "포크", "클래식", "파티", "로파이", "딥하우스",
                "Emo", "Funk", "디스코", "캐리비안", "뉴에이지",
                "메탈코어", "누메탈", "레게톤", "트로피칼", "EDM",
                "트랜스", "트로트", "발라드", "브릿팝", "보사노바");

        List<Integer> randomNumbers = getUniqueRandomIntegers(tags.size(), 3);
        return List.of(
                new TagRequest(tags.get(randomNumbers.get(0)), TagState.FEATURED),
                new TagRequest(tags.get(randomNumbers.get(1)), TagState.STANDARD),
                new TagRequest(tags.get(randomNumbers.get(2)), TagState.STANDARD)
        );
    }

    private List<TagRequest> makeDummyHobbyTagRequests() {

        List<String> tags =
                List.of("영화", "캠핑", "클럽", "바다가기", "콘서트",
                        "드라이빙", "공연", "경기관람", "박물관", "봉사활동",
                        "공원가기", "게임하기", "유튜브 시청", "TV 시청", "음악",
                        "댄스", "악기 연주", "글쓰기", "요리", "독서",
                        "뜨개질", "그림 그리기", "바느질", "자수", "농구",
                        "골프", "복싱", "야구", "배구", "스키",
                        "요가", "축구", "테니스", "등산", "배드민턴",
                        "스케이트", "낚시", "탁구", "보드", "자전거",
                        "조깅", "걷기", "헬스");
        List<Integer> randomNumbers = getUniqueRandomIntegers(tags.size(), 3);
        return List.of(
                new TagRequest(tags.get(randomNumbers.get(0)), TagState.FEATURED),
                new TagRequest(tags.get(randomNumbers.get(1)), TagState.STANDARD),
                new TagRequest(tags.get(randomNumbers.get(2)), TagState.STANDARD)
        );
    }

    private List<Music> makeDummyMusics() {
        List<Music> musics = new ArrayList<>();

        for(int i = 0 ; i < 3; i++) {
            List<String> titles = List.of("Small girl", "뉴진스", "투모로우바이투게더", "Livingstone", "Glen Hansard & Marketa Irglova", "Charlie Puth");
            List<String> artists = List.of("이영지", "Attention", "어느날 머리에서 뿔이 자랐다", "Architect", "Falling Slowly", "I Don't Think That I Like Her");
            List<Integer> randomNumbers = getUniqueRandomIntegers(titles.size(), 1);

            musics.add(Music.of(titles.get(randomNumbers.get(0)), artists.get(randomNumbers.get(0)), "LnhE5-ONvOc", null));
        }
        return musics;
    }

    private List<MusicCreateRequest> makeDummyMusicRequests() {
        List<MusicCreateRequest> requests = new ArrayList<>();

        for(int i = 0 ; i < 3; i++) {
            List<String> titles = List.of("Small girl", "뉴진스", "투모로우바이투게더", "Livingstone", "Glen Hansard & Marketa Irglova", "Charlie Puth");
            List<String> artists = List.of("이영지", "Attention", "어느날 머리에서 뿔이 자랐다", "Architect", "Falling Slowly", "I Don't Think That I Like Her");
            List<Integer> randomNumbers = getUniqueRandomIntegers(titles.size(), 1);
            MusicCreateRequest musicCreateRequest = new MusicCreateRequest(titles.get(randomNumbers.get(0)), artists.get(randomNumbers.get(0)), "LnhE5-ONvOc");
            requests.add(musicCreateRequest);
        }

        return requests;
    }

    private Profile makeDummyProfile(){
        List <String> names = List.of("김동현", "이현영", "박현제", "김민석", "이은규", "이윤성", "장광진", "김준휘");

        GenderType gender = GenderType.values()[random.nextInt(GenderType.values().length-1)];
        MbtiType mbti = MbtiType.values()[random.nextInt(MbtiType.values().length)];

        return Profile.builder()
                .name(names.get(random.nextInt(names.size())) + "-" + UUID.randomUUID().toString().substring(0, 4))
                .gender(gender)
                .mbti(mbti)
                .birthDate("2000년 10월 10일")
                .location(Location.of(getRandomDouble(37.5593193,  37.5554931), getRandomDouble(126.8947961, 127.0495665)))
                .profileImage(makeDummyProfileImage())
                .oneLineIntroduction("안녕하세요")
                .selfIntroduction("안녕하세요. 반갑습니다! 안녕하세요. 반갑습니다! 안녕하세요. 반갑습니다! 안녕하세요. 반갑습니다! 안녕하세요. 반갑습니다!")
                .likeableMusicTaste("좋아하는 음악은 없습니다.좋아하는 음악은 없습니다.좋아하는 음악은 없습니다.좋아하는 음악은 없습니다.좋아하는 음악은 없습니다.")
                .mood(makeDummyMood())
                .isProfileComplete(true)
                .profileTags(new ArrayList<>())
                .build();
    }
    private Profile makeDummyProfileAfterSignup(){
        List <String> names = List.of("김동현", "이현영", "박현제", "김민석", "이은규", "이윤성", "장광진", "김준휘");

        GenderType gender = GenderType.values()[random.nextInt(GenderType.values().length-1)];

        return Profile.builder()
                .name(names.get(random.nextInt(names.size())) + "-" + UUID.randomUUID().toString().substring(0, 4))
                .gender(gender)
                .birthDate("2000년 10월 10일")
                .location(Location.of(getRandomDouble(37.5593193, 37.5554931), getRandomDouble(126.8947961, 127.0495665)))
                .oneLineIntroduction("안녕하세요")
                .isProfileComplete(false)
                .build();
    }
    private Role makeDummyRole(){
        return Role.builder()
                .id(1L)
                .name(RoleName.USER.name())
                .build();
    }

    public Member createDummyMember(){
        Member member = Member.builder()
                .phoneNumber(UUID.randomUUID().toString().substring(0, 11))
                .kakaoId(UUID.randomUUID().toString().substring(0, 8))
                .coin(0)
                .state(MemberState.ACTIVE)
                .role(makeDummyRole())
                .profile(makeDummyProfile())
                .build();

        memberRepository.save(member);
        profileTagService.changeProfileTags(member.getId(), makeDummyMusicTagRequests(), makeDummyHobbyTagRequests());
        profileService.changeMusics(member.getId(), makeDummyMusicRequests(), null, null);
        return member;
    }

    private Image makeDummyMoodImage(){
        return Image.builder()
                .name(envBean.getBucketDir() + "/df658869-d57c-40bb-a2f6-247eb0ffb1b6")
                .extension("image/webp")
                .uuid("df658869-d57c-40bb-a2f6-247eb0ffb1b6")
                .build();
    }

    private Image makeDummyProfileImage(){
        return Image.builder()
                .name(envBean.getBucketDir() + "/2ac84e25-736a-420c-8c7e-09c42eff4993")
                .extension("image/webp")
                .uuid("2ac84e25-736a-420c-8c7e-09c42eff4993")
                .build();
    }

    private List<Integer> getUniqueRandomIntegers(int range, int count){
        Set<Integer> set= new HashSet<>();
        while(set.size() < count){
            set.add(random.nextInt(range));
        }
        return new ArrayList<>(set);
    }

    private double getRandomDouble(double min, double max) {
        double value = min + (max - min) * random.nextDouble();
        BigDecimal bd = new BigDecimal(value).setScale(7, RoundingMode.HALF_UP);

        return bd.doubleValue();
    }
}
