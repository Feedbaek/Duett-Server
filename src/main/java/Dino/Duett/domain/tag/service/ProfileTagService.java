package Dino.Duett.domain.tag.service;

import Dino.Duett.domain.member.entity.Member;
import Dino.Duett.domain.member.exception.MemberException;
import Dino.Duett.domain.member.repository.MemberRepository;
import Dino.Duett.domain.profile.entity.Profile;
import Dino.Duett.domain.tag.dto.request.TagRequest;
import Dino.Duett.domain.tag.dto.response.TagResponse;
import Dino.Duett.domain.tag.entity.ProfileTag;
import Dino.Duett.domain.tag.entity.Tag;
import Dino.Duett.domain.tag.enums.TagState;
import Dino.Duett.domain.tag.enums.TagType;
import Dino.Duett.domain.tag.exception.TagException;
import Dino.Duett.domain.tag.repository.ProfileTagRepository;
import Dino.Duett.domain.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static Dino.Duett.global.enums.LimitConstants.TAG_MAX_LIMIT;

@Service
@RequiredArgsConstructor
public class ProfileTagService {
    private final ProfileTagRepository profileTagRepository;
    private final MemberRepository memberRepository;
    private final TagRepository tagRepository;

    public List<TagResponse> getProfileTags(final Long profileId,final TagType tagType) {
        return profileTagRepository.findAllByProfileIdAndTagType(profileId, tagType).stream()
                .map(tag -> TagResponse.of(
                        tag.getName(),
                        tag.getState()))
                .toList();
    }

    public List<TagResponse> getProfileTagsWithAllTags(final Long profileId, final TagType tagType) {
        return profileTagRepository.findAllByProfileIdAndTagTypeWithAllTag(profileId, tagType).stream()
                .map(tag -> TagResponse.of(
                        tag.getName(),
                        tag.getState()))
                .toList();
    }

    public Long countProfileTags(final Long profileId, final TagType tagType) {
        return profileTagRepository.countByProfileIdAndTagType(profileId, tagType);
    }

    public List<TagResponse> getProfileTagsOnlyFeatured(final Long profileId) {
        return profileTagRepository.findAllByProfileIdAndTagState(profileId, TagState.FEATURED).stream()
                .map(tag -> TagResponse.of(
                        tag.getName(),
                        tag.getState()))
                .toList();
    }

    public void validateProfileTagLimit(final Profile profile, final TagType tagType) {
        if (profileTagRepository.countByProfileIdAndTagType(profile.getId(), tagType) > TAG_MAX_LIMIT.getLimit()) {
            throw new TagException.TagMaxLimitException();
        }
    }

    @Transactional
    public void changeProfileTags(final Long memberId, final List<TagRequest> musicTags, final List<TagRequest> hobbyTags) {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberException.MemberNotFoundException::new);
        Profile profile = member.getProfile();
        changeProfileTagsByTagType(profile, musicTags, TagType.MUSIC);
        changeProfileTagsByTagType(profile, hobbyTags, TagType.HOBBY);
    }

    @Transactional
    public void changeProfileTagsByTagType(final Profile profile, final List<TagRequest> tagRequests, final TagType tagType) {
        List<ProfileTag> profileTags = tagRequests.stream()
                .map(tagRequest -> processTagRequest(profile, tagRequest, tagType))
                .toList();

        deleteProfileTagOfNone(profile, profileTags);
        validateProfileTagLimit(profile, tagType);
    }

    private ProfileTag processTagRequest(final Profile profile, final TagRequest tagRequest, final TagType tagType) {
        Tag tag = tagRepository.findByNameAndType(tagRequest.getName(), tagType)
                .orElseThrow(TagException.TagNotFoundException::new);

        Optional<ProfileTag> existingProfileTag = profile.getProfileTags().stream()
                .filter(profileTag -> profileTag.getTag().getId().equals(tag.getId()))
                .findFirst();
        if (existingProfileTag.isPresent()) {
            updateTagState(existingProfileTag.get(), tagRequest);
            return existingProfileTag.get();
        } else {
            return createProfileTag(profile, tagRequest, tag);
        }
    }

    private void updateTagState(final ProfileTag existingProfileTag, final TagRequest tagRequest) {
        if (existingProfileTag.getState() == tagRequest.getState()) {
            return;
        }
        existingProfileTag.updateState(tagRequest.getState());
    }

    private ProfileTag createProfileTag(final Profile profile, final TagRequest tagRequest, final Tag tag) {
        ProfileTag newProfileTag = ProfileTag
                .of(
                    tagRequest.getState(),
                    profile,
                    tag);
        return profileTagRepository.save(newProfileTag);
    }

    private void deleteProfileTagOfNone(final Profile profile, final List<ProfileTag> profileTags) {
        profileTags.forEach(profileTag -> {
            if (profileTag.getState().equals(TagState.NONE)) {
                profileTag.removeProfile(profile);
                profileTagRepository.delete(profileTag);
            }
        });
    }
}
