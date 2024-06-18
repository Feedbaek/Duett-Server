package Dino.Duett.domain.profile.controller;

import Dino.Duett.config.EnvBean;
import Dino.Duett.domain.member.entity.Member;
import Dino.Duett.domain.member.repository.MemberRepository;
import Dino.Duett.domain.profile.service.ProfileService;
import Dino.Duett.domain.tag.entity.Tag;
import Dino.Duett.domain.tag.repository.TagRepository;
import Dino.Duett.utils.TestUtil;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class ProfileControllerTest{
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EnvBean envBean;
    @Autowired
    private TestUtil testUtil;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private TagRepository tagRepository;


    @Test
    @Disabled("유저 소개 조회 테스트")
    public void getUserIntroduction() throws Exception {
        Member member = TestUtil.createMemberWithProfile();
        memberRepository.save(member);

        final String GET_API = "/api/v1/profiles/intro";

        MvcResult result = mockMvc.perform(get(GET_API)
                        .header("Authorization", "Bearer " + testUtil.createAccessToken(member.getId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.introduction").exists())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        System.out.println("Response Body: " + responseBody);
    }

    @Test
    @DisplayName("선택 가능한 모든 태그 조회 테스트")
    public void getAllTags() throws Exception {
        // given
        final String GET_API = "/api/v1/profiles/tags";

        List<Tag> tag = TestUtil.createTags();
        tagRepository.saveAll(tag);
        Member member = TestUtil.createMemberWithProfile();
        memberRepository.save(member);

        // when
        MvcResult result = mockMvc.perform(get(GET_API)
                        .header("Authorization", "Bearer " + testUtil.createAccessToken(member.getId())))
                .andExpect(status().isOk())
                .andReturn();

        // then
        String responseBody = result.getResponse().getContentAsString();
        System.out.println("Response Body: " + responseBody);
    }

    @Test
    @DisplayName("유저 소개 등록 수정 테스트")
    public void changeIntro() throws Exception {
        Member member = TestUtil.createMemberWithProfile();
        memberRepository.save(member);

        final String POST_API = "/api/v1/profiles/intro";

        MvcResult result = mockMvc.perform(post(POST_API)
                        .header("Authorization", "Bearer " + testUtil.createAccessToken(member.getId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.introduction").exists())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        System.out.println("Response Body: " + responseBody);
    }
}