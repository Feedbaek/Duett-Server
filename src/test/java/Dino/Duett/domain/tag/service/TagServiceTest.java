package Dino.Duett.domain.tag.service;

import Dino.Duett.domain.member.entity.Member;
import Dino.Duett.domain.member.repository.MemberRepository;
import Dino.Duett.domain.profile.entity.Profile;
import Dino.Duett.domain.tag.dto.request.TagRequest;
import Dino.Duett.domain.tag.dto.response.TagByTypeResponse;
import Dino.Duett.domain.tag.entity.ProfileTag;
import Dino.Duett.domain.tag.entity.Tag;
import Dino.Duett.domain.tag.enums.PriorityType;
import Dino.Duett.domain.tag.enums.TagType;
import Dino.Duett.domain.tag.exception.TagException;
import Dino.Duett.domain.tag.repository.ProfileTagRepository;
import Dino.Duett.domain.tag.repository.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static Dino.Duett.global.enums.LimitConstants.TAG_MAX_LIMIT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TagServiceTest {

    @InjectMocks
    private TagService tagService;

    @Mock
    private TagRepository tagRepository;

    @Mock
    private ProfileTagRepository profileTagRepository;

    @Mock
    private MemberRepository memberRepository;

    private Member member;
    private Profile profile;
    private Tag musicTag;
    private Tag hobbyTag;
    private ProfileTag profileMusicTag;
    private ProfileTag profileHobbyTag;

    @BeforeEach
    public void setUp() {

        musicTag = new Tag(1L, "팝", TagType.MUSIC.getType());
        hobbyTag = new Tag(2L, "영화", TagType.HOBBY.getType());


        profileMusicTag = ProfileTag.of(PriorityType.STANDARD, profile, musicTag);
        profileHobbyTag = ProfileTag.of(PriorityType.STANDARD, profile, hobbyTag);

        profile = Profile.builder()
                .profileTags(Arrays.asList(profileMusicTag, profileHobbyTag))
                .build();
        member = Member.builder()
                .id(1L)
                .phoneNumber("010-1234-5678")
                .kakaoId("kakao123")
                .coin(100)
                .profile(profile)
                .build();
    }

    @Test
    @DisplayName("테그 전체 조회 테스트")
    public void getAllTagsTest() {
        // given
        when(memberRepository.findById(any(Long.class))).thenReturn(Optional.of(member));

        List<Tag> musicTags = List.of(musicTag);
        List<Tag> hobbyTags = List.of(hobbyTag);

        when(tagRepository.findAllByType(TagType.MUSIC.getType())).thenReturn(musicTags);
        when(tagRepository.findAllByType(TagType.HOBBY.getType())).thenReturn(hobbyTags);

        // when
        TagByTypeResponse response = tagService.getAllTags(1L);


        // then
        assertEquals(1, response.getMusicTags().size());
        assertEquals(1, response.getHobbyTags().size());
    }

    @Test
    @DisplayName("프로필 태그 변경 테스트 - 체크")
    public void checkChangeProfileTags() {

        // given
        when(tagRepository.findByNameAndType("팝", TagType.MUSIC.getType())).thenReturn(Optional.of(musicTag));
        TagRequest newTagRequest = new TagRequest("팝", PriorityType.FEATURED.getKey());

        // when
        tagService.changeProfileTagsByTagType(profile, List.of(newTagRequest), TagType.MUSIC);

        // then
        List<ProfileTag> profileTags = profile.getProfileTags().stream()
                .filter(pt -> pt.getTag().getName().equals("팝") && pt.getTag().getType().equals(TagType.MUSIC.getType()))
                .toList();

        assertEquals(1, profileTags.size());
        assertEquals(PriorityType.FEATURED, profileTags.get(0).getPriority());
    }
    @Test
    @DisplayName("프로필 태그 변경 테스트 - 언체크(PriorityType.NONE이면 삭제)")
    public void uncheckChangeProfileTags() {
        // given
        when(tagRepository.findByNameAndType("일렉트로닉", TagType.MUSIC.getType())).thenReturn(Optional.of(musicTag));
        TagRequest newTagRequest = new TagRequest("일렉트로닉", PriorityType.NONE.getKey());

        // when
        tagService.changeProfileTagsByTagType(profile, List.of(newTagRequest), TagType.MUSIC);

        // then
        List<ProfileTag> profileTags = profile.getProfileTags().stream()
                .filter(pt -> pt.getTag().getName().equals("일렉트로닉") && pt.getTag().getType().equals(TagType.MUSIC.getType()))
                .toList();

        assertEquals(0, profileTags.size());
    }


    @Test
    @DisplayName("최대 태그 선택 갯수 초과 테스트")
    public void validateTagLimitTest_ThrowsTagMaxLimitException() {
        // given
        List<ProfileTag> profileTags = new ArrayList<>(profile.getProfileTags());

        for (int i = 0; i <= TAG_MAX_LIMIT.getLimit(); i++) {
            profileTags.add(ProfileTag.of(PriorityType.STANDARD, profile, musicTag));
        }

        Profile profile = Profile.builder()
                .profileTags(profileTags)
                .build();

        // when, then
        assertThrows(TagException.TagMaxLimitException.class, () -> tagService.validateTagLimit(profile, TagType.MUSIC));
    }
    @Test
    @DisplayName("태그 생성기 초기화 테스트")
    public void tagGeneratorInitTest() {
        // given
        when(tagRepository.existsByNameAndType(any(String.class), any(String.class))).thenReturn(false);

        // when
        TagGenerator tagGenerator = new TagGenerator(tagRepository);
        tagGenerator.init();

        // then
        verify(tagRepository, atLeastOnce()).save(any(Tag.class));
    }
}

