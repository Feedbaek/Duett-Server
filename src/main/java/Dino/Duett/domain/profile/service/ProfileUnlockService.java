package Dino.Duett.domain.profile.service;

import Dino.Duett.domain.member.entity.Member;
import Dino.Duett.domain.member.exception.MemberException;
import Dino.Duett.domain.member.repository.MemberRepository;
import Dino.Duett.domain.music.dto.response.MusicResponse;
import Dino.Duett.domain.profile.dto.response.ProfileCardBriefResponse;
import Dino.Duett.domain.profile.entity.Profile;
import Dino.Duett.domain.profile.entity.ProfileUnlock;
import Dino.Duett.domain.profile.exception.ProfileException;
import Dino.Duett.domain.profile.repository.ProfileRepository;
import Dino.Duett.domain.profile.repository.ProfileUnlockRepository;
import Dino.Duett.domain.tag.service.ProfileTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProfileUnlockService {
    private final ProfileUnlockRepository profileUnlockRepository;
    private final ProfileRepository profileRepository;
    private final MemberRepository memberRepository;
    private final ProfileTagService profileTagService;

    @Transactional
    public void createProfileUnlock(final Long viewerProfileId, final Long viewedProfileId) {
        Profile viewerProfile = profileRepository.findById(viewerProfileId)
                .orElseThrow(() -> new ProfileException.ProfileNotFoundException(Map.of("viewerProfileId", viewerProfileId.toString())));
        Profile viewedProfile = profileRepository.findById(viewedProfileId)
                .orElseThrow(() -> new ProfileException.ProfileNotFoundException(Map.of("viewedProfileId", viewedProfileId.toString())));

        ProfileUnlock profileUnlock = ProfileUnlock.of(
                viewerProfile,
                viewedProfile);

        viewerProfile.getProfileUnlocks().add(profileUnlock);
        profileUnlockRepository.save(profileUnlock);
    }


    public List<ProfileCardBriefResponse> getUnlockedProfiles(final Long memberId, final Pageable pageable) {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberException.MemberNotFoundException::new);
        Profile profile = validateProfileIsNull(member.getProfile());

        Page<ProfileUnlock> profileUnlocks = profileUnlockRepository.findAllByViewerProfile(profile, pageable);
        return profileUnlocks.map(profileUnlock -> ProfileCardBriefResponse.builder()
                .profileId(profileUnlock.getViewedProfile().getId())
                .name(profileUnlock.getViewedProfile().getName())
                .birthDate(profileUnlock.getViewedProfile().getBirthDate())
                .mbti(profileUnlock.getViewedProfile().getMbti())
                .lifeMusic(profileUnlock.getViewedProfile().getMusics() !=null && !profileUnlock.getViewedProfile().getMusics().isEmpty() ?
                                MusicResponse.of(
                                        profileUnlock.getViewedProfile().getMusics().get(0).getId(),
                                        profileUnlock.getViewedProfile().getMusics().get(0).getTitle(),
                                        profileUnlock.getViewedProfile().getMusics().get(0).getArtist()
                                ) : null)
                .tags(profileTagService.getProfileTagsOnlyFeatured(profileUnlock.getId()))
                .build()).stream()
                .toList();
    }
    private Profile validateProfileIsNull(final Profile profile) {
        if (profile == null) {
            throw new ProfileException.ProfileNotFoundException();
        }
        return profile;
    }
}