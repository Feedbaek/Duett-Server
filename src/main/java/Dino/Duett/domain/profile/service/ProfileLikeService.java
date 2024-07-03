package Dino.Duett.domain.profile.service;

import Dino.Duett.domain.member.entity.Member;
import Dino.Duett.domain.member.repository.MemberRepository;
import Dino.Duett.domain.music.dto.response.MusicResponse;
import Dino.Duett.domain.profile.dto.response.LikedProfileResponse;
import Dino.Duett.domain.profile.dto.response.ProfileCardBriefResponse;
import Dino.Duett.domain.profile.dto.response.ProfileCardSummaryResponse;
import Dino.Duett.domain.profile.dto.response.ProfileInfoResponse;
import Dino.Duett.domain.profile.entity.Profile;
import Dino.Duett.domain.profile.entity.ProfileLike;
import Dino.Duett.domain.profile.exception.ProfileException;
import Dino.Duett.domain.profile.repository.ProfileLikeRepository;
import Dino.Duett.domain.profile.repository.ProfileRepository;
import Dino.Duett.domain.tag.service.ProfileTagService;
import Dino.Duett.global.enums.LimitConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ProfileLikeService {
    private final MemberRepository memberRepository;
    private final ProfileRepository profileRepository;
    private final ProfileLikeRepository profileLikeRepository;
    private final ProfileCardService profileCardService;
    private final ProfileTagService profileTagService;

    @Transactional
    public boolean likeOrUnlikeProfile(Long memberId, Long profileId) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        Profile profile = profileRepository.findById(profileId).
                orElseThrow(ProfileException.ProfileNotFoundException::new);

        if (profile.getId().equals(member.getProfile().getId())) {
            throw new ProfileException.ProfileSelfLikeException();
        }

        ProfileLike profileLike = profileLikeRepository.findByMemberAndLikedProfile(member, profile);

        if (profileLike != null) {
            profileLikeRepository.delete(profileLike);
            return false;
        } else {
            profileLikeRepository.save(ProfileLike.builder()
                    .member(member)
                    .likedProfile(profile)
                    .build());
            return true;
        }
    }

    public List<ProfileCardBriefResponse> getLikedProfiles(Long memberId, Integer page) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        List<ProfileLike> profileLikes = profileLikeRepository.findByMember(member);
        return profileLikes.stream()
                .map(profileLike -> profileCardService.convertToBriefDto(profileLike.getLikedProfile()))
                .collect(Collectors.toList());
    }

    public List<ProfileCardBriefResponse> getMembersWhoLikedProfile(Long memberId, Integer page) {
        Pageable pageable = PageRequest.of(page, LimitConstants.PROFILE_MAX_LIMIT.getLimit(), Sort.by(Sort.Direction.ASC, "createdDate"));
        Member member = memberRepository.findById(memberId).orElseThrow();
        List<ProfileLike> profileLikes = profileLikeRepository.findByLikedProfile(member.getProfile(), pageable);
        return profileLikes.stream()
                .map(profileLike -> profileCardService.convertToBriefDto(profileLike.getMember().getProfile()))
                .collect(Collectors.toList());
    }
}
