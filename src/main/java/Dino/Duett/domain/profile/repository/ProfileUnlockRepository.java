package Dino.Duett.domain.profile.repository;

import Dino.Duett.domain.profile.entity.Profile;
import Dino.Duett.domain.profile.entity.ProfileUnlock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileUnlockRepository extends JpaRepository<ProfileUnlock, Long> {
    Page<ProfileUnlock> findAllByViewerProfile(Profile viewerProfile, Pageable pageable);
}
