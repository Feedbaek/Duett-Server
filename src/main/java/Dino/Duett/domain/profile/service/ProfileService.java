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
import Dino.Duett.domain.profile.dto.response.ProfileHomeResponse;
import Dino.Duett.domain.profile.dto.response.ProfileInfoResponse;
import Dino.Duett.domain.profile.dto.response.ProfileIntroResponse;
import Dino.Duett.domain.profile.dto.response.ProfileMusicResponse;
import Dino.Duett.domain.profile.entity.Location;
import Dino.Duett.domain.profile.entity.Profile;
import Dino.Duett.domain.profile.enums.GenderType;
import Dino.Duett.domain.profile.exception.ProfileException;
import Dino.Duett.domain.profile.repository.ProfileRepository;
import Dino.Duett.domain.signup.dto.SignUpReq;
import Dino.Duett.domain.tag.dto.response.TagByTypeResponse;
import Dino.Duett.domain.tag.enums.TagType;
import Dino.Duett.domain.tag.service.ProfileTagService;
import Dino.Duett.global.utils.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static Dino.Duett.global.enums.LimitConstants.MUSIC_MAX_LIMIT;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProfileService {

    private final MemberRepository memberRepository;
    private final ProfileRepository profileRepository;
    private final MusicService musicService;
    private final MoodService moodService;
    private final ImageService imageService;
    private final ProfileTagService profileTagService;

    @Transactional
    public void createProfile(final SignUpReq signUpReq) { // todo: 프로필 생성 임시. 수정 바람
        Member member = memberRepository.findByPhoneNumber(signUpReq.getPhoneNumber()).orElseThrow(MemberException.MemberNotFoundException::new);

        // Location 생성
        Location location = Location.of(signUpReq.getLocation()[0], signUpReq.getLocation()[1]);

        // 프로필 이미지 생성
        Image profileImage = imageService.saveImage(signUpReq.getProfileImage());

        // 프로필 생성
        Profile profile = Profile.builder()
                .name(signUpReq.getName())
                .birthDate(signUpReq.getBirthDate())
                .oneLineIntroduction(signUpReq.getOneLineIntroduction())
                .gender(GenderType.valueOf(signUpReq.getGender()))
                .location(location)
                .profileImage(profileImage)
                .build();

        profileRepository.save(profile);
        member.updateProfile(profile);
    }

    public ProfileHomeResponse getProfileHome(final Long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow(MemberException.MemberNotFoundException::new);
        Profile profile = validateProfileIsNull(member.getProfile());

        return ProfileHomeResponse.builder()
                .profileImageUrl(profile.getProfileImage() != null ? imageService.getUrl(profile.getProfileImage()) : null)
                .name(profile.getName())
                .birthDate(profile.getBirthDate())
                .mbti(profile.getMbti())
                .infoCount(getProfileInfoCount(profile))
                .introCount(getProfileIntroCount(profile))
                .musicCount(getProfileMusicCount(profile))
                .build();
    }

    private int getProfileInfoCount(final Profile profile){
        int count = 0;
        if(profile.getName() != null){
            count++;
        }
        if(profile.getOneLineIntroduction() != null){
            count++;
        }
        return count;
    }

    private int getProfileIntroCount(final Profile profile){
        int count = 0;
        if(profile.getMbti() != null){
            count++;
        }
        if(profile.getProfileTags() != null){
            if(profileTagService.checkFeaturedProfileTagsCount(profile)){
                count++;
            }
        }
        if(profile.getSelfIntroduction() != null){
            count++;
        }
        if(profile.getLikeableMusicTaste() != null){
            count++;
        }
        return count;
    }

    private int getProfileMusicCount(final Profile profile){
        int count = 0;
        if(profile.getMusics() != null){
            if(profile.getMusics().size() == MUSIC_MAX_LIMIT.getLimit())
                count++;
        }
        Mood mood = profile.getMood();
        if(mood != null){
            count++;
        }
        return count;
    }

    public ProfileInfoResponse getProfileInfo(final Long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow(MemberException.MemberNotFoundException::new);
        Profile profile = validateProfileIsNull(member.getProfile());

        return ProfileInfoResponse.builder()
                .profileImageUrl(profile.getProfileImage() != null ? imageService.getUrl(profile.getProfileImage()) : null)
                .name(profile.getName())
                .birthDate(profile.getBirthDate())
                .gender(profile.getGender().getValue())
                .oneLineIntroduction(profile.getOneLineIntroduction())
                .build();
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

    public ProfileIntroResponse getProfileIntro(final Long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow(MemberException.MemberNotFoundException::new);
        Profile profile = validateProfileIsNull(member.getProfile());

        return ProfileIntroResponse.builder()
                .mbti(profile.getMbti())
                .musicTags(profileTagService.getProfileTags(profile.getId(), TagType.MUSIC))
                .hobbyTags(profileTagService.getProfileTags(profile.getId(), TagType.HOBBY))
                .selfIntroduction(profile.getSelfIntroduction())
                .likeableMusicTaste(profile.getLikeableMusicTaste())
                .build();
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
    public void updateProfileMusic(final Long memberId, final ProfileMusicRequest profileMusicRequest){
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

        ProfileMusicResponse.of(
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

    public TagByTypeResponse getProfileTagsWithAllTagsByMemberId(final Long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow(MemberException.MemberNotFoundException::new);
        Profile profile = validateProfileIsNull(member.getProfile());
        return TagByTypeResponse.of(
                profileTagService.getProfileTagsWithAllTags(profile.getId(), TagType.MUSIC),
                profileTagService.getProfileTagsWithAllTags(profile.getId(), TagType.HOBBY));
    }
}
