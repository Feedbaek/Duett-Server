package Dino.Duett.domain.profile.service;

import Dino.Duett.domain.image.service.ImageService;
import Dino.Duett.domain.member.entity.Member;
import Dino.Duett.domain.member.exception.MemberException;
import Dino.Duett.domain.member.repository.MemberRepository;
import Dino.Duett.domain.mood.dto.response.MoodResponse;
import Dino.Duett.domain.mood.service.MoodService;
import Dino.Duett.domain.music.dto.MusicResponse;
import Dino.Duett.domain.music.service.MusicService;
import Dino.Duett.domain.profile.dto.response.ProfileCardResponse;
import Dino.Duett.domain.profile.dto.response.ProfileCardSummaryResponse;
import Dino.Duett.domain.profile.dto.response.ProfileMusicResponse;
import Dino.Duett.domain.profile.entity.Profile;
import Dino.Duett.domain.profile.exception.ProfileException;
import Dino.Duett.domain.profile.repository.ProfileRepository;
import Dino.Duett.domain.tag.enums.TagType;
import Dino.Duett.domain.tag.service.ProfileTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ProfileCardService {

    private final ProfileRepository profileRepository;
    private final MemberRepository memberRepository;
    private final MusicService musicService;
    private final MoodService moodService;
    private final ImageService imageService;
    private final ProfileTagService profileTagService;

    /**
    * 프로필 카드 전체 조회
    * @param memberId 사용자 id
    * @return ProfileCardResponse
    */
    @Transactional
    public ProfileCardResponse getProfileCard(final Long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow(MemberException.MemberNotFoundException::new);
        Profile profile = validateProfileIsNull(member.getProfile());

        return convertToDto(profile);
    }
    /**
     * 만 나이 계산
     * @param date 생년월일
     * @return int
     */
    protected int calculateAge(final LocalDate date) {
        return Period.between(date, LocalDate.now()).getYears();
    }

    /**
     * 프로필 카드 요약 조회
     * @param memberId 사용자 id
     * @param page 페이지
     * @param size 사이즈
     * @param radius 반경
     * @return List<ProfileCardSummaryResponse>
     */
    public List<ProfileCardSummaryResponse> getProfileCardsOfSummary(final Long memberId,
                                                                     final int page,
                                                                     final int size,
                                                                     double radius) {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberException.MemberNotFoundException::new);
        PageRequest pageRequest = PageRequest.of(page, size);
        List<Profile> profiles = profileRepository.findAllUsersWithinRadius(
                member.getProfile().getRegion().getLatitude(),
                member.getProfile().getRegion().getLatitude(),
                radius,
                member.getProfile().getGender().getOppositeGender(),
                pageRequest
        );

        return profiles.stream()
                .map(profile -> ProfileCardSummaryResponse.builder()
                                .profileId(profile.getId())
                                .name(profile.getName())
                                .age(calculateAge(profile.getBirthDate()) + "세")
                                .mbti(profile.getMbti())
                                .oneLineIntroduction(profile.getOneLineIntroduction())
                                .distance(calculateDistance(member.getProfile(), profile))
                                .profileImageUrl(imageService.getUrl(profile.getProfileImage()))
                                .tags(profileTagService.getProfileTagsOnlyFeatured(profile.getId()))
                                .build()
                ).toList();
    }

    /**
     * 프로필 카드 코인으로 조회
     * @param memberId 사용자 id
     * @param profileId 프로필 id
     * @return ProfileCardResponse
     */
    @Transactional
    public ProfileCardResponse getProfileCardWithCoin(final Long memberId,
                                                      final Long profileId) {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberException.MemberNotFoundException::new);
        Profile profile = profileRepository.findById(profileId).orElseThrow(ProfileException.ProfileNotFoundException::new);
        validateProfileOwner(member, profile);

        //member.updateCoin(COIN);//todo: mvp에서는 coin을 사용하지 않음

        return convertToDto(profile);
    }

    /**
     * 프로필 소유자 확인
     * @param member 사용자
     * @param profile 프로필
     */
    private void validateProfileOwner(Member member, Profile profile) {
        if (!member.getProfile().getId().equals(profile.getId())) {
            throw new ProfileException.ProfileForbiddenException();
        }
    }

    /**
     * 두 프로필 간 거리 계산
     * @param profile1 프로필1
     * @param profile2 프로필2
     * @return double
     */
    public double calculateDistance(final Profile profile1, final Profile profile2) {
        return Math.sqrt(
                    Math.pow(profile1.getRegion().getLatitude() - profile2.getRegion().getLatitude(), 2) +
                        Math.pow(profile1.getRegion().getLongitude() - profile2.getRegion().getLongitude(), 2)

        );
    }


    public ProfileMusicResponse getProfileMusic(final Long memberId) { // todo: 제거 예정
        Member member = memberRepository.findById(memberId).orElseThrow(MemberException.MemberNotFoundException::new);

        return ProfileMusicResponse.of(
                musicService.getMusics(memberId),
                moodService.getMood(memberId)
        );
    }

    private Profile validateProfileIsNull(Profile profile) {
        if (profile == null) {
            throw new ProfileException.ProfileNotFoundException();
        }
        return profile;
    }

    private ProfileCardResponse convertToDto(Profile profile) {
        return ProfileCardResponse.builder()
                .profileId(profile.getId())
                .name(profile.getName())
                .age(calculateAge(profile.getBirthDate()) + "세")
                .mbti(profile.getMbti())
                .oneLineIntroduction(profile.getOneLineIntroduction())
                .profileImageUrl(imageService.getUrl(profile.getProfileImage()))
                .musicTags(profileTagService.getProfileTags(profile.getId(), TagType.MUSIC))
                .hobbyTags(profileTagService.getProfileTags(profile.getId(), TagType.HOBBY))
                .lifeMusics(profile.getMusics().stream().map(MusicResponse::of).toList())
                .mood(MoodResponse.of(
                        profile.getMood().getTitle(),
                        profile.getMood().getArtist(),
                        imageService.getUrl(profile.getMood().getMoodImage())))
                .selfIntroduction(profile.getSelfIntroduction())
                .likeableMusicTaste(profile.getLikeableMusicTaste())
                .build();
    }
}
