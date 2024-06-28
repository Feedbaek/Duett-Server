package Dino.Duett.domain.profile.controller;

import Dino.Duett.config.EnvBean;
import Dino.Duett.domain.member.entity.Member;
import Dino.Duett.domain.member.repository.MemberRepository;
import Dino.Duett.domain.profile.dto.request.ProfileInfoRequest;
import Dino.Duett.domain.profile.dto.request.ProfileIntroRequest;
import Dino.Duett.domain.profile.enums.MbtiType;
import Dino.Duett.domain.profile.service.ProfileService;
import Dino.Duett.domain.tag.repository.TagRepository;
import Dino.Duett.utils.TestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@Disabled
public class ProfileControllerTest {

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

    @MockBean
    private ProfileService profileService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("유저 정보 조회")
    public void getProfileInfo() throws Exception {
        // given
        Member member = TestUtil.createMemberWithProfile();
        memberRepository.save(member);

        final String GET_API = "/api/v1/profiles/info";


        // when
        MvcResult result = mockMvc.perform(get(GET_API)
                        .header("Authorization", "Bearer " + testUtil.createAccessToken(member.getId())))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        System.out.println("Response Body: " + responseBody);
    }
    @Test
    @DisplayName("유저 정보 등록")
    public void updateProfileInfo() throws Exception {
        // given
        Member member = TestUtil.createMemberWithProfile();
        memberRepository.save(member);

        final String POST_API = "/api/v1/profiles/info";
        ProfileInfoRequest profileInfoRequest = new ProfileInfoRequest(
                null,
                "name",
                "oneLineIntroduce",
                false
        );

        Mockito.doNothing().when(profileService).updateProfileInfo(Mockito.eq(member.getId()), Mockito.any(ProfileInfoRequest.class));

        // when
        MvcResult result = mockMvc.perform(post(POST_API)
                        .header("Authorization", "Bearer " + testUtil.createAccessToken(member.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(profileInfoRequest)))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        System.out.println("Response Body: " + responseBody);
    }




    @Test
    @DisplayName("유저 소개 조회")
    public void getProfileIntro() throws Exception {
        // given
        Member member = TestUtil.createMemberWithProfile();
        memberRepository.save(member);

        final String GET_API = "/api/v1/profiles/intro";


        // when
        MvcResult result = mockMvc.perform(get(GET_API)
                        .header("Authorization", "Bearer " + testUtil.createAccessToken(member.getId())))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        System.out.println("Response Body: " + responseBody);
    }

    @Test
    @DisplayName("유저 소개 등록")
    public void updateProfileIntro() throws Exception {
        // given
        Member member = TestUtil.createMemberWithProfile();
        memberRepository.save(member);

        final String POST_API = "/api/v1/profiles/intro";
        ProfileIntroRequest profileIntroRequest = new ProfileIntroRequest(
                MbtiType.ENFJ,
                null,
                null,
                "introduce",
                "likeableMusicTaste"
        );

        Mockito.doNothing().when(profileService).updateProfileIntro(Mockito.eq(member.getId()), Mockito.any(ProfileIntroRequest.class));

        // when
        MvcResult result = mockMvc.perform(post(POST_API)
                        .header("Authorization", "Bearer " + testUtil.createAccessToken(member.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(profileIntroRequest)))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        System.out.println("Response Body: " + responseBody);
    }
}


//    @Test
//    @DisplayName("Update User Profile Info Test")
//    public void updateProfileInfo_shouldUpdateProfileInfo() throws Exception {
//        // given
//        Member member = TestUtil.createMemberWithProfile();
//        memberRepository.save(member);
//        final String POST_API = "/api/v1/profiles/info";
//
//        // when
//        MvcResult result = mockMvc.perform(post(POST_API)
//                        .header("Authorization", "Bearer " + testUtil.createAccessToken(member.getId()))
//                        .contentType(MediaType.MULTIPART_FORM_DATA))
//                        .andExpect(status().isOk())
//                        .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
//                        .andExpect(jsonPath("$.message").value("내 정보 수정 성공"))
//                        .andReturn();
//
//        String responseBody = result.getResponse().getContentAsString();
//        System.out.println("Response Body: " + responseBody);
//    }
//
//    @Test
//    @DisplayName("Get User Introduction Test")
//    public void getUserIntroduction() throws Exception {
//        // given
//        Member member = TestUtil.createMemberWithProfile();
//        memberRepository.save(member);
//        final String GET_API = "/api/v1/profiles/intro";
//
//        // when
//        MvcResult result = mockMvc.perform(get(GET_API)
//                .header("Authorization", "Bearer " + testUtil.createAccessToken(member.getId())))
//
//    }