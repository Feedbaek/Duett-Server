package Dino.Duett.domain.tag.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TagTypeTest{
    @Test
    @DisplayName("PriorityType에 따른 우선순위 찾기 테스트")
    public void findByPriorityType(){
        assertEquals(PriorityType.NONE, PriorityType.findByPriorityType(0));
        assertEquals(PriorityType.STANDARD, PriorityType.findByPriorityType(1));
        assertEquals(PriorityType.FEATURED, PriorityType.findByPriorityType(2));
    }

    @Test
    @DisplayName("TagType에 따른 태그 찾기 테스트")
    void findByTagType() {
        // given
        TagType musicTagType = TagType.MUSIC;

        // when
        List<String> musicNames = musicTagType.getNames();
        String foundMusicTag = TagType.findByMusicTagType("팝");
        String foundHobbyTag = TagType.findByHobbyTagType("영화");

        // then
        assertTrue(musicNames.contains("팝"));
        assertFalse(musicNames.contains("영화"));
        assertEquals("팝", foundMusicTag);
        assertEquals("영화", foundHobbyTag);
    }
}