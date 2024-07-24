package Dino.Duett.domain.profile.service;

import Dino.Duett.domain.member.entity.Member;
import Dino.Duett.domain.member.exception.MemberException;
import Dino.Duett.domain.member.repository.MemberRepository;
import Dino.Duett.domain.profile.dto.request.LocationRequest;
import Dino.Duett.domain.profile.entity.Location;
import Dino.Duett.domain.profile.entity.Profile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LocationService {
    private final MemberRepository memberRepository;

    @Transactional
    public void updateLocation(final Long memberId, final LocationRequest locationRequest) {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberException.MemberNotFoundException::new);
        Profile profile = member.getProfile();
        profile.updateLocation(
                Location.of(
                locationRequest.getLocation().get(0),
                locationRequest.getLocation().get(1))
        );
    }
}
