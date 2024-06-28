package Dino.Duett.domain.profile.entity;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Location {
    private double latitude;
    private double longitude;

    public static Location of(double latitude,
                              double longitude) {
        return new Location(
                latitude,
                longitude);
    }
}
