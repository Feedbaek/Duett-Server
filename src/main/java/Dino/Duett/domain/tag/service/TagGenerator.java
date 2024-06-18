package Dino.Duett.domain.tag.service;

import Dino.Duett.domain.tag.entity.Tag;
import Dino.Duett.domain.tag.enums.TagType;
import Dino.Duett.domain.tag.repository.TagRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TagGenerator {
    private final TagRepository tagRepository;

    @PostConstruct
    protected void init() {
        for (TagType type : TagType.values()) {
            for (String name : type.getNames()) {
                if (!tagRepository.existsByNameAndType(name, type)) {
                    tagRepository.save(Tag.of(name, type)
                    );
                }
            }
        }
    }
}
