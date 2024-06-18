package Dino.Duett.domain.profile.service;

import Dino.Duett.domain.image.entity.Image;
import Dino.Duett.domain.image.service.ImageService;
import Dino.Duett.domain.member.entity.Member;
import Dino.Duett.domain.member.exception.MemberException;
import Dino.Duett.domain.member.repository.MemberRepository;
import Dino.Duett.domain.mood.dto.response.MoodResponse;
import Dino.Duett.domain.mood.service.MoodService;
import Dino.Duett.domain.music.service.MusicService;
import Dino.Duett.domain.profile.dto.request.ProfileInfoRequest;
import Dino.Duett.domain.profile.dto.request.ProfileIntroRequest;
import Dino.Duett.domain.profile.dto.response.*;
import Dino.Duett.domain.profile.entity.Profile;
import Dino.Duett.domain.profile.exception.ProfileException;
import Dino.Duett.domain.profile.repository.ProfileRepository;
import Dino.Duett.domain.tag.dto.response.TagByTypeResponse;
import Dino.Duett.domain.tag.enums.TagType;
import Dino.Duett.domain.tag.service.ProfileTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final MemberRepository memberRepository;
    private final MusicService musicService;
    private final MoodService moodService;
    private final ImageService imageService;
    private final ProfileTagService profileTagService;
    private final ProfileRepository profileRepository;
    /**
     * 마이페이지 프로필 조회
     * @param memberId 사용자 id
     * @return ProfileResponse
     */
    public ProfileResponse getProfile(final Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberException.MemberNotFoundException::new);
        Profile profile = validateProfileIsNull(member.getProfile());
        return ProfileResponse.builder()
                .profileId(profile.getId())
                .name(profile.getName())
                .birthDate(profile.getBirthDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")))
                .mbti(profile.getMbti())
                .oneLineIntroduction(profile.getOneLineIntroduction())
                .profileImageUrl(imageService.getUrl(profile.getProfileImage()))
                .musicTags(profileTagService.getProfileTags(profile.getId(), TagType.MUSIC))
                .hobbyTags(profileTagService.getProfileTags(profile.getId(), TagType.HOBBY))
                .lifeMusics(musicService.getMusics(profile.getId()))
                .mood(MoodResponse.of(
                        profile.getMood().getTitle(),
                        profile.getMood().getArtist(),
                        imageService.getUrl(profile.getMood().getMoodImage())))
                .selfIntroduction(profile.getSelfIntroduction())
                .likeableMusicTaste(profile.getLikeableMusicTaste())
                .build();
    }

    public ProfileInfoResponse getProfileInfo(final Long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow(MemberException.MemberNotFoundException::new);
        Profile profile = validateProfileIsNull(member.getProfile());
        return ProfileInfoResponse.of(
                profile,
                imageService.getUrl(profile.getProfileImage())
        );
    }

    public ProfileIntroResponse getProfileIntro(final Long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow(MemberException.MemberNotFoundException::new);
        Profile profile = validateProfileIsNull(member.getProfile());
        return ProfileIntroResponse.of(
                profile.getMbti(),
                profileTagService.getProfileTags(profile.getId(), TagType.MUSIC),
                profileTagService.getProfileTags(profile.getId(), TagType.HOBBY),
                profile.getSelfIntroduction(),
                profile.getLikeableMusicTaste()

        );
    }

    @Transactional
    public ProfileIntroResponse updateProfileIntro(final Long memberId, final ProfileIntroRequest profileIntroRequest){
        Member member = memberRepository.findById(memberId).orElseThrow(MemberException.MemberNotFoundException::new);
        Profile profile = validateProfileIsNull(member.getProfile());

        profile.updateProfileIntro(
                profileIntroRequest.getMbti(),
                profileIntroRequest.getSelfIntroduction(),
                profileIntroRequest.getLikeableMusicTaste()
        );

        profileTagService.changeProfileTags(memberId, profileIntroRequest.getMusicTags(), profileIntroRequest.getHobbyTags());
        return ProfileIntroResponse.of(
                profile.getMbti(),
                profileTagService.getProfileTags(profile.getId(), TagType.MUSIC),
                profileTagService.getProfileTags(profile.getId(), TagType.HOBBY),
                profile.getSelfIntroduction(),
                profile.getLikeableMusicTaste()
        );
    }

    @Transactional
    public ProfileInfoResponse updateProfileInfo(final Long memberId, final ProfileInfoRequest profileInfoRequest){
        Member member = memberRepository.findById(memberId).orElseThrow(MemberException.MemberNotFoundException::new);
        Profile profile = validateProfileIsNull(member.getProfile());

        Image image = null;
        MultipartFile imageFile = profileInfoRequest.getProfileImage();
        if (imageFile != null && !imageFile.isEmpty()) {
            image = imageService.saveImage(imageFile);

            if (profile.getProfileImage() != null) {
                if (profileInfoRequest.getIsDeleteImage()) {
                    imageService.deleteImage(profile.getProfileImage().getId());
                }
            }
        }
        profile.updateProfileInfo(
                image,
                profileInfoRequest.getName(),
                profileInfoRequest.getOneLineIntroduction()
        );
        return ProfileInfoResponse.of(
                profile,
                imageService.getUrl(profile.getProfileImage())
        );
    }

    private Profile validateProfileIsNull(Profile profile) {
        if (profile == null) {
            throw new ProfileException.ProfileNotFoundException();
        }
        return profile;
    }


    public TagByTypeResponse getProfileTagsWithAllTagsByMemberId(final Long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow(MemberException.MemberNotFoundException::new);
        Profile profile = validateProfileIsNull(member.getProfile());
        return TagByTypeResponse.of(
                profileTagService.getProfileTagsWithAllTags(profile.getId(), TagType.MUSIC),
                profileTagService.getProfileTagsWithAllTags(profile.getId(), TagType.HOBBY));
    }
    public Profile createProfile(Long memberId){ //todo: 수정혹은 삭제예정. - 멤버 생성시 프로필카드 생성
        Member member = memberRepository.findById(memberId).orElseThrow(MemberException.MemberNotFoundException::new);
        Profile profile = Profile.builder()
                .member(member)
                .build();
        return profileRepository.save(profile);
    }


//    private Mood validateMoodIsNull(Mood mood) {
//        if (mood == null) {
//            moodService.createMood(
//        }
//        return mood;
//    }
}
