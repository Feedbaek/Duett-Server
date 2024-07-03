package Dino.Duett.test.controller;

import Dino.Duett.domain.member.entity.Member;
import Dino.Duett.domain.member.entity.Role;
import Dino.Duett.domain.member.repository.MemberRepository;
import Dino.Duett.domain.message.dto.request.MessageDeleteRequest;
import Dino.Duett.domain.message.dto.request.MessageSendRequest;
import Dino.Duett.domain.message.entity.Message;
import Dino.Duett.utils.TestUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestReporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("MessageController 테스트")
public class MessageControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TestUtil testUtil;

    @Test
    @DisplayName("모든 메시지 조회 테스트")
    public void getAllMessagesTest(TestReporter testReporter) throws Exception {
        // given
        // 메시지 생성
        Member sender = testUtil.createTestMember();
        Member receiver = testUtil.createTestMember();
        Message message = testUtil.createTestMessage(sender, receiver);
        // 토큰 생성
        String receiverToken = testUtil.createAccessToken(receiver.getId());

        // when
        // 모든 메시지 조회 요청
        testReporter.publishEntry(mockMvc.perform(get("/api/v1/message/all")
                .header("Authorization", "Bearer " + receiverToken))

        // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].senderId").value(sender.getId()))
                .andReturn().getResponse().getContentAsString());
    }

    @Test
    @DisplayName("메시지 전송 테스트")
    public void sendMessageTest(TestReporter testReporter) throws Exception {
        // given
        // 메시지 생성
        Member sender = testUtil.createTestMember();
        Member receiver = testUtil.createTestMember();
        MessageSendRequest messageSendRequest = MessageSendRequest.builder()
                .receiverId(receiver.getId())
                .content("test message")
                .build();
        // 토큰 생성
        String senderToken = testUtil.createAccessToken(sender.getId());

        // when
        // 메시지 전송 요청
        testReporter.publishEntry(mockMvc.perform(post("/api/v1/message/send")
                .header("Authorization", "Bearer " + senderToken)
                .contentType("application/json")
                .content(testUtil.toJson(messageSendRequest)))

        // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.senderId").value(sender.getId()))
                .andReturn().getResponse().getContentAsString());
    }

    @Test
    @DisplayName("메시지 삭제 테스트")
    public void deleteMessageTest(TestReporter testReporter) throws Exception {
        // given
        // 메시지 생성
        Member sender = testUtil.createTestMember();
        Member receiver = testUtil.createTestMember();
        Message message = testUtil.createTestMessage(sender, receiver);
        MessageDeleteRequest messageDeleteRequest = MessageDeleteRequest.builder()
                .messageIds(new Long[]{message.getId()})
                .build();
        // 토큰 생성
        String receiverToken = testUtil.createAccessToken(receiver.getId());

        // when
        // 메시지 삭제 요청
        testReporter.publishEntry(mockMvc.perform(delete("/api/v1/message/delete")
                .header("Authorization", "Bearer " + receiverToken)
                .contentType("application/json")
                .content(testUtil.toJson(messageDeleteRequest)))

        // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.messageIds[0]").value(message.getId()))
                .andReturn().getResponse().getContentAsString());
    }
}
