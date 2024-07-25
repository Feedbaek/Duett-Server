package Dino.Duett.domain.music.service;

import Dino.Duett.domain.member.entity.Member;
import Dino.Duett.domain.member.exception.MemberException;
import Dino.Duett.domain.member.repository.MemberRepository;
import Dino.Duett.domain.music.dto.request.MusicCreateRequest;
import Dino.Duett.domain.music.dto.request.MusicDeleteRequest;
import Dino.Duett.domain.music.dto.request.MusicUpdateRequest;
import Dino.Duett.domain.music.dto.response.MusicResponse;
import Dino.Duett.domain.music.entity.Music;
import Dino.Duett.domain.music.exception.MusicException;
import Dino.Duett.domain.music.repository.MusicRepository;
import Dino.Duett.domain.profile.entity.Profile;
import Dino.Duett.global.utils.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static Dino.Duett.global.enums.LimitConstants.MUSIC_MAX_LIMIT;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MusicService {
    private final MusicRepository musicRepository;
    private final MemberRepository memberRepository;

    public List<MusicResponse> getMusics(final Profile profile) {
        return MusicResponse.of(profile.getMusics());
    }

    @Transactional
    public void changeMusics(final Profile profile,
                             final List<MusicCreateRequest> createMusics,
                             final List<MusicUpdateRequest> updateMusics,
                             final List<MusicDeleteRequest> deleteMusics) {
        if(!Validator.isNullOrEmpty(createMusics)) {
            createMusics(profile, createMusics);
        }
        if(!Validator.isNullOrEmpty(updateMusics)) {
            updateMusics(profile, updateMusics);
        }
        if(!Validator.isNullOrEmpty(deleteMusics)){
            deleteMusics(profile, deleteMusics);
        }
    }

    @Transactional
    public Profile changeMusics(final Long memberId,
                             final List<MusicCreateRequest> createMusics,
                             final List<MusicUpdateRequest> updateMusics,
                             final List<MusicDeleteRequest> deleteMusics) {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberException.MemberNotFoundException::new);
        Profile profile = member.getProfile();

        if(!Validator.isNullOrEmpty(createMusics)) {
            createMusics(profile, createMusics);
        }
        if(!Validator.isNullOrEmpty(updateMusics)) {
            updateMusics(profile, updateMusics);
        }
        if(!Validator.isNullOrEmpty(deleteMusics)){
            deleteMusics(profile, deleteMusics);
        }

        if(!Validator.isNullOrEmpty(profile.getMusics())) {
            if (profile.getMusics().size() > MUSIC_MAX_LIMIT.getLimit()) {
                throw new MusicException.MusicMaxLimitException();
            }
        }
        return profile;
    }

    private void createMusics(final Profile profile, final List<MusicCreateRequest> requests) {
        List<Music> musics = requests.stream()
                .map(request -> Music.of(
                        request.getTitle(),
                        request.getArtist(),
                        request.getUrl(),
                        profile))
                .toList();
        musicRepository.saveAll(musics);
        musics.forEach(profile::addMusic);
    }

    private void updateMusics(final Profile profile, final List<MusicUpdateRequest> requests) {
        for(MusicUpdateRequest request : requests) {
            Music music = musicRepository.findById(request.getMusicId()).orElseThrow(MusicException.MusicNotFoundException::new);
            validateMusicForbidden(music, profile);
            music.updateMusic(request);
            musicRepository.save(music);
        }
    }

    private void validateMusicForbidden(final Music music, final Profile profile){
        if (!music.getProfile().getId().equals(profile.getId())) {
            throw new MusicException.MusicForbiddenException(Map.of("MusicId", String.valueOf(music.getId())));
        }
    }

    private void deleteMusics(final Profile profile, final List<MusicDeleteRequest> requests) {
        for(MusicDeleteRequest request : requests) {
            Music music = musicRepository.findById(request.getMusicId()).orElseThrow(MusicException.MusicNotFoundException::new);
            validateMusicForbidden(music, profile);
            profile.removeMusic(music);
            musicRepository.delete(music);
        }
    }
}