package Dino.Duett.domain.profile.repository;

import Dino.Duett.domain.profile.entity.Profile;
import Dino.Duett.domain.profile.enums.GenderType;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long>{
    @Query("SELECT u FROM Profile u WHERE " +
            "u.gender = :oppositeGender AND " +
            "u.isProfileComplete = true AND " +
            "(6371 * acos(cos(radians(:latitude)) * cos(radians(u.location.latitude)) * " +
            "cos(radians(u.location.longitude) - radians(:longitude)) + " +
            "sin(radians(:latitude)) * sin(radians(u.location.latitude)))) < :radius AND " +
            "(:excludedProfileIds IS NULL OR u.id NOT IN :excludedProfileIds)" +
            "ORDER BY RANDOM()")
    List<Profile> findAllUsersWithinRadius(
            @Param("latitude") double latitude,
            @Param("longitude") double longitude,
            @Param("radius") double radius,
            @Param("oppositeGender") GenderType oppositeGender,
            @Param("excludedProfileIds") List<Long> excludedProfileIds,
            PageRequest pageRequest
    );

    boolean existsByName(String name);
}
