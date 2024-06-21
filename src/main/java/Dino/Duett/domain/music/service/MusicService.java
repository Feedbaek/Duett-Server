package Dino.Duett.domain.music.service;

import Dino.Duett.domain.music.dto.request.MusicCreateRequest;
import Dino.Duett.domain.music.dto.request.MusicDeleteRequest;
import Dino.Duett.domain.music.dto.request.MusicUpdateRequest;
import Dino.Duett.domain.music.dto.response.MusicResponse;
import Dino.Duett.domain.music.entity.Music;
import Dino.Duett.domain.music.exception.MusicException;
import Dino.Duett.domain.music.repository.MusicRepository;
import Dino.Duett.domain.profile.entity.Profile;
import Dino.Duett.global.util.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MusicService {
    private final MusicRepository musicRepository;

    public List<MusicResponse> getMusics(final Profile profile) {
        return MusicResponse.of(profile.getMusics());
    }

    public void changeMusics(final Profile profile,
                             final List<MusicCreateRequest> createMusics,
                             final List<MusicUpdateRequest> updateMusics,
                             final List<MusicDeleteRequest> deleteMusics) {
        if(!Validator.isNullOrEmpty(createMusics)) {
            createMusics(profile, createMusics);
        }
        if(!Validator.isNullOrEmpty(updateMusics)) {
            updateMusics(updateMusics);
        }
        if(!Validator.isNullOrEmpty(deleteMusics)){
            deleteMusics(profile, deleteMusics);
        }
    }

    private void createMusics(final Profile profile, final List<MusicCreateRequest> requests) {
        List<Music> musics = requests.stream()
                .map(request -> Music.of(
                        request.getTitle(),
                        request.getArtist(),
                        request.getUrl()))
                .toList();

        musicRepository.saveAll(musics);
        profile.getMusics().addAll(musics);
    }

    private void updateMusics(final List<MusicUpdateRequest> requests) {
         for(MusicUpdateRequest request : requests) {
            Music music = musicRepository.findById(request.getMusicId()).orElseThrow(MusicException.MusicNotFoundException::new);

            music.updateMusic(request);
        }
    }

    private void deleteMusics(final Profile profile, final List<MusicDeleteRequest> requests) {
        for(MusicDeleteRequest request : requests) {
            Music music = musicRepository.findById(request.getMusicId()).orElseThrow(MusicException.MusicNotFoundException::new);

            profile.getMusics().remove(music);
            musicRepository.deleteById(music.getId());
        }
    }
}