package Dino.Duett.domain.profile.enums;

import Dino.Duett.domain.profile.exception.ProfileException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum MbtiType {
    ISFP,
    ISFJ,
    ISTP,
    ISTJ,
    INFP,
    INFJ,
    INTP,
    INTJ,
    ESFP,
    ESFJ,
    ESTP,
    ESTJ,
    ENFP,
    ENFJ,
    ENTP,
    ENTJ,
    NONE;
}
