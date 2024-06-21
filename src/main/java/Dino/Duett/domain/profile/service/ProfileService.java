package Dino.Duett.domain.profile.service;

import Dino.Duett.domain.image.entity.Image;
import Dino.Duett.domain.image.service.ImageService;
import Dino.Duett.domain.member.entity.Member;
import Dino.Duett.domain.member.exception.MemberException;
import Dino.Duett.domain.member.repository.MemberRepository;
import Dino.Duett.domain.mood.dto.response.MoodResponse;
import Dino.Duett.domain.mood.entity.Mood;
import Dino.Duett.domain.mood.service.MoodService;
import Dino.Duett.domain.music.dto.response.MusicResponse;
import Dino.Duett.domain.music.service.MusicService;
import Dino.Duett.domain.profile.dto.request.ProfileInfoRequest;
import Dino.Duett.domain.profile.dto.request.ProfileIntroRequest;
import Dino.Duett.domain.profile.dto.request.ProfileMusicRequest;
import Dino.Duett.domain.profile.dto.response.ProfileInfoResponse;
import Dino.Duett.domain.profile.dto.response.ProfileIntroResponse;
import Dino.Duett.domain.profile.dto.response.ProfileMusicResponse;
import Dino.Duett.domain.profile.entity.Profile;
import Dino.Duett.domain.profile.exception.ProfileException;
import Dino.Duett.domain.profile.repository.ProfileRepository;
import Dino.Duett.domain.tag.dto.response.TagByTypeResponse;
import Dino.Duett.domain.tag.enums.TagType;
import Dino.Duett.domain.tag.service.ProfileTagService;
import Dino.Duett.global.util.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final MemberRepository memberRepository;
    private final MusicService musicService;
    private final MoodService moodService;
    private final ImageService imageService;
    private final ProfileTagService profileTagService;
    private final ProfileRepository profileRepository;
//    /**
//     * 마이페이지 프로필 조회
//     * @param memberId 사용자 id
//     * @return ProfileResponse
//     */
//    @Transactional
//    public ProfileResponse getProfile(final Long memberId) {
//        Member member = memberRepository.findById(memberId).orElseThrow(MemberException.MemberNotFoundException::new);
//        Profile profile = validateProfileIsNull(member.getProfile());
//        return ProfileResponse.builder()
//                .profileId(profile.getId())
//                .name(profile.getName())
//                .birthDate(profile.getBirthDate())
//                .mbti(profile.getMbti())
//                .oneLineIntroduction(profile.getOneLineIntroduction())
//                .profileImageUrl(imageService.getUrl(profile.getProfileImage()))
//                .musicTags(profileTagService.getProfileTags(profile.getId(), TagType.MUSIC))
//                .hobbyTags(profileTagService.getProfileTags(profile.getId(), TagType.HOBBY))
//                .lifeMusics(musicService.getMusics(profile.getId()))
//                .mood(MoodResponse.of(
//                        profile.getMood().getTitle(),
//                        profile.getMood().getArtist(),
//                        imageService.getUrl(profile.getMood().getMoodImage())))
//                .selfIntroduction(profile.getSelfIntroduction())
//                .likeableMusicTaste(profile.getLikeableMusicTaste())
//                .build();
//    }

    @Transactional
    public ProfileInfoResponse getProfileInfo(final Long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow(MemberException.MemberNotFoundException::new);
        Profile profile = validateProfileIsNull(member.getProfile());

        return ProfileInfoResponse.of(
                profile,
                profile.getProfileImage() != null ? imageService.getUrl(profile.getProfileImage()) : null
        );
    }

    @Transactional
    public void updateProfileInfo(final Long memberId, final ProfileInfoRequest profileInfoRequest) {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberException.MemberNotFoundException::new);
        Profile profile = validateProfileIsNull(member.getProfile());
        Image image = profile.getProfileImage();
        MultipartFile imageFile = profileInfoRequest.getProfileImage();

        if (!Validator.isNullOrEmpty(imageFile)) {
            if (image != null) {
                if(profileInfoRequest.getIsDeleteImage() == null || profileInfoRequest.getIsDeleteImage()) {
                    imageService.deleteImage(profile.getProfileImage().getId());
                }
            }
            image = imageService.saveImage(imageFile);
        }

        profile.updateProfileInfo(
                image,
                profileInfoRequest.getName(),
                profileInfoRequest.getOneLineIntroduction()
        );
    }

    @Transactional
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
    public void updateProfileIntro(final Long memberId, final ProfileIntroRequest profileIntroRequest){
        Member member = memberRepository.findById(memberId).orElseThrow(MemberException.MemberNotFoundException::new);
        Profile profile = validateProfileIsNull(member.getProfile());

        profile.updateProfileIntro(
                profileIntroRequest.getMbti(),
                profileIntroRequest.getSelfIntroduction(),
                profileIntroRequest.getLikeableMusicTaste()
        );

        profileTagService.changeProfileTags(memberId, profileIntroRequest.getMusicTags(), profileIntroRequest.getHobbyTags());
    }


    @Transactional
    public ProfileMusicResponse getProfileMusic(final Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberException.MemberNotFoundException::new);
        Profile profile = validateProfileIsNull(member.getProfile());
        Mood mood = profile.getMood();

        return ProfileMusicResponse.of(
                musicService.getMusics(profile),
                mood != null ? moodService.getMood(profile) : null
        );
    }

    @Transactional
    public ProfileMusicResponse updateProfileMusic(final Long memberId, final ProfileMusicRequest profileMusicRequest){
        Member member = memberRepository.findById(memberId).orElseThrow(MemberException.MemberNotFoundException::new);
        Profile profile = validateProfileIsNull(member.getProfile());

        musicService.changeMusics(
                profile,
                profileMusicRequest.getCreateLifeMusics(),
                profileMusicRequest.getUpdateLifeMusics(),
                profileMusicRequest.getDeleteLifeMusics());

        if(profileMusicRequest.getMood() != null) {
            moodService.changeMood(
                    profile,
                    profileMusicRequest.getMood());
        }

        return ProfileMusicResponse.of(
                MusicResponse.of(
                        profile.getMusics()),
                MoodResponse.of(
                        profile.getMood() != null ? profile.getMood().getTitle() : null,
                        profile.getMood() != null ? profile.getMood().getArtist() : null,
                        profile.getMood() != null ? imageService.getUrl(profile.getMood().getMoodImage()) : null
                )
        );
    }

    private Profile validateProfileIsNull(Profile profile) {
        if (profile == null) {
            throw new ProfileException.ProfileNotFoundException();
        }
        return profile;
    }

    @Transactional
    public TagByTypeResponse getProfileTagsWithAllTagsByMemberId(final Long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow(MemberException.MemberNotFoundException::new);
        Profile profile = validateProfileIsNull(member.getProfile());
        return TagByTypeResponse.of(
                profileTagService.getProfileTagsWithAllTags(profile.getId(), TagType.MUSIC),
                profileTagService.getProfileTagsWithAllTags(profile.getId(), TagType.HOBBY));
    }
}
