package Dino.Duett.domain.profile.service;

import Dino.Duett.domain.image.service.ImageService;
import Dino.Duett.domain.member.entity.Member;
import Dino.Duett.domain.member.exception.MemberException;
import Dino.Duett.domain.member.repository.MemberRepository;
import Dino.Duett.domain.message.repository.MessageRepository;
import Dino.Duett.domain.mood.dto.response.MoodResponse;
import Dino.Duett.domain.music.dto.response.MusicResponse;
import Dino.Duett.domain.profile.dto.response.*;
import Dino.Duett.domain.profile.entity.Profile;
import Dino.Duett.domain.profile.exception.ProfileException;
import Dino.Duett.domain.profile.repository.ProfileLikeRepository;
import Dino.Duett.domain.profile.repository.ProfileRepository;
import Dino.Duett.domain.tag.enums.TagType;
import Dino.Duett.domain.tag.service.ProfileTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProfileCardService {

    private final ProfileRepository profileRepository;
    private final MemberRepository memberRepository;
    private final ProfileLikeRepository profileLikeRepository;
    private final MessageRepository messageRepository;
    private final ImageService imageService;
    private final ProfileTagService profileTagService;
    private final ProfileUnlockService profileUnlockService;
    private final ProfileService profileService;

    /**
     * 프로필 카드 완성 여부 조회
     * @param memberId 사용자 id
     * @return boolean
     */
    public boolean getProfileCardCompletionStatus(final Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberException.MemberNotFoundException::new);
        Profile profile = validateProfileIsNull(member.getProfile());
        return profileService.isProfileComplete(profile);
    }


    /**
     * 잠긴 프로필 카드 목록 조회
     * @param memberId 사용자 id
     * @param page 페이지
     * @param size 사이즈
     * @param radius 반경(km)
     * @param checkProfileComplete 사용자 프로필 완성 여부를 확인할지 여부
     * @return ProfileLockResponse
     */
    @Transactional
    public ProfileLockResponse getLockedProfileCards(final Long memberId,
                                                     final Integer page,
                                                     final Integer size,
                                                     final Double radius,
                                                     final Boolean checkProfileComplete) {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberException.MemberNotFoundException::new);
        Profile viewerProfile = validateProfileIsNull(member.getProfile());
        return ProfileLockResponse.of(
                getProfileCardsOfSummary(
                memberId,
                page,
                size,
                radius
                ),
                member.getCoin(),
                checkProfileComplete ? profileService.isProfileComplete(viewerProfile) : null
        );
    }

    /**
     * 프로필 카드 상세 조회
     * @param memberId 사용자 id
     * @param profileId 프로필 id
     */
    @Transactional
    public ProfileUnlockResponse getUnlockedProfileCard(final Long memberId, final Long profileId) {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberException.MemberNotFoundException::new);
        return ProfileUnlockResponse.of(
                getProfileCard(memberId, profileId),
                member.getCoin());
    }


    /**
     * 코인을 사용해서 프로필 카드 상세 조회
     * @param memberId 사용자 id
     * @param profileId 프로필 id
     */
    @Transactional
    public ProfileUnlockResponse getUnlockedProfileCardWithCoin(final Long memberId, final Long profileId) {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberException.MemberNotFoundException::new);
        return ProfileUnlockResponse.of(
                getProfileCardWithCoin(memberId, profileId),
                member.getCoin());
    }


    /**
     * 프로필 카드 요약 조회
     * @param memberId 사용자 id
     * @param page 페이지
     * @param size 사이즈
     * @param radius 반경(km)
     * @return List<ProfileCardSummaryResponse>
     */
    public List<ProfileCardSummaryResponse> getProfileCardsOfSummary(final Long memberId,
                                                                     final Integer page,
                                                                     final Integer size,
                                                                     final Double radius) {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberException.MemberNotFoundException::new);
        Profile viewerProfile = validateProfileIsNull(member.getProfile());

        PageRequest pageRequest = PageRequest.of(page, size);
        List<Profile> profiles = profileRepository.findAllUsersWithinRadius(
                member.getProfile().getLocation().getLatitude(),
                member.getProfile().getLocation().getLongitude(),
                radius,
                member.getProfile().getGender().getOppositeGender(),
                viewerProfile.getProfileUnlocks() != null ? viewerProfile.getProfileUnlocks().stream().map(profileUnlock -> profileUnlock.getViewedProfile().getId()).toList() : null,
                pageRequest
        );

        return profiles.stream()
                .map(profile -> ProfileCardSummaryResponse.builder()
                                .profileId(profile.getId())
                                .name(profile.getName())
                                .birthDate(profile.getBirthDate())
                                .mbti(profile.getMbti())
                                .oneLineIntroduction(profile.getOneLineIntroduction())
                                .distance(calculateDistance(member.getProfile(), profile))
                                .lifeMusics(profile.getMusics().stream().map(MusicResponse::of).toList())
                                .tags(profileTagService.getProfileTagsOnlyFeatured(profile.getId()))
                                .build())
                .toList();
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
        Profile viewerProfile = validateProfileIsNull(member.getProfile());
        Profile viewedProfile = profileRepository.findById(profileId).orElseThrow(ProfileException.ProfileNotFoundException::new);
        validateProfileIsComplete(viewerProfile);
        validateProfileIsCompleteForOthers(viewedProfile);

        //member.updateCoin(COIN);//todo: mvp에서는 coin을 사용하지 않음
        profileUnlockService.createProfileUnlock(viewerProfile.getId(), viewedProfile.getId());

        return convertToDto(viewedProfile, member, calculateDistance(viewerProfile, viewedProfile));
    }

    /**
     * 프로필 카드 단일 조회
     * @param memberId 사용자 id
     * @param profileId 조회할 프로필 id
     * @return ProfileCardResponse
     */
    public ProfileCardResponse getProfileCard(final Long memberId, final Long profileId) {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberException.MemberNotFoundException::new);
        Profile viewerProfile = validateProfileIsNull(member.getProfile());
        Profile viewedProfile = profileRepository.findById(profileId).orElseThrow(ProfileException.ProfileNotFoundException::new);
        Member sender = memberRepository.findByProfileId(profileId).orElseThrow(MemberException.MemberNotFoundException::new);
        validateProfileIsComplete(viewerProfile);
        validateUnlockedProfileOrReceivedMessage(viewerProfile, viewedProfile, member, sender);

        return convertToDto(viewedProfile, member, calculateDistance(viewerProfile, viewedProfile));
    }

    /**
     * 프로필 잠금 해제 여부 혹은 쪽지 수신자 여부 검증
     * @param viewerProfile 잠금해제 하는 유저의 프로필
     * @param viewedProfile 잠금해제 당하는 유저의 프로필
     * @param receiver 쪽지 수신자
     * @param sender 쪽지 발신자
     */
    private void validateUnlockedProfileOrReceivedMessage(final Profile viewerProfile,
                                                          final Profile viewedProfile,
                                                          final Member receiver,
                                                          final Member sender){
        if(!(checkProfileIsLocked(viewerProfile, viewedProfile) || checkMemberReceivedMessage(receiver, sender))){
            throw new ProfileException.ProfileForbiddenException();
        }
    }

    /**
     * 프로필 잠금해제 여부 확인
     * @param viewerProfile 잠금해제 하는 유저의 프로필
     * @param viewedProfile 잠금해제 당하는 유저의 프로필
     * @return boolean
     */
    private boolean checkProfileIsLocked(final Profile viewerProfile, final Profile viewedProfile) {
        return viewerProfile.getProfileUnlocks().stream()
                .anyMatch(profileUnlock -> profileUnlock.getViewedProfile().getId().equals(viewedProfile.getId()));
    }

    /**
     * 프로필 잠금해제 여부 확인
     * @param receiver 수신자
     * @param sender 발신자
     * @return boolean
     */
    private boolean checkMemberReceivedMessage(final Member receiver, final Member sender){
        return messageRepository.existsByReceiverIdAndSenderId(receiver.getId(), sender.getId());
    }

    /**
     * 자신의 프로필 완성 여부 검증
     * @param profile 프로필
     */
    private void validateProfileIsComplete(final Profile profile) {
        if (!profile.getIsProfileComplete()) {
            throw new ProfileException.ProfileIncompleteException();
        }
    }

    /**
     * 상대의 프로필 완성 여부 검증
     * @param profile 프로필
     */

    private void validateProfileIsCompleteForOthers(final Profile profile) {
        if(!profile.getIsProfileComplete()){
            throw new ProfileException.ProfileForbiddenException();
        }
    }


    /**
     * 프로필 소유자 검증
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
    private double calculateDistance(final Profile profile1, final Profile profile2) {
        final BigDecimal EARTH_RADIUS_KM = new BigDecimal("6371.0");

        double lat1 = profile1.getLocation().getLatitude();
        double lon1 = profile1.getLocation().getLongitude();
        double lat2 = profile2.getLocation().getLatitude();
        double lon2 = profile2.getLocation().getLongitude();

        BigDecimal lat1Rad = BigDecimal.valueOf(Math.toRadians(lat1));
        BigDecimal lon1Rad = BigDecimal.valueOf(Math.toRadians(lon1));
        BigDecimal lat2Rad = BigDecimal.valueOf(Math.toRadians(lat2));
        BigDecimal lon2Rad = BigDecimal.valueOf(Math.toRadians(lon2));

        BigDecimal deltaLat = lat2Rad.subtract(lat1Rad);
        BigDecimal deltaLon = lon2Rad.subtract(lon1Rad);

        BigDecimal sinDeltaLatDiv2 = BigDecimal.valueOf(Math.sin(deltaLat.doubleValue() / 2));
        BigDecimal sinDeltaLonDiv2 = BigDecimal.valueOf(Math.sin(deltaLon.doubleValue() / 2));

        BigDecimal a = sinDeltaLatDiv2.multiply(sinDeltaLatDiv2)
                .add(BigDecimal.valueOf(Math.cos(lat1Rad.doubleValue()))
                        .multiply(BigDecimal.valueOf(Math.cos(lat2Rad.doubleValue())))
                        .multiply(sinDeltaLonDiv2).multiply(sinDeltaLonDiv2));

        BigDecimal sqrtA = BigDecimal.valueOf(Math.sqrt(a.doubleValue()));
        BigDecimal sqrtOneMinusA = BigDecimal.valueOf(Math.sqrt(1.0 - a.doubleValue()));
        BigDecimal c = BigDecimal.valueOf(2.0).multiply(BigDecimal.valueOf(Math.atan2(sqrtA.doubleValue(), sqrtOneMinusA.doubleValue())));

        BigDecimal distance = EARTH_RADIUS_KM.multiply(c, new MathContext(10, RoundingMode.HALF_UP));

        distance = distance.setScale(1, RoundingMode.HALF_UP);

        return distance.doubleValue();
    }

    private Profile validateProfileIsNull(Profile profile) {
        if (profile == null) {
            throw new ProfileException.ProfileNotFoundException();
        }
        return profile;
    }

    private ProfileCardResponse convertToDto(Profile profile, Member member, Double distance) {
        return ProfileCardResponse.builder()
                .profileId(profile.getId())
                .memberId(memberRepository.findByProfileId(profile.getId()).orElseThrow(MemberException.MemberNotFoundException::new).getId())
                .name(profile.getName())
                .birthDate(profile.getBirthDate())
                .mbti(profile.getMbti())
                .distance(distance)
                .oneLineIntroduction(profile.getOneLineIntroduction())
                .profileImageUrl(profile.getProfileImage() != null ? imageService.getUrl(profile.getProfileImage()) : null)
                .musicTags(profileTagService.getProfileTags(profile.getId(), TagType.MUSIC))
                .hobbyTags(profileTagService.getProfileTags(profile.getId(), TagType.HOBBY))
                .lifeMusics(profile.getMusics().stream().map(MusicResponse::of).toList())
                .mood(profile.getMood() != null ? (MoodResponse.of(
                        profile.getMood().getTitle(),
                        profile.getMood().getArtist(),
                        profile.getMood().getMoodImage() != null ? imageService.getUrl(profile.getMood().getMoodImage()) : null)) : null)
                .selfIntroduction(profile.getSelfIntroduction())
                .likeableMusicTaste(profile.getLikeableMusicTaste())
                .likeState(profileLikeRepository.existsByMemberAndLikedProfile(member, profile))
                .build();
    }

    public ProfileCardBriefResponse convertToBriefDto(Profile profile, LocalDateTime likeDate) {
        return ProfileCardBriefResponse.builder()
                .profileId(profile.getId())
                .name(profile.getName())
                .birthDate(profile.getBirthDate())
                .mbti(profile.getMbti())
                .lifeMusic(profile.getMusics().stream().findFirst().map(MusicResponse::of).orElse(null))
                .tags(profileTagService.getProfileTagsOnlyFeatured(profile.getId()))
                .likeDate(likeDate)
                .build();
    }
}
