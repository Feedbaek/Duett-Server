package Dino.Duett.domain.tag.repository.projection;


import Dino.Duett.domain.tag.enums.TagState;

public interface ProfileTagProjection {
    String getName();
    TagState getState();
}
