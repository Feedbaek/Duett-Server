package Dino.Duett.domain.tag.enums;

import Dino.Duett.domain.tag.exception.TagException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum TagState {
    NONE, STANDARD, FEATURED;
}
