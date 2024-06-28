package Dino.Duett.domain.tag.entity;

import Dino.Duett.domain.tag.enums.TagType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tag")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long id;

    @Column(nullable = false, length = 20)
    private String name;

    @Enumerated(EnumType.STRING)
    private TagType type;

    public Tag(Long id, String name, TagType type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public static Tag of(String name, TagType type) {
        return new Tag(
                null,
                name,
                type
        );
    }
}
