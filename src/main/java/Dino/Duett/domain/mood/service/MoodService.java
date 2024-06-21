package Dino.Duett.domain.mood.service;

import Dino.Duett.domain.image.entity.Image;
import Dino.Duett.domain.image.service.ImageService;
import Dino.Duett.domain.mood.dto.request.MoodRequest;
import Dino.Duett.domain.mood.dto.response.MoodResponse;
import Dino.Duett.domain.mood.entity.Mood;
import Dino.Duett.domain.mood.repository.MoodRepository;
import Dino.Duett.domain.profile.entity.Profile;
import Dino.Duett.global.util.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class MoodService {
    private final MoodRepository moodRepository;
    private final ImageService imageService;

    @Transactional
    public MoodResponse getMood(final Profile profile) {
        Mood mood = profile.getMood();

        return MoodResponse.of(
                mood.getTitle(),
                mood.getArtist(),
                mood.getMoodImage() != null ? imageService.getUrl(mood.getMoodImage()) : null
        );
    }

    @Transactional
    public void changeMood(final Profile profile, final MoodRequest moodRequest) {
        Mood mood = profile.getMood();
        if(mood == null) {
            createMood(profile, moodRequest);
            return;
        }
        updateMood(mood, moodRequest);
    }

    private void createMood(final Profile profile, final MoodRequest moodRequest) {
        Mood mood = Mood.of(
                moodRequest.getTitle(),
                moodRequest.getArtist(),
                imageService.saveImage(moodRequest.getMoodImage())
        );
        moodRepository.save(mood);
        profile.updateMood(mood);
    }

    private void updateMood(final Mood mood, final MoodRequest moodRequest) {
        Image image = mood.getMoodImage();
        MultipartFile imageFile = moodRequest.getMoodImage();

        if (!Validator.isNullOrEmpty(imageFile)) {
            if (image != null) {
                if (moodRequest.getIsDeleteImage() == null || moodRequest.getIsDeleteImage()) {
                    imageService.deleteImage(mood.getMoodImage().getId());
                }
            }
            image = imageService.saveImage(imageFile);
        }

        mood.updateMood(
                moodRequest.getTitle(),
                moodRequest.getArtist(),
                image
        );
    }
}
