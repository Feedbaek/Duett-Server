package Dino.Duett.domain.signup.service;

import Dino.Duett.domain.image.service.ImageService;
import Dino.Duett.domain.member.entity.Member;
import Dino.Duett.domain.member.exception.MemberException;
import Dino.Duett.domain.member.repository.MemberRepository;
import Dino.Duett.domain.member.service.MemberService;
import Dino.Duett.domain.mood.service.MoodService;
import Dino.Duett.domain.profile.service.ProfileService;
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
    private final MemberRepository memberRepository;
    private final ProfileService profileService;
    private final MoodService moodService;
    private final ImageService imageService;

    public boolean withdrawal(String phoneNumber, WithdrawalReq withdrawalReq) throws CustomException {
        gmailReader.sendWithdrawalEmail(phoneNumber, withdrawalReq.getReason());
        Member member = memberRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(MemberException.MemberNotFoundException::new);

        if (member.getProfile() != null && member.getProfile().getMood() != null && member.getProfile().getMood().getMoodImage() != null) {
            imageService.deleteImage(member.getProfile().getMood().getMoodImage().getId());
        }
        memberService.deleteMember(phoneNumber);
        return true;
    }
}
