package Dino.Duett.domain.tag.service;

import Dino.Duett.domain.tag.entity.ProfileTag;
import Dino.Duett.domain.tag.enums.PriorityType;
import Dino.Duett.domain.tag.enums.TagType;
import Dino.Duett.domain.tag.repository.ProfileTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileTagService {
    private final ProfileTagRepository profileTagRepository;

    public String getProfileTagsByPriorityTagAndTagType(PriorityType priorityType, TagType tagType) {
        List<ProfileTag> profileTags = profileTagRepository.findByPriority(PriorityType.FEATURED);
        return profileTags.stream()
                .filter(profileTag -> profileTag.getTag().getType().equals(tagType.getType()))
                .map(profileTag -> profileTag.getTag().getName())
                .findFirst()
                .orElse(null);
    }
}
