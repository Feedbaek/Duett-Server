package Dino.Duett.domain.signup.service;

import Dino.Duett.domain.authentication.VerificationCodeManager;
import Dino.Duett.domain.member.dto.MemberDto;
import Dino.Duett.domain.member.entity.Member;
import Dino.Duett.domain.member.service.MemberService;
import Dino.Duett.domain.profile.service.ProfileService;
import Dino.Duett.domain.signup.dto.request.SignUpReq;
import Dino.Duett.domain.signup.dto.request.WithdrawalReq;
import Dino.Duett.domain.signup.dto.response.SignUpRes;
import Dino.Duett.global.exception.CustomException;
import Dino.Duett.gmail.GmailReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SignUpService {
    private final VerificationCodeManager verificationCodeManager;
    private final GmailReader gmailReader;
    private final MemberService memberService;
    private final ProfileService profileService;
    private final StringRedisTemplate redisTemplate;

    // 회원가입
    public SignUpRes signUp(SignUpReq signUpReq) throws CustomException {
        // redis, gmail 인증 코드 확인
        verificationCodeManager.verifyCode(signUpReq.getPhoneNumber(), signUpReq.getVerificationCode());
        gmailReader.validate(signUpReq.getPhoneNumber(), signUpReq.getVerificationCode());
        // 회원가입 처리
        Member member = memberService.createMember(signUpReq.getPhoneNumber(), signUpReq.getKakaoId());

        MemberDto memberDto = memberService.makeMemberDto(member);
        // todo: 프로필 생성
        profileService.createProfile(signUpReq);

        // redis 인증 코드 삭제. 회원가입 처리 성공 시 인증 코드 삭제
        verificationCodeManager.deleteCode(signUpReq.getPhoneNumber());
        // todo: gmail 인증 코드 삭제
        // gmailReader.deleteCode(signUpReq.getPhoneNumber());

        return SignUpRes.builder()
                .member(memberDto)
                .build();
    }


    // 회원가입 DB Mock
    public SignUpRes signUpMock(SignUpReq signUpReq) throws CustomException {
        Member member = memberService.createMember(signUpReq.getPhoneNumber(), signUpReq.getKakaoId());
        MemberDto memberDto = memberService.makeMemberDto(member);
        profileService.createProfile(signUpReq);
        return SignUpRes.builder()
                .member(memberDto)
                .build();
    }
}
