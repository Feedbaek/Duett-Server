package Dino.Duett.domain.signup.service;

import Dino.Duett.domain.member.entity.Member;
import Dino.Duett.domain.member.service.MemberService;
import Dino.Duett.domain.signup.dto.request.WithdrawalReq;
import Dino.Duett.global.exception.CustomException;
import Dino.Duett.gmail.GmailReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class WithdrawalService {
    private final GmailReader gmailReader;
    private final MemberService memberService;

    public boolean withdrawal(WithdrawalReq withdrawalReq) throws CustomException {
        // 탈퇴 이유 메일 전송
        gmailReader.sendWithdrawalEmail(withdrawalReq.getPhoneNumber(), withdrawalReq.getReason());
        // 회원탈퇴 처리
        memberService.deleteMember(withdrawalReq.getPhoneNumber());
        return true;
    }
}
