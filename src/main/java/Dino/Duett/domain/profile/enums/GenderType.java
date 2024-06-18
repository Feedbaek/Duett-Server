package Dino.Duett.domain.profile.enums;

import Dino.Duett.domain.profile.exception.ProfileException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum GenderType {
    MAN("남성"),
    WOMAN("여성"),
    NONE("NONE");

    private final String value;

    public GenderType getOppositeGender() {
        switch (this) {
            case MAN:
                return WOMAN;
            case WOMAN:
                return MAN;
            default:
                return NONE;
        }
    }
}
