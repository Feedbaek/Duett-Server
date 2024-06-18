package Dino.Duett.domain.tag.repository;

import Dino.Duett.domain.tag.entity.ProfileTag;
import Dino.Duett.domain.tag.enums.TagState;
import Dino.Duett.domain.tag.enums.TagType;
import Dino.Duett.domain.tag.repository.projection.ProfileTagProjection;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileTagRepository extends JpaRepository<ProfileTag, Long> {
    List<ProfileTag> findByState(TagType state);

    @Query("SELECT t.name as name, pt.state as state " +
            "FROM ProfileTag pt " +
            "JOIN pt.tag t " +
            "WHERE pt.profile.id = :profileId AND t.type = :type")
    List<ProfileTagProjection> findAllByProfileIdAndTagType(
            @Param("profileId") Long profileId,
            @Param("type") TagType type);

    @Query("SELECT t.name as name, " +
            "COALESCE(pt.state, 'NONE') as state " +
            "FROM Tag t " +
            "LEFT JOIN ProfileTag pt ON t.id = pt.tag.id AND pt.profile.id = :profileId " +
            "WHERE t.type = :type")
    List<ProfileTagProjection> findAllByProfileIdAndTagTypeWithAllTag(
            @Param("profileId") Long profileId,
            @Param("type") TagType type);

    @Query("SELECT COUNT(pt) " +
            "FROM ProfileTag pt " +
            "JOIN pt.tag t " +
            "WHERE pt.profile.id = :profileId AND t.type = :type")
    Long countByProfileIdAndTagType(
            @Param("profileId") Long profileId,
            @Param("type") TagType type);

    @Query("SELECT t.name as tagName, pt.state as state " +
            "FROM ProfileTag pt " +
            "JOIN pt.tag t " +
            "WHERE pt.profile.id = :profileId AND pt.state = :state")
    List<ProfileTagProjection> findAllByProfileIdAndTagState(
            @Param("profileId") Long profileId,
            @Param("state") TagState state);
}
