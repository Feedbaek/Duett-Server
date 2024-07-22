package Dino.Duett.domain.signup.service;

import Dino.Duett.config.login.jwt.JwtTokenProvider;
import Dino.Duett.config.login.jwt.JwtTokenType;
import Dino.Duett.domain.authentication.VerificationCodeManager;
import Dino.Duett.domain.member.dto.MemberDto;
import Dino.Duett.domain.member.entity.Member;
import Dino.Duett.domain.member.service.MemberService;
import Dino.Duett.domain.profile.service.ProfileService;
import Dino.Duett.domain.signup.dto.SignUpReq;
import Dino.Duett.domain.signup.dto.SignUpRes;
import Dino.Duett.global.dto.TokenDto;
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
public class SignUpService {
    private final VerificationCodeManager verificationCodeManager;
    private final GmailReader gmailReader;
    private final MemberService memberService;
    private final ProfileService profileService;
    private final JwtTokenProvider jwtTokenProvider;

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

        // token 발급
        String accessToken = jwtTokenProvider.createToken(member.getId(), JwtTokenType.ACCESS_TOKEN);
        String refreshToken = jwtTokenProvider.createToken(member.getId(), JwtTokenType.REFRESH_TOKEN);
        TokenDto tokens = TokenDto.of(accessToken, refreshToken);

        return SignUpRes.builder()
                .member(memberDto)
                .token(tokens)
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
