package Dino.Duett.domain.music.service;

import Dino.Duett.domain.member.entity.Member;
import Dino.Duett.domain.member.exception.MemberException;
import Dino.Duett.domain.member.repository.MemberRepository;
import Dino.Duett.domain.music.dto.request.MusicCreateRequest;
import Dino.Duett.domain.music.dto.response.MusicResponse;
import Dino.Duett.domain.music.dto.request.MusicUpdateRequest;
import Dino.Duett.domain.music.entity.Music;
import Dino.Duett.domain.music.exception.MusicException;
import Dino.Duett.domain.music.repository.MusicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MusicService {
    private final MusicRepository musicRepository;
    private final MemberRepository memberRepository;

    public MusicResponse getMusic(final Long musicId) {
        Music music = musicRepository.findById(musicId).orElseThrow(MusicException.MusicNotFoundException::new);
        return MusicResponse.of(music);
    }

    public List<MusicResponse> getMusics(final Long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow(MemberException.MemberNotFoundException::new);

        return member.getProfile().getMusics().stream()
                .map(MusicResponse::of)
                .toList();
    }

    public MusicResponse createMusic(final Long memberId, final MusicCreateRequest musicCreateRequest) {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberException.MemberNotFoundException::new);
        Music music = Music.of(
                musicCreateRequest.getTitle(),
                musicCreateRequest.getArtist(),
                musicCreateRequest.getUrl());

        musicRepository.save(music);
        member.getProfile().getMusics().add(music);

        return MusicResponse.of(music);
    }

    public List<MusicResponse> updateMusics(final Long memberId, final List<MusicUpdateRequest> musicUpdateRequests) {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberException.MemberNotFoundException::new);

        for(MusicUpdateRequest musicUpdateRequest : musicUpdateRequests) {
            Music music = musicRepository.findById(musicUpdateRequest.getMusicId()).orElseThrow(MusicException.MusicNotFoundException::new);
            music.updateMusic(musicUpdateRequest);
        }

        return member.getProfile().getMusics().stream()
                .map(MusicResponse::of)
                .toList();
    }

    public void deleteMusic(final Long memberId, final Long musicId) { //todo: profile에서 delete확인
        Member member = memberRepository.findById(memberId).orElseThrow(MemberException.MemberNotFoundException::new);
        Music music = musicRepository.findById(musicId).orElseThrow(MusicException.MusicNotFoundException::new);
        member.getProfile().getMusics().remove(music);
        musicRepository.deleteById(musicId);
    }
}